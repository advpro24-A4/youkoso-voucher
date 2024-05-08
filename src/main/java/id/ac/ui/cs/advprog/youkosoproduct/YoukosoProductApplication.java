package id.ac.ui.cs.advprog.youkosoproduct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class YoukosoProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(YoukosoProductApplication.class, args);
    }

}
