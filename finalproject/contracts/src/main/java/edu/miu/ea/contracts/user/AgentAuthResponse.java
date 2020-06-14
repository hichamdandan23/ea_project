package edu.miu.ea.contracts.user;

import edu.miu.ea.contracts.Code;
import edu.miu.ea.contracts.Response;

public class AgentAuthResponse extends Response {
    public AgentAuthResponse(Code code, String msg, String token) {
        super(code, msg);
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String token;
}
