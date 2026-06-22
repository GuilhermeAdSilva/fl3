package br.edu.ifsudestemg.fl.config;

import br.edu.ifsudestemg.fl.security.JwtAuthFilter;
import br.edu.ifsudestemg.fl.security.JwtService;
import br.edu.ifsudestemg.fl.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Arrays;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtService jwtService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public OncePerRequestFilter jwtFilter(){
        return new JwtAuthFilter(jwtService, usuarioService);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(usuarioService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .authorizeRequests()

                //.antMatchers("/api/v1/cartoes/**")
                //.permitAll()
                .antMatchers(HttpMethod.GET,"/api/v1/cartoes/**")
                .permitAll()
                .antMatchers(HttpMethod.POST,"/api/v1/cartoes/**")
                .permitAll()
                .antMatchers(HttpMethod.PUT,"/api/v1/cartoes/**")
                .hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/api/v1/cartoes/**")
                .hasAnyRole("ADMIN")
                //.authenticated()

                //.antMatchers("/api/v1/equipes/**")
                //.permitAll()
                .antMatchers(HttpMethod.GET,"/api/v1/equipes/**")
                .permitAll()
                .antMatchers(HttpMethod.POST,"/api/v1/equipes/**")
                .permitAll()
                .antMatchers(HttpMethod.PUT,"/api/v1/equipes/**")
                .hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/api/v1/equipes/**")
                .hasAnyRole("ADMIN")


                //.antMatchers("/api/v1/escalacoes/**")
                //.permitAll()
                .antMatchers(HttpMethod.GET,"/api/v1/escalacoes/**")
                .permitAll()
                .antMatchers(HttpMethod.POST,"/api/v1/escalacoes/**")
                .permitAll()
                .antMatchers(HttpMethod.PUT,"/api/v1/escalacoes/**")
                .hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/api/v1/escalacoes/**")
                .hasAnyRole("ADMIN")


                //.antMatchers("/api/v1/gols/**")
                //.permitAll()
                .antMatchers(HttpMethod.GET,"/api/v1/gols/**")
                .permitAll()
                .antMatchers(HttpMethod.POST,"/api/v1/gols/**")
                .permitAll()
                .antMatchers(HttpMethod.PUT,"/api/v1/gols/**")
                .hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/api/v1/gols/**")
                .hasAnyRole("ADMIN")


                //.antMatchers("/api/v1/inscricoes/**")
                //.permitAll()
                .antMatchers(HttpMethod.GET,"/api/v1/inscricoes/**")
                .permitAll()
                .antMatchers(HttpMethod.POST,"/api/v1/inscricoes/**")
                .permitAll()
                .antMatchers(HttpMethod.PUT,"/api/v1/inscricoes/**")
                .hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/api/v1/inscricoes/**")
                .hasAnyRole("ADMIN")


                //.antMatchers("/api/v1/jogadores/**")
                //.permitAll()
                .antMatchers(HttpMethod.GET,"/api/v1/jogadores/**")
                .permitAll()
                .antMatchers(HttpMethod.POST,"/api/v1/jogadores/**")
                .permitAll()
                .antMatchers(HttpMethod.PUT,"/api/v1/jogadores/**")
                .permitAll()
                .antMatchers(HttpMethod.DELETE,"/api/v1/jogadores/**")
                .hasAnyRole("ADMIN")


                //.antMatchers("/api/v1/partidas/**")
                //.permitAll()
                .antMatchers(HttpMethod.GET,"/api/v1/partidas/**")
                .permitAll()
                .antMatchers(HttpMethod.POST,"/api/v1/partidas/**")
                .permitAll()
                .antMatchers(HttpMethod.PUT,"/api/v1/partidas/**")
                .hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/api/v1/partidas/**")
                .hasAnyRole("ADMIN")


                //.antMatchers("/api/v1/torneios/**")
                //.permitAll()
                .antMatchers(HttpMethod.GET,"/api/v1/torneios/**")
                .permitAll()
                .antMatchers(HttpMethod.POST,"/api/v1/torneios/**")
                .permitAll()
                .antMatchers(HttpMethod.PUT,"/api/v1/torneios/**")
                .hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/api/v1/torneios/**")
                .hasAnyRole("ADMIN")


                //.antMatchers( "/api/v1/usuarios/**")
                //.permitAll()
                .antMatchers( "/api/v1/usuarios/**")
                .permitAll()

                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
        ;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Origem permitida (seu Frontend)
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));

        // Métodos HTTP permitidos
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Cabeçalhos permitidos (essencial para enviar o Content-Type e o Bearer Token)
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));

        // Permite envio de credenciais (cookies, headers de autenticação)
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}