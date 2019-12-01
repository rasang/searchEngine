package edu.net.searchEngine.crawler.dao.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;

import edu.net.searchEngine.crawler.dao.BufferedResultWriterDao;

public class LinksListWriter implements BufferedResultWriterDao{
	List<String> links = new ArrayList<>();

	@Override
	public synchronized void flush() throws IOException {
		// TODO 自动生成的方法存根
		
	}

	public List<String> getLinks() {
		return links;
	}

	@Override
	public synchronized void write(String url, Document doc) {
		// TODO 自动生成的方法存根
		this.links.add(url);
	}
	
	public synchronized void clear() {
		this.links.clear();
	}
	
}
