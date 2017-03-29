package chen.yy.com.download_demo.db;/**
 * download_demo
 * Created by chenrongfa on 2017/3/29
 * email:18720979339@163.com
 * qq:952786280
 * company:yy
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *
 *download_demo
 * Created by chenrongfa on 2017/3/29
 * email:18720979339@163.com
 * qq:952786280
 * company:yy
 */
public class Dbhelper extends SQLiteOpenHelper{
	public  static final String DB_NAME="download_db";
	public static final int  VERSION=1;
	public static final String CREATE_SQL="create table thread_info"
			+" ( id integer primary key, thread_id integer,url text," +
			" start integer ,end integer,finished integer)";
	public static final String DROP_SQL="drop table thread_info if exist thread_info";
	public Dbhelper(Context context
			) {
		super(context, DB_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_SQL);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(DROP_SQL);
	}
}
