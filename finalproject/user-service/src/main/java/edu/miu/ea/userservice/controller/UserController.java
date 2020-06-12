package edu.miu.ea.userservice.controller;

import edu.miu.ea.commons.contracts.*;
import edu.miu.ea.userservice.domain.User;
import edu.miu.ea.userservice.service.ItemType;
import edu.miu.ea.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.EnumMap;

@RestController
public class UserController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserRegisterResponse register(UserRegisterRequest request) {
        UserRegisterResponse userRegisterResponse = new UserRegisterResponse(Code.Success, "", "");
        // validate..
        if(request.getEmail() == null || request.getEmail().length() > 50) {
            userRegisterResponse.setCode(Code.ParamError);
            userRegisterResponse.setMsg("Parameter error");
            return userRegisterResponse;
        }


        EnumMap<ItemType, Object> result = userService.createUser(request.getEmail(), request.getPassword());
        Code code = (Code) result.get(ItemType.Code);
        String msg = (String) result.get(ItemType.Msg);
        User user = (User) result.get(ItemType.User);

        if(code == Code.Success) {
            userRegisterResponse.setToken("Test token");
        }

        userRegisterResponse.setCode(code);
        userRegisterResponse.setMsg(msg);
        return userRegisterResponse;
    }

    @PostMapping("/auth")
    public UserAuthResponse token(UserAuthRequest request) {
        UserAuthResponse userAuthResponse = new UserAuthResponse(Code.Success, "", "");

        User user = userService.getUserByEmail(request.getEmail());
        if(user == null) {
            userAuthResponse.setCode(Code.NotExist);
            userAuthResponse.setMsg("User does not exist");
            return userAuthResponse;
        }

        if(!user.getPassword().equals(request.getPassword())) {
            userAuthResponse.setCode(Code.AuthFailed);
            userAuthResponse.setMsg("User auth failed");
            return userAuthResponse;
        }

        String token = userService.generateToken(user);
        if(token.equals("")) {
            userAuthResponse.setCode(Code.SystemError);
            userAuthResponse.setMsg("System error");
            return userAuthResponse;
        }


        userAuthResponse.setToken(token);
        return userAuthResponse;
    }

    @PostMapping("/verify")
    public UserVerifyResponse verify(UserVerifyRequest request) {
        UserVerifyResponse userVerifyResponse = new UserVerifyResponse(
                Code.Success, "", 0L, "", null, null);

        if(request.getToken() == null || request.getToken().equals("")) {
            userVerifyResponse.setCode(Code.ParamError);
            userVerifyResponse.setMsg("Verify failed");
            return userVerifyResponse;
        }

        User user = userService.verifyToken(request.getToken());
        if(user == null) {
            userVerifyResponse.setCode(Code.AuthFailed);
            userVerifyResponse.setMsg("Verify failed");
            return userVerifyResponse;
        }

        userVerifyResponse.setId(user.getId());
        userVerifyResponse.setEmail(user.getEmail());
        userVerifyResponse.setCreatedAt(user.getCreatedAt());
        userVerifyResponse.setDisabledAt(user.getDisabledAt());

        return userVerifyResponse;
    }

    @PostMapping("/edit")
    public UserEditProfileResponse edit(UserEditProfileRequest request) {
        boolean result = userService.editProfile(request.getCompany(), request.getWebsite());

        UserEditProfileResponse userEditProfileResponse = new UserEditProfileResponse(Code.Success, "");

        return userEditProfileResponse;
    }
}
