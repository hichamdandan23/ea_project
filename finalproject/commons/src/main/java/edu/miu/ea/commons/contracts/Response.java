package edu.miu.ea.commons.contracts;

public class Response {
    public Response() {}
    public Response(Code code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Code getCode() {
        return code;
    }

    public void setCode(Code code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private Code code;
    private String msg;
}
