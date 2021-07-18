package ir.lucifer.approject;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

//package com.codewithgolap.tablayout.Adapters;
import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
//import com.codewithgolap.tablayout.Fragments.ChatsFragment;
//import com.codewithgolap.tablayout.Fragments.HomeFragment;
//import com.codewithgolap.tablayout.Fragments.SettingsFragment;
public class AdminAdapter extends FragmentPagerAdapter {
    private Context myContext;
    int totalTabs;
    public AdminAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }
    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                AdminProductsTab homeFragment = new AdminProductsTab();
                return homeFragment;
            case 1:
                AdminSellersTab secondFragment = new AdminSellersTab();
                return secondFragment;
            case 2:
                AdminBestSellerTab thirdFragment = new AdminBestSellerTab();
                return thirdFragment;
            default:
                return null;
        }
    }
    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}