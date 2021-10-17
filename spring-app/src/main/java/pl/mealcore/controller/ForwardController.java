package pl.mealcore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ForwardController {

    @RequestMapping(path = {"/", "/**/{regex:[^.]+}"})
    public String forward() {
        return "forward:/index.html";
    }
}
