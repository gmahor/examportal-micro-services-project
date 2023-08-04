package com.examportal.services;

import com.examportal.constant.MessageConstant;
import com.examportal.dtos.SignInDTO;
import com.examportal.dtos.SignUpDTO;
import com.examportal.entities.Role;
import com.examportal.entities.User;
import com.examportal.enums.RoleType;
import com.examportal.repositories.RoleRepository;
import com.examportal.repositories.UserRepository;
import com.examportal.utils.CommonUtil;
import com.examportal.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    private final CommonUtil commonUtil;


    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       ModelMapper modelMapper,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil,
                       CommonUtil commonUtil) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.commonUtil = commonUtil;
    }


    public String signUp(SignUpDTO signUpDTO) {
        User user = modelMapper.map(signUpDTO, User.class);
        user.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findByName(RoleType.USER);
        if (role != null)
            roles.add(role);
        user.setRoles(roles);
        userRepository.save(user);
        log.info("User save successfully");
        return MessageConstant.SIGN_UP_SUCCESS;
    }


    public Object signIn(SignInDTO signInDTO) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        Map<String, Object> signInResponse = new HashMap<>();
        Optional<User> optionalUser = userRepository.getByUsernameOrEmail(signInDTO.getUsername());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getRoles().stream().noneMatch(role -> role.getName().name().equals("ADMIN"))) {
                String matched = commonUtil.isPasswordMatched(signInDTO.getPassword(), user.getPassword());
                if (Strings.isNotBlank(matched)) {
                    return matched;
                }
                String token = jwtUtil.getToken(user.getId(), user.getRoles()
                                .stream()
                                .map(role -> role.getName().name()).collect(Collectors.toList()).get(0)
                        , user.getEmail());
                signInResponse.put("user", user);
                signInResponse.put("token", token);
                return signInResponse;
            } else {
                return MessageConstant.ROLE_NOT_USER;
            }
        }
        return MessageConstant.USER_NOT_FOUND;
    }

    public User getUser(long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElse(null);
    }


}
