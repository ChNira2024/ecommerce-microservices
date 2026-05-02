package com.niranjana.ecommerce.product.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.niranjana.ecommerce.product.dto.ProductRequest;
import com.niranjana.ecommerce.product.dto.ProductResponse;
import com.niranjana.ecommerce.product.service.ProductService;
import com.niranjana.ecommerce.product.util.LogUtil;


@RestController
@RequestMapping("/api/products")
public class ProductController {
	private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Autowired private ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {
        log.info("Request: Create product name={}", productRequest.getName());
        
        ProductResponse response = productService.createProduct(productRequest);
        
        log.info("Product created: {}", LogUtil.toJson(response));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts() {
    	 log.info("Request: Fetch all products");
         List<ProductResponse> products = productService.getAllProducts();

         if (products.isEmpty()) {
             log.warn("No products found");
             return ResponseEntity.noContent().build(); // 204
         }
         log.info("Fetched {} products", products.size());
         return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
    	 log.info("Request: Get product id={}", id);

         ProductResponse response = productService.getProductById(id);
         log.info("Response: {}", LogUtil.toJson(response));
         return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable("id") Long id,@RequestBody ProductRequest productRequest) {
        log.info("Request: Update product id={}", id);
        ProductResponse response = productService.updateProduct(id, productRequest);

        log.info("Product updated: {}", LogUtil.toJson(response));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id) {
        log.info("Request: Delete product id={}", id);
        productService.deleteProduct(id);
        log.info("Product deleted (soft delete), id={}", id);
        return ResponseEntity.ok("Product deleted successfully");

    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchProducts(@RequestParam String keyword) {
    	log.info("Request: Search products keyword={}", keyword);

        List<ProductResponse> results = productService.searchProducts(keyword);
        
        if (results.isEmpty()) {
            log.warn("No products found for keyword={}", keyword);
            return ResponseEntity.noContent().build();
        }
        log.info("Found {} products", results.size());
        return ResponseEntity.ok(results);
    }
}