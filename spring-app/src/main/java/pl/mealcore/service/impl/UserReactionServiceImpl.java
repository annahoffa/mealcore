package pl.mealcore.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mealcore.dao.UserReactionRepository;
import pl.mealcore.dto.account.User;
import pl.mealcore.dto.account.UserReaction;
import pl.mealcore.helper.DateHelper;
import pl.mealcore.model.product.ProductCategory;
import pl.mealcore.model.user.additionalData.UserReactionEntity;
import pl.mealcore.service.UserReactionService;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Service
@Transactional
@RequiredArgsConstructor
public class UserReactionServiceImpl implements UserReactionService {
    private final UserReactionRepository userReactionRepository;

    @Override
    public boolean addUserReaction(@NonNull User user, @NonNull ProductCategory category, @NonNull Integer value, Date date) {
        date = DateHelper.getDateWithoutTime(date, new Date());
        Optional<UserReactionEntity> found = userReactionRepository.findByUserIdAndDateAndCategory(user.getId(), date, category);
        if (found.isEmpty()) {
            userReactionRepository.save(UserReaction.builder()
                    .user(user)
                    .category(category)
                    .value(value)
                    .date(date)
                    .build().toEntity());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean editUserReaction(@NonNull User user, @NonNull ProductCategory category, @NonNull Integer value, Date date) {
        date = DateHelper.getDateWithoutTime(date, new Date());
        UserReactionEntity found = userReactionRepository.findByUserIdAndDateAndCategory(user.getId(), date, category)
                .orElse(null);
        if (nonNull(found)) {
            found.setValue(value);
            userReactionRepository.save(found);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<UserReaction> getReactions(@NonNull User user, Date date) {
        date = DateHelper.getDateWithoutTime(date, new Date());
        return userReactionRepository.findAllByUserIdAndDate(user.getId(), date).stream()
                .map(UserReaction::new)
                .collect(Collectors.toList());
    }
}
