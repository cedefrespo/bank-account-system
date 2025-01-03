package com.example.bank.config;

import com.example.bank.middleware.LargeDepositInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // Bean para el interceptor de depósitos grandes
    @Bean
    public LargeDepositInterceptor largeDepositInterceptor() {
        return new LargeDepositInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Aplica el interceptor solo al endpoint de /transactions
        registry.addInterceptor(largeDepositInterceptor())
                .addPathPatterns("/transactions");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Permite solicitudes CORS desde tu frontend (ajusta según sea necesario)
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")  // Asegúrate de cambiar la URL de acuerdo a tu configuración de frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Métodos permitidos
                .allowedHeaders("*") // Permite cualquier header
                .allowCredentials(true); // Si necesitas enviar credenciales (cookies, autenticación)
    }
}
