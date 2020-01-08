package edu.net.itsearch.elasticsearch.dao;

import java.io.IOException;
import java.util.List;

import crawler.SearchResultEntry;
/**
 * 
 * @author xingkyh
 * @date 2020/01/08
 */
public interface EsSearchDao {
	/**
	 * 返回搜索结果的总条数
	 * @return 总搜索结果条数
	 */
	public long getResultNum();
	/**
	 * 全文检索
	 * @param queryString 要检索的字符串
	 * @param page 页码
	 * @return 检索结果
	 */
	public List<SearchResultEntry> fullTextSerch(String queryString,int page);
	
	/**
	 * 带日期限制的全文检索
	 * @param queryString 搜索字符串
	 * @param page 页码
	 * @param startDate 起始日期
	 * @param closingDate 终止日期
	 * @return 检索结果
	 */
	public List<SearchResultEntry> rangeSerch(String queryString,int page,String startDate,String closingDate);
	
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
