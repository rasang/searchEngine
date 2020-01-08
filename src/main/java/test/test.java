package test;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import crawler.SearchResultEntry;
import edu.net.searchEngine.crawler.crawlers.UrlCollector;
import edu.net.searchEngine.crawler.dao.impl.CrawlListWriter;
import edu.net.searchEngine.elasticsearch.dao.impl.EsCreatIndex;
import edu.net.searchEngine.elasticsearch.dao.impl.EsSearch;
import edu.net.searchEngine.elasticsearch.dao.impl.EsSuggest;

public class test{
	public static void main(String[] args) {
		EsSuggest test;
		try {
			test = new EsSuggest();
			System.out.println(test.getSuggest("zh").size());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}