package mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class MysqlHelper {
	static final String driver = "com.mysql.jdbc.Driver";
	static final String url = "jdbc:mysql://120.79.232.37:3306/search_engine";
	static final String userName = "plumk";
	static final String password = "plumk";
	
	// 连接数据库
	public static Connection getConnection() throws SQLException{
		Connection conn = null;
			conn = DriverManager.getConnection(url, userName, password);
			return conn;
	}
	
	// 断开数据库链接
	public static void closeConnection(Connection conn) {
		if(conn != null) {
			try {
				conn.close();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	// 释放资源
	public static void realeaseAll(ResultSet rs, Statement st, Connection conn) {
		if(rs != null) {
			try {
				rs.close();
				rs = null;
			}
			catch(SQLException e){
				e.printStackTrace();
			}
		}
		if(st != null) {
			try {
				st.close();
				st = null;
			}
			catch(SQLException e){
				e.printStackTrace();
			}
		}
		closeConnection(conn);
	}
	
}