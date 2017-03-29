package chen.yy.com.download_demo.service;/**
 * download_demo
 * Created by chenrongfa on 2017/3/28
 * email:18720979339@163.com
 * qq:952786280
 * company:yy
 */

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import chen.yy.com.download_demo.bean.DownloadInfo;
import chen.yy.com.download_demo.db.DbHelperManager;

/**
 * download_demo
 * Created by chenrongfa on 2017/3/28
 * email:18720979339@163.com
 * qq:952786280
 * company:yy
 */
public class DownloadService extends Service {
	public static final String DOWNLOAD = "download";
	public static final String STOP = "stop";
	private static final String TAG = "DownloadService";
	public static  final String UPDATE="update";
	public  static final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath()
			+ "/download";
	private static final int INIT = 0;
	private DownloadTask mDt;
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case INIT:
				DownloadInfo info= (DownloadInfo) msg.obj;
					Log.e(TAG, "handleMessage: length="+ info.getLength());
					mDt=new DownloadTask(DownloadService.this,info);
					mDt.download();
						break;

			}
			super.handleMessage(msg);
		}
	};

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (DOWNLOAD.equals(intent.getAction())) {
			DownloadInfo info = (DownloadInfo) intent.getSerializableExtra("down");
			Log.e(TAG, "onStartCommand: " + info.toString());
			//开始线程
			new ThreadDownload(info).start();
		} else if (STOP.equals(intent.getAction())) {
			DownloadInfo info = (DownloadInfo) intent.getSerializableExtra("down");
			Log.e(TAG, "onStartCommand: " + info.toString());
			if (mDt!=null){
				mDt.setPause(true);
			}

		}

		return super.onStartCommand(intent, flags, startId);
	}

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	class ThreadDownload extends Thread{
		DownloadInfo info;
		public ThreadDownload(DownloadInfo info)  {
			this.info=info;
		}

		@Override
		public void run() {
			super.run();
			URL url= null;
			try {
				url = new URL(info.getUrl());
				HttpURLConnection http= (HttpURLConnection) url.openConnection();
				http.setConnectTimeout(3000);
				http.setRequestMethod("GET");
				http.connect();
				long length=0;
				if (http.getResponseCode()==200){
					length=http.getContentLength();
				}
				if (length<=0){
					return;
				}
				File file=new File(PATH);
				if (!file.exists()){
					file.mkdir();
				}
				File file1=new File(file,info.getFileName());
				if (!file1.exists()){
					Log.e(TAG, "run: "+"不存在" );
//					file1.mkdirs();
				}
				if (file1.exists()){
					Log.e(TAG, "run: " );
				}
				RandomAccessFile rf=new RandomAccessFile(file1,"rwd");
				rf.setLength( length);
				info.setLength(length);
				handler.obtainMessage(INIT,info).sendToTarget();


			} catch (MalformedURLException e) {
				e.printStackTrace();
				Log.e(TAG, "run: 2" );
			} catch (IOException e) {
				e.printStackTrace();
				Log.e(TAG, "run: 1" );
			}

		}

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		DbHelperManager.getInstance().close();

	}
}
