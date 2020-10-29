package com.example.auth.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
//@EnableAutoConfiguration
public class SwaggerConfig{
	

//	@Override
//	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler("swagger-ui.html")
//		.addResourceLocations("classpath:/META)
//	}
	
	@Bean
    public Docket api() {
		
		List<ResponseMessage> responseMessages = new ArrayList<>();
	    responseMessages.add(new ResponseMessageBuilder()
	            .code(200)
	            .message("OK ~~")
	            .build());
	    responseMessages.add(new ResponseMessageBuilder()
	    		.code(400)
	    		.message("BAD request")
	    		.build());
	    responseMessages.add(new ResponseMessageBuilder()
	    		.code(401)
	    		.message("Unauthorized")
	    		.build());
	    responseMessages.add(new ResponseMessageBuilder()
	            .code(404)
	            .message("Not Found ~~")
	            .build());
	    responseMessages.add(new ResponseMessageBuilder()
	            .code(500)
	            .message("Internal Server Error ~~")
	            .build());
		
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.auth.Controller"))
                .paths(PathSelectors.any())
                .build();
//                .useDefaultResponseMessages(false)
//                .globalResponseMessage(RequestMethod.GET, responseMessages)
//                .enable(true);
//                .globalResponseMessage(RequestMethod.GET,
//                        newArrayList(new ResponseMessageBuilder()
//                                        .code(500)
//                                        .message("Internal Server Error")
//                                        .responseModel(new ModelRef("Error"))
//                                        .build(),
//                                new ResponseMessageBuilder()
//                                        .code(400)
//                                        .message("Bad Request")
//                                        .build(),
//                                new ResponseMessageBuilder()
//                                        .code(404)
//                                        .message("Not Found")
//                                        .build()));
    }
 
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("test swagger2")
                .description("swagger2 사용해 봅시다.")
                .build();
 
    }


}
