package in.pranjal.expensetrackerapi.config;

import in.pranjal.expensetrackerapi.security.CustomUserDetailService;
import in.pranjal.expensetrackerapi.security.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
public class WebConfigurer {

    //extends WebSecurityConfigurerAdapter

    @Autowired
    private CustomUserDetailService userDetailService;

    @Bean
    public JwtRequestFilter authenticationJwtTokenFilter(){
        return new JwtRequestFilter();
    }

//     @Override
//     protected void configure(HttpSecurity http) throws Exception {
//         http
//                 .csrf().disable()
//                 .authorizeRequests()
//                 .antMatchers("/login", "/register").permitAll()
//                 .anyRequest().authenticated()
//                 .and()
//                 .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//         http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//         http.httpBasic();
//     }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/login", "/register").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        http.httpBasic();

        return http.build();
    }

     /*

     //First Approach - Not Working Getting error for passwordEncoder

     @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

       auth
               .inMemoryAuthentication()
               .withUser("Pranjal").password("12345").authorities("admin")
               .and()
               .withUser("Pranjal2").password("12345").authorities("user")
               .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }
*/

    /*
    // Second Approach - Working
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();

        UserDetails user1 = User.withUsername("Pranjal").password("12345").authorities("admin").build();
        UserDetails user2 = User.withUsername("UserPranjal").password("123456").authorities("User").build();

        userDetailsManager.createUser(user1);
        userDetailsManager.createUser(user2);

        auth.userDetailsService(userDetailsManager);
    }*/

    //It is used for custom user service**

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailService);
//    }

    /*@Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }*/

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider(){

        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception{
        return authConfig.getAuthenticationManager();
    }

//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws  Exception{
//        return super.authenticationManagerBean();
//    }

}
