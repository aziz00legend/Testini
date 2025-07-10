package com.example.backend;

import com.example.backend.entites.Instructor;
import com.example.backend.enums.Privilege;
import com.example.backend.services.ServiceImpl.InstructorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
public class BackEndApplication implements CommandLineRunner {
    @Autowired
    private InstructorServiceImpl instructorService;

    public static void main(String[] args) {
        SpringApplication.run(BackEndApplication.class, args);
    }

    @Bean
    public CorsFilter corsFilter() {
        // Create a new CorsConfiguration object
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowCredentials(true);  // Allow credentials like cookies
        corsConfig.addAllowedOrigin("http://localhost:4200");  // Add your frontend URL (Angular app in this case)
        corsConfig.addAllowedHeader("*");  // Allow all headers
        corsConfig.addAllowedMethod("*");  // Allow all methods


        // Set up the source with the configuration
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);  // Apply the config for all paths

        return new CorsFilter(source);  // Return a CorsFilter with the configuration
    }


    @Override
    public void run(String... args) throws Exception {
        // Create a new Instructor
        Instructor instructor = Instructor.builder()
                .name("John Doe")
                .email("johndoe@example.com")
                .passworld("0000") // Assuming 'passworld' is intended to be 'password'
                .privilege(Privilege.USER)
                .build();

        // Save the Instructor
        //instructorService.saveInstructor(instructor);

        System.out.println("Instructor saved: " + instructor);
    }

}
