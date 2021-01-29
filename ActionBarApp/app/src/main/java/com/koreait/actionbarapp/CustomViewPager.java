package com.koreait.actionbarapp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

//오직 액션메뉴에서만 페이지를 전환할 수 있도록 기존 viewPager의 기능중 터치슬라이드를 금지시켜보자
public class CustomViewPager extends ViewPager {
	//우리가 정의한 클래스를 xml에 명시하여 인플레이션 시킬때에느 생성자는 AttributeSet을 준비해놓아야
	//인플레이션 시 xml 속성값들을 받을 수 있다.
	public CustomViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return false;
	}
}
