
package com.Tienda;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;




@Configuration
public class ProjectConfig implements WebMvcConfigurer{
    
    // Estos metodos son para la implementacion de la internalizacion
    
    @Bean
    public LocaleResolver localeResolver(){
        var slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.getDefault());
        slr.setLocaleAttributeName("session.current.locale");
        slr.setTimeZoneAttributeName("session.current.timezone");
        return slr;
    }
    
    // Se utiliza localeChangeInterceptor para crear una interceptor de cambio de idioma
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor(){
        var lci = new LocaleChangeInterceptor();
        return lci;
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(localeChangeInterceptor());
    }
    
    // Bean para poder acceder a los mensajes
    @Bean("messagesSource")
    public MessageSource messageSource(){
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("message");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/inde").setViewName("index");
        registry.addViewController("/Contacto").setViewName("index");
        registry.addViewController("/registro/nuevo").setViewName("/registro/nuevo");
    }
    
//    /* El siguiente método se utiliza para completar la clase no es 
//    realmente funcional, la próxima semana se reemplaza con usuarios de BD */    
//    @Bean
//    public UserDetailsService users() {
//        //USUARIO Admin
//        UserDetails admin = User.builder()
//                .username("juan")
//                .password("{noop}123")
//                .roles("USER", "VENDEDOR", "ADMIN")
//                .build();
//        //USUARIO Vendedor
//        UserDetails sales = User.builder()
//                .username("rebeca")
//                .password("{noop}456")
//                .roles("USER", "VENDEDOR")
//                .build();
//        //USURIO user
//        UserDetails user = User.builder()
//                .username("pedro")
//                .password("{noop}789")
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(user, sales, admin);
//    }
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception{
        builder.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
    
    // DICE A QUE TIENEN PERMISO LOS ROLES
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((request) -> request
                .requestMatchers("/","/index","/errores/**", "/error","/error/***",
                        "/carrito/**","/pruebas/**","/reportes/**",
                        "/registro/**","/js/**","/webjars/**")
                        .permitAll()
                .requestMatchers(
                        "/producto/nuevo","/producto/guardar",
                        "/producto/modificar/**","/producto/eliminar/**",
                        "/categoria/nuevo","/categoria/guardar",
                        "/categoria/modificar/**","/categoria/eliminar/**",
                        "/usuario/nuevo","/usuario/guardar",
                        "/usuario/modificar/**","/usuario/eliminar/**",
                        "/reportes/**"
                ).hasRole("ADMIN")
                .requestMatchers(
                        "/producto/listado",
                        "/categoria/listado",
                        "/usuario/listado"
                ).hasAnyRole("ADMIN", "VENDEDOR")
                .requestMatchers("/facturar/carrito")
                .hasRole("USER")
                )
                .formLogin((form) -> form
                .loginPage("/login").permitAll())
                .logout((logout) -> logout.permitAll());
        return http.build();
    } 
}