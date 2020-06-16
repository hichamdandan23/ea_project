package edu.miu.ea.userservice.controller;

import edu.miu.ea.contracts.Code;
import edu.miu.ea.contracts.user.AdminAuthRequest;
import edu.miu.ea.contracts.user.AdminAuthResponse;
import edu.miu.ea.contracts.user.AdminVerifyRequest;
import edu.miu.ea.contracts.user.AdminVerifyResponse;
import edu.miu.ea.userservice.domain.Admin;
import edu.miu.ea.userservice.service.AdminService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/administrator")
public class AdminController {
    @Autowired
    private AdminService adminService;
    private static final Log logger = LogFactory.getLog(UserController.class);


    @PostMapping("/token")
    public AdminAuthResponse token(AdminAuthRequest request) {
        AdminAuthResponse adminAuthResponse = new AdminAuthResponse(Code.Success, "", "");

        Admin admin = adminService.getAdminByEmail(request.getEmail());
        if (admin == null) {
            adminAuthResponse.setCode(Code.NotExist);
            adminAuthResponse.setMsg("User does not exist");
            return adminAuthResponse;
        }

        if (!admin.getPassword().equals(request.getPassword())) {
            adminAuthResponse.setCode(Code.AuthFailed);
            adminAuthResponse.setMsg("User auth failed");
            return adminAuthResponse;
        }

        String token = adminService.generateToken(admin);
        if (token.equals("")) {
            adminAuthResponse.setCode(Code.SystemError);
            adminAuthResponse.setMsg("System error");
            return adminAuthResponse;
        }


        adminAuthResponse.setToken(token);
        return adminAuthResponse;
    }

    @PostMapping("/verify")
    public AdminVerifyResponse verify(AdminVerifyRequest request) {
        AdminVerifyResponse adminVerifyResponse = new AdminVerifyResponse(
                Code.Success, "", 0L, "", null);

        if (request.getToken() == null || request.getToken().equals("")) {
            adminVerifyResponse.setCode(Code.ParamError);
            adminVerifyResponse.setMsg("Verify failed");
            return adminVerifyResponse;
        }

        Admin user = adminService.verifyToken(request.getToken());
        if (user == null) {
            adminVerifyResponse.setCode(Code.AuthFailed);
            adminVerifyResponse.setMsg("Verify failed");
            return adminVerifyResponse;
        }

        adminVerifyResponse.setId(user.getId());
        adminVerifyResponse.setEmail(user.getEmail());
        adminVerifyResponse.setCreatedAt(user.getCreatedAt());

        return adminVerifyResponse;
    }
}
