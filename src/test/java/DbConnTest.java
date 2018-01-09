import java.sql.Connection;

import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * ͨ数据库连接测试
 *
 * @author andyc @
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext-dao.xml" })
public class DbConnTest {

	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	@Test
	public void testConn() {
		Connection conn = sqlSessionFactory.openSession().getConnection();
		Assert.assertNotNull("链接为空!!!", conn);
	}
}
