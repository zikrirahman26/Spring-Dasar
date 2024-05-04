package zikrirahman.restapi.service.impl;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import zikrirahman.restapi.entity.User;
import zikrirahman.restapi.modelDTO.request.UserRegisterRequest;
import zikrirahman.restapi.modelDTO.request.UserUpdateRequest;
import zikrirahman.restapi.modelDTO.response.UserResponse;
import zikrirahman.restapi.repository.UserRepository;
import zikrirahman.restapi.security.BCrypt;
import zikrirahman.restapi.service.UserService;
import zikrirahman.restapi.service.ValidationService;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationService validationService;

    //register
    @Transactional
    @Override
    public void register(UserRegisterRequest request) {
        
        validationService.validate(request);

        if (userRepository.existsById(request.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username sudah terdaftar");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        user.setName(request.getName());
        userRepository.save(user);
    }

    //get user
    @Override
    public UserResponse get(User user) {
        
        return UserResponse.builder()
                .username(user.getUsername())
                .name(user.getName())
                .build();
    }

    //update user
    @Transactional
    @Override
    public UserResponse update(User user, UserUpdateRequest request) {

        validationService.validate(request);

        if (Objects.nonNull(request.getName())) {
            user.setName(request.getName());
        }

        if (Objects.nonNull(request.getPassword())) {
            user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        }
        
        userRepository.save(user);

        return UserResponse.builder()
        .name(user.getName())
        .username(user.getUsername())
        .build();
    }
}
