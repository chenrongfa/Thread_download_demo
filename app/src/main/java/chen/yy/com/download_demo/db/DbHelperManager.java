package chen.yy.com.download_demo.db;/**
 * download_demo
 * Created by chenrongfa on 2017/3/29
 * email:18720979339@163.com
 * qq:952786280
 * company:yy
 */

import android.content.Context;

/**
 *
 *download_demo
 * Created by chenrongfa on 2017/3/29
 * email:18720979339@163.com
 * qq:952786280
 * company:yy
 */
public class DbHelperManager {
	private static DbHelperManager db;

	public DbhelperImpl getMdbImpl() {
		return mdbImpl;
	}

	public void setMdbImpl(DbhelperImpl mdbImpl) {
		this.mdbImpl = mdbImpl;
	}

	private DbhelperImpl mdbImpl;

	public Dbhelper getmHelper() {
		return mHelper;
	}

	public void setmHelper(Dbhelper mHelper) {
		this.mHelper = mHelper;
	}

	private Dbhelper mHelper;
	private static Context mContext;
	private DbHelperManager(){
		mHelper=new Dbhelper(mContext);
		mdbImpl=new DbhelperImpl();
	}
	public static DbHelperManager getInstance() {
		if (db==null){
			db=new DbHelperManager();
		}
		return db;
	}
	public static void init(Context context){
		mContext=context;
	}
	public void close(){
		if (mHelper!=null){
			mHelper.close();
		}
	}
}
