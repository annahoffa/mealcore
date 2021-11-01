package pl.mealcore.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserNoteResponse extends BasicResponse{
    private String text;
    private String date;

    @Override
    public UserNoteResponse withSuccess(boolean success) {
        this.success = success;
        return this;
    }
}
