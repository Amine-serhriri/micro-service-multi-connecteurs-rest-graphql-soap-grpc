package net.serhiri.customerservice.web;

import lombok.AllArgsConstructor;
import net.serhiri.customerservice.entities.Customer;
import net.serhiri.customerservice.repository.CustomerRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class CustomerRestController {
    private CustomerRepository customerRepository;
    @GetMapping("customers")
   public List<Customer>customerList(){
       return customerRepository.findAll();
   }
   @GetMapping("/customers/{id}")
    public Customer customer(@PathVariable Long id){
       Customer customer = customerRepository.findById(id).get();
       return customer;
   }
   @PostMapping("/customers")
    public void saveCustomer(@RequestBody Customer customer){
        customerRepository.save(customer);
   }
}
