package zikrirahman.restapi.service;

import zikrirahman.restapi.entity.User;
import zikrirahman.restapi.modelDTO.request.UserLoginRequest;
import zikrirahman.restapi.modelDTO.response.TokenResponse;

public interface AuthService {
    
    TokenResponse login(UserLoginRequest request);

    void logout(User user);
}
