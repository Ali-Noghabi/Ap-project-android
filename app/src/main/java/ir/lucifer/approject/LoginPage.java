package ir.lucifer.approject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginPage extends AppCompatActivity {

    public EditText userName;
    public EditText passWord;
    public Button login;
    public MainAPI mainAPI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        userName = findViewById(R.id.username);
        passWord = findViewById(R.id.password);
        login = findViewById(R.id.login);

        Retrofit test2 = new Retrofit.Builder().baseUrl(Controler.url)
                .addConverterFactory(GsonConverterFactory.create()).build();

        mainAPI = test2.create(MainAPI.class);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postLogin();
            }
        });
    }
    private void postLogin()
    {

        JsonObject obj1 = new JsonObject();
        obj1.addProperty("email" , userName.getText().toString());
        obj1.addProperty("password" , passWord.getText().toString());
//        obj1.addProperty("LoginTime" , "00:01");

        Call<JsonObject> call = mainAPI.getLoginInfo(obj1);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful())
                {
                    JsonObject obj = response.body();

                    if(obj.get("code").getAsInt() == 200)
                    {
                        Controler.Token = obj.get("token").getAsString();
                        Controler.isLogin = true;
                        startActivity(new Intent(MainActivity.activity , SearchViewPage.class));
                        Toast.makeText(getApplicationContext(), "Login :)", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), obj.get("msg").getAsString(), Toast.LENGTH_SHORT).show();
                    }
                    passWord.setText("");
                }
                else {
                    Toast.makeText(getApplicationContext() , "Error " + response.code() , Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getApplicationContext() , t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
    }
}