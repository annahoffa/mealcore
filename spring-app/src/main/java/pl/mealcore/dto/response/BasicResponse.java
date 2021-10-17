package pl.mealcore.dto.response;

import lombok.Data;

@Data
public class BasicResponse {
    private String message;
    private boolean success;

    public BasicResponse withMessage(String message) {
        this.message = message;
        return this;
    }

    public BasicResponse withSuccess(boolean success) {
        this.success = success;
        return this;
    }
}
