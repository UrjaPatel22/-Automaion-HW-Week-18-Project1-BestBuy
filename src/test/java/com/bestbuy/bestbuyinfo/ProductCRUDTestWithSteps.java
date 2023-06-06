package com.bestbuy.bestbuyinfo;

import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class ProductCRUDTestWithSteps extends TestBase {

    static int productId;
    static String name = "Duracell - AAA Batteries (4-Pack)" + TestUtils.getRandomValue();

    static String type = "HardGood" + TestUtils.getRandomValue();

    static Double price= 5.49;

    static String upc="041333424019";

    static double shipping = 0;

    static String description = "Compatible with select electronic devices";
    static String manufacturer = "Duracell";
    static String model = "MN2400B4Z";
    static String url = "http://www.bestbuy.com/site/duracell-aaa-batteries-4-pack/43900.p?id=1051384074145&skuId=43900&cmp=RMXCC";

    static String image= "http://img.bbystatic.com/BestBuy_US/images/products/4390/43900_sa.jpg";



@Steps
    ProductSteps productSteps;

@Title("This will create a new product")
    @Test
    public  void test001(){
    ValidatableResponse response=productSteps.createNewProduct(name,type,price,upc,shipping,description,manufacturer,model,url,image);
    productId = response.log().all().extract().path("id");
    response.log().all().statusCode(201);
}

    @Title("Verify If the Product was Added to the application")
    @Test
    public void test002(){
        HashMap<String,Object> productMAp= productSteps.getProductInfoById(productId);
        Assert.assertThat(productMAp, hasValue(productId));
       // productId = (int) productMAp.get("id");
    }

    @Title("Update the Product information and Verify the Updated information")
    @Test
    public  void test003(){
        name=name + "_Updated";
        productSteps.updateProduct(productId,name,type,price,upc,shipping,description,manufacturer,model,url,image)
                .statusCode(200);
        HashMap<String,Object> productMap= productSteps.getProductInfoById(productId);
        Assert.assertThat(productMap, hasValue(name));
    }


    @Title("Delete the Product and verify if the product is deleted")
    @Test
    public void test004(){
    productSteps.deleteProduct(productId)
            .statusCode(200);

    }


}
