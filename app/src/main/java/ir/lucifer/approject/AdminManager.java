package ir.lucifer.approject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;

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
}