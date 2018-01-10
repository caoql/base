
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cal.base.common.aop.TestClass;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations = {"classpath:spring/applicationContext-common.xml"})
public class AopTest {
	
	@Autowired
	private TestClass t;
	
	@Test
	public void test() {
		Assert.assertNotNull(t);
		t.test("hello",true);
	}

}
