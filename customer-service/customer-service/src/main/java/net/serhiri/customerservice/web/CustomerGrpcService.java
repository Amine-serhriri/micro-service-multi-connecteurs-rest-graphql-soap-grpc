package net.serhiri.customerservice.web;


import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import net.serhiri.customerservice.entities.Customer;
import net.serhiri.customerservice.mapper.CustomerMapper;
import net.serhiri.customerservice.repository.CustomerRepository;
import net.serhiri.customerservice.stub.CustomerServiceGrpc;
import net.serhiri.customerservice.stub.CustomerServiceOuterClass;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@GrpcService
public class CustomerGrpcService extends CustomerServiceGrpc.CustomerServiceImplBase {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerMapper customerMapper;
    @Override
    public void getAllCustomers(CustomerServiceOuterClass.GetAllCustomersRequest request, StreamObserver<CustomerServiceOuterClass.getCustomersResponse> responseObserver) {
        List<Customer> customerList = customerRepository.findAll();
        List<CustomerServiceOuterClass.Customer> grpcCustomers =
                customerList.stream()
                        .map(customerMapper::fromCustomer)
                        .collect(Collectors.toList());

        CustomerServiceOuterClass.getCustomersResponse customerResponse =
                CustomerServiceOuterClass.getCustomersResponse.newBuilder()
                .addAllCustomers(grpcCustomers)
                .build();

        responseObserver.onNext(customerResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void getCustomerById(CustomerServiceOuterClass.GetCustomerByIdRequest request, StreamObserver<CustomerServiceOuterClass.GetCustomerByIdResponse> responseObserver) {
     Customer customer = customerRepository.findById(request.getCustomerId()).get();

        CustomerServiceOuterClass.Customer grpcCustomer = customerMapper.fromCustomer(customer);

        CustomerServiceOuterClass.GetCustomerByIdResponse response = CustomerServiceOuterClass.GetCustomerByIdResponse.newBuilder()
                .setCustomer(grpcCustomer)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void saveCustomer(CustomerServiceOuterClass.saveCustomerRequest request, StreamObserver<CustomerServiceOuterClass.saveCustomerResponse> responseObserver) {
        Customer customer = customerMapper.fromGrpccustomer(request.getCustomer());
        Customer savedCustomer = customerRepository.save(customer);
        CustomerServiceOuterClass.saveCustomerResponse response = CustomerServiceOuterClass.saveCustomerResponse.newBuilder()
                .setCustomer(customerMapper.fromCustomer(savedCustomer))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
