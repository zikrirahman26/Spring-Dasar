package zikrirahman.restapi.service;

import zikrirahman.restapi.entity.User;
import zikrirahman.restapi.modelDTO.request.UserRegisterRequest;
import zikrirahman.restapi.modelDTO.request.UserUpdateRequest;
import zikrirahman.restapi.modelDTO.response.UserResponse;

public interface UserService {
    
    void register(UserRegisterRequest request);

    UserResponse get(User user);

    UserResponse update(User user, UserUpdateRequest request);
}
