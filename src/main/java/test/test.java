package test;

import java.net.UnknownHostException;
import java.util.List;

import edu.net.searchEngine.elasticsearch.dao.EsSearchDao;
import edu.net.searchEngine.elasticsearch.dao.impl.EsSearch;
import edu.net.searchEngine.elasticsearch.dao.impl.EsSuggest;

public class test{
	public static void main(String[] args) {
		try {
			EsSuggest see=new EsSuggest();
			List<String>list= see.getSuggest("厦门");
			System.out.println(list.size());
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i));
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}