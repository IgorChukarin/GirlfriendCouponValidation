package main;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Controller
public class DefaultController {

    @RequestMapping("/")
    public String index(Map<String, String> model) {
        model.put("message", "hello");
        return "main";
    }
}
