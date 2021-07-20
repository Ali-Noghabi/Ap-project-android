package ir.lucifer.approject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;

import java.io.ByteArrayOutputStream;

import br.com.sapereaude.maskedEditText.MaskedEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddProduct extends AppCompatActivity {

    public MainAPI mainAPI;
    public ImageView previewImageView;
    public Button addProButton;
    int SELECT_PICTURE = 200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);


        previewImageView = findViewById(R.id.productimage_adv);
        previewImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });

        addProButton = findViewById(R.id.addpro_adv);
        addProButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postProduct();
            }
        });
        Retrofit test2 = new Retrofit.Builder().baseUrl(Controler.url)
                .addConverterFactory(GsonConverterFactory.create()).build();

        mainAPI = test2.create(MainAPI.class);

    }

    private void postProduct() {

        JsonObject obj1 = new JsonObject();
        EditText proCategory = findViewById(R.id.category_adv);
        EditText proName = findViewById(R.id.productname_adv);
        EditText proPrice = findViewById(R.id.price_adv);
        EditText proDes = findViewById(R.id.description_av);


        previewImageView.buildDrawingCache();
        Bitmap bitmap = previewImageView.getDrawingCache();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
        byte[] image = stream.toByteArray();

        String img_str = Base64.encodeToString(image, 0);

//        String proImageBMasString = new String(b);

        obj1.addProperty("subject", proName.getText().toString());
        obj1.addProperty("price", proPrice.getText().toString());
        obj1.addProperty("description", proDes.getText().toString());
        obj1.addProperty("photoLink", img_str);
        obj1.addProperty("sellerToken", Controler.Token);
        obj1.addProperty("category" , proCategory.getText().toString());
        obj1.addProperty("isStar" , false);

        if (proName.getText().toString().isEmpty() == false && proDes.getText().toString().isEmpty() == false && proPrice.getText().toString().isEmpty() == false) {
            Call<JsonObject> call = mainAPI.addProduct(obj1);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.isSuccessful()) {
                        JsonObject obj = response.body();

                        if (obj.has("ID")) {
                            Toast.makeText(getApplicationContext(), "محصول شما با موفقیت اضافه شد", Toast.LENGTH_SHORT).show();
                            addProButton.setEnabled(false);
                        } else {
                            Toast.makeText(getApplicationContext(), "Error? :)", Toast.LENGTH_SHORT).show();
                        }
//                    passWord.setText("");
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
        else
            Toast.makeText(getApplicationContext(), "Fill Empty Fields", Toast.LENGTH_SHORT).show();
    }

    void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    previewImageView.setImageURI(selectedImageUri);
                }
            }
        }
    }
}