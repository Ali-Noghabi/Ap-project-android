package ir.lucifer.approject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.gesture.GestureOverlayView;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

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
    public static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view_page);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.my_icon);
        getSupportActionBar().setTitle("Dikan");

        NavigationView navigationView = findViewById(R.id.NavigationView);
        View header = navigationView.getHeaderView(0);
        TextView NVemail = header.findViewById(R.id.navigationViewEmailTV);
        TextView NVname = header.findViewById(R.id.navigationViewNameTV);
        Toast.makeText(getApplicationContext(), "" + Controler.Username + " " + Controler.Name, Toast.LENGTH_SHORT).show();
        NVemail.setText(Controler.Username);
        NVname.setText(Controler.Name);

        activity = this;

        ImageView imageView = findViewById(R.id.profile_image);

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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavigationView navigationView = findViewById(R.id.NavigationView);
                Toast.makeText(getApplicationContext(), "" + navigationView.isShown(), Toast.LENGTH_SHORT).show();
                DrawerLayout drawerLayout = findViewById(R.id.search_toolbar);

                if (navigationView.isShown() == true)
                    drawerLayout.closeDrawer(Gravity.LEFT);
                else
                    drawerLayout.openDrawer(Gravity.LEFT);
//                NavUtils.navigateUpFromSameTask(this);
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
                            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                                    DividerItemDecoration.VERTICAL);
                            recyclerView.addItemDecoration(dividerItemDecoration);


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
                    ArrayList<Product> products;
                    products = response.body();
                    recyclerView = findViewById(R.id.RecyclerViewSVP);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


                    MyRecyclerViewAdapter myRecyclerViewAdapter = new MyRecyclerViewAdapter(getApplicationContext(), products);
                    recyclerView.setAdapter(myRecyclerViewAdapter);

                    //divider for vertical recyclebar
                    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                            DividerItemDecoration.VERTICAL);
                    recyclerView.addItemDecoration(dividerItemDecoration);


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
