package com.generation.blogpessoal.configuration;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

@Configuration
public class SwaggerConfig {

	private ApiResponse createApiResponse;

	@Bean
	public OpenAPI SpringBootOpenAPI() {
		return new OpenAPI().info(new Info().title("Projeto Blog Pessoal")
				.description("Projeto Generation - Generation Brasil").version("v0.0.1")
				.license(new License().name("Generation Brasil").url("https://brazil.generation.org/"))
				.contact(new Contact().name("Samuel Silverio")
						.url("https://github.com/Samuel-prata/Projeto-guia-blog-pessoal-do-Samuel")
						.email("samuel.silverio001@hotmail.com")));
	}

	@Bean
	// A Classe OpenApiCustomiser permite personalizar o Swagger, baseado na
	// Especificação OpenAPI. O Método acima, personaliza todas as mensagens HTTP
	// Responses (Respostas das requisições) do Swagger.
	public OpenApiCustomizer costumerGlobalHeadersApiCustomiser() {
		return OpenAPI -> {// CRIA UM OBJETO API QUE GERA A DOCUMENTAÇÃO UTILIZANDO A ESPECIFICAÇÃO OPENAPI

			OpenAPI.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation -> {
				// PRIMEIRO LOOPING PARA PEGAR TODOS OS CAMINHOS DA URL TENDO NA SEQUENCIA UM
				// LOOPING QUE IDENTIFICA QUAL MÉTODO (OPERATIONS)
				// ESTÁ SENDO EXECUTADO ATRAVÉS DO READOPERATIONS

				// AS MENSAGENS SERÃO LIDAS E MODIFICADAS
				ApiResponses apiResponses = operation.getResponses();// RECEBE AS RESPOSTAS DE CADA ENDPOINT

				// ADICIONANDO AS NOVAS RESPOSTAS
				apiResponses.addApiResponse("200", createApiResponse("sucesso"));
				apiResponses.addApiResponse("201", createApiResponse("Objeto persistido"));
				apiResponses.addApiResponse("204", createApiResponse("Objeto Excluido"));
				apiResponses.addApiResponse("400", createApiResponse("Erro na requisiçãos"));
				apiResponses.addApiResponse("401", createApiResponse("Acesso não autorizado"));
				apiResponses.addApiResponse("404", createApiResponse("Objeto não encontrado"));
				apiResponses.addApiResponse("500", createApiResponse("Erro na aplicação"));
			}));
		};
	}

	// ADICIONA UMA RESPOSTA NO CORPO DA REQUISIÇÃO
	private ApiResponse createApiResponse(String message) {
		return new ApiResponse().description(message);
	}

}
