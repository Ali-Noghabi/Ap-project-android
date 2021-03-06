package ir.lucifer.approject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpPage extends AppCompatActivity {

    public TextInputLayout tilName,tilEmail;
    public EditText Email;
    public EditText Name;
    public EditText PhoneNum;
    public EditText password;
    public EditText confirmPassword;
    public MainAPI mainAPI;
    public Button signUpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        Email = findViewById(R.id.usernameSignup);
        Name = findViewById(R.id.name);
        PhoneNum = findViewById(R.id.phonenum);
        password = findViewById(R.id.passwordSignup);
        confirmPassword = findViewById(R.id.confirmPassword);
        signUpButton = findViewById(R.id.signupButton);

        String EmailValidator = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern EmailValidatorPattern = Pattern.compile(EmailValidator);
        String PasswordValidator = "^(?=.*\\d)(?=.*[a-z]).{6,15}$";
        Pattern PasswordValidatorPattern = Pattern.compile(PasswordValidator);

        Retrofit test2 = new Retrofit.Builder().baseUrl(Controler.url)
                .addConverterFactory(GsonConverterFactory.create()).build();

        mainAPI = test2.create(MainAPI.class);


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Matcher EmailValidatorMatcher = EmailValidatorPattern.matcher(Email.getText());
                Matcher PasswordValidatorMatcher = PasswordValidatorPattern.matcher(password.getText());

                if(EmailValidatorMatcher.find() && PasswordValidatorMatcher.find()) {
                    if (password.getText().toString().equals(confirmPassword.getText().toString())) {
                        postUser();
                    } else {
                        Toast.makeText(getApplicationContext(), "Passwords doesn't Match ", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                    Toast.makeText(getApplicationContext(), "Email or password is not Available", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void postUser()
    {

        JsonObject obj1 = new JsonObject();
        obj1.addProperty("email" , Email.getText().toString());
        obj1.addProperty("name" , Name.getText().toString());
        obj1.addProperty("password" , password.getText().toString());
        obj1.addProperty("phoneNum" , PhoneNum.getText().toString());


        Call<JsonObject> call = mainAPI.addUser(obj1);
        call.enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful())
                {
                    JsonObject obj = response.body();
                    if(obj.get("code").getAsString().equals("200"))
                    {
                        Controler.Token = obj.get("token").getAsString();
                        Controler.isLogin = true;
                        startActivity(new Intent(MainActivity.activity , MainActivity.class));
                        Toast.makeText(getApplicationContext() , "Welcome Now please login " , Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext() , obj.get("msg").getAsString() , Toast.LENGTH_SHORT).show();
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