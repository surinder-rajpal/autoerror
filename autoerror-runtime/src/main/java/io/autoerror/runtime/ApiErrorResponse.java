package io.autoerror.runtime;

import java.util.Map;

public record ApiErrorResponse(
        String code, String message, Map<String,String> fields
){
    public static ApiErrorResponse simple(String c,String m){
        return new ApiErrorResponse(c,m,null);
    }
}
