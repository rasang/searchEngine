package edu.net.searchEngine.crawler.crawlers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import edu.net.searchEngine.crawler.dao.BufferedResultWriterDao;
import edu.net.searchEngine.crawler.dao.impl.LinksJDBCWriter;
import edu.net.searchEngine.crawler.dao.impl.LinksListWriter;

public class UrlCollector {
	public static void main(String[] args) {
		String url = "http://cec.jmu.edu.cn/";
		String cssSelector = ".menu0_0_";
		List<String> menu = null;
		List<String> list = new ArrayList<>();
		BufferedResultWriterDao linkJDBCWriter = new LinksJDBCWriter(200);
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
		LinksListWriter tempListWriter = new LinksListWriter();
		/**
		 * 1. 第一层是Menu，先把Menu的href爬取下来
		 */
		SingleCrawler menuCrawler = new SingleCrawler(url, cssSelector, httpClient, tempListWriter);
		menuCrawler.start();
		try {
			menuCrawler.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		menu = new ArrayList<String>(tempListWriter.getLinks());
		tempListWriter.clear();
		for (String e : menu) {
			System.out.println(e);
		}
		/**
		 * 2. Menu
		 * URL的形式是http://cec.jmu.edu.cn/list.jsp?urltype=tree.TreeTempUrl&wbtreeid=1005
		 * Menu下有我们想要的具体文章的URL，其形式为：http://rsc.jmu.edu.cn/info/1067/4721.htm
		 * list.jsp中还有翻页的URL，其形式为：http://cec.jmu.edu.cn/list.jsp?a2t=7&a2p=2&a2c=10&urltype=tree.TreeTempUrl&wbtreeid=1067
		 * 
		 */
		SingleCrawler[] listCrawler = new SingleCrawler[menu.size()];
		for (int i = 0; i < listCrawler.length; i++) {
			String menuUrl = menu.get(i);
			if(menuUrl.contains("?")) {
				System.out.println(menu.get(i)+"&a2c=1000000");
				listCrawler[i] = new SingleCrawler(menu.get(i)+"&a3c=1000000&a2c=10000000", "a[href~=^info/[0-9]+/[0-9]+\\.htm]", httpClient, tempListWriter);
				listCrawler[i].start();
			}
			else {
				listCrawler[i] = null;
				list.add(menuUrl);
			}
		}
		for(int i = 0; i < listCrawler.length; i++) {
			if(listCrawler[i] != null) {
				try {
					listCrawler[i].join();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
		Set<String> s = new HashSet<>();
		for(String e : tempListWriter.getLinks()) {
			s.add(e);
		}
		System.out.println(s.size());
		for(String e : s) {
			System.out.println(e);
		}
	}
}
