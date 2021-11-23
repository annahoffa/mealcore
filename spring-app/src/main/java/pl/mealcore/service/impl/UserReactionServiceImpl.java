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
import pl.mealcore.service.UserReactionService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserReactionServiceImpl implements UserReactionService {
    private final UserReactionRepository userReactionRepository;

    @Override
    public void addEditUserReaction(@NonNull User user, @NonNull ProductCategory category, @NonNull Integer value, Date date) {
        date = DateHelper.getDateWithoutTime(date, new Date());
        UserReaction reaction = userReactionRepository.findByUserIdAndDateAndCategory(user.getId(), date, category)
                .map(UserReaction::new)
                .orElse(UserReaction.builder()
                        .user(user)
                        .category(category)
                        .date(date)
                        .build());
        reaction.setValue(value);
        userReactionRepository.save(reaction.toEntity());
    }

    @Override
    public List<UserReaction> getReactions(@NonNull User user, Date date) {
        date = DateHelper.getDateWithoutTime(date, new Date());
        return userReactionRepository.findAllByUserIdAndDate(user.getId(), date).stream()
                .map(UserReaction::new)
                .collect(Collectors.toList());
    }
}
