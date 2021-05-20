package com.example.myapplication.ui.kisisel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class PageAdapter  extends FragmentStatePagerAdapter {
    ArrayList<Fragment> mFragmentsList;
    ArrayList<String> mTitleList;

    public PageAdapter(ArrayList<String> titleList, ArrayList<Fragment> fragmentsList, FragmentManager fm, int behavior) {
        super(fm, behavior);
        mTitleList = titleList;
        mFragmentsList = fragmentsList;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragmentsList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentsList.size();
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        return mTitleList.get(position);
    }
}
