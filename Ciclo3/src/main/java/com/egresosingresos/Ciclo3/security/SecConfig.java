package com.egresosingresos.Ciclo3.security;

import com.egresosingresos.Ciclo3.handler.CustomSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private DataSource dataSource;
    @Autowired
    CustomSuccessHandler customSuccessHandler;
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception{
        auth.jdbcAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .dataSource(dataSource)
                .usersByUsernameQuery("SELECT correo,rol FROM empleado WHERE correo = ?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers("/","/VerEmpresas/**").hasRole("ADMIN")
                .antMatchers("/VerEmpleados/**").hasRole("ADMIN")
                .antMatchers("/Empresa/**").hasRole("ADMIN")
                .antMatchers("/Empleado/**").hasRole("ADMIN")
                .antMatchers("/VerMovimientos/**").hasRole("ADMIN","USER")
                .antMatchers("/AgregarMovimiento/**").hasRole("ADMIN","USER")
                .antMatchers("/EditarMovimiento/**").hasRole("ADMIN","USER")
                .and().formLogin.successHandler(customSuccessHandler)
                .and().exceptionHandling().accessDeniedPage("/Denegado")
                .and().logout().permitAll();
    }

}
