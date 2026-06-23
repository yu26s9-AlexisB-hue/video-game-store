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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
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

   @Test
    //Trying to see if bug 2 is in the productService
   //The Service is not the problem
    void editingTheProduct_ShouldEditEverything(){

       Integer id = 1;

       Product currentProduct = new Product();
       currentProduct.setProductId(id);
       currentProduct.setName("Saints Row 2");
       currentProduct.setPrice(39.99);
       currentProduct.setCategoryId(1);
       currentProduct.setDescription("Open World Game");
       currentProduct.setSubCategory("Sandbox");
       currentProduct.setStock(15);
       currentProduct.setFeatured(true);
       currentProduct.setImageUrl("SaintsRow2.jpg");


       Product updatedProduct = new Product();
       updatedProduct.setPrice(49.99);
       updatedProduct.setDescription(" is a 2008 action-adventure game developed by Volition and published by THQ.");
       updatedProduct.setSubCategory("Open World Game");


       when(productRepository.findById(id)).thenReturn(Optional.of(currentProduct));
       when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

       Product result = productService.update(id,updatedProduct);

       assertEquals(49.99, result.getPrice());
       assertEquals(" is a 2008 action-adventure game developed by Volition and published by THQ.", result.getDescription());
       assertEquals("Open World Game", result.getSubCategory());

       verify(productRepository).findById(id);
       verify(productRepository).save(currentProduct);
    }

}