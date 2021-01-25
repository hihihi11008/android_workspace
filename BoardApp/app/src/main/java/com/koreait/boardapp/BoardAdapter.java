package com.koreait.boardapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/*보여질 데이터가 단순한 텍스트 뿐이라면, ArrayAdapter를 사용하면 된다
* 하지만 listView의 각 아이템에 들어갈 데이터형태는 대부분 복잡하고 복합적으로
* 이뤄져 있기 때문에, 개발자가 정의한 UI를 반영하려면 어댑터를 재정의하면 된다 ..
* 참고) 안드로이드의 Adapter는 자바 Swing의 TableModel과 그 역할이 같다
* */
public class BoardAdapter extends BaseAdapter {
	String TAG = this.getClass().getName();
	MainActivity mainActivity;
	LayoutInflater layoutInflater; //XML을 자바 객체화 시켜주는 객체
	List<String> data = new ArrayList<String>();

	public BoardAdapter(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
		layoutInflater = mainActivity.getLayoutInflater();

		data.add("사과");
		data.add("딸기");
		data.add("복숭아");
		data.add("망고");
		data.add("파인애플");
		data.add("망고스틴");
		data.add("드래곤프룻");
		data.add("메론");
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
	/* 아래의 getView 메서드는 getCount() 메서드의 반환값 만큼 호출된다.
	* 호출하면서, 리스트뷰의 각칸(아이텐) 을 차지하게 될 View를 반환해간다
	* 파라미터1) position은 getCount() 만큼 getView메서드가 호출될때 자동증가 되는 값
	* 파라미터2) old view
	* */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		//convertView란? 리스트뷰의 각 아이템을 차지하는 뷰가 새롭게 태어날때는 convertView(기존 뷰) 값이 존재하지 않는다
		//하지만 일단 태어나고, 화면에서 가려진 후 다시 등장할때는 새로운 아이템이 아니라 기존의 아이템이므로,
		//자신이 부여받았던 뷰주소가 convertView에 실려져 있다.
		//따라서 개발자는 화면에 가려졌다가 다시 보여지는 아이템들에 대해서는 굳이 새로운 뷰를 new하지말고
		//기존에 아이템 본인이 가지고 있었던 old View(기존뷰)를 재사용하는 것이 메모리 효율상 좋다.
		View view =null;
		if (convertView==null) {
			//여기서 리스트뷰에 보여질 하나의 아이템을 이룰 복합뷰를 생성한다
			//false라고 옵션을 줘야 , parent인 ListView가 아닌, 인플레이션 대상 xml의 최상위 뷰그룹이 반환된다. 즉 아이템의 최상위
			View parentView = layoutInflater.inflate(R.layout.board_item, parent, false);
			Log.d(TAG, "parentView is "+parentView);

		}else {
			view = convertView;
		}
		Log.d(TAG, data.get(position)+"의 뷰는 "+ view+ ", parent는 "+parent);
		Log.d(TAG, data.get(position)+"의 convertView 는 "+ convertView);
		return view;
	}

	//REST server에 등록 요청
	public void regist(View view){
		
	}

}
