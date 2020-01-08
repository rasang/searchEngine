package edu.net.itsearch.crawler.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;

import edu.net.itsearch.crawler.dao.ResultWriterDao;
/**
 * 
 * @author PlumK
 * @date 2020/01/08
 */
public class LinksListWriter implements ResultWriterDao{
	List<String> links = new ArrayList<>();
	
	@Override
	public List<String> getLinks() {
		return links;
	}

	@Override
	public synchronized void write(String url, Document doc) {
		this.links.add(url);
	}
	
	public synchronized void clear() {
		this.links.clear();
	}
	
}
