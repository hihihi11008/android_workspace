package com.koreait.viewpagertest;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class ViewPagerAdapter extends FragmentPagerAdapter {
	Fragment[] fragments = new Fragment[3];


	public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
		super(fm, behavior);
		fragments[0]=new YellowFragment();
		fragments[1]=new RedFragment();
		fragments[2]=new BlueFragment();
	}
	public int getCount() {
		return fragments.length;
	}

	public Fragment getItem(int position) {
		return fragments[position];
	}
}

