package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = { "hello" })
@EnableJpaRepositories(basePackages="hello")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
