package pl.mealcore.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.mealcore.annotations.RestApiController;
import pl.mealcore.dao.SportRepository;
import pl.mealcore.dto.sport.Sport;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestApiController(path = "sport")
@RequiredArgsConstructor
public class SportController {
    private final SportRepository sportRepository;

    @ResponseBody
    @GetMapping("/getAll")
    ResponseEntity<List<Sport>> getAll() {
        List<Sport> sports = sportRepository.findAll().stream()
                .map(Sport::new)
                .collect(Collectors.toList());
        if (sports.isEmpty()) {
            log.info("FAILED sports not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.info("SUCCESS getting all sports");
        return new ResponseEntity<>(sports, HttpStatus.OK);
    }
}
