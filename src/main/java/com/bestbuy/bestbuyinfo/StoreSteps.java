package com.bestbuy.bestbuyinfo;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.model.StorePojo;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class StoreSteps {

    @Step("Creating a new Store  with name: {0}, type: {1}, address: {2},address2:{3}, City: {4}, state: {5},zip: {6}, lat: {7}, lng: {8}, hours: {9}")
    public ValidatableResponse createNewStore(String name, String type, String address, String address2, String City, String state, String zip, double lat, double lng, String hours){

        StorePojo storePojo = StorePojo.getStorePojo(name,type,address,address2,City,state,zip,lat,lng,hours);

        return  SerenityRest.given()
                .header("Content-Type","application/json")
                .body(storePojo)
                .when()
                .post(EndPoints.CREATE_STORE)
                .then();

    }

    @Step("Getting the Store information with single storeID : {0}")
    public HashMap<String, Object> getStoreInfoById(int storeId) {
        HashMap<String, Object> storeMap = SerenityRest.given().log().all()
                .when()
                .pathParam("storeID",storeId)
                .get(EndPoints.GET_SINGLE_STORE_BY_ID)
                .then().statusCode(200)
                .extract()
                .path("");
        return  storeMap;

    }


    @Step("Updating a existing Store  with storeId: {0}, name: {1}, type: {2}, address: {3},address2:{4}, City: {5}, state: {6},zip: {7}, lat: {8}, lng: {9}, hours: {10}")
    public ValidatableResponse updateStore(int storeId,String name, String type, String address, String address2, String City, String state, String zip, double lat, double lng, String hours){

        StorePojo storePojo = StorePojo.getStorePojo(name,type,address,address2,City,state,zip,lat,lng,hours);

        return  SerenityRest.given().log().all()
                .header("Content-Type","application/json")
                .pathParam("storeID", storeId)
                .body(storePojo)
                .when()
                .put(EndPoints.UPDATE_STORE_BY_ID)
                .then();

    }

    @Step("Deleting product information with StoreId: {0}")
    public ValidatableResponse deleteStore(int storeId){
        return SerenityRest.given().log().all()
                .pathParam("storeID", storeId)
                .when()
                .delete(EndPoints.DELETE_STORE_BY_ID)
                .then();
    }








}
