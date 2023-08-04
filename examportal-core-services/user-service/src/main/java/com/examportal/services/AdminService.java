package com.examportal.services;

import com.examportal.constant.MessageConstant;
import com.examportal.dtos.SignInDTO;
import com.examportal.entities.User;
import com.examportal.repositories.UserRepository;
import com.examportal.utils.CommonUtil;
import com.examportal.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AdminService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    private final CommonUtil commonUtil;

    @Autowired
    public AdminService(UserRepository userRepository,
                        PasswordEncoder passwordEncoder,
                        JwtUtil jwtUtil,
                        CommonUtil commonUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.commonUtil = commonUtil;
    }

    public Object createAdmin(SignInDTO signInDTO) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        Map<String, Object> signInResponse = new HashMap<>();
        Optional<User> optionalUser = userRepository.getByUsernameOrEmail(signInDTO.getUsername());
        if (optionalUser.isPresent()) {
            User adminUser = optionalUser.get();
            if (adminUser.getRoles().stream().noneMatch(role -> role.getName().name().equals("USER"))) {
                String passwordMatched = commonUtil.isPasswordMatched(signInDTO.getPassword(), adminUser.getPassword());
                if (passwordMatched != null) {
                    return passwordMatched;
                }
                String token = jwtUtil.getToken(adminUser.getId(), adminUser.getRoles()
                                .stream()
                                .map(role -> role.getName().name()).collect(Collectors.toList()).get(0),
                        adminUser.getEmail()
                );
                signInResponse.put("user", adminUser);
                signInResponse.put("token", token);
                return signInResponse;
            } else {
                return MessageConstant.ROLE_NOT_ADMIN;
            }
        }
        return MessageConstant.USER_NOT_FOUND;
    }


}
