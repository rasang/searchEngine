package edu.net.itsearch.elasticsearch.dao.impl;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;

import edu.net.itsearch.elasticsearch.EsClient;
import edu.net.itsearch.elasticsearch.dao.EsSuggestDao;
/**
 * 
 * @author xingkyh
 * @date 2020/01/08
 */
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
	@Override
	public List<String> getSuggest(String prefix){
		List<String> suggestList=new ArrayList<String>();
		SearchRequestBuilder searchRequestBuilder;
		int size=10;
		
		//从历史搜索这获取搜索建议
		searchRequestBuilder=client.prepareSearch(EsClient.suggestName);
		this.returnSuggest(searchRequestBuilder, suggestList, prefix, "text", size);
		
		
		//当建议不足10条时从索引库中获取剩余建议
		if(suggestList.size()<size) {
			searchRequestBuilder=client.prepareSearch(EsClient.indexName);
			size-=suggestList.size();
			this.returnSuggest(searchRequestBuilder, suggestList, prefix, "title.suggest", size);
		}
		return suggestList;
	}
	
	private void returnSuggest(SearchRequestBuilder searchRequestBuilder,List<String> suggestList,String prefix,String field,int size){
		CompletionSuggestionBuilder completionSuggestionBuilder = new CompletionSuggestionBuilder(field);
		// 前缀查询 每次返回最多size条数据
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
			return;
		} else {
			// 转为list保存结果字符串
			for (Suggest.Suggestion.Entry<? extends Suggest.Suggestion.Entry.Option> e : list) {
				for (Suggest.Suggestion.Entry.Option option : e) {
					String str=option.getText().toString();
					if(!suggestList.contains(str)) {
						suggestList.add(str);
					}
				}
			}
		}
	}
	
	/**
	 * 关闭client，释放资源
	 */	
	@Override
	public void close() {
		EsClient.closeTransportClient();
	}
}
