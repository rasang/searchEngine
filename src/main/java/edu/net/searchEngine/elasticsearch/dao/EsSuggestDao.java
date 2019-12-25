package edu.net.searchEngine.elasticsearch.dao;

import java.util.List;

public interface EsSuggestDao{
	/**
	 * 获取至多10条搜索建议
	 * @param prefix 搜索建议的前缀
	 * @return 至多10条搜索建议
	 */
	public List<String> getSuggest(String prefix);
	
	/**
	 * 关闭client，释放资源
	 */
	public void close();
}