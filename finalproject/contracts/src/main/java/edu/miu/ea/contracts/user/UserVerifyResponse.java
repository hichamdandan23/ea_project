package edu.miu.ea.contracts.user;

import edu.miu.ea.contracts.Code;
import edu.miu.ea.contracts.Response;

import java.time.LocalDateTime;

public class UserVerifyResponse extends Response {
    public UserVerifyResponse() {
    }

    public UserVerifyResponse(Code code, String msg, Long id, String email, LocalDateTime disabledAt, LocalDateTime createdAt) {
        super(code, msg);
        this.id = id;
        this.email = email;
        this.disabledAt = disabledAt;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getDisabledAt() {
        return disabledAt;
    }

    public void setDisabledAt(LocalDateTime disabledAt) {
        this.disabledAt = disabledAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    private Long id;
    private String email;
    private LocalDateTime disabledAt;
    private LocalDateTime createdAt;
}
