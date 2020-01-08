package init;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import crawler.SearchResultEntry;
import edu.net.itsearch.crawler.crawlers.UrlCollector;
import edu.net.itsearch.crawler.dao.impl.CrawlListWriter;
import edu.net.itsearch.elasticsearch.dao.impl.EsCreatIndex;
import edu.net.itsearch.elasticsearch.dao.impl.EsSearch;
import edu.net.itsearch.elasticsearch.dao.impl.EsSuggest;
/**
 * 
 * @author 12858
 * @date 2020/01/08
 */
public class ItSearchInit{
	public static void main(String[] args) {
		String suggestInitDate = "test";
		UrlCollector.setresultWriter(new CrawlListWriter());
		List<SearchResultEntry> re = UrlCollector.crawl();
		System.out.println("本次共爬取到"+re.size()+"条数据");
		System.out.println("正在插入到Elasticsearch...");
		EsCreatIndex creat = new EsCreatIndex();
		try {
			creat.bulkIndex(re);
			System.out.println("插入成功！！！");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("插入失败！！！");
			System.exit(1);
		}
		System.out.println("正在初始化推荐数据...");
		EsSearch initSuggestData = new EsSearch();
		try {
			initSuggestData.inseartSearch("test");
		} catch (IOException e) {
			e.printStackTrace();
		}
		EsSuggest test;
		try {
			test = new EsSuggest();
			if(test.getSuggest(suggestInitDate).size()!=0) {
				System.out.println("初始化推荐数据成功！！！");
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.out.println("初始化推荐数据失败！！！");
		}
		System.out.println("初始化完毕");
	}
}