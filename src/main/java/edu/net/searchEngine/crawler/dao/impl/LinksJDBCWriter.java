package edu.net.searchEngine.crawler.dao.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;

import crawler.SearchResultEntry;
import edu.net.searchEngine.crawler.dao.BufferedResultWriterDao;
import mysql.MysqlHelper;

public class LinksJDBCWriter implements BufferedResultWriterDao{

	private List<SearchResultEntry> linkResult = null;
	private int BufferSize;
	
	public LinksJDBCWriter(int bufferSize) {
		this.linkResult = new ArrayList<>();
		this.BufferSize = bufferSize;
	}

	@Override
	public synchronized void write(String url, Document doc)  {
		if(this.linkResult.size() >= this.BufferSize) {
			try {
				this.flush();
				this.linkResult.clear();
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}
		SearchResultEntry entry = new SearchResultEntry(url, doc.title(), doc.text());
		this.linkResult.add(entry);
	}

	@Override
	public synchronized void flush() throws IOException {
		if(this.linkResult.size() == 0)
			return;
		Connection conn = null;
		PreparedStatement pstat = null;
		
		String sql = "insert ignore into search_engine (url,title,text) values (?,?,?);";
		try {
			conn = MysqlHelper.getConnection();
			pstat = conn.prepareStatement(sql);
			for(SearchResultEntry e : this.linkResult) {
				pstat.setString(1, e.getUrl());
				pstat.setString(2,e.getTitle());
				pstat.setString(3, e.getText());
				pstat.addBatch();
			}
			pstat.executeBatch();
		}catch (SQLException sqle) {
			 sqle.printStackTrace();
		 }catch(Exception e){
			 e.printStackTrace();
		 }finally{
			 MysqlHelper.realeaseAll(null,pstat, conn);
		}
	}
	
}
