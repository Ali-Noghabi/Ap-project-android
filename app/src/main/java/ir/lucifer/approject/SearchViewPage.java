package ir.lucifer.approject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SearchViewPage extends AppCompatActivity {
    public Spinner categorySpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view_page);

        categorySpinner = findViewById(R.id.categorySVP);
        String array_spinner[] = new String[10];
        array_spinner[0] = "CAR";
        array_spinner[1] = "CARPET";
        array_spinner[2] = "HOME";
        array_spinner[3] = "PC";
        array_spinner[4] = "LAPTOP";

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, array_spinner);
        categorySpinner.setAdapter(adapter);


    }
}