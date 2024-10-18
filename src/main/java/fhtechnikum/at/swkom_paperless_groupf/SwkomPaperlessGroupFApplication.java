package fhtechnikum.at.swkom_paperless_groupf;

import fhtechnikum.at.swkom_paperless_groupf.apps.repository.CustomJpaRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepository.class)
public class SwkomPaperlessGroupFApplication {

    public static void main(String[] args) {
        SpringApplication.run(SwkomPaperlessGroupFApplication.class, args);
    }

}
