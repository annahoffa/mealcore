package pl.mealcore.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mealcore.dao.UserAllergicReactionRepository;
import pl.mealcore.dto.account.User;
import pl.mealcore.dto.account.UserAllergicReaction;
import pl.mealcore.dto.allergicReaction.AllergicReaction;
import pl.mealcore.helper.DateHelper;
import pl.mealcore.model.user.additionalData.UserAllergicReactionEntity;
import pl.mealcore.service.UserAllergicReactionService;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserAllergicReactionServiceImpl implements UserAllergicReactionService {
    private final UserAllergicReactionRepository userAllergicReactionRepository;

    @Override
    public List<AllergicReaction> getAllergicReactionsForUser(@NonNull User user, Date date) {
        return userAllergicReactionRepository.findAllByUserIdAndDate(user.getId(), DateHelper.getDateWithoutTime(date, new Date())).stream()
                .map(UserAllergicReactionEntity::getAllergicReaction)
                .map(AllergicReaction::new)
                .collect(Collectors.toList());
    }

    @Override
    public void updateAllergySymptoms(List<Long> symptomIds, Instant date, User user) {
        Date truncatedDate = Date.from(
                Optional.ofNullable(date).orElse(Instant.now())
                        .truncatedTo(ChronoUnit.DAYS));
        userAllergicReactionRepository.deleteAllByUserIdAndDate(user.getId(), DateHelper.getDateWithoutTime(truncatedDate, new Date()));
        var reactions = symptomIds.stream().map(id -> UserAllergicReaction.builder()
                .allergicReaction(new AllergicReaction(id))
                .user(user)
                .date(truncatedDate).build())
                .map(UserAllergicReaction::toEntity)
                .collect(Collectors.toList());
        userAllergicReactionRepository.saveAll(reactions);
    }
}
