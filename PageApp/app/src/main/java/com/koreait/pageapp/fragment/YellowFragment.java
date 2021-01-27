package com.koreait.pageapp.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.koreait.pageapp.R;


//하나의 화면 일부를 담당하는 Fragment는 일명 작은 액티비티라고도 불린다.
//따라서 액티비티에 생명주기 메서드가 있듯, Fragment 또한 생명주기 메서드가 지원된다.
public class YellowFragment extends Fragment {
	String TAG = this.getClass().getName();

	//프레그먼트가 사용할 디자인 레이아웃 xml
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_yellow,container,false);
		return view;
	}
}
