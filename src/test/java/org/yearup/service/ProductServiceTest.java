package org.yearup.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.yearup.models.Product;
import org.yearup.repository.ProductRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

   @Mock
    private ProductRepository productRepository;

   @InjectMocks
    private ProductService productService;

   @Test
   //test for bug 1
    void getAllProducts_ShouldReturnAllProducts(){
       Product notFeatured = new Product(78, "Pong",7.82, 2,"Classic Video game", "Strategy",37,false,"pong.jpg");
       Product featured = new Product(79, "Asteroids", 6.25, 2, "Classic Video Game", "Strategy",48,true,"asteroids.jpg");

       //Turning these sample products into a list
       List<Product> allProducts = Arrays.asList(notFeatured, featured);

       // Simulating what should happen.
       when(productRepository.findAll()).thenReturn(allProducts);

       // Using the method with the issue
       List<Product> result = productService.search(null, null, null, null);

       assertEquals(2, result.size());
       assertTrue(allProducts.contains(notFeatured));
       assertTrue(allProducts.contains(featured));

   }

}