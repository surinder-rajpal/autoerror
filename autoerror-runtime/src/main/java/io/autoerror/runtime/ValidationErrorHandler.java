package io.autoerror.runtime;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.Map;

public final class ValidationErrorHandler{
    public static ResponseEntity<ApiErrorResponse>
    handle(MethodArgumentNotValidException ex){
        Map<String,String> f=new HashMap<>();
        for(FieldError e:ex.getBindingResult().getFieldErrors())
            f.put(e.getField(),e.getDefaultMessage());
        return ResponseEntity.badRequest()
                .body(new ApiErrorResponse("INVALID_REQUEST","Validation failed",f));
    }
}
