package com.example.fileexplorer.dynamic_tabs;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class DynamicFragmentAdapter extends FragmentStatePagerAdapter {

    private int numOfTabs;
    DynamicFragmentAdapter(FragmentManager fm, int NumOfTabs){
        super(fm);
        this.numOfTabs = NumOfTabs;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        Fragment fragment = DynamicFragment.newInstance();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
