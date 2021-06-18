package pharma.lms.PharmaLMS.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

import java.util.List;

import static pharma.lms.PharmaLMS.user.domain.UserPermissions.COURSE_ADD;
import static pharma.lms.PharmaLMS.user.domain.UserPermissions.PRESENTATION_ADD;
import static pharma.lms.PharmaLMS.user.domain.UserRole.ADMIN;
import static pharma.lms.PharmaLMS.user.domain.UserRole.USER;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public WebSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/", "/registration").permitAll()
                    .antMatchers(HttpMethod.GET, "/course/**", "/presentations/**").hasAnyRole(USER.name(), ADMIN.name())
                    .antMatchers(HttpMethod.POST, "/course/**", "/presentations/**").hasRole(ADMIN.name())
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/course/all")
                    .permitAll()
                .and()
                    .logout()
                    .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin")
                    .password(passwordEncoder.encode("admin"))
                    .roles(ADMIN.name())
                .and()
                    .withUser("user")
                    .password(passwordEncoder.encode("user"))
                    .roles(USER.name());

        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder)
                .usersByUsernameQuery("SELECT username, password, active FROM users WHERE username=?")
                .authoritiesByUsernameQuery("SELECT username, user_role FROM users WHERE username=?")
                .rolePrefix("ROLE_");
    }
}