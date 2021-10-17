package pl.mealcore.error;

import lombok.NoArgsConstructor;

import java.io.Serial;

@NoArgsConstructor
public final class UserInvalidPersonalDataException extends Exception {

    @Serial
    private static final long serialVersionUID = 7451323554787985612L;

    public UserInvalidPersonalDataException(final String message) {
        super(message);
    }
}
