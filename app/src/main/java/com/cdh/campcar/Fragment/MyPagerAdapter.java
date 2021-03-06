package com.cdh.campcar.Fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class  MyPagerAdapter extends FragmentStateAdapter {
    private  static int NUM_ITEMS = 4;
    public int mCount;
    public MyPagerAdapter(FragmentActivity fa, int count) {
        super(fa);
        mCount = count;
    }

    // Returns total number of pages
    @Override
    public int getItemCount() {
        return NUM_ITEMS;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return  Reg01Fragment.getInstance();
            case 1:
                return  Reg02Fragment.getInstance();
            case 2:
                return  Reg03Fragment.getInstance();
            case 3:
                return  Reg04Fragment.getInstance();
            default:
                return  Reg01Fragment.getInstance();
        }
    }


}
