package chen.yy.com.download_demo.db;/**
 * download_demo
 * Created by chenrongfa on 2017/3/29
 * email:18720979339@163.com
 * qq:952786280
 * company:yy
 */

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
public interface DbhelperDao {
	void insertThread(ThreadInfo info);
	void deleteThread(ThreadInfo info);
	List<ThreadInfo> queryThread(ThreadInfo info);
	boolean isExistThread(ThreadInfo info);
	void updateThread(ThreadInfo info);
}
