package chen.yy.com.download_demo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import chen.yy.com.download_demo.bean.DownloadInfo;
import chen.yy.com.download_demo.db.DbHelperManager;
import chen.yy.com.download_demo.service.DownloadService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
	private TextView mTv_file_name;
	private ProgressBar mProgressBar;
	private Button mDownload;
	private Button mStop;
	private DownloadInfo info;
	private ProgressBroadcast mPbBroadcast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//初始化数据库管理
		DbHelperManager.init(this);
		initView();
	}

	private void initView() {
		mTv_file_name = (TextView) findViewById(R.id.tv_file_name);
		mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
		mDownload = (Button) findViewById(R.id.download);
		mStop = (Button) findViewById(R.id.stop);
		mDownload.setOnClickListener(this);
		mStop.setOnClickListener(this);
		mProgressBar.setMax(100);
		info = new DownloadInfo();
		info.setId(0);
		info.setFinished(0);
		info.setUrl("http://p.gdown.baidu.com/34db6842f07b4ef2ac0a384f926836968cfb9ab88d2ba53060809f740bf50f9a78bd9be3afde64cbd46f6a37dc28f90812375edc452b91467539ecd85d8e710889dfd9e943c40280b34ace2907d733e9ea9f72168ff3497e75179c06c2a4c9c5e5d052c2503182945e16a0cf2e8a2c59a591a021307934e748a1f3054aa7ddf6e1b88d79380fa6aa891612d5ca1a66b166e8444fbad44cd232b083e522373b471538926fee90cd00fab243037ec23fb74c122bb3ad0f53384978b9fd2db04186fa8011d9cdedf4f408a0227855813a0373acc2b908d22123c0ff1dafcfe1b2f943950ad963ba421dc5fd5eba2c31286b485157e87bdc0be8");
		info.setFileName("test.apk");
		mPbBroadcast=new ProgressBroadcast();
	}


	private static final String TAG = "MainActivity";

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		intent.putExtra("down", info);
		intent.setClassName(this,DownloadService.class.getName());
		if (mStop == v) {
			Log.e(TAG, "onClick: ");
			intent.setAction(DownloadService.STOP);

		} else if (mDownload == v) {
			intent.setAction(DownloadService.DOWNLOAD);
			//注册广播
			IntentFilter intentFilter=new IntentFilter(DownloadService.UPDATE);
			registerReceiver(mPbBroadcast,intentFilter);
		}
		startService(intent);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mPbBroadcast);
	}

	class ProgressBroadcast extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.e(TAG, "onReceive:12 " );
		if (DownloadService.UPDATE.equals(intent.getAction())){
			long progress = intent.getLongExtra("progress", 0);
			Log.e(TAG, "onReceive: 00"+progress );

			mProgressBar.setProgress((int) progress);
		}
	}
}
}
