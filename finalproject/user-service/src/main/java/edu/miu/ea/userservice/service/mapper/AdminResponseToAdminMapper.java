package edu.miu.ea.userservice.service.mapper;

import edu.miu.ea.commons.service.mapper.BaseMapper;
import edu.miu.ea.contracts.user.AdminResponse;
import edu.miu.ea.contracts.user.UserResponse;
import edu.miu.ea.userservice.domain.Admin;
import edu.miu.ea.userservice.domain.User;
import ma.glasnost.orika.MapperFactory;
import org.springframework.stereotype.Component;

@Component
public class AdminResponseToAdminMapper extends BaseMapper<AdminResponse, Admin> {
    public AdminResponseToAdminMapper(MapperFactory mapperFactory) {
        super(mapperFactory, AdminResponse.class, Admin.class);
    }
}
