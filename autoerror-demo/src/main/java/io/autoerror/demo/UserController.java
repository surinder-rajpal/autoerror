package io.autoerror.demo;

import io.autoerror.annotations.UseApiErrors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@UseApiErrors(UserErrors.class)
public class UserController {
    @GetMapping("/users/{id}")
    public String get(@PathVariable("id") int id){
        throw new UserNotFoundException();
    }
}

