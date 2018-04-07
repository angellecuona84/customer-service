package com.everymundo.repository;

import com.everymundo.domain.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
/**
 * {@link CustomerRepository} class
 *  DAO Representation to access MongoDB
 *
 * @author Angel Lecuona
 *
 */
@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {

}
