package pl.javastart.couponscalc;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class PriceCalculatorTest {

    private PriceCalculator priceCalculator = new PriceCalculator();

    @Test
    public void shouldReturnZeroForNoProducts() {
        // when
        double result = priceCalculator.calculatePrice(null, null);

        // then
        assertThat(result, is(0.));
    }

    @Test
    public void shouldReturnPriceForSingleProductAndNoCoupons() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("Masło", 5.99, Category.FOOD));

        // when
        double result = priceCalculator.calculatePrice(products, null);

        // then
        assertThat(result, is(5.99));
    }

    @Test
    public void shouldReturnPriceForSingleProductAndOneApplicableCoupon() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("Masło", 5.99, Category.FOOD));

        List<Coupon> coupons = new ArrayList<>();
        coupons.add(new Coupon(Category.FOOD, 20));

        // when
        double result = priceCalculator.calculatePrice(products, coupons);

        // then
        assertThat(result, is(4.79));
    }

    @Test
    public void shouldReturnPriceForSingleProductAndOneNotApplicableCoupon() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("Mleko", 5.99, Category.FOOD));

        List<Coupon> coupons = new ArrayList<>();
        coupons.add(new Coupon(Category.CAR, 10));

        // when
        double result = priceCalculator.calculatePrice(products, coupons);

        // then
        assertThat(result, is(5.99));
    }

    @Test
    public void shouldReturnBestPriceForSingleProductAndFewCoupons() {

        //given
        List<Product> products = new ArrayList<>();
        products.add(new Product("Masło", 6.00, Category.FOOD));

        List<Coupon> coupons = new ArrayList<>();
        coupons.add(new Coupon(Category.FOOD, 20));
        coupons.add(new Coupon(null, 50));
        coupons.add(new Coupon(Category.FOOD, 30));
        coupons.add(new Coupon(Category.CAR, 40));

        //when

        double result = priceCalculator.calculatePrice(products, coupons);

        //then
        assertThat(result, is(3.0));
    }

    @Test
    public void shouldReturnPriceForFewDifferentCategoryProductsAndOneCoupon() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("Masło", 6.00, Category.FOOD));
        products.add(new Product("Masło", 7.5, Category.CAR));
        products.add(new Product("Masło", 5.3, Category.FOOD));
        products.add(new Product("Masło", 3.99, Category.HOME));

        List<Coupon> coupons = new ArrayList<>();
        coupons.add(new Coupon(Category.FOOD, 20));

        // when
        double result = priceCalculator.calculatePrice(products, coupons);

        // then
        assertThat(result, is(20.53));
    }

    @Test
    public void shouldReturnBestPriceForFewDifferentCategoryProductsAndFewCoupons() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("Masło", 6.00, Category.FOOD));
        products.add(new Product("Masło", 10.50, Category.FOOD));
        products.add(new Product("Opony", 100.0, Category.CAR));
        products.add(new Product("Żarówka", 50.5, Category.HOME));
        products.add(new Product("Żyrandol", 110.0, Category.HOME));

        List<Coupon> coupons = new ArrayList<>();
        coupons.add(new Coupon(Category.FOOD, 30));
        coupons.add(new Coupon(null, 10));
        coupons.add(new Coupon(Category.CAR, 15));
        coupons.add(new Coupon(Category.HOME, 18));

        double result = priceCalculator.calculatePrice(products, coupons);

        assertThat(result, equalTo(248.11));
    }

}