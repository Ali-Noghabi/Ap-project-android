package ir.lucifer.approject;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MainAPI {


    @GET("getProducts2")
    Call<ArrayList<Product>> getProductList ();

    @GET("getUsers")
    Call<ArrayList<User>> getUsersList ();

    @GET("AM/getBestSeller")
    Call<JsonObject> getBestSeller();

    @GET("getProductsAdmin")
    Call<ArrayList<Product>> getProductListAdmin ();

    @POST("getProducts")
    Call<ArrayList<Product>> getProductByID (@Body String user);

    @POST("login")
    Call<JsonObject> getLoginInfo(@Body JsonObject Input);

    @POST("deleteProduct")
    Call<Boolean> deleteProduct(@Body int ID);

    @POST("changeStar")
    Call<Boolean> changeStar(@Body JsonObject Input);

    @POST("editUser")
    Call<JsonObject> editUser(@Body JsonObject Input);

    @POST("editPro")
    Call<JsonObject> editPro(@Body JsonObject Input);

    @POST("postUser")
    Call<JsonObject> addUser(@Body JsonObject Input);

    @POST("forgetPass")
    Call<JsonObject> forgetPass(@Body JsonObject Input);

    @POST("postProduct")
    Call<JsonObject> addProduct(@Body JsonObject Input);

    @POST("search")
    Call<ArrayList<Product>> getSearchRes(@Body JsonObject Input);

    @POST("buy")
    Call<JsonObject> buyProduct(@Body JsonObject Input);


}
