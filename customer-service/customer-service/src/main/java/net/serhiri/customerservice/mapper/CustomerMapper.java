package net.serhiri.customerservice.mapper;

import net.serhiri.customerservice.dto.CustomerRequest;
import net.serhiri.customerservice.entities.Customer;
import net.serhiri.customerservice.stub.CustomerServiceOuterClass;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    private ModelMapper modelMapper=new ModelMapper();
    public Customer from(CustomerRequest customerRequest){
        Customer customer=new Customer();
        customer.setEmail(customerRequest.email());
        customer.setName(customerRequest.name());
        return customer;
    }
    public CustomerServiceOuterClass.Customer fromCustomer(Customer customer){
        return  modelMapper.map(customer,CustomerServiceOuterClass.Customer.Builder.class).build();
    }
    public Customer fromGrpccustomer(CustomerServiceOuterClass.CustomerRequest customerRequest){
        return  modelMapper.map(customerRequest,Customer.class);
    }
}
