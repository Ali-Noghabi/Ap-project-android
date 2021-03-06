package ir.lucifer.approject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LastActivity extends AppCompatActivity {

    public MainAPI mainAPI;
    public RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last);

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

    private void UpdateProductsList() {
        Call<ArrayList<Product>> call = mainAPI.getProductByID(Controler.Username);


        call.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {

                if (response.isSuccessful()) {
                    ArrayList<Product> products;
                    products = response.body();
                    recyclerView = findViewById(R.id.lastActivityRecyclerView);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


                    MyRecyclerViewAdapterLastActivity myRecyclerViewAdapter = new MyRecyclerViewAdapterLastActivity(getApplicationContext(), products);
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