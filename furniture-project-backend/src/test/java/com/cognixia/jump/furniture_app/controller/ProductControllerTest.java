package com.cognixia.jump.furniture_app.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.cognixia.jump.furniture_app.exception.ResourceNotFoundException;
import com.cognixia.jump.furniture_app.model.Product;
import com.cognixia.jump.furniture_app.repository.ProductRepository;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "user1", password = "pw123", roles = "ADMIN")
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductRepository productRepo;

    @Test
    public void testGetAllProducts() throws Exception {

        List<Product> productList = new ArrayList<>();
        productList.add(new Product(1, "Chair", 7, 78.99, "adfakld32589djhfs"));
        productList.add(new Product(2, "Couch", 10, 168.99, "asdgjdfjg"));

        when(productRepo.findAll()).thenReturn(productList);
        List<Product> products = productController.getAllProducts();
        assertEquals(productList.size(), products.size());
    }

    @Test
    public void testGetUserById() throws Exception {
        int id = 1;
        Product foundProduct = new Product(1, "Chair", 7, 78.99, "adfakld32589djhfs");

        when(productRepo.findById(id)).thenReturn(Optional.of(foundProduct));

        ResponseEntity<Product> result = productController.getProductById(id);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(foundProduct, result.getBody());
    }

    @Test
    public void testGetProductByIdNotFound() throws ResourceNotFoundException {
        ProductController pcMock = mock(ProductController.class);
        int id = 1;
        
        when(pcMock.getProductById(id)).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> pcMock.getProductById(id));
    }
    
    @Test
    public void testCreateProduct() throws Exception {
        Product createdProduct = new Product(1, "Chair", 7, 78.99, "adfakld32589djhfs");

        when(productRepo.save(Mockito.any(Product.class))).thenReturn(createdProduct);

        ResponseEntity<Product> response = productController.createProduct(createdProduct);
        
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testUpdateProduct() throws Exception {
        Product product = new Product(1, "Chair", 7, 78.99, "adfakld32589djhfs");
        Product updatedProduct = new Product(1, "Bigger Chair", 7, 78.99, "adfakld32589djhfs");

        when(productRepo.findById(Mockito.anyInt())).thenReturn(Optional.of(product));
        when(productRepo.save(Mockito.any(Product.class))).thenReturn(updatedProduct);

        ResponseEntity<Product> result = productController.updateProduct(product.getId(), updatedProduct);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testUpdateProductNotFound() throws Exception {
        ProductController pcMock = mock(ProductController.class);
        Product product = new Product(1, "Chair", 7, 78.99, "adfakld32589djhfs");
        int id = 1;

        when(pcMock.updateProduct(id, product)).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () ->  pcMock.updateProduct(id, product));
    }

    @Test 
    public void testDeleteProduct() throws Exception {
        int id = 1;
        Product product = new Product(1, "Chair", 7, 78.99, "adfakld32589djhfs");

        when(productRepo.findById(Mockito.anyInt())).thenReturn(Optional.of(product));

        ResponseEntity<?> result = productController.deleteProductById(id);

        assertNotNull(result);
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    @Test
    public void testDeleteProductNotFound() throws Exception {
        ProductController pcMock = mock(ProductController.class);
        int id = 1;

        when(pcMock.deleteProductById(id)).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () ->  pcMock.deleteProductById(id));
    }
}
