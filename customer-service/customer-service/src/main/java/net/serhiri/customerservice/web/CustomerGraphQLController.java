package net.serhiri.customerservice.web;

import lombok.AllArgsConstructor;
import net.serhiri.customerservice.dto.CustomerRequest;
import net.serhiri.customerservice.entities.Customer;
import net.serhiri.customerservice.mapper.CustomerMapper;
import net.serhiri.customerservice.repository.CustomerRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@AllArgsConstructor
public class CustomerGraphQLController {
    private CustomerRepository customerRepository;
    private CustomerMapper customerMapper;
    @QueryMapping
    public List<Customer>allCustomers(){
        return customerRepository.findAll();
    }
    @QueryMapping
    public Customer getCustomer(@Argument  Long id){
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer==null) throw new RuntimeException(String.format("customer %s does not exist ",id));
        return customer;
    }
    @MutationMapping
    public Customer saveCustomer(@Argument CustomerRequest customer){
        Customer from = customerMapper.from(customer);
        return customerRepository.save(from);
    }
}
