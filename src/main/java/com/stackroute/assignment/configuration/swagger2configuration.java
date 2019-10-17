package com.stackroute.assignment.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class swagger2configuration {
    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.stackroute.assignment.controller"))
                .paths(PathSelectors.regex("/api/v2/.*"))
                .build();
    }

//    private ApiInfo apiEndPointsInfo() {
//        return new ApiInfoBuilder().title("Spring Boot REST API")
//                .description("Muzix REST API")
//                .contact(new Contact("Mreenav Deka", "www.github.com/minz27", "mreenav@gmail.com"))
//                .license("Apache 2.0")
//                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
//                .version("1.0.0")
//                .build();
//    }
}
