package weaver.agent.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author w
 * @date 2019-12-01 20:50
 */
@SpringBootApplication
@ServletComponentScan(basePackages = "weaver.agent.server")
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class,args);
    }
}
