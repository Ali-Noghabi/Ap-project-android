package ir.lucifer.approject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public MainAPI mainAPI;
    public EditText username;
    public EditText password;
    public Button LoginButtonMainPage;
    public TextView signupButtonMainPage;
    public TextView forgetPassword;
    public static Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;

        username = findViewById(R.id.emailEditTextMA);
        password = findViewById(R.id.passwordEditTextMA);
        LoginButtonMainPage = findViewById(R.id.LoginButtonMA);
        signupButtonMainPage = findViewById(R.id.signupTextViewMA);
        forgetPassword = findViewById(R.id.textViewforgetpass);

        Retrofit test2 = new Retrofit.Builder().baseUrl(Controler.url)
                .addConverterFactory(GsonConverterFactory.create()).build();

        mainAPI = test2.create(MainAPI.class);

        LoginButtonMainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postLogin();
            }
        });

        signupButtonMainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                startActivity(new Intent(activity , SignUpPage.class));
                startActivity(new Intent(activity , SignUpPage.class));
            }
        });

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity , RecoveryPass.class));
            }
        });


    }
    private void postLogin()
    {

        if(username.getText().toString().equals("admin") && password.getText().toString().equals("admin"))
        {
            startActivity(new Intent(MainActivity.activity , AdminManager.class));
        }
        else {
            JsonObject obj1 = new JsonObject();
            obj1.addProperty("email", username.getText().toString());
            obj1.addProperty("password", password.getText().toString());

            Call<JsonObject> call = mainAPI.getLoginInfo(obj1);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.isSuccessful()) {
                        JsonObject obj = response.body();

                        if (obj.get("code").getAsInt() == 200) {
                            Controler.Token = obj.get("token").getAsString();
                            Controler.isLogin = true;
                            Controler.Name = obj.get("name").getAsString();
                            Controler.Username = username.getText().toString();
                            Controler.Password = password.getText().toString();
                            Controler.PhoneNum = obj.get("phonenum").getAsString();
                            Toast.makeText(getApplicationContext(), "Login", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.activity, SearchViewPage.class));
                        } else {
                            Toast.makeText(getApplicationContext(), obj.get("msg").getAsString(), Toast.LENGTH_SHORT).show();
                        }
                        password.setText("");
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

}