package test;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import edu.net.searchEngine.crawler.crawlers.SingleCrawler;
import edu.net.searchEngine.crawler.dao.BufferedResultWriterDao;
import edu.net.searchEngine.crawler.dao.impl.LinksListWriter;

public class test {
	static Queue<String> q = new LinkedList<>();
	public static void findFile(Path dir,String fileName) {
		try(DirectoryStream<Path> entries = Files.newDirectoryStream(dir)) {
			for(Path entry : entries) {
				if(entry.toFile().isDirectory()) {
					q.add(entry.toAbsolutePath().toString());
				}
				//System.out.println(entry.getFileName());
				if(entry.getFileName().toString().equals(fileName)) {
					System.out.println(entry.toAbsolutePath());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		String url = "http://cec.jmu.edu.cn/";
		String cssSelector = ".menu0_0_";
		List<String> menu = null;
		List<String> list = null;
		BufferedResultWriterDao linkJDBCWriter = null;
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
	}

}
