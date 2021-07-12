package ir.lucifer.approject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public MainAPI mainAPI;
//    Button singin;
    Button LoginButtonMainPage;
    public static Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;

        LoginButtonMainPage = findViewById(R.id.LoginButtonMainPage);

        Retrofit test2 = new Retrofit.Builder().baseUrl(Controler.url)
                .addConverterFactory(GsonConverterFactory.create()).build();

        mainAPI = test2.create(MainAPI.class);

        LoginButtonMainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity, SearchViewPage.class));
            }
        });
//        singin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(activity, LoginPage.class));
//            }
//        });


    }

 /*   private void ShowProducts() {
        Call<List<Product>> call = mainAPI.getProductList();

        //gettt
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    List<Product> products = response.body();
                    for (Product tempPro : products) {
                        String ret = "";
                        ret += "Subject : " + tempPro.subject + "\n";
                        ret += "Description" + tempPro.description + "\n";
                        ret += "Price : " + tempPro.price + "\n\n";
//                        test.append(ret);
                    }

                } else {
//                    test.setText("Error " + response.code());
                }

            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
//                test.setText(t.getMessage());
            }
        });
    }
*/
}