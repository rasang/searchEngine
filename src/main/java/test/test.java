package test;

import java.io.IOException;
import java.util.List;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import edu.net.searchEngine.crawler.crawlers.UrlCollector;
import edu.net.searchEngine.crawler.dao.impl.CrawlListWriter;
import edu.net.searchEngine.elasticsearch.EsClient;
import edu.net.searchEngine.gui.SearchJFrame;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.SearchResult.Hit;

public class test{
	public static void main(String[] args) {
		SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
		QueryBuilder queryBuilders=QueryBuilders.termQuery("title", "厦门大学");
		searchSourceBuilder.query(queryBuilders);
		searchSourceBuilder.size(10);
		searchSourceBuilder.from(0);
		Search search=new Search.Builder(searchSourceBuilder.toString())
				.addIndex(EsClient.indexName)
				.addType(EsClient.typeName)
				.build();
		JestClient jestClient=EsClient.getJestClient();
		try {
			SearchResult result=jestClient.execute(search);
			List<Hit<Object, Void>> hits = result.getHits(Object.class);
			System.out.println(hits.size());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}