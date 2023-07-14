package net.serhiri.customerservice;

import net.serhiri.customerservice.entities.Customer;
import net.serhiri.customerservice.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CustomerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(CustomerRepository customerRepository){
		return  args -> {
			customerRepository.save(Customer.builder()
							.name("hassan")
							.email("hassan@gmail.com")
					         .build());
			customerRepository.save(Customer.builder()
					.name("otmane")
					.email("otmane@gmail.com")
					.build());
			customerRepository.save(Customer.builder()
					.name("amine")
					.email("amine@gmail.com")
					.build());
		};
	}
}
