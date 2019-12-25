package edu.net.searchEngine.elasticsearch.dao;

import java.io.IOException;
import java.util.List;

import crawler.SearchResultEntry;

public interface EsSerchDao {
	/**
	 * 全文检索
	 * @param queryString 要检索的字符串
	 * @return 检索结果
	 */
	public List<SearchResultEntry> fullTextSerch(String queryString);
	
	/**
	 * 带日期限制的全文检索
	 * @param queryString 搜索字符串
	 * @param startDate 起始日期
	 * @param closingDate 终止日期
	 * @return 检索结果
	 */
	public List<SearchResultEntry> rangeSerch(String queryString,String startDate,String closingDate);
	
	/**
	 * 将搜索请求保存到历史搜索索引中
	 * @param queryString 搜索字符串
	 * @throws IOException
	 */
	public void inseartSearch(String queryString) throws IOException;
	
	/**
	 * 关闭jestClient，释放资源
	 * @throws IOException
	 */
	public void close() throws IOException;
}
