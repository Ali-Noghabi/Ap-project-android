package ir.lucifer.approject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchViewPage extends AppCompatActivity {

    public MainAPI mainAPI;
    public RecyclerView recyclerView;
    static public Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view_page);


        activity = this;

        Retrofit test2 = new Retrofit.Builder().baseUrl(Controler.url)
                .addConverterFactory(GsonConverterFactory.create()).build();

        mainAPI = test2.create(MainAPI.class);

        UpdateProductsList();

    }

    @Override
    protected void onResume() {
        super.onResume();
        UpdateProductsList();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_viewpage_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.SearchItemSVP);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search here");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                JsonObject obj1 = new JsonObject();
                obj1.addProperty("query", query);
                obj1.addProperty("category", "-");

                Call<ArrayList<Product>> call = mainAPI.getSearchRes(obj1);

                call.enqueue(new Callback<ArrayList<Product>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                        if (response.isSuccessful()) {
                            ArrayList<Product> products = response.body();

                            products = response.body();
                            recyclerView = findViewById(R.id.RecyclerViewSVP);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


                            MyRecyclerViewAdapter myRecyclerViewAdapter = new MyRecyclerViewAdapter(getApplicationContext(), products);
                            recyclerView.setAdapter(myRecyclerViewAdapter);



                        } else {
                            Toast.makeText(getApplicationContext(), "Error " + response.code(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                UpdateProductsList();
                return false;
            }
        });
        return true;
    }

    public Context getContext() {
        return this;
    }

    private void UpdateProductsList() {
        Call<ArrayList<Product>> call = mainAPI.getProductList();


        call.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {

                if (response.isSuccessful()) {
                    ArrayList<Product> products = response.body();
                    recyclerView = findViewById(R.id.RecyclerViewSVP);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


                    MyRecyclerViewAdapter myRecyclerViewAdapter = new MyRecyclerViewAdapter(getApplicationContext(), products);
                    recyclerView.setAdapter(myRecyclerViewAdapter);
//                    recyclerView.setOnClickListener(new MyRecyclerViewAdapter)


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
}
