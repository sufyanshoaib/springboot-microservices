package com.sufyanshoaib.department_service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sufyanshoaib.department_service.client.EmployeeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class WebClientConfig {

    /*@Autowired
    private LoadBalancedExchangeFilterFunction filterFunction;
*/


    @Bean
    @LoadBalanced
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("http://employee-service")
                .build();
    }

    @Bean
    public EmployeeClient employeeClient() {
        HttpServiceProxyFactory proxyFactory = HttpServiceProxyFactory
                //.builder(WebClientAdapter.forClient(employeeWebClient()))
                .builderFor(WebClientAdapter.create(webClient()))
                .build();
        return proxyFactory.createClient(EmployeeClient.class);
    }

}
