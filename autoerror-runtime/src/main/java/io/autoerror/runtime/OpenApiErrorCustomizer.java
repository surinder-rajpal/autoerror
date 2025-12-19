package io.autoerror.runtime;

@Component
public class OpenApiErrorCustomizer implements OperationCustomizer{
    public Operation customize(Operation o,HandlerMethod h){
        o.getResponses().addApiResponse("400",new ApiResponse());
        o.getResponses().addApiResponse("404",new ApiResponse());
        return o;
    }
}

