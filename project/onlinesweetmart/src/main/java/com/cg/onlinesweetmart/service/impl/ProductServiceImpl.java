package com.cg.onlinesweetmart.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.onlinesweetmart.entity.Category;
import com.cg.onlinesweetmart.entity.Product;
import com.cg.onlinesweetmart.repository.CategoryRepository;
import com.cg.onlinesweetmart.repository.ProductRepository;

@Service
public class ProductServiceImpl {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	public Product addProduct(Product product, int categoryId) {
		Category category = categoryRepository.findById(categoryId).get();
		product.setCategory(category);
		return productRepository.save(product);
	}
	
	public Product updateProduct(Product product) {
		return productRepository.save(product);
	}
	
	public void cancelProduct(int productId) {
		productRepository.deleteById(productId);;
	}
	
	public List<Product> showAllProduct() {
		return productRepository.findAll();
	}

	public List<Product> getProdyctByCategoryId(int categoryId) {
		return productRepository.findByCategoryCategoryId(categoryId);
	}
}
