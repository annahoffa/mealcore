package pl.mealcore.error;

import lombok.NoArgsConstructor;

import java.io.Serial;

@NoArgsConstructor
public final class UserInvalidCredentialsException extends Exception {

    @Serial
    private static final long serialVersionUID = 2586473578564762592L;

    public UserInvalidCredentialsException(final String message) {
        super(message);
    }
}
