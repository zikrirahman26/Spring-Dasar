package zikrirahman.restapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import zikrirahman.restapi.entity.User;
import zikrirahman.restapi.modelDTO.request.UserLoginRequest;
import zikrirahman.restapi.modelDTO.response.TokenResponse;
import zikrirahman.restapi.modelDTO.response.WebResponse;
import zikrirahman.restapi.service.AuthService;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/api/auth/login")
    public WebResponse<TokenResponse> login(@RequestBody UserLoginRequest request) {
        
        TokenResponse tokenResponse = authService.login(request);
        
        return WebResponse.<TokenResponse>builder().data(tokenResponse).build();
    }

    @DeleteMapping("/api/auth/logout")
    public WebResponse<String> logout(User user) {
        
        authService.logout(user);
        
        return WebResponse.<String>builder().data("OK").build();
    }
}
