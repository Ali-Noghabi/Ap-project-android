package ir.lucifer.approject;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MainAPI {


    @GET("getProducts")
    Call<ArrayList<Product>> getProductList ();

    @POST("getProducts")
    Call<ArrayList<Product>> getProductByID (@Body String user);

    @POST("login")
    Call<JsonObject> getLoginInfo(@Body JsonObject Input);

    @POST("editUser")
    Call<JsonObject> editUser(@Body JsonObject Input);

    @POST("editPro")
    Call<JsonObject> editPro(@Body JsonObject Input);

    @POST("postUser")
    Call<JsonObject> addUser(@Body JsonObject Input);

    @POST("postProduct")
    Call<JsonObject> addProduct(@Body JsonObject Input);

    @POST("search")
    Call<ArrayList<Product>> getSearchRes(@Body JsonObject Input);

    @POST("buy")
    Call<JsonObject> buyProduct(@Body JsonObject Input);


}
