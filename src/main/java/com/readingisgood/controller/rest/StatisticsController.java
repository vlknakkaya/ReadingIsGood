package com.readingisgood.controller.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.readingisgood.model.dto.MonthlyOrderStatisticsDTO;
import com.readingisgood.model.entity.Customer;
import com.readingisgood.model.entity.Order;
import com.readingisgood.service.CustomerService;
import com.readingisgood.service.OrderService;

@RestController
@RequestMapping("/statistics")
@Validated
public class StatisticsController {

	@Autowired
	private CustomerService customerService;
	@Autowired
	private OrderService orderService;

	/**
	 * Returns monthly all order statistics
	 * 
	 * @return MonthlyOrderStatisticsDTO list that represents monthly statistics
	 */
	@GetMapping
	public List<MonthlyOrderStatisticsDTO> getAllOrderStatistics() {
		List<MonthlyOrderStatisticsDTO> monthlyOrderStatisticsDTOs = new ArrayList<>();
		Map<Integer, List<Order>> ordersByMonthMap = generateOrdersByMonthMap(orderService.findAll());

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
	 * Returns monthly order statistics of customer that has given id
	 * 
	 * @param customerId
	 * @return MonthlyOrderStatisticsDTO list that represents monthly statistics
	 * @throws EntityNotFoundException can be thrown if the id is not found
	 */
	@GetMapping("/{customerId}")
	public List<MonthlyOrderStatisticsDTO> getOrderStatisticsByCustomer(@PathVariable @Min(value = 1, message = "id must be greater than 0") long customerId) {
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
