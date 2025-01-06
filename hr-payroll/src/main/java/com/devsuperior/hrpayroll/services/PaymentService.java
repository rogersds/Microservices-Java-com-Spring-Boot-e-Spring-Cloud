package com.devsuperior.hrpayroll.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.devsuperior.hrpayroll.entities.Payment;
import com.devsuperior.hrpayroll.entities.Worker;
import com.devsuperior.hrpayroll.feignclient.WorkerFeignClient;

@Service
// indica ao spring que aquela class será um componente dele.
public class PaymentService {
	


	@Autowired
	private WorkerFeignClient workerFeignClient;
	
	
	public Payment getPayment (long WorkerId, int days) {
		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("id", Long.toString(WorkerId));
		
		Worker worker = workerFeignClient.findById(WorkerId).getBody();
		return new Payment(worker.getName(), worker.getDailyIncome(), days);
	}

}
