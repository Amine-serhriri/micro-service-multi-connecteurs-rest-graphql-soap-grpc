package net.serhiri.AccountService.web;


import net.serhiri.AccountService.feign.CustomerRestClient;
import net.serhiri.AccountService.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/account-service")
public class AccountRestController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CustomerRestClient customerRestClient;
    @GetMapping("/customers")
   public List<Customer>customerList(){
       Customer[] customers =
               restTemplate.getForObject("http://localhost:8082/customers", Customer[].class);
       return List.of(customers);
   }
    @GetMapping("/customers/{id}")
    public Customer customerById(@PathVariable  Long id){
        Customer customer = restTemplate.getForObject("http://localhost:8082/customers/" + id, Customer.class);
        return customer ;
    }

    @GetMapping("/customers/v2")
    public Flux<Customer> customerListV2(){
        WebClient webClient=WebClient.builder()
                .baseUrl("http://localhost:8082")
                .build();
        Flux<Customer> customerFlux = webClient.get()
                .uri("/customers")
                .retrieve()
                .bodyToFlux(Customer.class);
        return customerFlux;
    }
    @GetMapping("/customers/v2/{id}")
    public Mono<Customer> customerByIdV2(@PathVariable  Long id){
        WebClient webClient=WebClient.builder()
                .baseUrl("http://localhost:8082")
                .build();
        Mono<Customer> customerMono = webClient.get()
                .uri("customers/{id}",id)
                .retrieve()
                .bodyToMono(Customer.class);
        return customerMono;
    }
    @GetMapping("/customers/v3")
    public List<Customer>customerListV3(){
       return customerRestClient.getCustomer();
    }
    @GetMapping("/customers/v3/{id}")
    public Customer customerByIdV3(@PathVariable Long id){
        return customerRestClient.getcustomerById(id);
    }
    //***********************************************************************

    @GetMapping("/gql/customers/{id}")
    public Mono<Customer>CustomerByIdGgl(@PathVariable Long id ){
        HttpGraphQlClient graphQlClient= HttpGraphQlClient.builder()
                .url("http://localhost:8082/graphql")
                .build();
        var httpRequestDocument= """
                query($id:Int){
                    getCustomer(id:$id){
                        id
                        name
                        email
                    }
                }
                """;
        Mono<Customer> customer = graphQlClient.document(httpRequestDocument)
                .variable("id", id)
                .retrieve(" getCustomer")
                .toEntity(Customer.class);


        return customer;
    }
    @GetMapping("/gql/customers")
    public Mono<List<Customer>>customerListGql(){
        HttpGraphQlClient graphQlClient= HttpGraphQlClient.builder()
                .url("http://localhost:8082/graphql")
                .build();
        var httpRequestDocument= """
                query{
                 allCustomers{
                  name
                  email
                    }
                }
                """;
        Mono<List<Customer>>customers = graphQlClient.document(httpRequestDocument)
                .retrieve("allCustomers")
                .toEntityList(Customer.class);

        return customers;
    }


}


