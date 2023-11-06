package net.tuke.dt.videoconferenceapi;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@OpenAPIDefinition(
		info =  @Info(
				title = "Spring Boot DT Meet REST API's",
				description = "Spring Boot DT Meet REST API's Documentation",
				version = "v1.0",
				contact =  @Contact(
						name = "DT Meet",
						email = "dtmeet@gmail.com",
						url = "https://www.dtmeet.com"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://www.dtmeet.com/license"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "SpringBoot DT Meet App Documentation",
				url = "https://github.com/Artem-ka-create/video-conference-api"
		)
)
public class VideoConferenceApiApplication {

	//TODO: set security , JWT
	//second commit
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(VideoConferenceApiApplication.class, args);


	}

}
