package chen.yy.com.download_demo.db;/**
 * download_demo
 * Created by chenrongfa on 2017/3/29
 * email:18720979339@163.com
 * qq:952786280
 * company:yy
 */

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import chen.yy.com.download_demo.bean.ThreadInfo;

/**
 *
 *download_demo
 * Created by chenrongfa on 2017/3/29
 * email:18720979339@163.com
 * qq:952786280
 * company:yy
 */
public class DbhelperImpl implements  DbhelperDao {
	public static final String TABLE_NAME="thread_info";
	public static final String THREAD_ID="thread_id";
	public static final String URL="url";
	public static final String START="start";
	public static final String END="end";
	public static final String FINISHED="finished";
	public DbhelperImpl(){}

	@Override
	public void insertThread(ThreadInfo info) {
		SQLiteDatabase writableDatabase = DbHelperManager.getInstance().getmHelper()
				.getWritableDatabase();
		ContentValues values=new ContentValues();
		values.put(THREAD_ID,info.getId());
		values.put(URL,info.getUrl());
		values.put(START,info.getStart());
		values.put(FINISHED,info.getFinished());
		values.put(END,info.getEnd());
		writableDatabase.insert(TABLE_NAME,null,values);
	}

	@Override
	public void deleteThread(ThreadInfo info) {
		if (isExistThread(info)){
			SQLiteDatabase writableDatabase = DbHelperManager.getInstance().getmHelper()
					.getWritableDatabase();
			writableDatabase.delete(TABLE_NAME," thread_id=? and url=?", new String[]{info.getId() + "", info.getUrl()});
		}

	}

	@Override
	public List<ThreadInfo> queryThread(ThreadInfo info) {
		SQLiteDatabase writableDatabase = DbHelperManager.getInstance().getmHelper()
				.getWritableDatabase();
		Cursor cursor = writableDatabase.rawQuery("select * from " + TABLE_NAME +
				" where thread_id=? and url=?", new String[]{info.getId() + "", info.getUrl()});
		List<ThreadInfo> threadInfos=new ArrayList<>();
		while (cursor.moveToNext()){
			int thread_id=cursor.getInt(cursor.getColumnIndex(THREAD_ID));
			int start=cursor.getInt(cursor.getColumnIndex(START));
			int end=cursor.getInt(cursor.getColumnIndex(END));
			int finished=cursor.getInt(cursor.getColumnIndex(FINISHED));
			String url=cursor.getString(cursor.getColumnIndex(URL));
			threadInfos.add(new ThreadInfo( thread_id,  url,  start, end, finished));
		}
		cursor.close();
		return threadInfos;
	}

	@Override
	public boolean isExistThread(ThreadInfo info) {
		SQLiteDatabase writableDatabase = DbHelperManager.getInstance().getmHelper()
				.getWritableDatabase();
		Cursor cursor = writableDatabase.rawQuery("select * from " + TABLE_NAME +
				" where thread_id=? and url=?", new String[]{info.getId() + "", info.getUrl()});
		boolean exist= cursor.moveToNext();
		cursor.close();
		return exist;
	}

	@Override
	public void updateThread(ThreadInfo info) {
		SQLiteDatabase wdb = DbHelperManager.getInstance().getmHelper()
				.getWritableDatabase();
		ContentValues values=new ContentValues();
		values.put(THREAD_ID,info.getId());
		values.put(URL,info.getUrl());
		values.put(START,info.getStart());
		values.put(FINISHED,info.getFinished());
		values.put(END,info.getEnd());
		wdb.update(TABLE_NAME,values," thread_id=? and url=?", new String[]{info.getId() + "", info.getUrl()});
	}
}
