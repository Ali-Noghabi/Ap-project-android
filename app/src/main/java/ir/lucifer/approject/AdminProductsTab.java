package ir.lucifer.approject;


import android.app.Activity;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

//Our class extending fragment
public class AdminProductsTab extends Fragment {
    public static Fragment fragment;
    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        fragment = this;
        return inflater.inflate(R.layout.admin_pros_tab, container, false);


    }

}