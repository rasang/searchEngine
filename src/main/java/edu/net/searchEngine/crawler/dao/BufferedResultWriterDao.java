package edu.net.searchEngine.crawler.dao;

import java.io.Flushable;
import java.util.List;

import org.jsoup.nodes.Document;

public interface BufferedResultWriterDao extends Flushable{
	public void write(String url, Document doc);
	public List getLinks();
}
