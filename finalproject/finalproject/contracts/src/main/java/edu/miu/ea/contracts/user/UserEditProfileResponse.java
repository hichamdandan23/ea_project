package edu.miu.ea.contracts.user;

import edu.miu.ea.contracts.Code;
import edu.miu.ea.contracts.Response;

public class UserEditProfileResponse extends Response {
    public UserEditProfileResponse(Code code, String msg) {
        super(code, msg);
    }
}
