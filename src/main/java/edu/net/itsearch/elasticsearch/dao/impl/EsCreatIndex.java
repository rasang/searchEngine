package edu.net.itsearch.elasticsearch.dao.impl;

import java.io.IOException;
import java.util.List;

import crawler.SearchResultEntry;
import edu.net.itsearch.elasticsearch.EsClient;
import edu.net.itsearch.elasticsearch.dao.EsCreatIndexDao;
import io.searchbox.client.JestClient;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.mapping.PutMapping;
/**
 * 
 * @author xingkyh
 * @date 2020/01/08
 */
public class EsCreatIndex implements EsCreatIndexDao{
	private static JestClient jestClient;
	
	public EsCreatIndex() {
		jestClient =EsClient.getJestClient();
		try {
			jestClient.execute(new CreateIndex.Builder(EsClient.indexName).build());
			jestClient.execute(new CreateIndex.Builder(EsClient.suggestName).build());
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.createIndexMapping();
	}
	
	/**
	 * put映射
	 * @throws IOException
	 */
	private void createIndexMapping() {
		String sourceIndex="{\"" + EsClient.typeName + "\":{\"properties\":{"
				+"\"title\":{\"type\":\"text\",\"analyzer\":\"ik_max_word\",\"search_analyzer\":\"ik_smart\""
				+ ",\"fields\":{\"suggest\":{\"type\":\"completion\",\"analyzer\":\"ik_max_word\",\"search_analyzer\":\"ik_smart\"}}}"
				+",\"text\":{\"type\":\"text\",\"index\":\"true\",\"analyzer\":\"ik_max_word\",\"search_analyzer\":\"ik_smart\"}"
				+",\"url\":{\"type\":\"keyword\"}"
				+",\"time\":{\"type\":\"date\"}"
				+ "}}}";
		PutMapping putMappingIndex=new PutMapping.Builder(EsClient.indexName, EsClient.typeName, sourceIndex).build();
		
		String sourceSuggest="{\"" + EsClient.typeName + "\":{\"properties\":{"
				+"\"text\":{\"type\":\"completion\",\"analyzer\":\"ik_max_word\",\"search_analyzer\":\"ik_smart\"}"
				+ "}}}";
		PutMapping putMappingSuggest=new PutMapping.Builder(EsClient.suggestName, EsClient.typeName, sourceSuggest).build();
		
		try {
			jestClient.execute(putMappingIndex);
			jestClient.execute(putMappingSuggest);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 关闭jestClient
	 * @throws IOException
	 */
	@Override
	public void close() throws IOException{
		if(jestClient!=null) {
			EsClient.closeJestClient();
		}
	}
	
	/**
	 * 插入数据
	 * @param e
	 * @throws IOException 
	 */	
	@Override
	public void insertIndex(SearchResultEntry webpage) throws IOException {
		Index index=new Index.Builder(webpage).index(EsClient.indexName).type(EsClient.typeName).build();
		jestClient.execute(index);
	}
	
	/**
	 * 批量插入数据
	 * @param list
	 * @throws IOException
	 */	
	@Override
	public void bulkIndex(List<SearchResultEntry> list) throws IOException {
		Bulk.Builder bulk=new Bulk.Builder();
		for(SearchResultEntry e:list) {
			Index index=new Index.Builder(e).index(EsClient.indexName).type(EsClient.typeName).build();
			bulk.addAction(index);
		}
		jestClient.execute(bulk.build());
	}
	
	
}
