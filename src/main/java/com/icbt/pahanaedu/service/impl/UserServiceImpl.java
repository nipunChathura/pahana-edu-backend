package com.icbt.pahanaedu.service.impl;

import com.icbt.pahanaedu.common.Constants;
import com.icbt.pahanaedu.common.LogSupport;
import com.icbt.pahanaedu.common.ResponseCodes;
import com.icbt.pahanaedu.common.ResponseStatus;
import com.icbt.pahanaedu.dto.UserDto;
import com.icbt.pahanaedu.dto.UserManageDto;
import com.icbt.pahanaedu.entity.User;
import com.icbt.pahanaedu.exception.InvalidRequestException;
import com.icbt.pahanaedu.repository.UserRepository;
import com.icbt.pahanaedu.service.UserService;
import com.icbt.pahanaedu.util.Utils;
import com.icbt.pahanaedu.util.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Value("${password.min.length}")
    private String PASSWORD_MIN_LENGTH;

    @Value("${password.max.length}")
    private String PASSWORD_MAX_LENGTH;

    @Value("${password.policy.regex}")
    private String PASSWORD_POLICY_REGEX;

    @Value("${username.min.length}")
    private String USERNAME_MIN_LENGTH;

    @Value("${username.max.length}")
    private String USERNAME_MAX_LENGTH;

    @Value("${username.policy.regex}")
    private String USERNAME_POLICY_REGEX;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserManageDto addUser(UserManageDto userManageDto) {
        log.info(LogSupport.USER_LOG + "starting.", "addUser()", userManageDto.getUserId());

        if (userManageDto.getUserDto() == null) {
            log.error(LogSupport.USER_LOG + "user data is required.", "addUser()", userManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "User data is required");
        }


        UserDto userDto = userManageDto.getUserDto();

        if (userDto.getUsername() == null || userDto.getUsername().isEmpty()) {
            log.error(LogSupport.USER_LOG + "username data is required.", "addUser()", userManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "Username data is required");
        }

        validateUsername(userDto.getUsername(), userManageDto.getUserId());

        if (userDto.getPassword() == null || userDto.getPassword().isEmpty()) {
            log.error(LogSupport.USER_LOG + "username data is required.", "addUser()", userManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "Username data is required");
        }

        validatePassword(userDto.getPassword(), userManageDto.getUserId());

        if (userDto.getRole() == null || userDto.getRole().isEmpty()) {
            log.info(LogSupport.USER_LOG + "set user role default value ", "addUser()", userManageDto.getUserId());
            userDto.setRole(Constants.ROLE + Constants.ROLE_ADMIN);
        } else {
            userDto.setRole(Constants.ROLE + userDto.getRole().toUpperCase());
        }

        if (userDto.getStatus() == null || userDto.getStatus().isEmpty()) {
            log.info(LogSupport.USER_LOG + "set user status default value ", "addUser()", userManageDto.getUserId());
            userDto.setStatus(Constants.USER_INITIATE_STATUS);
        }

        User user = userMapper.toEntity(userDto);
        user.setCreatedBy(userManageDto.getUserId());
        user.setCreatedDatetime(Utils.getCurrentDateByTimeZone(Constants.TIME_ZONE));
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        userRepository.save(user);

        userDto.setUserId(user.getUserId());

        userManageDto.setUserDto(userDto);
        userManageDto.setStatus(ResponseStatus.SUCCESS.getStatus());
        userManageDto.setResponseCode(ResponseCodes.SUCCESS_CODE);
        userManageDto.setResponseMessage("User registered successfully");
        log.info(LogSupport.USER_LOG + "end.", "addUser()", userManageDto.getUserId());
        return userManageDto;
    }

    @Override
    public void validateUsername(String username, Long userId) {
        log.info(LogSupport.USER_LOG + "username validate starting.", "addUser()", userId);
        if (username.length() < Integer.parseInt(USERNAME_MIN_LENGTH)) {
            log.error(LogSupport.USER_LOG + "username = {} min length exceed.", "addUser()", userId, username);
            throw new InvalidRequestException(ResponseCodes.INVALID_USERNAME_MIN_LENGTH_CODE, "Username min length exceed");
        }

        if (username.length() > Integer.parseInt(USERNAME_MAX_LENGTH)) {
            log.error(LogSupport.USER_LOG + "username = {} max length exceed.", "addUser()", userId, username);
            throw new InvalidRequestException(ResponseCodes.INVALID_USERNAME_MAX_LENGTH_CODE, "Username max length exceed");
        }

        if (!username.matches(USERNAME_POLICY_REGEX)) {
            log.error(LogSupport.USER_LOG + "username = {} invalid username policy.", "addUser()", userId, username);
            throw new InvalidRequestException(ResponseCodes.INVALID_USERNAME_POLICY_CODE, "Invalid username policy");
        }

        Optional<User> byUsername = userRepository.findByUsername(username);
        if (byUsername.isPresent()) {
            log.error(LogSupport.USER_LOG + "username = {} username already existing.", "addUser()", userId, username);
            throw new InvalidRequestException(ResponseCodes.USERNAME_ALREADY_EXISTING_CODE, "Username already existing");
        }

        log.info(LogSupport.USER_LOG + "username validate end.", "addUser()", userId);
    }

    @Override
    public void validatePassword(String password, Long userId) {
        log.info(LogSupport.USER_LOG + "password validate starting.", "addUser()", userId);
        if (password.length() < Integer.parseInt(PASSWORD_MIN_LENGTH)) {
            log.error(LogSupport.USER_LOG + "password = {} min length exceed.", "addUser()", userId, password);
            throw new InvalidRequestException(ResponseCodes.INVALID_PASSWORD_MIN_LENGTH_CODE, "Username min length exceed");
        }

        if (password.length() > Integer.parseInt(PASSWORD_MAX_LENGTH)) {
            log.error(LogSupport.USER_LOG + "password = {} max length exceed.", "addUser()", userId, password);
            throw new InvalidRequestException(ResponseCodes.INVALID_PASSWORD_MAX_LENGTH_CODE, "Username max length exceed");
        }

        if (!password.matches(PASSWORD_POLICY_REGEX)) {
            log.error(LogSupport.USER_LOG + "password = {} invalid password policy.", "addUser()", userId, password);
            throw new InvalidRequestException(ResponseCodes.INVALID_PASSWORD_POLICY_CODE, "Invalid password policy");
        }
        log.info(LogSupport.USER_LOG + "password validate end.", "addUser()", userId);
    }

    @Override
    public UserManageDto updateUser(UserManageDto userManageDto) {
        log.info(LogSupport.USER_LOG + "starting.", "updateUser()", userManageDto.getUserId());

        if (userManageDto.getUserDto() == null) {
            log.error(LogSupport.USER_LOG + "user data is required.", "updateUser()", userManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "User data is required");
        }
        UserDto userDto = userManageDto.getUserDto();

        if (userDto.getUserId() == null) {
            log.error(LogSupport.USER_LOG + "userId data is required.", "updateUser()", userManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "userId data is required");
        }

        Optional<User> byId = userRepository.findById(userDto.getUserId());
        if (byId.isEmpty()) {
            log.error(LogSupport.USER_LOG + "invalid userId .", "updateUser()", userManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.INVALID_USER_ID_CODE, "Invalid user id.");
        }

        User user = byId.get();
        if (user.getStatus().equalsIgnoreCase(Constants.USER_DELETE_STATUS)) {
            log.error(LogSupport.USER_LOG + "invalid userId .", "updateUser()", userManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.INVALID_USER_ID_CODE, "Invalid user id.");
        }

        if (user.getRole().equalsIgnoreCase(Constants.ROLE + Constants.ROLE_SYSTEM)) {
            log.error(LogSupport.USER_LOG + "invalid userId .", "getUserById()", userManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.SYSTEM_USER_NOT_EDITING, "System user can't update.");
        }

        user.setStatus(userDto.getStatus());

        userRepository.save(user);

        userManageDto.setUserDto(userDto);
        userManageDto.setStatus(ResponseStatus.SUCCESS.getStatus());
        userManageDto.setResponseCode(ResponseCodes.SUCCESS_CODE);
        userManageDto.setResponseMessage("User updating successfully");
        log.info(LogSupport.USER_LOG + "end.", "updateUser()", userManageDto.getUserId());
        return userManageDto;
    }

    @Override
    public UserManageDto deleteUser(UserManageDto userManageDto) {
        log.info(LogSupport.USER_LOG + "starting.", "deleteUser()", userManageDto.getUserId());

        if (userManageDto.getUserId() == null) {
            log.error(LogSupport.USER_LOG + "userId data is required.", "deleteUser()", null);
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "userId data is required");
        }

        Optional<User> byId = userRepository.findById(userManageDto.getUserId());
        if (byId.isEmpty()) {
            log.error(LogSupport.USER_LOG + "invalid userId .", "deleteUser()", userManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.INVALID_USER_ID_CODE, "Invalid user id.");
        }

        User user = byId.get();

        if (user.getRole().equalsIgnoreCase(Constants.ROLE + Constants.ROLE_SYSTEM)) {
            log.error(LogSupport.USER_LOG + "invalid userId .", "deleteUser()", userManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.SYSTEM_USER_NOT_EDITING, "System user can't delete.");
        }

        user.setUsername(Constants.DELETE_USER_FORMAT + user.getUsername());

        userRepository.save(user);

        userManageDto.setStatus(ResponseStatus.SUCCESS.getStatus());
        userManageDto.setResponseCode(ResponseCodes.SUCCESS_CODE);
        userManageDto.setResponseMessage("User deleting successfully");
        log.info(LogSupport.USER_LOG + "end.", "deleteUser()", userManageDto.getUserId());
        return userManageDto;
    }

    @Override
    public UserManageDto getUserById(UserManageDto userManageDto) {
        log.info(LogSupport.USER_LOG + "starting.", "getUserById()", userManageDto.getUserId());

        if (userManageDto.getUserId() == null) {
            log.error(LogSupport.USER_LOG + "userId data is required.", "getUserById()", null);
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "userId data is required");
        }

        Optional<User> byId = userRepository.findById(userManageDto.getUserId());
        if (byId.isEmpty()) {
            log.error(LogSupport.USER_LOG + "invalid userId .", "getUserById()", userManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.INVALID_USER_ID_CODE, "Invalid user id.");
        }

        User user = byId.get();
        if (user.getStatus().equalsIgnoreCase(Constants.USER_DELETE_STATUS)) {
            log.error(LogSupport.USER_LOG + "invalid userId .", "getUserById()", userManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.INVALID_USER_ID_CODE, "Invalid user id.");
        }

        UserDto userDto = userMapper.toDto(user);

        userManageDto.setUserDto(userDto);
        userManageDto.setStatus(ResponseStatus.SUCCESS.getStatus());
        userManageDto.setResponseCode(ResponseCodes.SUCCESS_CODE);
        userManageDto.setResponseMessage("User getting successfully");
        log.info(LogSupport.USER_LOG + "end.", "getUserById()", userManageDto.getUserId());
        return userManageDto;
    }

    @Override
    public UserManageDto getUserAllUser(UserManageDto userManageDto) {
        log.info(LogSupport.USER_LOG + "starting.", "getUserAllUser()", userManageDto.getUserId());

        List<User> users = userRepository.findAllByStatusNot(Constants.USER_DELETE_STATUS);

        List<UserDto> dtoList = userMapper.toDtoList(users);

        userManageDto.setUserDtos(dtoList);
        userManageDto.setStatus(ResponseStatus.SUCCESS.getStatus());
        userManageDto.setResponseCode(ResponseCodes.SUCCESS_CODE);
        userManageDto.setResponseMessage("User getting successfully");
        log.info(LogSupport.USER_LOG + "end.", "getUserAllUser()", userManageDto.getUserId());
        return userManageDto;
    }

    @Override
    public UserManageDto searchUser(UserManageDto userManageDto) {
        log.info(LogSupport.USER_LOG + "starting.", "searchUser()", userManageDto.getUserId());

        List<User> users = new ArrayList<>();

        if (userManageDto.getUserStatus() == null || userManageDto.getUserStatus().isEmpty()) {
            users = userRepository.findAllByStatusNotAndUsernameContaining(Constants.USER_DELETE_STATUS, userManageDto.getSearchValue());
        } else {
            if (userManageDto.getSearchValue() == null || userManageDto.getSearchValue().isEmpty()) {
                users = userRepository.findAllByStatus(userManageDto.getUserStatus());
            } else {
                users = userRepository.findAllByStatusAndUsernameContaining(userManageDto.getUserStatus(),
                        userManageDto.getSearchValue()).stream()
                        .filter(user -> !"DELETE".equalsIgnoreCase(user.getStatus()))
                        .collect(Collectors.toList());
            }
        }
        List<UserDto> dtoList = new ArrayList<>();
        if (!users.isEmpty()) {
            dtoList = userMapper.toDtoList(users);
        }

        userManageDto.setUserDtos(dtoList);
        userManageDto.setStatus(ResponseStatus.SUCCESS.getStatus());
        userManageDto.setResponseCode(ResponseCodes.SUCCESS_CODE);
        userManageDto.setResponseMessage("User searching successfully");
        log.info(LogSupport.USER_LOG + "end.", "searchUser()", userManageDto.getUserId());
        return userManageDto;
    }
}
