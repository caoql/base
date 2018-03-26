import org.apache.shiro.codec.Base64;
import org.junit.Assert;
import org.junit.Test;


public class JavaTest {
	
	@Test
	public void test() {
		String name = "caoql";
		System.out.println("原来的name: " + name);
		m1(name);
		System.out.println("调用方法后的：" + name);
	}
	
	private void m1(String name) {
		name = "qinqin";
		System.out.println("修改后的："+name);
	}
	
	@Test
	public void test2() {
		int age = 23;
		System.out.println("原来的age: " + age);
		m2(age);
		System.out.println("调用方法后的：" + age);
	}
	
	private void m2(int age) {
		age = 25;
		System.out.println("修改后的：" + age);
	}
	
	@Test
	public void test3() {
		User u = new User();
		u.name = "cao";
		u.age = 23;
		m3(u);
		System.out.println("调用方法后的：" + u.name);
	}
	
	private void m3(final User u) {
		u.name = "qin";
		System.out.println("修改后的：" + u.name);
	}
	
	@Test
	public void test4() {
		String str = "hello";  
		String base64Encoded = Base64.encodeToString(str.getBytes());  
		System.out.println(base64Encoded);
		String str2 = Base64.decodeToString(base64Encoded);  
		Assert.assertEquals(str, str2); 
		
		// System.out.println(Sha256Hash("hello","").toString());
		System.out.println(new User().name);
	}
	
}

class User {
	public String name;
	public int age;
}
