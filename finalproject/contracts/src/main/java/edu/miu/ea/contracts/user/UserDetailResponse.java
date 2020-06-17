package edu.miu.ea.contracts.user;

import edu.miu.ea.contracts.Response;

import java.util.List;

public class UserDetailResponse extends Response {

    private List<UserResponse> userResponses;

    public List<UserResponse> getUserResponses() {
        return userResponses;
    }

    public void setUserResponses(List<UserResponse> userResponses) {
        this.userResponses = userResponses;
    }

    public UserDetailResponse(List<UserResponse> userResponses) {
        this.userResponses = userResponses;
    }

    public UserDetailResponse(){

    }
}
