package com.rbi.HDFC;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
//@EnableSwagger2
public class HdfcApplication {

	public static void main(String[] args) {
		SpringApplication.run(HdfcApplication.class, args);
	}
	/*@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis((Predicate<RequestHandler>) RequestHandlerSelectors.basePackage("com.rbi.HDFC")).build();
	}*/

}
