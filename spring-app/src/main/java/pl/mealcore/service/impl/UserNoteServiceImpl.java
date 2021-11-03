package pl.mealcore.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mealcore.dao.UserNoteRepository;
import pl.mealcore.dto.account.User;
import pl.mealcore.dto.account.UserNote;
import pl.mealcore.helper.DateHelper;
import pl.mealcore.model.user.additionalData.UserNoteEntity;
import pl.mealcore.service.UserNoteService;

import java.util.Date;
import java.util.Optional;

import static java.util.Objects.nonNull;

@Service
@Transactional
@RequiredArgsConstructor
public class UserNoteServiceImpl implements UserNoteService {
    private final UserNoteRepository userNoteRepository;

    @Override
    public boolean addUserNote(@NonNull User user, @NonNull String text, Date date) {
        date = DateHelper.getDateWithoutTime(date, new Date());
        Optional<UserNoteEntity> found = userNoteRepository.findByUserIdAndDate(user.getId(), date);
        if (found.isEmpty()) {
            userNoteRepository.save(UserNote.builder()
                    .user(user)
                    .text(text)
                    .date(date)
                    .build().toEntity());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean editUserNote(@NonNull User user, @NonNull String text, Date date) {
        date = DateHelper.getDateWithoutTime(date, new Date());
        UserNoteEntity found = userNoteRepository.findByUserIdAndDate(user.getId(), date)
                .orElse(null);
        if (nonNull(found)) {
            found.setText(text);
            userNoteRepository.save(found);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public UserNote getNote(@NonNull User user, Date date) {
        date = DateHelper.getDateWithoutTime(date, new Date());
        return userNoteRepository.findByUserIdAndDate(user.getId(), date)
                .map(UserNote::new)
                .orElse(null);
    }
}
