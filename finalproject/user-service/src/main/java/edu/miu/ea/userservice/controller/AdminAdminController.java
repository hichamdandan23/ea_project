package edu.miu.ea.userservice.controller;

import edu.miu.ea.commons.controller.BaseReadWriteController;
import edu.miu.ea.contracts.Code;
import edu.miu.ea.contracts.Response;
import edu.miu.ea.contracts.user.AdminCreateRequest;
import edu.miu.ea.contracts.user.AdminResponse;
import edu.miu.ea.contracts.user.AgentResponse;
import edu.miu.ea.contracts.user.UserCreateRequest;
import edu.miu.ea.userservice.domain.Admin;
import edu.miu.ea.userservice.domain.User;
import edu.miu.ea.userservice.service.AdminService;
import edu.miu.ea.userservice.service.ItemType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.EnumMap;

@RestController
@RequestMapping("/admin/admin")
public class AdminAdminController extends BaseReadWriteController<AdminResponse, Admin, Long> {
    @Autowired
    private AdminService adminService;

    @PostMapping("/create")
    public Response create(AdminCreateRequest adminCreateRequest) {
        if(adminCreateRequest.getEmail() == null || adminCreateRequest.getPassword() == null) {
            return new Response(Code.ParamError, "Param error");
        }

        Admin admin = new Admin();
        admin.setEmail(adminCreateRequest.getEmail());
        admin.setPassword(adminCreateRequest.getPassword());
        admin.setCreatedAt(LocalDateTime.now());

        EnumMap result = adminService.create(admin);
        Code code = (Code) result.get(ItemType.Code);
        String msg = (String) result.get(ItemType.Msg);
        if(code != Code.Success) {
            return new Response(code, msg);
        }

        return new Response(Code.Success, "");
    }
}
