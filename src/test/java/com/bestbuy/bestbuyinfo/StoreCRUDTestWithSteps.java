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
public class StoreCRUDTestWithSteps extends TestBase {

    static int storeId;

    static String name = "PrimUser" + TestUtils.getRandomValue();
    static String type = "BigBox" + TestUtils.getRandomValue();
    static String address =  TestUtils.getRandomValue() + " ,Random Street" ;
    static String address2 = "Roaming Street";
    static String City = "London" ;
    static String state = "England" ;
    static String zip = "445665";
    static double lat = 54.23569;
    static double lng = -93.56;
    static String hours = "Mon: 10-9; Tue: 10-9; Wed: 10-9; Thurs: 10-9; Fri: 10-9; Sat: 10-9; Sun: 10-8";

    @Steps
    StoreSteps storeSteps;

    @Title("This will create a new store")
    @Test
    public  void test001(){
        ValidatableResponse response=storeSteps.createNewStore(name,type,address,address2,City,state,zip,lat,lng,hours);
        storeId = response.log().all().extract().path("id");
        response.log().all().statusCode(201);
    }

    @Title("Verify If the store was Added to the application")
    @Test
    public void test002(){
        HashMap<String,Object> storeMap= storeSteps.getStoreInfoById(storeId);
        Assert.assertThat(storeMap, hasValue(storeId));
        System.out.println(storeMap);

    }


    @Title("Update the Store information and Verify the Updated information")
    @Test
    public  void test003(){
        name=name + "_Updated";
        storeSteps.updateStore(storeId,name,type,address,address2,City,state,zip,lat,lng,hours)
                .statusCode(200);
        HashMap<String,Object> storeMap= storeSteps.getStoreInfoById(storeId);
        Assert.assertThat(storeMap, hasValue(name));
    }

    @Title("Delete the store and verify if the store is deleted")
    @Test
    public void test004(){
        storeSteps.deleteStore(storeId)
                .statusCode(200);

    }


}
