package com.cal.base.common.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClientThreadTest {
	// 用多线程来模拟
	public static void main(String[] args) {
		Service s = new Service();
		for (int i = 0; i < 10; i++) {
			Test ct = new Test(s);
			ct.start();
		}
	}
}

class Test extends Thread {
	private Service service;

	public Test(Service service) {
		this.service = service;
	}

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName());
		service.test(Thread.currentThread().getName());
	}
}

class Service {
	public void test(String name) {
		Connection conn = null;
		// 预编译的Statement，使用预编译的Statement提高数据库性能
		try {
			conn = JdbcUtil.getConnection();
			conn.setAutoCommit(false);// 自动提交事务关闭（开启事务）
			// 执行操作
			insert(conn, name);
			update(conn);

			// 提交事务
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if (conn != null) {
					conn.rollback();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			JdbcUtil.release(conn, null, null);
		}
	}

	public void insert(Connection conn, String name) throws SQLException {
		String sql = "insert into test(name) values(?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, name);
		int i = pstmt.executeUpdate();
		if (i > 0) {
			System.out.println("insert操作成功");
		}
	}

	public void update(Connection conn) throws SQLException {
		String sql = "update test set name = '天下第一' where id = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, 1);
		int i = pstmt.executeUpdate();
		if (i > 0) {
			System.out.println("update操作成功");
		}
	}
}
