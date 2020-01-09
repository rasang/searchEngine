package edu.net.itsearch.crawler.dao;

import java.util.List;

import org.jsoup.nodes.Document;
/**
 * 
 * @author PlumK
 * @date 2020/01/08
 */

public interface ResultWriterDao{
	/**
	 * 
	 * 用于数据保存的dao接口，在类的内部定义存储方式，使用write方法写入
	 * @param url 传入的网页的url
	 * @param doc 传入的网页的document对象，可用于保存title，正文，等等
	 */
	public void write(String url, Document doc);
	/**
	 * 得到保存的数据
	 * @return getLinks方法返回数据List
	 */
	@SuppressWarnings("rawtypes")
	public List getLinks();
}
