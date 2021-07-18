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

public class EditProfile extends AppCompatActivity {

    public MainAPI mainAPI;
    public Button confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        confirmButton = findViewById(R.id.editProfileButton);
        Retrofit test2 = new Retrofit.Builder().baseUrl(Controler.url)
                .addConverterFactory(GsonConverterFactory.create()).build();

        mainAPI = test2.create(MainAPI.class);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editUser();
            }
        });

    }

    private void editUser() {


        JsonObject obj1 = new JsonObject();
        EditText editName = findViewById(R.id.editName);
        EditText editPhone = findViewById(R.id.editPhonenum);
        EditText editPassword = findViewById(R.id.editPassword);
        EditText confirmEditPassword = findViewById(R.id.confirmPassEdit);

        if (editPassword.getText().toString().equals(confirmEditPassword.getText().toString())) {
            String finalEditName;
            if (editName.getText().toString().isEmpty())
                finalEditName = Controler.Name;
            else
                finalEditName = editName.getText().toString();

            String finalEditPhone;
            if (editPhone.getText().toString().isEmpty())
                finalEditPhone = Controler.PhoneNum;
            else
                finalEditPhone = editPhone.getText().toString();

            String finalEditPas;
            if (editPassword.getText().toString().isEmpty())
                finalEditPas = Controler.Password;
            else
                finalEditPas = editPassword.getText().toString();

            obj1.addProperty("name", finalEditName);
            obj1.addProperty("phonenumber", finalEditPhone);
            obj1.addProperty("password", finalEditPas);
            obj1.addProperty("token", Controler.Token);

            Call<JsonObject> call = mainAPI.editUser(obj1);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.isSuccessful()) {
                        JsonObject obj = response.body();

                        if (obj.get("code").getAsInt() == 200) {

                            Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
                            confirmButton.setEnabled(false);
                            editName.setText("");
                            editPhone.setText("");
                            editPassword.setText("");
                            confirmEditPassword.setText("");
                        } else {
                            Toast.makeText(getApplicationContext(), obj.get("msg").getAsString(), Toast.LENGTH_SHORT).show();
                        }

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