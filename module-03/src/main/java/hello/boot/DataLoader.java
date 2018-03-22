package hello.boot;

import hello.model.User;
import hello.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private UserRepository userRepository;

    @Autowired
    public void setProductRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... strings) throws Exception {

        User user1 = new User();
        user1.setName("Jeff Bar");
        user1.setEmail("bar@gmail.com");

        userRepository.save(user1);

        User user2 = new User();
        user2.setName("John Bell");
        user2.setEmail("bell@gmail.com");

        userRepository.save(user2);
    }
}
