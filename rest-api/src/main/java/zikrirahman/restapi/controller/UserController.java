package zikrirahman.restapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import zikrirahman.restapi.entity.User;
import zikrirahman.restapi.modelDTO.request.UserRegisterRequest;
import zikrirahman.restapi.modelDTO.request.UserUpdateRequest;
import zikrirahman.restapi.modelDTO.response.UserResponse;
import zikrirahman.restapi.modelDTO.response.WebResponse;
import zikrirahman.restapi.service.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/api/users")
    public WebResponse<String> register(@RequestBody UserRegisterRequest request) {
        
        userService.register(request);
        
        return WebResponse.<String>builder().data("Sukses").build();
    }

    @GetMapping("/api/users/current")
    public WebResponse<UserResponse> get(User user) {
        
        UserResponse userResponse = userService.get(user);
        
        return WebResponse.<UserResponse>builder().data(userResponse).build();
    }

    @PatchMapping("/api/users/current")
    public WebResponse<UserResponse> update(User user, @RequestBody UserUpdateRequest request){
        
        UserResponse userResponse = userService.update(user, request);
        
        return WebResponse.<UserResponse>builder().data(userResponse).build();
    }
}