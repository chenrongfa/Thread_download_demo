package chen.yy.com.download_demo.bean;/**
 * download_demo
 * Created by chenrongfa on 2017/3/28
 * email:18720979339@163.com
 * qq:952786280
 * company:yy
 */

import java.io.Serializable;

/**
 *下载信息
 *download_demo
 * Created by chenrongfa on 2017/3/28
 * email:18720979339@163.com
 * qq:952786280
 * company:yy
 */
public class DownloadInfo implements Serializable {
	private static final long serialVersionUID = 4961428384506344710L;
	private  int id;
	private String url;
	private String FileName;
	private long length;
	private int finished;

	public DownloadInfo() {
	}

	@Override
	public String toString() {
		return "DownloadInfo{" +
				"id=" + id +
				", url='" + url + '\'' +
				", FileName='" + FileName + '\'' +
				", length=" + length +
				", finished=" + finished +
				'}';
	}

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}

	public int getFinished() {
		return finished;
	}

	public void setFinished(int finished) {
		this.finished = finished;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFileName() {
		return FileName;
	}

	public void setFileName(String fileName) {
		FileName = fileName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
