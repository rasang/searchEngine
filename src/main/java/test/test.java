package test;

import java.util.List;

import edu.net.searchEngine.crawler.crawlers.UrlCollector;
import edu.net.searchEngine.crawler.dao.impl.CrawlListWriter;

public class test{
	public static void main(String[] args) {
		UrlCollector test = new UrlCollector(new CrawlListWriter());
		List eee = test.crawl();
		System.out.println(eee.size());
	}
}