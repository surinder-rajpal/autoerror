package io.autoerror.demo;

import io.autoerror.annotations.ApiError;
import io.autoerror.annotations.ApiErrors;

@ApiErrors({
        @ApiError(
                code="USER_NOT_FOUND",
                httpStatus=404,
                exception=UserNotFoundException.class,
                message="User not found"
        )
})
public interface UserErrors {}

