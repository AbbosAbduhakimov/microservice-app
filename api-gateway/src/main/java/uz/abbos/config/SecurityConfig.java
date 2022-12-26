package uz.abbos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {
//, Converter<Jwt, Mono<AbstractAuthenticationToken>> jwtAuthenticationConverter
//    .jwtAuthenticationConverter(jwtAuthenticationConverter)
    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity security) {

        security.csrf().disable()
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/eureka/**","/order/test")
                        .permitAll()
                        .anyExchange()
                        .authenticated()
                        .and()
                        .oauth2ResourceServer()
                        .jwt());
        return security.build();
    }
}
