package com.ghb.mydemo.tablayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * (what to do)
 * Created by lakewave on 2016/8/24.
 */
public class TabAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 6;
    private String[] title = {"今日精选","要闻","三字经","唐诗三百首","余罪","tara"};
    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.newInstance(position+1);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
