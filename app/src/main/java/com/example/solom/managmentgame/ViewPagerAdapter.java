package com.example.solom.managmentgame;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import com.example.solom.managmentgame.dataLayer.Game;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    private final List<Drawable> mFragmentIconList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
//        Drawable image = mFragmentIconList.get(position);
//        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
//        SpannableString sb = new SpannableString(" " + mFragmentTitleList.get(position));
//        ImageSpan imageSpan = new ImageSpan(image);
//        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return mFragmentTitleList.get(position);
    }

    public void addFragment(Fragment fragment, String title, Drawable drawable) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
        mFragmentIconList.add(drawable);
    }

    public void changeFragment(int i, Fragment fragment, String title, Drawable drawable){
        mFragmentList.set(i, fragment);
        mFragmentTitleList.set(i, title);
        mFragmentIconList.set(i, drawable);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }


}
