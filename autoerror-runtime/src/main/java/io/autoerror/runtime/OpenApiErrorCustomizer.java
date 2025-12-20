package io.autoerror.runtime;

import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

@Component
public class OpenApiErrorCustomizer implements OperationCustomizer {
    public Operation customize(Operation o, HandlerMethod h){
        o.getResponses().addApiResponse("400",new ApiResponse());
        o.getResponses().addApiResponse("404",new ApiResponse());
        return o;
    }
}

