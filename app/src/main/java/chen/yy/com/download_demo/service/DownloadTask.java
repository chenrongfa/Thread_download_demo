package chen.yy.com.download_demo.service;/**
 * download_demo
 * Created by chenrongfa on 2017/3/29
 * email:18720979339@163.com
 * qq:952786280
 * company:yy
 */

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import chen.yy.com.download_demo.bean.DownloadInfo;
import chen.yy.com.download_demo.bean.ThreadInfo;
import chen.yy.com.download_demo.db.DbHelperManager;

/**
 * download_demo
 * Created by chenrongfa on 2017/3/29
 * email:18720979339@163.com
 * qq:952786280
 * company:yy
 */
public class DownloadTask {
	private static final String TAG = "DownloadTask";
	private DownloadInfo mDl;
	private long start;
	private Context mContext;

	public boolean isPause() {
		return isPause;
	}

	public void setPause(boolean pause) {
		isPause = pause;
	}

	private boolean isPause;
	private long finished = 0;

	public DownloadTask(Context context, DownloadInfo downloadInfo) {
		mDl = downloadInfo;
		mContext = context;
	}

	public void download() {
		ThreadInfo threadInfo = new ThreadInfo();
		threadInfo.setId(0);
		threadInfo.setUrl(mDl.getUrl());
		threadInfo.setStart(0);
		threadInfo.setEnd(mDl.getLength());
		List<ThreadInfo> threadInfos = DbHelperManager.getInstance().getMdbImpl().queryThread
				(threadInfo);
		if (threadInfos.size() == 0) {
			DbHelperManager.getInstance().getMdbImpl().insertThread(threadInfo);

		} else {
			threadInfo = threadInfos.get(0);
		}
		new DownloadThread(threadInfo).start();

	}

	class DownloadThread extends Thread {
		private ThreadInfo mInfo;
		private InputStream inputStream;
		private RandomAccessFile rf;

		public DownloadThread(ThreadInfo info) {
			mInfo = info;
		}

		@Override
		public void run() {

			try {
				URL url = new URL(mDl.getUrl());
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setConnectTimeout(3000);
				connection.setRequestMethod("GET");
				start = mInfo.getStart() + mInfo.getFinished();
				connection.setRequestProperty("Range", "bytes= " + start + "-" + mDl.getLength());
				connection.connect();

				finished = start;

				Log.e(TAG, "run: " + connection.getResponseCode());
				//定义广播
				Intent intent = new Intent(DownloadService.UPDATE);
				if (connection.getResponseCode() == HttpURLConnection.HTTP_PARTIAL) {
					//得到输入流和创建输出流
					inputStream = connection.getInputStream();
					int len = 0;
					File file = new File(DownloadService.PATH, mDl.getFileName());
					rf = new RandomAccessFile(file, "rwd"
					);
					//设置写入的位置
					rf.seek(start);
					byte btn[] = new byte[1024 * 3];
					long startTime = System.currentTimeMillis();

					while ((len = inputStream.read(btn)) != -1) {
						rf.write(btn, 0, len);
						finished += len;

						long endTime = System.currentTimeMillis();
						//0.5s发一次进度广播
						if (endTime - startTime > 500) {
							Log.e(TAG, "run: fasong" +mDl.getLength());
							startTime = endTime;
							intent.putExtra("progress", finished * 100 / mDl.getLength());
							mContext.sendBroadcast(intent);
						}
						if (isPause) {
							//保存数据
							mInfo.setStart(finished);
							DbHelperManager.getInstance().getMdbImpl().updateThread(mInfo);
							return;
						}
					}

				}
				//下载完成删除数据
				Log.e(TAG, "run: finished"+finished );
				intent.putExtra("progress", finished * 100 / mDl.getLength());
				mContext.sendBroadcast(intent);
				DbHelperManager.getInstance().getMdbImpl().deleteThread(mInfo);
			} catch (IOException e) {
				e.printStackTrace();
				Log.e(TAG, "run: " + e.toString());
			} finally {
				//关闭资源
				try {
					if (inputStream != null)
						inputStream.close();
					if (rf != null)
						rf.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
