package com.assignament.ssms;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import com.assignament.ssms.model.Student;
import com.assignament.ssms.model.StudentRepository;

@SpringBootApplication
public class SsmsApplication {

	public static void main(String[] args) {
        SpringApplication.run(SsmsApplication.class, args);
    }

	@Bean
	public WebMvcConfigurer corsConfigurer() {
	    return new WebMvcConfigurer() {
	        @Override
	        public void addCorsMappings(CorsRegistry registry) {
	            registry.addMapping("/**")
	                    .allowedOrigins("*")
	                    .allowedMethods("GET", "PUT", "POST", "PATCH", "DELETE", "OPTIONS");
	        }
	    };
	}
	
    @Component
    public class DatabaseLoader implements CommandLineRunner {

        private final StudentRepository repository;

        @Autowired
        public DatabaseLoader(StudentRepository repository) {
            this.repository = repository;
        }

        @Override
        public void run(String... strings) throws Exception {
        	for(int i=0;i<100;i++) {
        		this.repository.save(new Student("Joe"+i, "Doe"+i, new Date(95,i%12,5)));
            }
        }
        
    }
}
