package crawler;
/**
 * 
 * @author PlumK
 * @date 2020/01/08
 */
public class SearchResultEntry {
	private String url;
	private String title;
	private String text;
	private String time;
	
	public SearchResultEntry(String url, String title, String text) {
		this.url = url;
		this.title = title;
		this.text = text;
	}
	
	public SearchResultEntry(String url, String title, String text, String time) {
		this.url = url;
		this.title = title;
		this.text = text;
		this.time = time;
	}
	
	public SearchResultEntry() {
		
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
}
