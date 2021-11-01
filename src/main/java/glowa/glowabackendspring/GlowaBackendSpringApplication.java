package glowa.glowabackendspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GlowaBackendSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(GlowaBackendSpringApplication.class, args);
	}

}
