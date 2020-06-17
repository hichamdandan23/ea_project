package miu.edu.ea.airlineservice.service.request;

import javax.validation.constraints.NotEmpty;

public class AirlineRequest {
    @NotEmpty
    private String code;
    @NotEmpty
    private String name;
    private String history;

    public AirlineRequest() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }
}
