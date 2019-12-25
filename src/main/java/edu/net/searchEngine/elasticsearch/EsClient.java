package edu.net.searchEngine.elasticsearch;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;

public class EsClient {
	public static String indexName="webpage";
	public static String typeName="doc";
	public static String suggestName="history_search";
	private static JestClient jestClient=null;
	private static TransportClient transportClient=null;
	
	/**
	 * jest api:连接elasticsearch
	 * @return jestClient
	 */
	public static JestClient getJestClient() {
		if(jestClient==null) {
			JestClientFactory factory = new JestClientFactory();  
			factory.setHttpClientConfig(new HttpClientConfig
					.Builder("http://localhost:9200")
					//.gson(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create())
					.multiThreaded(true)
					.readTimeout(10000)
					.build());
			jestClient=factory.getObject();
		}
		return jestClient;
	}
	
	/**
	 * jest api:关闭jestClient，释放资源
	 * @param jest 要关闭的jestClient
	 * @throws IOException
	 */
	public static void closeJestClient() throws IOException {
		if(jestClient!=null) {
			jestClient.close();
		}
	}
	
	/**
	 * transport api:连接elasticsearch
	 * @return
	 * @throws UnknownHostException
	 */
	public static TransportClient getTransportClient() throws UnknownHostException {
		if(transportClient==null) {
			transportClient = new PreBuiltTransportClient(Settings.EMPTY)
					.addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
		}
		return transportClient;
	}
	
	/**
	 * transport api:关闭transportClient，释放资源
	 */
	public static void closeTransportClient() {
		if(transportClient!=null) {
			transportClient.close();
		}
	}
}
