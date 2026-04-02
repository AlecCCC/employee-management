package SpringSecurityPractice.SecureApp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {


    // The app now uses this instance and overrides default no users exist yet.

    @Bean
    UserDetailsService userDetailsService() {

        UserDetails user = User.withUsername("Alec")
                .password("123")
                .authorities("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
           //     .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .logout(logout -> logout
                        .logoutUrl("/security-practice/logout")
                        .logoutSuccessUrl("/security-practice/hello")
                )
                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/security-practice/hello").permitAll()
//                        .requestMatchers("/security-practice/account").authenticated()
                        .anyRequest().authenticated()
                );

        return http.build();

    }

}
