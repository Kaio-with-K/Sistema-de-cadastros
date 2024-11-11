package br.org.clavedesol.CrudJava.webconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // Configuração de CORS para permitir requisições do frontend
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Permite CORS para todas as rotas
                .allowedOrigins("http://127.0.0.1:5500") // Permite acesso apenas do front-end
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Permite os métodos que você deseja
                .allowedHeaders("*") // Permite qualquer cabeçalho
                .allowCredentials(true); // Permite credenciais como cookies, headers, etc.
    }
}
