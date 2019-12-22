package edu.net.searchEngine.crawler.crawlers;

import java.io.IOException;
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
		List<String> list = null;
		BufferedResultWriterDao linkJDBCWriter = new LinksJDBCWriter(400);
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
				listCrawler[i] = new SingleCrawler(menu.get(i)+"&a3c=1000000&a2c=10000000", "a[href~=^info/[0-9]+/[0-9]+\\.htm]", httpClient, tempListWriter);
				listCrawler[i].start();
			}
			else {
				listCrawler[i] = null;
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
		list = new ArrayList<>(tempListWriter.getLinks());
		tempListWriter.clear();
		/**
		 * 
		 */
		SingleCrawler[] documentCrawler = new SingleCrawler[list.size()];
		for (int i = 0; i < documentCrawler.length; i++) {
			String menuUrl = list.get(i);
			documentCrawler[i] = new SingleCrawler(list.get(i), "", httpClient, linkJDBCWriter);
			documentCrawler[i].start();
		}
		for(int i = 0; i < documentCrawler.length; i++) {
			if(documentCrawler[i] != null) {
				try {
					documentCrawler[i].join();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
		try {
			linkJDBCWriter.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	}
}
