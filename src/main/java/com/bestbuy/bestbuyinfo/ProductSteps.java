package com.bestbuy.bestbuyinfo;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.model.ProductPojo;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class ProductSteps {

    @Step("Creating a new product with name: {0}, type: {1}, price: {2},upc:{3}, shipping: {4}, description: {5},manufacturer: {6}, model: {7}, url: {8}, image: {9}")
    public ValidatableResponse createNewProduct(String name, String type, double price, String upc, double shipping, String description, String manufacturer, String model, String url, String image){

       ProductPojo productPojo = ProductPojo.getProductPojo(name,type,price,upc,shipping,description,manufacturer,model,url,image);

        return  SerenityRest.given()
                .header("Content-Type","application/json")
                .body(productPojo)
                .when()
                .post(EndPoints.CREATE_PRODUCT)
                .then();

    }

    @Step("Getting the product information with single productID : {0}")
    public HashMap<String, Object> getProductInfoById(int productId) {
        HashMap<String, Object> productMap = SerenityRest.given().log().all()
                .when()
                .pathParam("productID",productId)
                .get(EndPoints.GET_SINGLE_PRODUCT_BY_ID)
                .then().statusCode(200)
                .extract()
                .path("");
        return  productMap;

    }

    @Step(" Updating Product Information with productId:{0}, name: {1}, type: {2}, price: {3},upc:{4}, shipping: {5}, description: {6},manufacturer: {7}, model: {8}, url: {9}, image: {10}")
    public ValidatableResponse updateProduct(int productId,String name, String type, double price, String upc, double shipping, String description, String manufacturer, String model, String url, String image){

        ProductPojo productPojo = ProductPojo.getProductPojo(name,type,price,upc,shipping,description,manufacturer,model,url,image);


        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .pathParam("productID", productId)
                .body(productPojo)
                .when()
                .put(EndPoints.UPDATE_PRODUCT_BY_ID)
                .then();
    }

@Step("Deleting product information with productId: {0}")
public ValidatableResponse deleteProduct(int productId){
    return SerenityRest.given().log().all()
            .pathParam("productID", productId)
            .when()
            .delete(EndPoints.DELETE_PRODUCT_BY_ID)
            .then();
}






}
