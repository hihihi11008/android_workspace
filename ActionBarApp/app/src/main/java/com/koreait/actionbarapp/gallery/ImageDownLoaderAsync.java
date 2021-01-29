package com.koreait.actionbarapp.gallery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

//매 요청마다 객체가 1:1 대응됨
//대부분 <URL, Void(Integer) ,String(JSON)>
public class ImageDownLoaderAsync extends AsyncTask<String, Void, Bitmap> {
	Gallery gallery;
	GalleryFragment galleryFragment;

	public ImageDownLoaderAsync(Gallery gallery,GalleryFragment galleryFragment){
		this.gallery = gallery;
		this.galleryFragment = galleryFragment;
	}

	//웹서버 요청전 하고싶은 작업은 여기서 (메인쓰레드가 수행 따라서 UI 제어가능
	protected void onPreExecute() {
		super.onPreExecute();
	}

	//웹서버 요청 수행 (메인쓰레드가 수행 따라서 UI 제어불가)
	protected Bitmap doInBackground(String... param) {
		Bitmap bitmap =null;
		try {
			URL url =new URL(param[0]); //이미지가 있는 웹서버주소
			InputStream is = url.openStream();
			bitmap = BitmapFactory.decodeStream(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	//웹서버 요청도중에 하고싶은 작업 (메인쓰레드가 수행 따라서 UI 제어가능)
	protected void onProgressUpdate(Void... values) {
		super.onProgressUpdate(values);
	}

	//웹서버 요청 완료 후에 하고싶은 작업은 여기 (메인쓰레드가 수행 따라서 UI 제어가능)
	protected void onPostExecute(Bitmap bitmap) {
		//생성된 비트맵 Gallery에 넣어, 최종적으로 어댑터가 보유한 리스트에 대입
		gallery.setBitmap(bitmap);//형한테 넘겨받은 갤러리 vo에 비워져있던 Bitmap을 마저 채우는 시점

		//어댑터의 리스트에 채워넣기 !
		galleryFragment.galleryList.add(gallery);
		galleryFragment.galleryAdapter.galleryList= galleryFragment.galleryList;
		
		//핸들러없이도 가능
		galleryFragment.galleryAdapter.notifyDataSetChanged();
		galleryFragment.gridView.invalidate();

	}
}
