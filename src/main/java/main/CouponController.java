package main;
import main.model.Coupon;
import main.model.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@Controller
public class CouponController {

    @Autowired
    CouponRepository couponRepository;

    @PostMapping("createCoupon")
    public String createCoupon(@RequestParam String description, Map<String, String> model) {
        Coupon coupon = new Coupon();
        int id = getRandomNumber();
        coupon.setId(id);
        coupon.setDescription(description);
        coupon.setRelevant(true);
        couponRepository.save(coupon);
        model.put("message", "coupon created =)");
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
        return "main";
    }
}
