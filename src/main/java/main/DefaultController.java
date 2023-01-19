package main;
import main.model.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class DefaultController {

    @Autowired
    CouponRepository couponRepository;

    @RequestMapping("/")
    public String index(Map<String, String> model) {
        Statistics statistics = new Statistics();
        String used = String.valueOf(statistics.countUsedCoupons(couponRepository));
        String unused = String.valueOf(statistics.countUnusedCoupons(couponRepository));
        String total = String.valueOf(statistics.countTotalAmount(couponRepository));
        model.put("used", used);
        model.put("unused", unused);
        model.put("total", total);
        model.put("message", "Привет!");
        model.put("image", "https://sun9-north.userapi.com/sun9-79/s/v1/ig2/yixyRxbNXfdBvR7pU3d4Bj77f6hRJQXAozElXxk-EH4FCkFp3QC1u_BNSQVyExHmDPZFyyIcAtyIFGfoYRhJguLA.jpg?size=1170x1170&quality=95&type=album");
        return "main";
    }

}
