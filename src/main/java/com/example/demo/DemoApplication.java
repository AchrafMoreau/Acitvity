package com.example.demo;

import com.example.demo.model.User;
import com.example.demo.model.UserRole;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
public class DemoApplication {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

//	public DemoApplication(UserRepository userRepository){
//		this.userRepository = userRepository;
//	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


	@Bean
	CommandLineRunner runner(){
		return args -> {
			User me = userRepository.findByUsername("Achraf");
			if(me == null){
				User admin = new User();
				admin.setUsername("Achraf");
				admin.setPassword(passwordEncoder.encode("admin0210"));
				admin.setEmail("achraf@gmail.com");
				admin.setUserRoles(new ArrayList<UserRole>(Arrays.asList(UserRole.ROLE_ADMIN)));
				userRepository.save(admin);
			}
		};
	}
}
