package edu.miu.ea.userservice.service.mapper;

import edu.miu.ea.commons.service.mapper.BaseMapper;
import edu.miu.ea.contracts.user.AdminResponse;
import edu.miu.ea.contracts.user.UserResponse;
import edu.miu.ea.userservice.domain.Admin;
import edu.miu.ea.userservice.domain.User;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdminToAdminResponseMapper extends BaseMapper<Admin, AdminResponse>  {
    @Autowired
    public AdminToAdminResponseMapper(MapperFactory mapperFactory) {
        super(mapperFactory, Admin.class, AdminResponse.class);
    }
}
