package com.everymundo.service;

import com.everymundo.domain.Customer;
import com.everymundo.dto.Order;
import com.everymundo.exception.CustomServiceException;
import com.everymundo.rabbitmq.ProducerOutput;
import com.everymundo.repository.CustomerRepository;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * {@link CustomerService} class
 *
 * @author Angel Lecuona
 *
 */
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProducerOutput producerOutput;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    RestTemplate restTemplate;

    /**
     * Save the costumer
     *
     * @param customer to save
     * @return the costumer
     */
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    /**
     * Remove a customer by Id
     *
     * @param id of costumer
     */
    public void delete(String id) {
       customerRepository.delete(id);
       producerOutput.deleteConsumer(id);
    }

    /**
     * Get a customer with orders by Id
     *
     * @param id of costumer
     * @return a costumer with specific id
     *
     * throws CustomServiceException
     */
    public Customer findById(String id) throws CustomServiceException{
        Customer customerResult = customerRepository.findOne(id);

        String uri = "/customers/" + id + "/orders";
        String baseURL = getStringBuilder("ORDER-SERVICE", uri);

        ResponseEntity<String> response;

        try{
            response = restTemplate.exchange(baseURL, HttpMethod.GET, null,String.class);
            List<Order> orders = new ArrayList<>();
            orders = objectMapper.readValue(response.getBody(), objectMapper.getTypeFactory().constructCollectionType(List.class, Order.class));
            customerResult.setOrders(orders);
        }
        catch(  JsonParseException | JsonMappingException e){
            throw new CustomServiceException("Error occurred handling Json value", e.getCause());
        } catch (IOException e) {
            throw new CustomServiceException("Input/Output error", e.getCause());
        }

        return customerResult;
    }

    private String getStringBuilder(String serviceName, String uri) {
        List<ServiceInstance> serviceInstances = discoveryClient.getInstances(serviceName);
        ServiceInstance serviceInstance = serviceInstances.get(0);

        StringBuilder baseURL = new StringBuilder(serviceInstance.getUri().toString());
        baseURL.append(uri);
        return baseURL.toString();
    }

    /**
     * Find all the costumers with orders
     *
     * @return list of costumers
     */
    public List<Customer> findAll() throws CustomServiceException {
        List<Customer> customers = customerRepository.findAll();
        List<String> ids = customers.stream().map(customer -> customer.getCustomerId()).collect(Collectors.toList());

        String baseURL = getStringBuilder("ORDER-SERVICE", "/customers/orders");

        ResponseEntity<String> response;

        try{
            HttpEntity<List<String>> request = new HttpEntity<>(ids);
            response = restTemplate.exchange(baseURL, HttpMethod.POST, request,String.class);
            List<Order> orders = objectMapper.readValue(response.getBody(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Order.class));
            customers.forEach(customer -> {
                List<Order> orderList = orders.stream()
                        .filter(order -> order.getCustomerId().equals(customer.getCustomerId()))
                        .collect(Collectors.toList());
                customer.setOrders(orderList);
            });
        }
        catch(  JsonParseException | JsonMappingException e){
            throw new CustomServiceException("Error occurred handling Json value", e.getCause());
        } catch (IOException e) {
            throw new CustomServiceException("Input/Output error", e.getCause());
        }

        return customers;

    }

    /**
     * Update an specific costumer
     *
     * @param customer to update
     * @param customerId
     */
    public void update(Customer customer, String customerId) {
        if (customerId != null){
            customer.setCustomerId(customerId);
            customerRepository.save(customer);
        }
    }
}
