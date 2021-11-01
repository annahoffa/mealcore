package pl.mealcore.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BasicResponse {
    protected String message;
    protected boolean success;

    public BasicResponse withMessage(String message) {
        this.message = message;
        return this;
    }

    public BasicResponse withSuccess(boolean success) {
        this.success = success;
        return this;
    }
}
