package by.makouski.news.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Stanislau_Makouski on 11/16/2016.
 */
@Controller
@RequestMapping("/")
public class HomeController {
    public String home() {
        String result;
        result = "redirect:/login";
        return result;
    }
}
