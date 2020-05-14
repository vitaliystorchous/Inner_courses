package homework4.dataproviders;

import org.testng.annotations.DataProvider;

import static java.lang.Math.round;
import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.apache.commons.lang3.RandomUtils.nextDouble;
import static org.apache.commons.lang3.RandomUtils.nextInt;

public class ProductsGenerator {

    @DataProvider (name = "several products generator")
    public Object[][] generateSeveralProductsWithAmounts() {
        int numberOfProducts = nextInt(1, 3);
        Object[][] products = new Object[numberOfProducts][3];

        for(int i = 0; i < numberOfProducts; i++) {
            String productName = random(nextInt(5, 11), true, true);
            double productPrice = (double) round(nextDouble(1, 100) * 100) / 100;
            int amountOfProducts = nextInt(1, 100);
            products[i][0] = productName;
            products[i][1] = productPrice;
            products[i][2] = amountOfProducts;
        }

        return products;
    }

    @DataProvider (name = "single product generator")
    public Object[][] generateOneProduct() {
        String productName = random(nextInt(5, 11), true, true);
        double productPrice = (double) round(nextDouble(1, 100) * 100) / 100;
        int numberOfProducts = nextInt(1, 100);
        return new Object[][] {
                { productName, productPrice, numberOfProducts },
        };
    }
}
