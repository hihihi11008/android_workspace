package com.koreait.pageapp.intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.koreait.pageapp.R;

public class FormActivity extends AppCompatActivity implements View.OnClickListener{
	String TAG = this.getClass().getName();
	EditText t_id,t_pass,t_name;
	private static final int REQUEST_CODE=1;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_form);

		//위젯 버튼과 리스너와의 연결
		Button bt_send = (Button)findViewById(R.id.bt_send);
		Button bt_dial = (Button)findViewById(R.id.bt_dial);

		t_id=(EditText)findViewById(R.id.t_id);
		t_pass=(EditText)findViewById(R.id.t_pass);
		t_name=(EditText)findViewById(R.id.t_name);

		bt_send.setOnClickListener(this);
		bt_dial.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		Log.d(TAG, "나눌렀오?");
		if (v.getId()==R.id.bt_send){
			send();
		}else if(v.getId()==R.id.bt_dial) {
			callPhone();
		}else if (v.getId()==R.id.bt_receive){
			sendAndGet();
		}
	}

	//다른 액티비티를 호출하자!!
	public void send(){
		//아래와 같이 대상 클래스를 정확히 명시하는 인텐트 사용법을 가리켜 명시적(explicit) 인텐트라 한다
		Intent intent = new Intent(this, ReceiveActivity.class);

		//intent 는 jsp의 request, session,application객체처럼 데이터를 심을 수 있다.
		Member member = new Member();
		member.setId(t_id.getText().toString());
		member.setPass(t_pass.getText().toString());
		member.setName(t_name.getText().toString());

		//인텐트에 데이터 심기
		Bundle bundle = new Bundle();
		bundle.putParcelable("member", member);
		intent.putExtra("data",bundle);

		startActivity(intent);
	}

	public void callPhone(){
		//우리가 제작중인 현재 앱안에 있는 액티비티가 아니라, 외부 앱의 액티비티명..?
		Intent intent = new Intent(Intent.ACTION_DIAL);
	}

	//다른 액티비티를 호출하되, 다시전달받을 것을 염두해두고 코드를 작성
	public void sendAndGet(){
		Intent intent = new Intent(this, ResultActivity.class);
		//전달할 데이터 구성
		Member member = new Member();

		member.setId(t_id.getText().toString());
		member.setPass(t_pass.getText().toString());
		member.setName(t_name.getText().toString());

		Bundle bundle = new Bundle();
		bundle.putParcelable("member", member);
		intent.putExtra("data", bundle);

		//그냥 출발이 아니라 , 결과를 받아올 것을 염두해둔 출발
		//매개변수: 전달할 데이터, 요청구분코드
		startActivityForResult(intent, REQUEST_CODE);
	}

	//호출자가 전달한 데이터 받기 ! 이미 정해진 메서드 오버라이드 하자
	//requestCode 매개변수 : 내가보냈던 통신 요청 코드(1)
	//아래의 메서드에서 전달된 코드와 내가 보낸 코드가 일치한 경우만, 통신에 방해요소가 없는 무결성 응답임
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (REQUEST_CODE==requestCode){//보장받은 응답
			if (resultCode==this.RESULT_OK){//성공의 응답이라면
				Bundle bundle =data.getBundleExtra("data");
				Member member = (Member)bundle.getParcelable("member");

				t_id.setText(member.getId());
				t_pass.setText(member.getPass());
				t_name.setText(member.getName());
			}
		}
	}
}