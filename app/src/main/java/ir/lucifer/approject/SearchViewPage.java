package ir.lucifer.approject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class SearchViewPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view_page);

//        String list[] = {"item1" , "item2","item3" , "item4"};
        ArrayList<Product> list = new ArrayList<>();
        list.add(new Product("laptop" , "2000"));
        list.add(new Product("car" , "1000"));
        list.add(new Product("pc" , "500"));
        list.add(new Product("plant" , "100"));
        list.add(new Product("mamad" , "moft"));
        list.add(new Product("mamad" , "moft"));
        list.add(new Product("gg" , "asdas"));
        list.add(new Product("mamad" , "moft"));
        list.add(new Product("mamad" , "moft"));
        list.add(new Product("mamad" , "moft"));
        list.add(new Product("mamad" , "moft"));
        list.add(new Product("mamad" , "moft"));
        list.add(new Product("mamad" , "moft"));
        list.add(new Product("mamad" , "moft"));
        list.add(new Product("mamad" , "moft"));
        list.add(new Product("mamad" , "moft"));
        list.add(new Product("mamad" , "moft"));
        RecyclerView recyclerView = findViewById(R.id.RecyclerViewSVP);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MyRecyclerViewAdapter myRecyclerViewAdapter = new MyRecyclerViewAdapter(this,list);
        recyclerView.setAdapter(myRecyclerViewAdapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_viewpage_menu, menu);
        return true;
    }
}