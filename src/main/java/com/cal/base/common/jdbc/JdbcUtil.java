package com.cal.base.common.jdbc;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * 
 * @Description 通过单独的jdbc程序，总结其中的问题
 * @author andyc
 * @since 2018-1-2
 * @version V1.0.0
 */
public class JdbcUtil {
	// 驱动
	private static String driver;
	// url地址
	private static String url;
	// 用户名
	private static String username;
	// 密码
	private static String password;

	// 静态代码块执行初始化操作
	static {
		try {
			InputStream in = JdbcUtil.class.getClassLoader()
					.getResourceAsStream("jdbc.properties");
			Properties prop = new Properties();
			prop.load(in);
			// 获取数据库连接驱动
			driver = prop.getProperty("jdbc.driver");
			// 获取数据库连接URL地址
			url = prop.getProperty("jdbc.url");
			// 获取数据库连接用户名
			username = prop.getProperty("jdbc.username");
			// 获取数据库连接密码
			password = prop.getProperty("jdbc.password");

			// 加载驱动
			Class.forName(driver);
			System.out.println("》》》数据库驱动加载成功《《《");
		} catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	/**
	 * 获取链接
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}

	/**
	 * 释放资源
	 * 
	 * @param conn
	 * @param st
	 * @param rs
	 */
	public static void release(Connection conn, Statement st, ResultSet rs) {
		if (rs != null) {
			try {
				// 关闭存储查询结果的ResultSet对象
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			rs = null;
		}
		if (st != null) {
			try {
				// 关闭负责执行SQL命令的Statement对象
				st.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				// 关闭Connection数据库连接对象
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			conn = null;
		}
	}
	
	public static void main(String[] args) {
		Connection conn = null;
		// 预编译的Statement，使用预编译的Statement提高数据库性能
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			// 定义sql语句 ?表示占位符
			String sql = "select * from system_user where account = ?";
			// 获取预处理statement
			pstmt = conn.prepareStatement(sql);
			// 设置参数，第一个参数为sql语句中参数的序号（从1开始），第二个参数为设置的参数值
			pstmt.setString(1, "caoql");
			// 向数据库发出sql执行查询，查询出结果集
			rs = pstmt.executeQuery();
			// 遍历查询结果集
			while (rs.next()) {
				System.out.println(rs.getString("user_id") + " "
						+ rs.getString("name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			release(conn, pstmt, rs);
		}
	}
}
/**
 * 1、数据库连接，使用时就创建，不使用立即释放，对数据库进行频繁连接开启和关闭，造成数据库资源浪费，影响数据库性能。 设想：使用数据库连接池管理数据库连接。
 * 2、将sql语句硬编码到java代码中，如果sql语句修改，需要重新编译java代码，不利于系统维护。
 * 设想：将sql语句配置在xml配置文件中，即使sql变化，不需要对java代码进行重新编译。
 * 3、向preparedStatement中设置参数，对占位符号位置和设置参数值，硬编码在java代码中，不利于系统维护。
 * 设想：将sql语句及占位符号和参数全部配置在xml中。 4、从resutSet中遍历结果集数据时，存在硬编码，将获取表的字段进行硬编码，，不利于系统维护。
 * 设想：将查询的结果集，自动映射成java对象。
 */
