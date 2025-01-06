package com.devsuperior.hrpayroll.services;

import org.springframework.stereotype.Service;

import com.devsuperior.hrpayroll.entities.Payment;

@Service
// indica ao spring que aquela class será um componente dele.
public class PaymentService {
	
	
	public Payment getPayment (long WorkerId, int days) {
		return new Payment("bob", 200.0, days);
	}

}
