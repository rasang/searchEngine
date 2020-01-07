package test;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import com.alibaba.fastjson.JSON;

import crawler.SearchResultEntry;
import edu.net.searchEngine.crawler.crawlers.UrlCollector;
import edu.net.searchEngine.crawler.dao.impl.CrawlListWriter;
import edu.net.searchEngine.elasticsearch.dao.impl.EsCreatIndex;
import edu.net.searchEngine.elasticsearch.dao.impl.EsSearch;
import edu.net.searchEngine.elasticsearch.dao.impl.EsSuggest;

public class test{
	public static void main(String[] args) {
		/*CrawlListWriter a = new CrawlListWriter();
		UrlCollector test = new UrlCollector(a);
		List<SearchResultEntry> re = test.crawl();
		System.out.println(re.size());
		EsCreatIndex c = new EsCreatIndex();
		try {
			c.bulkIndex(re);;
			System.out.println(1);
			c.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
		/*EsSearch test = new EsSearch();
		try {
			test.inseartSearch("zheng");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		/*
		EsSuggest test;
		try {
			test = new EsSuggest();
			System.out.println(JSON.toJSONString(test.getSuggest("郑")));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		System.out.println(java.net.URLEncoder.encode("@@测试test"));
	}
}