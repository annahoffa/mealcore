package pl.mealcore.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mealcore.dao.UserExerciseRepository;
import pl.mealcore.dto.account.User;
import pl.mealcore.dto.account.UserExercise;
import pl.mealcore.dto.response.UserExercisesResponse;
import pl.mealcore.dto.sport.Sport;
import pl.mealcore.helper.DateHelper;
import pl.mealcore.model.user.additionalData.UserExerciseEntity;
import pl.mealcore.service.UserExerciseService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.util.Objects.nonNull;

@Service
@Transactional
@RequiredArgsConstructor
public class UserExerciseServiceImpl implements UserExerciseService {
    private final UserExerciseRepository userExerciseRepository;

    @Override
    public void addUserExercise(@NonNull User user, @NonNull Long sportId, @NonNull Double duration, Date date) {
        date = DateHelper.getDateWithoutTime(date, new Date());
        UserExercise toSave = userExerciseRepository.findByUserIdAndSportIdAndDate(user.getId(), sportId, date)
                .map(UserExercise::new)
                .orElse(new UserExercise(user, new Sport(sportId), date, 0d));
        toSave.addDuration(duration);
        userExerciseRepository.save(toSave.toEntity());
    }

    @Override
    public boolean editUserExercise(@NonNull User user, @NonNull Long sportId, @NonNull Double duration, Date date) {
        date = DateHelper.getDateWithoutTime(date, new Date());
        UserExerciseEntity userExercise = userExerciseRepository.findByUserIdAndSportIdAndDate(user.getId(), sportId, date).orElse(null);
        if (nonNull(userExercise)) {
            userExercise.setDuration(duration);
            userExerciseRepository.save(userExercise);
            return true;
        }
        return false;
    }

    @Override
    public void deleteUserExercise(@NonNull User user, @NonNull Long sportId, Date date) {
        userExerciseRepository.findByUserIdAndSportIdAndDate(user.getId(), sportId, DateHelper.getDateWithoutTime(date, new Date()))
                .ifPresent(userExerciseRepository::delete);
    }

    @Override
    public UserExercisesResponse getExercisesForUser(@NonNull User user, Date date) {
        date = DateHelper.getDateWithoutTime(date, new Date());
        List<Sport> sports = new ArrayList<>();
        List<UserExerciseEntity> userExercises = userExerciseRepository.findAllByUserIdAndDate(user.getId(), date);
        for (UserExerciseEntity userExercise : userExercises) {
            Sport sport = new Sport(userExercise.getSport());
            sport.setDuration(userExercise.getDuration());
            sport.calculateKcal();
            sports.add(sport);
        }
        return new UserExercisesResponse(sports, DateHelper.format(date));
    }
}
