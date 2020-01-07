package edu.net.searchEngine.elasticsearch.dao;

import java.io.IOException;
import java.util.List;

import crawler.SearchResultEntry;

public interface EsCreatIndexDao {
	/**
	 * 插入一个数据
	 * @param webpage 要插入的数据
	 * @throws IOException
	 */
	public void insertIndex(SearchResultEntry webpage) throws IOException;
	
	/**
	 * 批量插入数据
	 * @param list 要插入的数据
	 * @throws IOException
	 */
	public void bulkIndex(List<SearchResultEntry> list) throws IOException;
	
	/**
	 * 关闭jestClient，释放资源
	 * @throws IOException
	 */
	public void close() throws IOException;
}