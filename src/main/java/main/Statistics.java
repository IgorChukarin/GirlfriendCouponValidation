package main;

import main.model.Coupon;
import main.model.CouponRepository;

public class Statistics {
    private int usedCoupons;
    private int unusedCoupons;
    private int totalAmount;

    public int countUsedCoupons(CouponRepository couponRepository) {
        int amount = 0;
        Iterable<Coupon> coupons = couponRepository.findAll();
        for (Coupon coupon : coupons) {
            if (!coupon.isRelevant()) {
                amount++;
            }
        }
        return amount;
    }

    public int countUnusedCoupons(CouponRepository couponRepository) {
        int amount = 0;
        Iterable<Coupon> coupons = couponRepository.findAll();
        for (Coupon coupon : coupons) {
            if (coupon.isRelevant()) {
                amount++;
            }
        }
        return amount;
    }

    public int countTotalAmount(CouponRepository couponRepository) {
        int amount = 0;
        Iterable<Coupon> coupons = couponRepository.findAll();
        for (Coupon coupon : coupons) {
            amount++;
        }
        return amount;
    }
}
