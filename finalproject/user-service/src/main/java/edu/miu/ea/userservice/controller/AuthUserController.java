package edu.miu.ea.userservice.controller;

import edu.miu.ea.contracts.*;
import edu.miu.ea.contracts.user.*;
import edu.miu.ea.userservice.domain.User;
import edu.miu.ea.userservice.service.ItemType;
import edu.miu.ea.userservice.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.EnumMap;

@RestController
@RequestMapping("/passenger")
public class AuthUserController {
    private UserService userService;
    private static final Log logger = LogFactory.getLog(AuthUserController.class);

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserRegisterResponse register(UserRegisterRequest request) {
        UserRegisterResponse userRegisterResponse = new UserRegisterResponse(Code.Success, "", "");
        // validate..
        if (request.getEmail() == null || request.getEmail().length() > 50) {
            userRegisterResponse.setCode(Code.ParamError);
            userRegisterResponse.setMsg("Parameter error");
            return userRegisterResponse;
        }


        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        EnumMap<ItemType, Object> result = userService.create(user);

        Code code = (Code) result.get(ItemType.Code);
        String msg = (String) result.get(ItemType.Msg);
        user = (User) result.get(ItemType.User);

        if (code == Code.Success) {
            userRegisterResponse.setToken("Test token");
        }

        userRegisterResponse.setCode(code);
        userRegisterResponse.setMsg(msg);
        return userRegisterResponse;
    }

    @PostMapping("/token")
    public UserAuthResponse token(UserAuthRequest request) {
        UserAuthResponse userAuthResponse = new UserAuthResponse(Code.Success, "", "");

        User user = userService.getUserByEmail(request.getEmail());
        if (user == null) {
            userAuthResponse.setCode(Code.NotExist);
            userAuthResponse.setMsg("User does not exist");
            return userAuthResponse;
        }

        if (!user.getPassword().equals(request.getPassword())) {
            userAuthResponse.setCode(Code.AuthFailed);
            userAuthResponse.setMsg("User auth failed");
            return userAuthResponse;
        }

        String token = userService.generateToken(user);
        if (token.equals("")) {
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

        if (request.getToken() == null || request.getToken().equals("")) {
            userVerifyResponse.setCode(Code.ParamError);
            userVerifyResponse.setMsg("Verify failed");
            return userVerifyResponse;
        }

        User user = userService.verifyToken(request.getToken());
        if (user == null) {
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
    public UserEditProfileResponse edit(UserEditProfileRequest request, @RequestHeader(value = "USER_ID", defaultValue = "0") String userIdStr) {
        Long userId = Long.parseLong(userIdStr);

        logger.info("/edit userId=" + userId);

        boolean result = userService.editProfile(request.getCompany(), request.getWebsite());

        UserEditProfileResponse userEditProfileResponse = new UserEditProfileResponse(Code.Success, "");

        return userEditProfileResponse;
    }
}
