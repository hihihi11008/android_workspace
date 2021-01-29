package com.study.app0120;

import android.icu.util.Output;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//스프링 웹서버와 통신하기 위한 객체
public class WebConnector extends Thread{
	private String TAG = this.getClass().getName();//클래스명을 담자 로그출력시 구분 태그명으로 쓰려고
	URL url;
	HttpURLConnection con;
	OutputStream os;

	public void getData(){
		try {
			url = new URL("http://192.168.219.100:8889/rest/member");
			con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");

			//서버에서 받은 응답코드 확인
			int code = con.getResponseCode();
			Log.d(TAG,"서버로부터 받은 http응답코드 ="+code);


		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override //독립적으로 실행될 코드를 run에 작성한다
	public void run() {
		getData();
	}
}
