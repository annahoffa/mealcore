package pl.mealcore.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.mealcore.annotations.RestApiController;
import pl.mealcore.dao.AllergicReactionRepository;
import pl.mealcore.dto.allergicReaction.AllergicReaction;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestApiController(path = "allergicReaction")
@RequiredArgsConstructor
public class AllergicReactionController {
    private final AllergicReactionRepository allergicReactionRepository;

    @ResponseBody
    @GetMapping("/getAll")
    ResponseEntity<List<AllergicReaction>> getAll() {
        List<AllergicReaction> allergicReactions = allergicReactionRepository.findAll().stream()
                .map(AllergicReaction::new)
                .collect(Collectors.toList());
        if (allergicReactions.isEmpty()) {
            log.info("FAILED allergicReactions not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.info("SUCCESS getting all allergicReactions");
        return new ResponseEntity<>(allergicReactions, HttpStatus.OK);
    }
}
