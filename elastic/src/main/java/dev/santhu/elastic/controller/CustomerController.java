package dev.santhu.elastic.controller;

import dev.santhu.elastic.model.Customer;
import dev.santhu.elastic.repository.CustomerRepository;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(("/customer"))
public class CustomerController {
    private  final CustomerRepository repository;

    public CustomerController(CustomerRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/save")
    public Mono<Customer> saveCustomer(@RequestBody Customer customer) {
        return repository.save(customer);
    }

    @GetMapping("/view/all")
    public Flux<Customer> viewAll(){
        return repository.findAll();
    }
    @DeleteMapping("/delete/{id}")
    public Mono<Void> delete(@PathVariable String id) {
        return repository.deleteById(id);
    }

}
