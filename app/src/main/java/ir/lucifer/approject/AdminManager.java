package ir.lucifer.approject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonObject;

import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AdminManager extends AppCompatActivity {

    public CardView ProList;
    public CardView UserList;
    public MainAPI mainAPI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manager);

        Retrofit test2 = new Retrofit.Builder().baseUrl(Controler.url)
                .addConverterFactory(GsonConverterFactory.create()).build();

        mainAPI = test2.create(MainAPI.class);

        UpdateProductsList();
        UpdateUsersList();
        getBestSeller();
        ProList = findViewById(R.id.prolist_adminManager);
        UserList = findViewById(R.id.sellerlist_adminManager);
        ProList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext() , AdminProducts.class));

            }
        });
        UserList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext() , AdminUsers.class));

            }
        });

    }
    private void UpdateProductsList() {
        Call<ArrayList<Product>> call = mainAPI.getProductListAdmin();
        call.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {

                if (response.isSuccessful()) {
                    ArrayList<Product> products;
                    products = response.body();
                    EditText productNum = findViewById(R.id.numSel);
                    productNum.setText(products.size() + "");

                    int sumPrice = 0;
                    for (Product tempPro:products ) {
                        sumPrice += Integer.parseInt(tempPro.price);
                    }

                    EditText productSum = findViewById(R.id.sumPrices);
                    productSum.setText(sumPrice + " ");

                } else {
                    Toast.makeText(getApplicationContext(), "Error " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void UpdateUsersList() {
        Call<ArrayList<User>> call = mainAPI.getUsersList();


        call.enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {

                if (response.isSuccessful()) {
                    ArrayList<User> Users;
                    Users = response.body();
                    EditText productNum = findViewById(R.id.numSellers);
                    productNum.setText(Users.size() + "");

                } else {
                    Toast.makeText(getApplicationContext(), "Error " + response.code(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getBestSeller() {
        Call<JsonObject> call = mainAPI.getBestSeller();


        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.isSuccessful()) {
                    JsonObject Users;
                    Users = response.body();
                    EditText productNum = findViewById(R.id.BestSeller);
                    productNum.setText(Users.get("best").getAsString().split("@")[0]);

                } else {
                    Toast.makeText(getApplicationContext(), "Error " + response.code(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}