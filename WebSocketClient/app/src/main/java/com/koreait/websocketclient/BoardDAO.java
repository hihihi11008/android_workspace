package com.koreait.websocketclient;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

public class BoardDAO {
	String TAG = this.getClass().getName();
	String ip="192.168.219.102";
	int port=7777;
	Gson gson= new Gson();
	MainActivity mainActivity;

	public BoardDAO(MainActivity mainActivity){
		this.mainActivity = mainActivity;
	}
	//목록
	public List selectAll() throws BoardUpdateException{
		//쓰레드 작업 후 ,UI제어 요청
		String uri="/board";
		BufferedReader buffr=null;
		try {
			URL url = new URL("http://"+ip+":"+port+uri);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");

			//연결이 끊어지기 전에 스트림으로 데이터 가져오기
			buffr = new BufferedReader(new InputStreamReader(con.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String data = null;//한줄을 받을 임시 데이터

			while (true){
				data = buffr.readLine();
				if (data==null)break;
				sb.append(data);
			}
			int code= con.getResponseCode();//요청 및 응답
			if (code!=200){
				throw new BoardUpdateException("목록 조회 실패 ");
			}
			//제이슨에 파싱하여, 자바의 객체화
			Log.d(TAG,"sb.toString() =" +sb.toString());
			JSONObject jsonObject = new JSONObject(sb.toString());
			JSONArray jsonArray =(JSONArray)jsonObject.get("data");
			//jsonArray--> Java Object List
			ArrayList<Board> boardList = new ArrayList<Board>();
			for(int i=0; i< jsonArray.length();i++){
				JSONObject json = (JSONObject)jsonArray.get(i);
				Board board = gson.fromJson(json.toString(), Board.class);
				Log.d(TAG,"writer is "+board.getWriter());
				boardList.add(board);

				//BoardAdapter
			}
			Log.d(TAG,"리스트 사이즈는? "+boardList.size());
			//아래의 두 작업을 핸들러에게 부탁// 어댑터의 List값을 변경// 리스트뷰 갱신
			Message message = new Message();
			Bundle bundle = new Bundle();
			bundle.putParcelableArrayList("boardList", boardList);
			message.setData(bundle);
			mainActivity.handler.sendMessage(message);

		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}finally {
			if (buffr!=null){
				try {
					buffr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	//상세보기
	public Board select(){
		return null;
	}
	//등록
	//수정
	public void edit(Board board) throws BoardUpdateException{
		String uri="/board";
		BufferedWriter buffw=null;
		try {
			URL url  =new URL("http://"+ip+":"+port+uri);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("PUT");
			con.setRequestProperty("Content-Type","application/json;charset=utf-8");
			con.setDoOutput(true);//서버에 데이터를 보낼때는 이 옵션을 줘야함 (POST, PUT)

			buffw = new BufferedWriter(new OutputStreamWriter(con.getOutputStream(),"UTF-8"));
			String jsonString = gson.toJson(board);
			buffw.write(jsonString);
			buffw.flush();

			int code = con.getResponseCode();//요청 및 응답
			if (code!=200){
				throw new BoardUpdateException("수정실패 ");
			}
			//서버에 수정했음을 알려주자
			SocketMessage socketMessage = new SocketMessage();
			socketMessage.setRequestCode("update");
			socketMessage.data=jsonString;
			mainActivity.myWebSocketClient.sendMsg(socketMessage);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (buffw!=null){
				try {
					buffw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	//삭제
}
