package ir.lucifer.approject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateDeleteProduct extends AppCompatActivity {

    public MainAPI mainAPI;
    public Button updateProduct;
    public Button deleteProduct;
    int SELECT_PICTURE = 200;
    public ImageView editImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_product);

        editImage = findViewById(R.id.productimage_udp);
        updateProduct = findViewById(R.id.updatepro_udp);
        deleteProduct = findViewById(R.id.deletepro_udp);
        Retrofit test2 = new Retrofit.Builder().baseUrl(Controler.url)
                .addConverterFactory(GsonConverterFactory.create()).build();

        mainAPI = test2.create(MainAPI.class);

        updateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProduct();
            }
        });
        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });

    }

    private void editProduct() {


        JsonObject obj1 = new JsonObject();
        EditText editSubject = findViewById(R.id.productname_udp);
        EditText editDes = findViewById(R.id.description_udp);
        EditText editPrice = findViewById(R.id.price_udp);


        editImage.buildDrawingCache();
        Bitmap bitmap = editImage.getDrawingCache();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
        byte[] image = stream.toByteArray();

        String img_str = Base64.encodeToString(image, 0);

        obj1.addProperty("subject", editSubject.getText().toString());
        obj1.addProperty("description", editDes.getText().toString());
        obj1.addProperty("price", editPrice.getText().toString());
        obj1.addProperty("image", img_str);

        obj1.addProperty("ID", 12);

        Call<JsonObject> call = mainAPI.editPro(obj1);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject obj = response.body();

                    if (obj.get("code").getAsInt() == 200) {

                        Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
                        updateProduct.setEnabled(false);
                        editSubject.setText("");
                        editDes.setText("");
                        editPrice.setText("");
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
                    editImage.setImageURI(selectedImageUri);
                }
            }
        }
    }
}