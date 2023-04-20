package cn.edu.bistu.cs.crawler;

/*import cn.edu.bistu.cs.crawler.service.HelloWorld;
import cn.edu.bistu.cs.crawler.service.impl.Helloworldimpl;
import org.springframework.boot.CommandLineRunner;*/

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class CrawlerApplication/* implements CommandLineRunner*/ {
    public static void main(String[] args) {
        SpringApplication.run(CrawlerApplication.class, args);
    }

/*    @Override
    public void run(String... args) {
        String alan = HelloWorld.sayHello("这是一个启动类");
        System.out.println(alan);
    }*/
}














