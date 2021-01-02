package com.cdh.campcar.Fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
/*
이미지 상세보기 및 수정에서 사용
 */
public class ImgPagerAdapter extends FragmentStateAdapter {
    private  static int NUM_ITEMS = 10;
    public int mCount;
    public ImgPagerAdapter(FragmentActivity fa, int count) {
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
                return  Img01Fragment.getInstance();
            case 1:
                return  Img02Fragment.getInstance();
            case 2:
                return  Img03Fragment.getInstance();
            case 3:
                return  Img04Fragment.getInstance();
            case 4:
                return  Img05Fragment.getInstance();
            case 5:
                return  Img06Fragment.getInstance();
            case 6:
                return  Img07Fragment.getInstance();
            case 7:
                return  Img08Fragment.getInstance();
            case 8:
                return  Img09Fragment.getInstance();
            case 9:
                return  Img10Fragment.getInstance();
            default:

                return  Img01Fragment.getInstance();
        }
    }


}
