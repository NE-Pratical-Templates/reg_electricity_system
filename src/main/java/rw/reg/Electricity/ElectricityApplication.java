package rw.reg.Electricity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ElectricityApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElectricityApplication.class, args);
	}

}
