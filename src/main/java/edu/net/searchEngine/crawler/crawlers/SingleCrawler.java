package edu.net.searchEngine.crawler.crawlers;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import edu.net.searchEngine.crawler.dao.ResultWriterDao;

public class SingleCrawler extends Thread {
	private final CloseableHttpClient httpClient;
	private final HttpContext context;
	private final HttpGet httpGet;
	private String cssSelector;
	private String url;
	private ResultWriterDao LinksWriter = null;

	public SingleCrawler(String url, String cssSelector, CloseableHttpClient httpClient,
			ResultWriterDao LinksWriter) {
		this.url = url;
		this.cssSelector = cssSelector;
		this.httpClient = httpClient;
		httpGet = new HttpGet(url);
		this.context = HttpClientContext.create();
		this.LinksWriter = LinksWriter;
	}

	@Override
	public void run() {
		try (CloseableHttpResponse response = httpClient.execute(this.httpGet, this.context)) {
			HttpEntity entity = response.getEntity();
			Document doc = Jsoup.parse(EntityUtils.toString(entity, "utf8"));
			if(this.cssSelector == "") {
				this.LinksWriter.write(this.url, doc);
			}
			else {
				Elements links = doc.select(this.cssSelector);
				for (Element link : links) {
					String newHref = link.attr("href");
					String httpPattern = "^http";
					Pattern p = Pattern.compile(httpPattern);
					Matcher m = p.matcher(newHref);
					if(m.find())
						continue;
					String newUrl = null;
					/**
					 *  判断href是相对路径还是决定路径，以及是否是传参
					 */
					if(newHref.length()>=1 &&  newHref.charAt(0)=='?') {
						newUrl = this.url.substring(0, this.url.indexOf('?')) + newHref;
					}
					else if(newHref.length()>=1 &&  newHref.charAt(0)=='/') {
						Matcher matcher = Pattern.compile("http(s)?://.+?/").matcher(this.url);
						if(matcher.find()) {
							String rootUrl = matcher.group(0);
							newUrl = rootUrl + newHref.substring(1);
						}
						else continue;
					}
					else if(newHref.length()>=1 &&  newHref.charAt(0)!='/'){
						Matcher matcher = Pattern.compile("http(s)?://.+/").matcher(this.url);
						if(matcher.find()) {
							String rootUrl = matcher.group(0);
							newUrl = rootUrl + newHref;
						}
						else continue;
					}
					else continue;
					this.LinksWriter.write(newUrl, doc);
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
