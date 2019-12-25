package edu.net.searchEngine.elasticsearch.dao.impl;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;

import edu.net.searchEngine.elasticsearch.EsClient;
import edu.net.searchEngine.elasticsearch.dao.EsSuggestDao;

public class EsSuggest implements EsSuggestDao{
	private TransportClient client;
	
	public EsSuggest() throws UnknownHostException {
		this.client=EsClient.getTransportClient();
	}
	
	/**
	 * 获取至多10条搜索建议
	 * @param prefix 搜索建议的前缀
	 * @return 至多10条搜索建议
	 */
	public List<String> getSuggest(String prefix){
		List<String> list=null;
		SearchRequestBuilder searchRequestBuilder;
		int size=10;
		
		//从历史搜索这获取搜索建议
		searchRequestBuilder=client.prepareSearch(EsClient.suggestName);
		list=this.returnSuggest(searchRequestBuilder, prefix, "text", size);
		
		//当建议不足10条时从索引库中获取剩余建议
		if(list.size()<10) {
			searchRequestBuilder=client.prepareSearch(EsClient.indexName);
			size-=list.size();
			List<String> temp=this.returnSuggest(searchRequestBuilder, prefix, "title.suggest", size);
			if(!temp.isEmpty()) {
				list.addAll(temp);
			}
		}
	
		return list;
	}
	
	private List<String> returnSuggest(SearchRequestBuilder searchRequestBuilder,String prefix,String field,int size){
		List<String> suggestList=new ArrayList<String>();
		CompletionSuggestionBuilder completionSuggestionBuilder = new CompletionSuggestionBuilder(field);
		// 前缀查询 每次返回最多10条数据
		completionSuggestionBuilder.prefix(prefix).size(size);
		// "mysuggest"自定义名字
		
		System.out.println(completionSuggestionBuilder);
		
		SuggestBuilder suggestBuilder = new SuggestBuilder().addSuggestion("mysuggest",
				completionSuggestionBuilder);
		SearchResponse searchResponse = searchRequestBuilder.suggest(suggestBuilder).execute().actionGet();
		// 保存es返回结果
		List<? extends Suggest.Suggestion.Entry<? extends Suggest.Suggestion.Entry.Option>> list = searchResponse
				.getSuggest().getSuggestion("mysuggest").getEntries();
		if (list == null) {
			return suggestList;
		} else {
			// 转为list保存结果字符串
			for (Suggest.Suggestion.Entry<? extends Suggest.Suggestion.Entry.Option> e : list) {
				for (Suggest.Suggestion.Entry.Option option : e) {
					suggestList.add(option.getText().toString());
				}
			}
		}
		return suggestList;
	}
	
	/**
	 * 关闭client，释放资源
	 */
	public void close() {
		EsClient.closeTransportClient();
	}
}
