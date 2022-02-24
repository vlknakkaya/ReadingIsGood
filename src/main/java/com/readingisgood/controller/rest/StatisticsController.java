package com.readingisgood.controller.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.readingisgood.model.dto.MonthlyOrderStatisticsDTO;
import com.readingisgood.model.entity.Customer;
import com.readingisgood.model.entity.Order;
import com.readingisgood.service.CustomerService;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

	@Autowired
	private CustomerService customerService;

	/**
	 * Returns monthly order statistics of customer that has given id
	 * 
	 * @param customerId
	 * @return MonthlyOrderStatisticsDTO list that represents monthly statistics
	 * @throws EntityNotFoundException can be thrown if the id is not found
	 */
	@GetMapping
	public List<MonthlyOrderStatisticsDTO> getOrderStatisticsByCustomer(@RequestParam long customerId) {
		Customer customer = customerService.findById(customerId);
		List<MonthlyOrderStatisticsDTO> monthlyOrderStatisticsDTOs = new ArrayList<>();
		Map<Integer, List<Order>> ordersByMonthMap = generateOrdersByMonthMap(customer.getOrders());

		for (Map.Entry<Integer, List<Order>> entry : ordersByMonthMap.entrySet()) {
			int monthId = entry.getKey();
			int totalOrderCount = entry.getValue().size();
			int totalBookCount = 0;
			double totalPurchasedAmount = 0.0;

			for (Order order : entry.getValue()) {
				totalPurchasedAmount += order.getTotalAmount();

				int totalCount = 0;
				for (int count : order.getCart().values()) {
					totalCount += count;
				}

				totalBookCount += totalCount;
			}
			monthlyOrderStatisticsDTOs
					.add(new MonthlyOrderStatisticsDTO(monthId, totalOrderCount, totalBookCount, totalPurchasedAmount));
		}

		return monthlyOrderStatisticsDTOs;
	}

	/**
	 * Creates a map that holds Orders group by months
	 * 
	 * @param orders
	 * @return HashMap that represents Orders group by months
	 */
	private Map<Integer, List<Order>> generateOrdersByMonthMap(List<Order> orders) {
		Map<Integer, List<Order>> map = new HashMap<>();
		orders.forEach(x -> {
			int monthValue = x.getDate().toLocalDate().getMonthValue();
			List<Order> ordersList = map.get(monthValue);
			if (ordersList != null) {
				ordersList.add(x);
			} else {
				ordersList = new ArrayList<>();
				ordersList.add(x);
			}
			map.put(monthValue, ordersList);
		});

		return map;
	}

}
