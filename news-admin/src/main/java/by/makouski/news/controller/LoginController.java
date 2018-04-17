package by.makouski.news.controller;

import by.makouski.news.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


/**
 * Created by Stanislau_Makouski on 11/9/2016.
 */
@Controller
public class LoginController {

    @RequestMapping("/login")
    public ModelAndView toLoginPage() {
        ModelAndView model = new ModelAndView();
        model.addObject("user", new User());
        model.setViewName("app.login");
        return model;
    }
}