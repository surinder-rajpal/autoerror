package io.autoerror.runtime;

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
