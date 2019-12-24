package edu.net.searchEngine.crawler.dao;

import java.util.List;

import org.jsoup.nodes.Document;

public interface ResultWriterDao{
	public void write(String url, Document doc);
	public List getLinks();
}
