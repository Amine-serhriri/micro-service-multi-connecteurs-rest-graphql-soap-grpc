package net.serhiri.customerservice.web;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import lombok.AllArgsConstructor;
import net.serhiri.customerservice.dto.CustomerRequest;
import net.serhiri.customerservice.entities.Customer;
import net.serhiri.customerservice.mapper.CustomerMapper;
import net.serhiri.customerservice.repository.CustomerRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
@WebService(serviceName = "CustomerWebService")
public class CustomerSoapService {
    private CustomerRepository customerRepository;
    private CustomerMapper customerMapper;

    @WebMethod
    public List<Customer>customerList(){
        return customerRepository.findAll();
    }
    @WebMethod()
    public Customer customerById(@WebParam(name = "id") Long Id){
        return customerRepository.findById(Id).get();
    }
    @WebMethod()
    public Customer save(@WebParam(name = "customer") CustomerRequest customerRequest){
        Customer customer = customerMapper.from(customerRequest);
        return customerRepository.save(customer);
    }
}
