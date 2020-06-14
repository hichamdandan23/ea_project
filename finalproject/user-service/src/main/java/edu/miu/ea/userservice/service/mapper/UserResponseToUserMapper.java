package edu.miu.ea.userservice.service.mapper;

import edu.miu.ea.commons.service.mapper.BaseMapper;
import edu.miu.ea.contracts.user.UserResponse;
import edu.miu.ea.userservice.domain.User;
import edu.miu.ea.userservice.service.UserService;
import ma.glasnost.orika.MapperFactory;
import org.springframework.stereotype.Component;

@Component
public class UserResponseToUserMapper extends BaseMapper<UserResponse, User> {
    public UserResponseToUserMapper(MapperFactory mapperFactory) {
        super(mapperFactory, UserResponse.class, User.class);
    }
}
