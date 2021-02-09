package br.com.senior.desafio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	private List<ResponseMessage> responseMessageForGET(){
	    return new ArrayList<ResponseMessage>() {{
	        add(new ResponseMessageBuilder()
	            .code(500)
	            .message("500")
	            .responseModel(new ModelRef("Error"))
	            .build());
	        add(new ResponseMessageBuilder()
		            .code(404)
		            .message("Não encontrado")
		            .build());
	    }};
	}
	
	private List<ResponseMessage> responseMessageForPOSTDELETE(){
	    return new ArrayList<ResponseMessage>() {{
	        add(new ResponseMessageBuilder()
	            .code(500)
	            .message("500")
	            .responseModel(new ModelRef("Error"))
	            .build());
	        add(new ResponseMessageBuilder()
		            .code(415)
		            .message("Padrão de body não suportado para método POST/DELETE")
		            .build());
	    }};
	}
	
	private ApiInfo apiInfo() {
	    return new ApiInfoBuilder()
	            .title("API do XPTO Systems")
	            .description("REST API desenvolvida para o desafio da Senior Sistemas<br>"
	            		+ "Primeiro, realize a configuração do arquivo src/main/resources/application.properties com o acesso do seu MySQL<br>"
	            		+ "Antes de iniciar a importação do banco de dados, configure o caminho do arquivo .csv na classe CidadeController.java, ou importe um banco de dados existente. <br>")
	            .version("1.0.0")
	            .license("Apache License Version 2.0")
	            .build();
	}
	
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
          .select()
          .apis(RequestHandlerSelectors.basePackage("br.com.senior.desafio.controller"))
          .paths(PathSelectors.any())
          .build()
          .useDefaultResponseMessages(false)
          .globalResponseMessage(RequestMethod.GET, responseMessageForGET())
          .globalResponseMessage(RequestMethod.POST, responseMessageForPOSTDELETE())
          .globalResponseMessage(RequestMethod.DELETE, responseMessageForPOSTDELETE())
          .apiInfo(apiInfo());
    }
}
