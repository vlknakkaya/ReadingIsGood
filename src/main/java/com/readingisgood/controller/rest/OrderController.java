package com.readingisgood.controller.rest;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.readingisgood.exception.StockIsNotEnoughException;
import com.readingisgood.model.converter.OrderDTOConverter;
import com.readingisgood.model.dto.CartItemDTO;
import com.readingisgood.model.dto.OrderDTO;
import com.readingisgood.model.entity.Book;
import com.readingisgood.model.entity.Order;
import com.readingisgood.service.BookService;
import com.readingisgood.service.CustomerService;
import com.readingisgood.service.OrderService;
import com.readingisgood.util.OrderStatus;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderDTOConverter orderDTOConverter;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private BookService bookService;

	/**
	 * Returns all Orders
	 * 
	 * @return OrderDTO list that represents all Orders
	 */
	@GetMapping
	public List<OrderDTO> getAllOrders() {
		return orderDTOConverter.convertToDTOList(orderService.findAll());
	}

	/**
	 * Returns Order that has given id
	 * 
	 * @param id Order id
	 * @return OrderDTO that represents founded Order
	 * @throws EntityNotFoundException can be thrown if the id is not found
	 */
	@GetMapping("/{id}")
	public OrderDTO getOrderById(@PathVariable long id) {
		return orderDTOConverter.convertToDTO(orderService.findById(id));
	}

	/**
	 * Updates Order that has given id with given Order properties
	 * 
	 * @param id       Order id
	 * @param orderDTO
	 * @return OrderDTO that represents updated Order
	 * @throws EntityNotFoundException can be thrown if the id is not found
	 */
	@PutMapping("/{id}")
	public OrderDTO updateOrder(@PathVariable long id, @RequestBody OrderDTO orderDTO) {
		Order entity = orderService.findById(id);

		if (StringUtils.hasText(String.valueOf(orderDTO.getCustomerId()))) {
			entity.setCustomer(customerService.findById(orderDTO.getCustomerId()));
		}

		if (StringUtils.hasText(String.valueOf(orderDTO.getDate()))) {
			entity.setDate(orderDTO.getDate());
		}

		if (StringUtils.hasText(orderDTO.getStatus())) {
			entity.setStatus(OrderStatus.valueOf(orderDTO.getStatus()));
		}

		return orderDTOConverter.convertToDTO(orderService.save(entity));
	}

	/**
	 * Creates new Order by given Order properties
	 * 
	 * @param orderDTO
	 * @return OrderDTO that represents created Order
	 * @throws StockIsNotEnoughException can be thrown if the stock is not enough
	 */
	@PostMapping
	public OrderDTO createOrder(@RequestBody OrderDTO orderDTO) {
		checkStock(orderDTO.getCart()); // can be handled by a validator??

		Order newEntity = orderDTOConverter.convertToEntity(orderDTO);

		newEntity.getCart().forEach((k, v) -> {
			decreaseStock(k.getId(), v);
		});

		return orderDTOConverter.convertToDTO(orderService.save(newEntity));
	}

	/**
	 * Removes Order that has given id
	 * 
	 * @param id Order id
	 */
	@DeleteMapping("/{id}")
	public void removeOrderById(@PathVariable long id) {
		orderService.removeById(id);
	}

	/**
	 * Returns Order list according to the given condition
	 * 
	 * @param date      (optional) for Orders that ordered on this date
	 * @param startDate (optional) for Orders that ordered after this date -
	 *                  default: 1970-01-01
	 * @param endDate   (optional) for Orders that ordered before this date -
	 *                  default: 3000-01-01
	 * @return OrderDTO list that represents all founded Orders
	 */
	@GetMapping("/date")
	public List<OrderDTO> getOrdersByDateBetween(@RequestParam(name = "day", required = false) Date date,
			@RequestParam(name = "start", required = false, defaultValue = "1970-01-01") Date startDate,
			@RequestParam(name = "end", required = false, defaultValue = "3000-01-01") Date endDate) {
		if (date != null) {
			return orderDTOConverter.convertToDTOList(orderService.findByDate(date));
		} else {
			return orderDTOConverter.convertToDTOList(orderService.findByDateBetween(startDate, endDate));
		}
	}

	/**
	 * Add given items to cart of Order that has given id
	 * 
	 * @param id           Order id
	 * @param cartItemDTOs
	 * @return OrderDTO that represents updated Order
	 * @throws EntityNotFoundException   can be thrown if the id is not found
	 * @throws StockIsNotEnoughException can be thrown if the stock is not enough
	 */
	@PutMapping("/{id}/add")
	public OrderDTO addToCart(@PathVariable long id, @RequestBody List<CartItemDTO> cartItemDTOs) {
		checkStock(cartItemDTOs);

		Order order = orderService.findById(id);

		for (CartItemDTO cartItemDTO : cartItemDTOs) {
			order.addToCart(decreaseStock(cartItemDTO.getBookId(), cartItemDTO.getCount()), cartItemDTO.getCount());
		}

		return orderDTOConverter.convertToDTO(orderService.save(order));
	}

	/**
	 * Sets Order status by given status id. For status id please see
	 * {@link OrderStatus}
	 * 
	 * @param id       Order id
	 * @param statusId OrderStatus id
	 * @return OrderDTO that represents updated Order
	 * @throws EntityNotFoundException can be thrown if the id is not found
	 */
	@PutMapping("/{id}/status/{statusId}")
	public OrderDTO setOrderStatus(@PathVariable long id, @PathVariable int statusId) {
		Order order = orderService.findById(id);
		order.setStatus(OrderStatus.getStatusById(statusId));

		return orderDTOConverter.convertToDTO(orderService.save(order));
	}

	/**
	 * Checks stocks for given books and counts
	 * 
	 * @param cartItemDTOs
	 * @throws StockIsNotEnoughException can be thrown if the stock is not enough
	 */
	private void checkStock(List<CartItemDTO> cartItemDTOs) {
		cartItemDTOs.forEach(x -> {
			Book book = bookService.findById(x.getBookId());
			if (book.getStock() < x.getCount()) {
				throw new StockIsNotEnoughException(x.getBookId(), book.getStock(), x.getCount());
			}
		});
	}

	/**
	 * Decreases book stocks and saves
	 * 
	 * @param bookId Book id
	 * @param count
	 * @return Book that represents updated Book entity
	 * @throws EntityNotFoundException can be thrown if the id is not found
	 */
	private synchronized Book decreaseStock(long bookId, int count) {
		Book book = bookService.findById(bookId);
		book.decreaseStock(count);
		return bookService.save(book);
	}

}
