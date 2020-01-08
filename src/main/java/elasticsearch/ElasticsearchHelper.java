package elasticsearch;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
/**
 * 
 * @author xingkyh
 * @date 2020/01/08
 */
public class ElasticsearchHelper {
	
	private TransportClient client = null;
	
	@SuppressWarnings("resource")
	public void connect() throws UnknownHostException {
		// 创建客户端
		client = new PreBuiltTransportClient(Settings.EMPTY)
				.addTransportAddresses(new TransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
	}

	public void closeConnect() {
		if (null != client) {
			client.close();
		}
	}
	
	public void createIndex(String index){
		// 1 创建索引
		client.admin().indices().prepareCreate(index).get();
		
		// 2 关闭连接
		client.close();
	}

	public static void main(String[] args) throws UnknownHostException {
	}
}
