package sesac.semiProject.common.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 경로에 대해 CORS 허용
            .allowedOrigins("http://localhost:5173") // 허용할 도메인
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 허용할 HTTP 메소드
            .allowedHeaders("*") // 허용할 헤더
            .exposedHeaders("Authorization")
            .allowCredentials(true) // 인증 정보를 허용할지 여부
            .maxAge(3600); // 캐시 시간
    }
}
