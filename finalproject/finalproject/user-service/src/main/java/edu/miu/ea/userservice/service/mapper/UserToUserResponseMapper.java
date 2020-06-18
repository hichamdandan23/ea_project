package edu.miu.ea.userservice.service.mapper;

import edu.miu.ea.commons.service.mapper.BaseMapper;
import edu.miu.ea.contracts.user.UserResponse;
import edu.miu.ea.userservice.domain.User;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserToUserResponseMapper extends BaseMapper<User, UserResponse> {
    @Autowired
    public UserToUserResponseMapper(MapperFactory mapperFactory) {
        super(mapperFactory, User.class, UserResponse.class);
    }
}
