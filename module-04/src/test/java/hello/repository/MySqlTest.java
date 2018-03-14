package hello.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import hello.repository.mysql.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
//@AutoConfigureMockMvc
public class MySqlTest {
	
  @Autowired
  UserRepository repository;
  
  
	@Test
	public void test () {
		System.out.println("\n#### count =" + repository.count());
	}

}
