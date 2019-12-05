package com.example.shopmall.utils;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

/**
 * Created by zhy on 16/5/7.
 */
public class NonPageTransformer implements ViewPager.PageTransformer
{
    @Override
    public void transformPage(View page, float position)
    {
        page.setScaleX(0.8f);//hack
    }

    public static final ViewPager.PageTransformer INSTANCE = new NonPageTransformer();
}
