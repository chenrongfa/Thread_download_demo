package chen.yy.com.download_demo.bean;/**
 * download_demo
 * Created by chenrongfa on 2017/3/28
 * email:18720979339@163.com
 * qq:952786280
 * company:yy
 */

/**
 *线程类
 *download_demo
 * Created by chenrongfa on 2017/3/28
 * email:18720979339@163.com
 * qq:952786280
 * company:yy
 */
public class ThreadInfo {
	private int id;
	private String url;
	private long start;
	private long end;
	private  int finished;

	public ThreadInfo(int id, String url, long start, long end, int finished) {
		this.id = id;
		this.url = url;
		this.start = start;
		this.end = end;
		this.finished = finished;
	}

	public ThreadInfo() {
	}

	@Override
	public String toString() {
		return "ThreadInfo{" +
				"id=" + id +
				", url='" + url + '\'' +
				", start=" + start +
				", end=" + end +
				", finished=" + finished +
				'}';
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getEnd() {
		return end;
	}

	public void setEnd(long end) {
		this.end = end;
	}

	public int getFinished() {
		return finished;
	}

	public void setFinished(int finished) {
		this.finished = finished;
	}
}
