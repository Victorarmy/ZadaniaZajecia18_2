package pl.javastart.couponscalc;

import java.util.List;

public class PriceCalculator {

    public double calculatePrice(List<Product> products, List<Coupon> coupons) {
        if (products == null) {
            return 0;
        } else {
            if (coupons == null){
                return sumProductsPriceWithoutCoupons(products);
            } else {
                double bestVariant = Double.MAX_VALUE;
                for (Coupon coupon : coupons) {
                    double possibleTotalPrice = sumProductsPriceWithCoupon(products, coupon);
                    bestVariant = Math.min(bestVariant, possibleTotalPrice);
                }
                return getBestSumVariantRoundedToNearestHundredth(bestVariant);
            }
        }
    }

    private double getBestSumVariantRoundedToNearestHundredth(double bestVariant) {
        return ((int) (bestVariant * 100)) / 100D;
    }

    private double sumProductsPriceWithCoupon(List<Product> products, Coupon coupon) {
        double totalPrice = 0;
        for (Product product : products) {

            Category productCategory = product.getCategory();
            Category couponCategory = coupon.getCategory();

            if (productCategory.equals(couponCategory) || couponCategory == null) {
                totalPrice += getProductPriceWithCoupon(coupon, product);
            } else {
                totalPrice += product.getPrice();
            }
        }
        return totalPrice;
    }

    private double getProductPriceWithCoupon(Coupon coupon, Product product) {
        return product.getPrice() - (product.getPrice() * coupon.getDiscountValueInPercents()/100);
    }

    private double sumProductsPriceWithoutCoupons(List<Product> products) {
        double sum = 0;
        for (Product product : products) {
            sum += product.getPrice();
        }
        return sum;
    }
}
