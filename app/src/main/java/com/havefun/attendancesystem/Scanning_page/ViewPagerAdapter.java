package com.havefun.attendancesystem.Scanning_page;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;


public class ViewPagerAdapter extends FragmentPagerAdapter {
    private  ArrayList<Fragment> fragmentslist = new ArrayList<>();
    private  ArrayList<String> fragmentstitle = new ArrayList<>();


    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
return fragmentslist.get(position);
    }

    @Override
    public int getCount() {
        return fragmentslist.size();
    }

    public void addFragment(Fragment fragment, String title) {

        fragmentslist.add(fragment);
        fragmentstitle.add(title);
    }

    public CharSequence getPageTitle(int position) {
return fragmentstitle.get(position);
}
}
