package com.example.entity;

import com.example.entity.model.Product;
import com.example.entity.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class EntityApplication implements CommandLineRunner {

	private ProductRepository productRepository;
	private RestTemplate restTemplate;

	private Logger LOG=LoggerFactory.getLogger(EntityApplication.class);

	@Autowired
	public void productRepository(ProductRepository productRepository) {
		this.productRepository=productRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(EntityApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		restTemplate = new RestTemplate();


		Product product1=new Product();
		product1.setName("Tester Product");
		product1.setCategory("TEST");
		product1.setType("GENERAL");
		product1.setDescription("Product1");
		product1.setPrice(10.0);
		product1.setSector("Science");

		productRepository.save(product1);

		Product product2=new Product();
		product2.setName("Another Tester Product");
		product2.setCategory("TEST");
		product2.setType("CUSTOM");
		product2.setDescription("This is a tester product");
		product2.setPrice(15.0);
		product2.setSector("IT");

		productRepository.save(product2);

		Product product3=new Product();
		product3.setName("Tester Product");
		product3.setCategory("TEST");
		product3.setType("SPECIFIC");
		product3.setDescription("description");
		product3.setPrice(19.0);
		product3.setSector("MATHS");

		productRepository.save(product3);


		//RestTemplate call
		ResponseEntity<Product> responseEntity = restTemplate.getForEntity("http://localhost:8080/api/products/4028b881894d9e9801894d9ea34f0000", Product.class);
		if (responseEntity.getStatusCode() == HttpStatus.OK) {
			Product productFromRestTemplate = responseEntity.getBody();
			if (productFromRestTemplate != null) {
				LOG.info("product received with restTemplate: " + productFromRestTemplate.toString());
			} else {
				LOG.info("productFromRestTemplate is null");
			}
		} else {
			LOG.info("Error retrieving product from restTemplate. Status code: " + responseEntity.getStatusCode());
		}

	}

}
