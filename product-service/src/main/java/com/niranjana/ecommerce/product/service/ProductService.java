package com.niranjana.ecommerce.product.service;

import java.util.List;

import com.niranjana.ecommerce.product.dto.ProductRequest;
import com.niranjana.ecommerce.product.dto.ProductResponse;

public interface ProductService {
	
	public ProductResponse createProduct(ProductRequest productRequest);
	
	public ProductResponse updateProduct(Long id, ProductRequest productRequest);
	
	public List<ProductResponse> getAllProducts();
	
	public void deleteProduct(Long id);
	
	public List<ProductResponse> searchProducts(String keyword);
	
	public ProductResponse getProductById(Long id);

}
