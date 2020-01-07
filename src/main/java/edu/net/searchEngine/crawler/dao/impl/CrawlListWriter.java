package edu.net.searchEngine.crawler.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import crawler.SearchResultEntry;
import edu.net.searchEngine.crawler.dao.ResultWriterDao;

public class CrawlListWriter implements ResultWriterDao{

	private List<SearchResultEntry> linkResult = null;
	
	public CrawlListWriter() {
		this.linkResult = new ArrayList<>();
	}

	@Override
	public synchronized void write(String url, Document doc)  {
		Elements timeSpan = doc.select(".timestyle124904");
		String time = timeSpan.get(0).text().substring(0,10);
		SearchResultEntry entry = new SearchResultEntry(url, doc.title(), doc.text(),time);
		this.linkResult.add(entry);
	}

	@Override
	public List getLinks() {
		return this.linkResult;
	}
	
}
