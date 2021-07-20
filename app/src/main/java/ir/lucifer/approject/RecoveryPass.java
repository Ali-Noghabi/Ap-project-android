package ir.lucifer.approject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecoveryPass extends AppCompatActivity {

    public EditText Email;

    public MainAPI mainAPI;

    public Button confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery_pass);

        Email = findViewById(R.id.emailForRecovery);

        String EmailValidator = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern EmailValidatorPattern = Pattern.compile(EmailValidator);

        confirmButton = findViewById(R.id.confirmRecoveryPage);

        Retrofit test2 = new Retrofit.Builder().baseUrl(Controler.url)
                .addConverterFactory(GsonConverterFactory.create()).build();

        mainAPI = test2.create(MainAPI.class);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Matcher EmailValidatorMatcher = EmailValidatorPattern.matcher(Email.getText());
                if(EmailValidatorMatcher.find()) {
                    postUser();
                }
                else
                    Toast.makeText(getApplicationContext(), "Email is not Valid", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void postUser()
    {

        JsonObject obj1 = new JsonObject();
        obj1.addProperty("username" , Email.getText().toString());


        Call<JsonObject> call = mainAPI.forgetPass(obj1);
        call.enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful())
                {
                    JsonObject obj = response.body();
                    if(obj.get("code").getAsString().equals("200"))
                    {
                        Toast.makeText(getApplicationContext(), "Check your Email \nalso check spam box please", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext() , "Error? :)" , Toast.LENGTH_SHORT).show();
                    }

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