package edu.net.itsearch.elasticsearch.dao;

import java.util.List;
/**
 * 
 * @author xingkyh
 * @date 2020/01/08
 */
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