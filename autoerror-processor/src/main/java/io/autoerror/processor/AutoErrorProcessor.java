package io.autoerror.processor;

import io.autoerror.annotations.ApiError;
import io.autoerror.annotations.ApiErrors;
import io.autoerror.annotations.UseApiErrors;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.Writer;
import java.util.*;

@SupportedAnnotationTypes({
        "io.autoerror.annotations.ApiErrors",
        "io.autoerror.annotations.UseApiErrors"
})
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class AutoErrorProcessor extends AbstractProcessor {

    record ErrorDef(String code,int status,String ex,String msg){}

    private final Map<String, List<ErrorDef>> registry=new HashMap<>();

    @Override
    public boolean process(Set<? extends TypeElement> a, RoundEnvironment r){
        for(Element e:r.getElementsAnnotatedWith(ApiErrors.class)) collect(e);
        for(Element e:r.getElementsAnnotatedWith(UseApiErrors.class)) generate(e);
        return true;
    }

    String typeFqn(UseApiErrors u) {
        try {
            u.value(); // this always throws
        } catch (MirroredTypeException m) {
            return m.getTypeMirror().toString();
        }
        throw new IllegalStateException();
    }

    void collect(Element e){
        ApiErrors api=e.getAnnotation(ApiErrors.class);
        Set<String> codes=new HashSet<>();
        List<ErrorDef> list=new ArrayList<>();
        for(ApiError x:api.value()){
            if(!codes.add(x.code()))
                error("Duplicate error code "+x.code(),e);
            list.add(new ErrorDef(
                    x.code(),x.httpStatus(),
                    exceptionFqn(x),x.message()
            ));
        }
        registry.put(e.asType().toString(),list);
    }

    String exceptionFqn(ApiError e){
        try{e.exception();}catch(MirroredTypeException m){
            return m.getTypeMirror().toString();
        }
        throw new IllegalStateException();
    }

    void generate(Element e) {
        UseApiErrors u = e.getAnnotation(UseApiErrors.class);

        String errorsType = typeFqn(u);
        List<ErrorDef> errors = registry.get(errorsType);

        if (errors == null) return;

        try {
            String pkg = processingEnv.getElementUtils()
                    .getPackageOf(e)
                    .getQualifiedName()
                    .toString();

            String name = e.getSimpleName() + "ApiErrorsAdvice";

            JavaFileObject f = processingEnv.getFiler()
                    .createSourceFile(pkg + "." + name, e);

            try (Writer w = f.openWriter()) {
                w.write("""
                package %s;

                import org.springframework.web.bind.annotation.*;
                import org.springframework.http.*;
                import io.autoerror.runtime.*;

                @ControllerAdvice(assignableTypes = %s.class)
                public class %s {
                %s
                }
                """.formatted(
                        pkg,
                        e.getSimpleName(),
                        name,
                        handlers(errors)
                ));
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    String handlers(List<ErrorDef> e){
        StringBuilder b=new StringBuilder();
        for(ErrorDef d:e){
            b.append("""
    @ExceptionHandler(%s.class)
    public ResponseEntity<ApiErrorResponse> handle%s(%s ex){
     return ResponseEntity.status(%d)
      .body(ApiErrorResponse.simple("%s","%s"));
    }
    """.formatted(d.ex,d.ex.substring(d.ex.lastIndexOf('.')+1),
                    d.ex,d.status,d.code,
                    d.msg.isEmpty()?d.code:d.msg));
        }
        return b.toString();
    }

    void error(String m, Element e){
        processingEnv.getMessager()
                .printMessage(Diagnostic.Kind.ERROR,m,e);
    }
}

