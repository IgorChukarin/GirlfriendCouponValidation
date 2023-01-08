package main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class CouponController {

    @PostMapping("createCoupon")
    public String saySomething(Map<String, Integer> model) {
        int id = getRandomNumber(100_000, 999_999);
        model.put("message", id);
        return "main";
    }

    public int getRandomNumber(int min, int max) {
        return ((int) ((Math.random()) * (max - min)) + min);
    }
}
