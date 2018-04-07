package com.everymundo.controller;

import com.everymundo.domain.Customer;
import com.everymundo.exception.CustomServiceException;
import com.everymundo.service.CustomerService;
import com.everymundo.util.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * {@link CustomerController} class
 *
 * @author Angel Lecuona
 */
@RestController
@RequestMapping(path = "/customers", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {


    @Autowired
    private CustomerService customerService;

    /**
     * Return a wrapper to customer if exist customer else an HttpStatus.BAD_REQUEST
     *
     * @param id of customer
     * @return a wrapper {@link ResponseEntity}
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> findByCustomerId(@PathVariable String id) {
        Customer customer;
        try {
            customer = customerService.findById(id);
        } catch (CustomServiceException e) {
            return new ResponseEntity<>(
                    new RestResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(
                (customer != null) ? customer :
                        new RestResponse(HttpStatus.OK.value(),"Resource not found"), HttpStatus.OK);
    }

    /**
     * Find all the costumers
     *
     * @return list of costumers
     */
    @GetMapping("")
    public ResponseEntity<Object> findAllCustomer() {

        List<Customer> customers;
        try {
            customers = customerService.findAll();
        } catch (CustomServiceException e) {
            return new ResponseEntity<>(
                    new RestResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    /**
     * Save the costumer
     *
     * @param request  {@link HttpServletRequest}
     * @param customer to save
     * @return a wrapper {@link ResponseEntity}
     */
    @PostMapping("")
    public ResponseEntity<RestResponse> save(HttpServletRequest request, @RequestBody Customer customer) {
        try {
            customer = customerService.save(customer);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new RestResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
        }

        HttpHeaders header = new HttpHeaders();
        header.set(HttpHeaders.LOCATION, request.getRequestURL().toString() + "/" + customer.getCustomerId());

        return new ResponseEntity<>(
                new RestResponse(HttpStatus.CREATED.value(), "Customer Resource Created"), header,
                HttpStatus.CREATED);
    }

    /**
     * Update an specific costumer
     *
     * @param request    HttpServletRequest
     * @param customer   to update
     * @param customerId
     * @return
     */
    @PutMapping("/{customerId}")
    public ResponseEntity<RestResponse> update(HttpServletRequest request, @PathVariable String customerId,
                                               @RequestBody Customer customer) {
        try {
            customerService.update(customer, customerId);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new RestResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
        }

        HttpHeaders header = new HttpHeaders();
        header.set(HttpHeaders.LOCATION, request.getRequestURL().toString());

        ResponseEntity<RestResponse> responseEntity = new ResponseEntity<>(
                new RestResponse(HttpStatus.OK.value(), "Customer Resource Created"), header,
                HttpStatus.OK
        );

        return responseEntity;
    }

    /**
     * Remove a customer by Id
     *
     * @param id of costumer
     * @return a wrapper {@link RestResponse}
     */
    @DeleteMapping("/{id}")
    public RestResponse delete(@PathVariable String id) {
        customerService.delete(id);
        return new RestResponse(HttpStatus.OK.value(), "Customer deleted");
    }


}
