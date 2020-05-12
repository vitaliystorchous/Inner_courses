package homework4.dataproviders;

import org.testng.annotations.DataProvider;

import static java.lang.Math.round;
import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.apache.commons.lang3.RandomUtils.nextDouble;
import static org.apache.commons.lang3.RandomUtils.nextInt;

public class ProductsGenerator {

    @DataProvider
    public Object[][] testData() {
        int numberOfProducts = nextInt(2, 6);
        Object[][] products = new Object[numberOfProducts][2];

        for(int i = 0; i < numberOfProducts; i++) {
            String productName = random(nextInt(5, 11), true, true);
            double productPrice = (double) round(nextDouble(1, 100) * 100) / 100;
            products[i][0] = productName;
            products[i][1] = productPrice;
        }

        return products;
    }
}
