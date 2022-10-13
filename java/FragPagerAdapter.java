package com.example.mapapap;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class FragPagerAdapter extends FragmentStateAdapter {

    private final int mSetItemCount = 3; // 프래그먼트 개수 지정

    public FragPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        int viewIdx = getRealPosition(position);
        switch (viewIdx){
            case 0:{return new framslide1();}
            case 1:{return new framslide2();}
            case 2:{return new framslide3();}
            default:{ return new framhome();}
        }
    }
    public int getRealPosition(int _iPosition){
        return _iPosition % mSetItemCount;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

}
