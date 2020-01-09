package edu.net.itsearch.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import crawler.SearchResultEntry;
import edu.net.itsearch.elasticsearch.EsClient;
import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * 
 * @author xingkyh
 */
public class EsGuiSearch {
	private JestClient jestClient;
	
	public EsGuiSearch() {
		this.jestClient=EsClient.getJestClient();
	}
	
	/**
	 * 全文检索
	 * 
	 * @param queryString 搜索字符串
	 * @return 检索结果
	 */
	public List<SearchResultEntry> fullTextSerch(String queryString) {
		// 声明一个搜索请求体
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.must(QueryBuilders.queryStringQuery(queryString));

		searchSourceBuilder.query(boolQueryBuilder);
		// 设置分页
		searchSourceBuilder.from(0);
		searchSourceBuilder.size(800);

		// 构建Search对象
		Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex(EsClient.indexName)
				.addType(EsClient.typeName).build();
		SearchResult searchResult = null;
		try {
			searchResult = jestClient.execute(search);
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<SearchResultEntry> list=new ArrayList<SearchResultEntry>();
		List<SearchResult.Hit<SearchResultEntry, Void>> hits = searchResult.getHits(SearchResultEntry.class);
		for (SearchResult.Hit<SearchResultEntry, Void> hit : hits) {
			list.add(hit.source);
		}
		return list;
	}
	
	public void close() throws IOException {
		EsClient.closeJestClient();
	}
}
