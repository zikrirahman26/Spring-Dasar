package zikrirahman.restapi.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import zikrirahman.restapi.entity.User;
import zikrirahman.restapi.modelDTO.request.UserLoginRequest;
import zikrirahman.restapi.modelDTO.response.TokenResponse;
import zikrirahman.restapi.repository.UserRepository;
import zikrirahman.restapi.security.BCrypt;
import zikrirahman.restapi.service.AuthService;
import zikrirahman.restapi.service.ValidationService;

@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationService validationService;

    //login auth
    @Transactional
    @Override
    public TokenResponse login(UserLoginRequest request) {
        
        validationService.validate(request);

        User user = userRepository.findById(request.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username atau password salah"));

        if (BCrypt.checkpw(request.getPassword(), user.getPassword())) {
            user.setToken(UUID.randomUUID().toString());
            user.setTokenExpiredAt(expiredToken());
            userRepository.save(user);
            return TokenResponse.builder()
                    .token(user.getToken())
                    .expiredAt(user.getTokenExpiredAt())
                    .build();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username atau password salah");
        }
    }

    //logout auth
    @Transactional
    @Override
    public void logout(User user) {
        
        user.setToken(null);
        user.setTokenExpiredAt(null);
        userRepository.save(user);
    }

    //expired 30 hari
    private long expiredToken(){
        return System.currentTimeMillis() + (24 * 60 * 60 * 1000);
    }
}
