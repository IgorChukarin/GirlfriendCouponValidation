package main;
import main.model.Coupon;
import main.model.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@Controller
public class CouponController {

    @Autowired
    CouponRepository couponRepository;

    @GetMapping("/dataBase")
    public String couponsBase(Map<String, Object> model) {
        return "dataBase";
    }

    @PostMapping("createCoupon")
    public String createCoupon(@RequestParam String description, Map<String, String> model) {
        Coupon coupon = new Coupon();
        int id = getRandomNumber();
        coupon.setId(id);
        coupon.setDescription(description);
        coupon.setRelevant(true);
        couponRepository.save(coupon);
        Statistics statistics = new Statistics();
        String used = String.valueOf(statistics.countUsedCoupons(couponRepository));
        String unused = String.valueOf(statistics.countUnusedCoupons(couponRepository));
        String total = String.valueOf(statistics.countTotalAmount(couponRepository));
        model.put("used", used);
        model.put("unused", unused);
        model.put("total", total);
        model.put("message", "Новый купон с номером " + id + " создан =)");
        return "main";
    }

    public int getRandomNumber() {
        int randomCode = ((int) ((Math.random()) * (999_999 - 100_000)) + 100_000);
        ArrayList<Integer> ids = new ArrayList<>();
        Iterable<Coupon> coupons = couponRepository.findAll();
        for (Coupon coupon : coupons) {
            ids.add(coupon.getId());
        }
        if (ids.contains(randomCode)) {
            return getRandomNumber();
        }
        else {
            return randomCode;
        }
    }

    @PostMapping("activateCoupon")
    public String activateCoupon(@RequestParam String code, Map<String, String> model) {
        int id = Integer.parseInt(code);
        Optional<Coupon> optionalCoupon = couponRepository.findById(id);
        if (optionalCoupon.isPresent()) {
            Coupon coupon = optionalCoupon.get();
            if (coupon.isRelevant()) {
                coupon.setRelevant(false);
                couponRepository.save(coupon);
                model.put("message", "coupon activated and relevant no more");
            } else {
                model.put("message", "вы уже использовали этот купон >:(");
            }
        }
        else {
            model.put("message", "купон не существует");
        }
        Statistics statistics = new Statistics();
        String used = String.valueOf(statistics.countUsedCoupons(couponRepository));
        String unused = String.valueOf(statistics.countUnusedCoupons(couponRepository));
        String total = String.valueOf(statistics.countTotalAmount(couponRepository));
        model.put("used", used);
        model.put("unused", unused);
        model.put("total", total);
        return "main";
    }
}
