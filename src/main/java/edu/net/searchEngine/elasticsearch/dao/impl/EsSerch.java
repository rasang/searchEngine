package edu.net.searchEngine.elasticsearch.dao.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;

import crawler.SearchResultEntry;
import edu.net.searchEngine.elasticsearch.EsClient;
import edu.net.searchEngine.elasticsearch.dao.EsSerchDao;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

public class EsSerch implements EsSerchDao{
	private JestClient jestClient;
	private boolean isDateRangeQuery=false;
	private String startDate=null;
	private String closingDate=null;
	
	public EsSerch(){
		jestClient=EsClient.getJestClient();
	}
	
	/**
	 * 全文检索
	 * @param queryString 搜索字符串
	 * @return 检索结果
	 */
	public List<SearchResultEntry> fullTextSerch(String queryString) {
		//声明一个搜索请求体
		SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
		
		BoolQueryBuilder boolQueryBuilder=QueryBuilders.boolQuery();
		boolQueryBuilder.must(QueryBuilders.queryStringQuery(queryString));
		
		if(this.isDateRangeQuery) {
			QueryBuilder queryBuilder = QueryBuilders  
					.rangeQuery("time")  
					.gte(this.startDate)  
					.lte(this.closingDate)  
					.includeLower(true)  
					.includeUpper(true);//区间查询 
			boolQueryBuilder=boolQueryBuilder.filter(queryBuilder);
		}
		
		searchSourceBuilder.query(boolQueryBuilder);
		
		//设置高亮字段
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("title");
        highlightBuilder.field("text");
        highlightBuilder.preTags("<span style=\"color:red;\">").postTags("</span>");
        highlightBuilder.fragmentSize(200);
        searchSourceBuilder.highlighter(highlightBuilder);
		
		//设置分页
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(800);
        
        //构建Search对象
        Search search=new Search.Builder(searchSourceBuilder.toString())
        		.addIndex(EsClient.indexName)
        		.addType(EsClient.typeName)
        		.build();
        SearchResult searchResult = null;
        try {
            searchResult = jestClient.execute(search);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.storageList(searchResult);
	}
	
	/**
	 * 带日期限制的全文检索
	 * @param queryString 搜索字符串
	 * @param startDate 起始日期
	 * @param closingDate 终止日期
	 * @return 检索结果
	 */
	public List<SearchResultEntry> rangeSerch(String queryString,String startDate,String closingDate){
		this.isDateRangeQuery=true;
		this.startDate=startDate;
		this.closingDate=closingDate;
		List<SearchResultEntry> list=this.fullTextSerch(queryString);
		this.isDateRangeQuery=false;
		return list;
	}
	
	private List<SearchResultEntry> storageList(SearchResult sr){
		List<SearchResultEntry>list=new ArrayList<SearchResultEntry>();
		List<SearchResult.Hit<SearchResultEntry, Void>> hits = sr.getHits(SearchResultEntry.class);
		for (SearchResult.Hit<SearchResultEntry, Void> hit : hits) {
			SearchResultEntry souce = hit.source;
			// 获取高亮后的内容
			Map<String, List<String>> ma = hit.highlight;
			if (ma != null) {
				List<String> title = ma.get("title");
				if (title != null) {
					souce.setTitle(title.get(0));
				}
				List<String> text = ma.get("text");
				if (text != null) {
					souce.setText(text.get(0));
				}
			}
			SearchResultEntry temp = new SearchResultEntry();
			temp.setText(souce.getText());
			temp.setTitle(souce.getTitle());
			temp.setUrl(souce.getUrl());
			temp.setTime(souce.getTime());
			list.add(temp);
		}
		return list;
	}
	
	/**
	 * 将搜索请求保存到历史搜索索引中
	 * @param queryString 搜索字符串
	 * @throws IOException
	 */
	public void inseartSearch(String queryString) throws IOException {
		HistorySearch historySearch=new HistorySearch(queryString);
		Index index=new Index.Builder(historySearch).index(EsClient.suggestName).type(EsClient.typeName).build();
		jestClient.execute(index);
	}
	
	/**
	 * 关闭jestClient
	 * @throws IOException 
	 */
	public void close() throws IOException {
		EsClient.closeJestClient();
	}
}

class HistorySearch{
	private String text;

	public HistorySearch(String text) {
		super();
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}