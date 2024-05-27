package com.cg.onlinesweetmart.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.onlinesweetmart.entity.Cart;
import com.cg.onlinesweetmart.entity.Product;
import com.cg.onlinesweetmart.entity.SweetOrder;
import com.cg.onlinesweetmart.entity.User;
import com.cg.onlinesweetmart.exception.GlobalExceptionHandler;
import com.cg.onlinesweetmart.repository.CartRepository;
import com.cg.onlinesweetmart.repository.SweetOrderRepository;
import com.cg.onlinesweetmart.repository.UserRepository;
import com.cg.onlinesweetmart.service.SweetOrderService;

@Service
public class SweetOrderServiceImpl implements SweetOrderService {
 
	@Autowired
	SweetOrderRepository sweetOrderRepository;
 
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CartRepository cartRepository;
 
	@Override
	public SweetOrder addSweetOrder(Long userId, SweetOrder sweetOrder) {
		Optional<User> userOpt = userRepository.findById(userId);
		if (userOpt.isPresent()) {
			User user = userOpt.get();
            Cart cart = user.getCart();
            
            List<Product> productListCopy = new ArrayList<>();
            for (Product product : cart.getListProduct()) {
                Product productCopy = new Product();
 
                productCopy.setProductId(product.getProductId());
                productCopy.setName(product.getName());
                productCopy.setPrice(product.getPrice());
                productListCopy.add(productCopy);
            }
            
            sweetOrder.setListProduct(productListCopy);
            sweetOrder.setTotalCost(cart.getGrandTotal());
            sweetOrder.setUser(user);
            SweetOrder savedOrder = sweetOrderRepository.save(sweetOrder);
 
 
            cart.getListProduct().clear();
            cart.setGrandTotal(0.0);
            cart.setProductCount(0);
            cartRepository.save(cart);
            
            userRepository.save(user);
            
            return savedOrder;
		} else {
			return null;
		}
		
	}
 
	@Override
	public SweetOrder updateSweetOrder(SweetOrder sweetOrder) {
		Optional<SweetOrder> optSweetOrd = sweetOrderRepository.findById(sweetOrder.getSweetOrderId());
		if (optSweetOrd.isPresent()) {
			return sweetOrderRepository.save(sweetOrder);
		}
		return null;
	}
	
	@Override
	public SweetOrder cancelSweetOrder(Long sweetOrderId) {
		Optional<SweetOrder> optSweetOrder = sweetOrderRepository.findById(sweetOrderId);
		if (optSweetOrder.isPresent()) {
			sweetOrderRepository.delete(optSweetOrder.get());
			return optSweetOrder.get();
		}
		return null;
		
	}
	
	@Override
	public List<SweetOrder> showAllSweetOrders() {
		List<SweetOrder> listSweetOrder = sweetOrderRepository.findAll();
		if (listSweetOrder.isEmpty()) {
			return null;
		}
		return listSweetOrder;
	}
	
	@Override
	public double calculateTotalCost(Long sweetOrderId) {
		Optional<SweetOrder> optSweetOrder = sweetOrderRepository.findById(sweetOrderId);
		if (optSweetOrder.isEmpty()) {
			return 0;
		}
		SweetOrder sweetOrder = optSweetOrder.get();
		User user = sweetOrder.getUser();
		
		Double totalPrice = user.getCart().getGrandTotal();
		
		return totalPrice;
	}
 
}
