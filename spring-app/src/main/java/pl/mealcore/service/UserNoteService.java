package pl.mealcore.service;

import lombok.NonNull;
import pl.mealcore.dto.account.User;
import pl.mealcore.dto.account.UserNote;

import java.util.Date;

public interface UserNoteService {

    boolean addUserNote(@NonNull User user, @NonNull String text, Date date);

    boolean editUserNote(@NonNull User user, @NonNull String text, Date date);

    UserNote getNote(@NonNull User user, Date date);
}
