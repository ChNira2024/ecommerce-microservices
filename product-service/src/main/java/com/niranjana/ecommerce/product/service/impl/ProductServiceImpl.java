package com.niranjana.ecommerce.product.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.niranjana.ecommerce.product.dto.ProductRequest;
import com.niranjana.ecommerce.product.dto.ProductResponse;
import com.niranjana.ecommerce.product.entity.Product;
import com.niranjana.ecommerce.product.exception.ProductNotFoundException;
import com.niranjana.ecommerce.product.producer.ProductProducer;
import com.niranjana.ecommerce.product.producer.event.ProductEvent;
import com.niranjana.ecommerce.product.repository.ProductRepository;
import com.niranjana.ecommerce.product.service.ProductService;


@Service
public class ProductServiceImpl implements ProductService {
	private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired private ProductRepository productRepository;
    @Autowired private ProductProducer productProducer;
    

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
    	log.info("Creating product: name={}", productRequest.getName());

        try {
            Product product = new Product();
            updateProductFromRequest(product, productRequest);
            Product savedProduct = productRepository.save(product);

            log.info("Product created successfully, id={}", savedProduct.getId());
            
            // CREATE EVENT
            ProductEvent event = new ProductEvent(savedProduct.getId(),savedProduct.getStockQuantity());

            // SEND TO KAFKA
            productProducer.sendProductCreatedEvent(event);

            log.info("ProductCreatedEvent sent for productId={}", savedProduct.getId());

            return mapToProductResponse(savedProduct);

        } catch (Exception ex) {
            log.error("Error creating product", ex);
            throw new RuntimeException("Failed to create product");
        }
    }

    
    @Override
    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        /*return productRepository.findById(id)
                .map(existingProduct -> {
                    updateProductFromRequest(existingProduct, productRequest);
                    Product savedProduct = productRepository.save(existingProduct);
                    return mapToProductResponse(savedProduct);
                });
          */
    	log.info("Updating product id={}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Product not found for update, id={}", id);
                    return new ProductNotFoundException("Product not found with id: " + id);
                });
        try {
            updateProductFromRequest(product, productRequest);
            Product saved = productRepository.save(product);

            log.info("Product updated successfully, id={}", id);
            return mapToProductResponse(saved);
        } catch (Exception ex) {
            log.error("Error updating product id={}", id, ex);
            throw new RuntimeException("Failed to update product");
        }
    }

    @Override
    public List<ProductResponse> getAllProducts() {
    	log.info("Fetching all active products");
        List<ProductResponse> products = productRepository.findByActiveTrue().stream().map(this::mapToProductResponse).toList();

        if (products.isEmpty()) {
            log.warn("No active products found");
        } else {
            log.info("Fetched {} products", products.size());
        }
        return products;
    }

    @Override
    public void deleteProduct(Long id) {        
        log.info("Deleting product id={}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Product not found for delete, id={}", id);
                    return new ProductNotFoundException("Product not found with id: " + id);
                });
        product.setActive(false);
        productRepository.save(product);
        log.info("Product deleted successfully, id={}", id);
    }

    @Override
    public List<ProductResponse> searchProducts(String keyword) {
    	log.info("Searching products with keyword={}", keyword);
        List<ProductResponse> results = productRepository.searchProducts(keyword).stream().map(this::mapToProductResponse).toList();
        log.info("Found {} products for keyword={}", results.size(), keyword);
        return results;
    }

    @Override
    public ProductResponse getProductById(Long id) {
    	log.info("Inside ProductServiceImpl - getProductById method, id={}", id);
    	log.info("Fetching product by id={}", id);
        return productRepository.findByIdAndActiveTrue(id)
                .map(product -> {
                    log.info("Product found id={}", id);
                    return mapToProductResponse(product);
                })
                .orElseThrow(() -> {
                    log.warn("Product not found id={}", id);
                    return new ProductNotFoundException("Product not found with id: " + id);
                });
    }
    
    private ProductResponse mapToProductResponse(Product savedProduct) {
        ProductResponse response = new ProductResponse();
        response.setId(savedProduct.getId());
        response.setName(savedProduct.getName());
        response.setActive(savedProduct.getActive());
        response.setCategory(savedProduct.getCategory());
        response.setDescription(savedProduct.getDescription());
        response.setPrice(savedProduct.getPrice());
        response.setImageUrl(savedProduct.getImageUrl());
        response.setStockQuantity(savedProduct.getStockQuantity());
        return response;
    }

    private void updateProductFromRequest(Product product, ProductRequest productRequest) {
        product.setName(productRequest.getName());
        product.setCategory(productRequest.getCategory());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setImageUrl(productRequest.getImageUrl());
        product.setStockQuantity(productRequest.getStockQuantity());
    }

}