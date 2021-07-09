package ir.lucifer.approject;

import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MainAPI {


    @GET("getProducts")
    Call<List<Product>> getProductList ();

    @POST("login")
    Call<JsonObject> getLoginInfo(@Body JsonObject Input);

    @POST("postUser")
    Call<JsonObject> addUser(@Body JsonObject Input);


}
