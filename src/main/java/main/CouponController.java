package main;
import main.model.Coupon;
import main.model.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
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
        if (description.equals("")) {
            displayStatistics(model);
            model.put("message", "Описание не заполнено");
            model.put("image", "https://sun9-north.userapi.com/sun9-79/s/v1/ig2/yixyRxbNXfdBvR7pU3d4Bj77f6hRJQXAozElXxk-EH4FCkFp3QC1u_BNSQVyExHmDPZFyyIcAtyIFGfoYRhJguLA.jpg?size=1170x1170&quality=95&type=album");
            return "main";
        }
        Coupon coupon = setCouponParameters(description);
        couponRepository.save(coupon);
        displayStatistics(model);
        model.put("message", "Купон №" + coupon.getId() + " выписан!");
        model.put("image", "https://sun9-west.userapi.com/sun9-4/s/v1/ig2/f06sZw_bu8N8zzjFADiIFpT5EOp9WRl63NJcgYYD9PtqWATFYUslqyJ3yE7RnZCosguIWshu5dhvVTWsW6QHPYdL.jpg?size=1170x1166&quality=96&type=album");
        try {
            CouponImageCreator.addTextInImage(String.valueOf(coupon.getId()), description);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "main";
    }

    public Coupon setCouponParameters(String description) {
        Coupon coupon = new Coupon();
        int id = getUniqueRandomNumber();
        coupon.setId(id);
        coupon.setDescription(description);
        coupon.setRelevant(true);
        return coupon;
    }

    public int getUniqueRandomNumber() {
        int randomCode = ((int) ((Math.random()) * (999_999 - 100_000)) + 100_000);
        ArrayList<Integer> ids = new ArrayList<>();
        Iterable<Coupon> coupons = couponRepository.findAll();
        for (Coupon coupon : coupons) {
            ids.add(coupon.getId());
        }
        if (ids.contains(randomCode)) {
            return getUniqueRandomNumber();
        }
        else {
            return randomCode;
        }
    }

    @PostMapping("activateCoupon")
    public String activateCoupon(@RequestParam String code, Map<String, String> model) {
        if (code.equals("") || code.length() != 6) {
            displayStatistics(model);
            model.put("message", "Неверный формат кода");
            model.put("image", "https://sun9-north.userapi.com/sun9-79/s/v1/ig2/yixyRxbNXfdBvR7pU3d4Bj77f6hRJQXAozElXxk-EH4FCkFp3QC1u_BNSQVyExHmDPZFyyIcAtyIFGfoYRhJguLA.jpg?size=1170x1170&quality=95&type=album");
            return "main";
        }
        int id = Integer.parseInt(code);
        Optional<Coupon> optionalCoupon = couponRepository.findById(id);
        if (optionalCoupon.isPresent()) {
            Coupon coupon = optionalCoupon.get();
            if (coupon.isRelevant()) {
                coupon.setRelevant(false);
                couponRepository.save(coupon);
                model.put("message", "Купон акивирован!");
                model.put("image", "https://sun9-north.userapi.com/sun9-83/s/v1/ig2/XUOo9cBu9e-lJ6ZlxgLUw-ZaQDx8x0Kf2ckAyzztB_c7Ddv9elHFCtWEj3E7UaG3cyQ90EzvFs5WlvuLb4zQf0ej.jpg?size=1170x1170&quality=95&type=album");

            } else {
                model.put("message", "Этот купон уже использован!");
                model.put("image", "https://sun9-east.userapi.com/sun9-60/s/v1/ig2/FGiZXxtEIo959GAk4iSRwsG3rucCPYIeS3zEDVrSLPQBLzBjmLx2zhewoRFJfULsToAo4WWtPbLjlP9O9KdqMsa1.jpg?size=1170x1170&quality=95&type=album");
            }
        }
        else {
            model.put("message", "*Купон не существует*");
            model.put("image", "https://sun9-north.userapi.com/sun9-88/s/v1/ig2/ku6qWk6Pqt6XgX_OvbsTXt4c2KJ-T2JXLSJppE6Rb_gJfN2fKOCadmrQ1taVABtySvpMDLi5ejtwZBHDnz--s0jt.jpg?size=1170x1166&quality=95&type=album");

        }
        displayStatistics(model);
        return "main";
    }

    public void displayStatistics(Map<String, String> model){
        Statistics statistics = new Statistics();
        String used = String.valueOf(statistics.countUsedCoupons(couponRepository));
        String unused = String.valueOf(statistics.countUnusedCoupons(couponRepository));
        String total = String.valueOf(statistics.countTotalAmount(couponRepository));
        model.put("used", used);
        model.put("unused", unused);
        model.put("total", total);
    }
}
