package com.icbt.pahanaedu.service.impl;

import com.icbt.pahanaedu.common.Constants;
import com.icbt.pahanaedu.common.LogSupport;
import com.icbt.pahanaedu.common.ResponseCodes;
import com.icbt.pahanaedu.common.ResponseStatus;
import com.icbt.pahanaedu.dto.*;
import com.icbt.pahanaedu.entity.*;
import com.icbt.pahanaedu.exception.InvalidRequestException;
import com.icbt.pahanaedu.repository.*;
import com.icbt.pahanaedu.service.CustomerService;
import com.icbt.pahanaedu.service.OrderService;
import com.icbt.pahanaedu.util.Utils;
import com.icbt.pahanaedu.util.mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private PromotionMapper promotionMapper;


    @Override
    public OrderManageDto addOrder(OrderManageDto orderManageDto) {
        log.info(LogSupport.ORDER_LOG + "starting.", "addOrder()", orderManageDto.getUserId(), orderManageDto.getCustomerId());

        if (orderManageDto.getOrderDto() == null) {
            log.error(LogSupport.ORDER_LOG + "order data is required.", "addOrder()", orderManageDto.getUserId(), orderManageDto.getCustomerId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "order data is required");
        }
        OrderDto orderDto = orderManageDto.getOrderDto();

        boolean guestCustomer = false;
        Customer customer = null;

        if (orderManageDto.getCustomerId() == null) {
            if (orderManageDto.getCustomerDto() == null) {
                log.error(LogSupport.ORDER_LOG + "customer data is required.", "addOrder()", orderManageDto.getUserId(), null);
                throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "customer data is required");
            } else {
                String mobileNumber = Utils.convertMobileNumber(orderManageDto.getCustomerDto().getPhoneNumber());
                List<Customer> customerList = customerRepository.findAllByPhoneNumber(mobileNumber);
                if (!customerList.isEmpty()) {
                    customer =  customerList.get(0);
                } else {
                    guestCustomer = true;
                }
            }
        } else {
            Optional<Customer> byId = customerRepository.findById(orderManageDto.getCustomerId());
            if (byId.isEmpty()) {
                log.error(LogSupport.ORDER_LOG + "customerId is invalid.", "addOrder()", orderManageDto.getUserId(), orderManageDto.getCustomerId());
                throw new InvalidRequestException(ResponseCodes.INVALID_CUSTOMER_ID_CODE, "customer Id is invalid");
            }
            customer = byId.get();
        }

        if (guestCustomer) {
            CustomerDto customerDto = orderManageDto.getCustomerDto();
            CustomerMangeDto customerMangeDto = new CustomerMangeDto();
            customerMangeDto.setCustomerDto(customerDto);
            CustomerMangeDto result = customerService.addCustomer(customerMangeDto);
            if (result.getStatus().equalsIgnoreCase(ResponseStatus.SUCCESS.getStatus())) {
                Optional<Customer> byId = customerRepository.findById(result.getCustomerId());
                if (byId.isPresent()) {
                    customer = byId.get();
                } else {
                    log.error(LogSupport.ORDER_LOG + "technical error.", "addOrder()", orderManageDto.getUserId(), orderManageDto.getCustomerId());
                    throw new InvalidRequestException(ResponseCodes.TECHNICAL_ERROR_CODE, "Technical Error Please Try Again Later");
                }
            } else {
                log.error(LogSupport.ORDER_LOG + "technical error.", "addOrder()", orderManageDto.getUserId(), orderManageDto.getCustomerId());
                throw new InvalidRequestException(ResponseCodes.TECHNICAL_ERROR_CODE, "Technical Error Please Try Again Later");
            }
        }


        if (orderDto.getPaidAmount() == null) {
            log.error(LogSupport.ORDER_LOG + "paidAmount is required.", "addOrder()", orderManageDto.getUserId(), orderManageDto.getCustomerId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "paidAmount is required");
        }

        if (orderDto.getPaymentType() == null || orderDto.getPaymentType().isEmpty()) {
            log.error(LogSupport.ORDER_LOG + "paymentType is required.", "addOrder()", orderManageDto.getUserId(), orderManageDto.getCustomerId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "paymentType is required");
        }

        if (orderDto.getOrderDetailDtos() == null || orderDto.getOrderDetailDtos().isEmpty()) {
            log.error(LogSupport.ORDER_LOG + "orderDetail is required.", "addOrder()", orderManageDto.getUserId(), orderManageDto.getCustomerId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "orderDetail is required");
        }

        List<OrderDetails> orderDetails = new ArrayList<>();

        List<OrderDetailDto> orderDetailsDto = orderDto.getOrderDetailDtos();
        OrderDetails details = null;
        for (int i = 0; i < orderDetailsDto.size(); i++) {
            details = validateOrderDetails(orderDetailsDto.get(i), customer.getCustomerId(), i + 1);
            details.setCreatedDatetime(Utils.getCurrentDateByTimeZone(Constants.TIME_ZONE));
            orderDetails.add(details);
        }

        Order order = orderMapper.toEntity(orderDto);
        order.setCustomer(customer);
        order.setOrderDate(Utils.getCurrentDateByTimeZone(Constants.TIME_ZONE));
        order.setCreatedDatetime(Utils.getCurrentDateByTimeZone(Constants.TIME_ZONE));

        orderRepository.save(order);

        orderDetails.forEach(orderDetail -> {
            Book book = orderDetail.getBook();
            Long bookStock = book.getQuantity();
            Integer orderQuantity = orderDetail.getItemQuantity();
            book.setQuantity(bookStock - orderQuantity);
            book.setModifiedDatetime(Utils.getCurrentDateByTimeZone(Constants.TIME_ZONE));
            // update book stock
            bookRepository.save(book);

            orderDetail.setOrder(order);
            orderDetail.setCreatedDatetime(Utils.getCurrentDateByTimeZone(Constants.TIME_ZONE));
            orderDetailRepository.save(orderDetail);
        });

        // TODO: 7/18/2025 print bill

        orderManageDto.setOrderId(order.getOrderId());
        orderManageDto.setStatus(ResponseStatus.SUCCESS.getStatus());
        orderManageDto.setResponseCode(ResponseCodes.SUCCESS_CODE);
        orderManageDto.setResponseMessage("Order saving successfully");
        log.info(LogSupport.ORDER_LOG + "end.", "addOrder()", orderManageDto.getUserId(), orderManageDto.getCustomerId());
        return orderManageDto;
    }

    private OrderDetails validateOrderDetails(OrderDetailDto orderDetailDto, Long customerId, int sequence) {
        if (orderDetailDto.getBookId() == null) {
            log.error(LogSupport.ORDER_LOG + sequence +" = bookId is required.", "addOrder()", null, customerId);
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, sequence +" = bookId is required");
        }

        Optional<Book> optionalBook = bookRepository.findById(orderDetailDto.getBookId());
        if (optionalBook.isEmpty()) {
            log.error(LogSupport.ORDER_LOG + "invalid bookId.", "addOrder()", null, customerId);
            throw new InvalidRequestException(ResponseCodes.INVALID_BOOK_ID_CODE, "Invalid Book Id");
        }

        if (orderDetailDto.getItemQuantity() == null) {
            log.error(LogSupport.ORDER_LOG + sequence +" = itemQuantity is required.", "addOrder()", null, customerId);
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, sequence +" = itemQuantity is required");
        }

        Book book = optionalBook.get();
        if (book.getQuantity() <= orderDetailDto.getItemQuantity()) {
            log.error(LogSupport.ORDER_LOG + sequence +" = book quantity exceed", "addOrder()", null, customerId);
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, sequence +" = book quantity exceed");
        }

        OrderDetails details = orderDetailMapper.toEntity(orderDetailDto);
        details.setBook(book);

        if (orderDetailDto.getPromotionId() != null) {
            Optional<Promotion> byId = promotionRepository.findById(orderDetailDto.getPromotionId());
            if (byId.isEmpty()) {
                log.error(LogSupport.ORDER_LOG + sequence +" = invalid promotionId.", "addOrder()", null, customerId);
                throw new InvalidRequestException(ResponseCodes.INVALID_PROMOTION_ID_CODE, sequence +" = invalid promotion id");
            }

            Promotion promotion = byId.get();
            Date currentDatetime = Utils.getCurrentDateByTimeZone(Constants.TIME_ZONE);
            if (currentDatetime.after(promotion.getPromotionEndDate())
                || currentDatetime.before(promotion.getPromotionStartDate())) {
                log.error(LogSupport.ORDER_LOG + sequence +" = invalid promotion date range.", "addOrder()", null, customerId);
                throw new InvalidRequestException(ResponseCodes.INVALID_PROMOTION_ID_CODE, sequence +" = invalid promotion date range");
            }
            details.setPromotion(promotion);
        }
        return details;
    }

    @Override
    public OrderManageDto getOrderById(OrderManageDto orderManageDto) {
        log.info(LogSupport.ORDER_LOG + "starting.", "getOrderById()", orderManageDto.getUserId(), orderManageDto.getCustomerId());

        if (orderManageDto.getOrderId() == null) {
            log.error(LogSupport.ORDER_LOG + "orderId is required.", "getOrderById()", orderManageDto.getUserId(), orderManageDto.getCustomerId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "orderId is required");
        }

        Optional<Order> byId = orderRepository.findById(orderManageDto.getOrderId());
        if (byId.isEmpty()) {
            log.error(LogSupport.ORDER_LOG + " invalid orderId.", "getOrderById()", orderManageDto.getUserId(), orderManageDto.getCustomerDto());
            throw new InvalidRequestException(ResponseCodes.INVALID_ORDER_ID_CODE, "invalid order id");
        }

        Order order = byId.get();
        OrderDto orderDto = orderMapper.toDto(order);
        CustomerDto customerDto = customerMapper.toDto(order.getCustomer());
        orderDto.setCustomerId(customerDto.getCustomerId());
        orderDto.setCustomer(customerDto);

        if (orderManageDto.isDetailsRequested()) {
            List<OrderDetailDto> orderDetailsList = getOrderDetailsList(order);
            orderDto.setOrderDetailDtos(orderDetailsList);
        }

        orderManageDto.setOrderDto(orderDto);
        orderManageDto.setStatus(ResponseStatus.SUCCESS.getStatus());
        orderManageDto.setResponseCode(ResponseCodes.SUCCESS_CODE);
        orderManageDto.setResponseMessage("Order getting successfully");
        log.info(LogSupport.ORDER_LOG + "end.", "getOrderById()", orderManageDto.getUserId(), orderManageDto.getCustomerId());
        return orderManageDto;
    }

    @Override
    public OrderManageDto getOrderDetailsByOrderId(OrderManageDto orderManageDto) {
        log.info(LogSupport.ORDER_LOG + "starting.", "getOrderDetailsByOrderId()", orderManageDto.getUserId(), orderManageDto.getCustomerId());

        if (orderManageDto.getOrderId() == null) {
            log.error(LogSupport.ORDER_LOG + "orderId is required.", "getOrderDetailsByOrderId()", orderManageDto.getUserId(), orderManageDto.getCustomerId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "orderId is required");
        }

        Optional<Order> byId = orderRepository.findById(orderManageDto.getOrderId());
        if (byId.isEmpty()) {
            log.error(LogSupport.ORDER_LOG + " invalid orderId.", "getOrderDetailsByOrderId()", orderManageDto.getUserId(), orderManageDto.getCustomerDto());
            throw new InvalidRequestException(ResponseCodes.INVALID_ORDER_ID_CODE, "invalid order id");
        }

        List<OrderDetailDto> orderDetailsList = getOrderDetailsList(byId.get());

        orderManageDto.setDetailDetailList(orderDetailsList);
        orderManageDto.setStatus(ResponseStatus.SUCCESS.getStatus());
        orderManageDto.setResponseCode(ResponseCodes.SUCCESS_CODE);
        orderManageDto.setResponseMessage("Order getting successfully");
        log.info(LogSupport.ORDER_LOG + "end.", "getOrderDetailsByOrderId()", orderManageDto.getUserId(), orderManageDto.getCustomerId());
        return orderManageDto;
    }

    private List<OrderDetailDto> getOrderDetailsList(Order order) {
        List<OrderDetails> orderDetails = orderDetailRepository.findByOrder(order);
        List<OrderDetailDto> orderDetailDtoList = new ArrayList<>();

        orderDetails.forEach(details -> {
            OrderDetailDto orderDetailDto = orderDetailMapper.toDto(details);
            orderDetailDto.setBook(bookMapper.toDto(details.getBook()));
            if (details.getPromotion() != null) {
                orderDetailDto.setPromotion(promotionMapper.toDto(details.getPromotion()));
            }
            orderDetailDtoList.add(orderDetailDto);
        });

        return orderDetailDtoList;
    }

    @Override
    public OrderManageDto getAllOrders(OrderManageDto orderManageDto) {
        log.info(LogSupport.ORDER_LOG + "starting.", "getAllOrders()", orderManageDto.getUserId(), orderManageDto.getCustomerId());

        List<Order> all = orderRepository.findAll();

        List<OrderDto> orderDtos = new ArrayList<>();
        if (all.isEmpty()) {
            log.info(LogSupport.ORDER_LOG + "order list is empty.", "getAllOrders()", orderManageDto.getUserId(), orderManageDto.getCustomerId());
            orderManageDto.setOrderList(orderDtos);
            orderManageDto.setStatus(ResponseStatus.SUCCESS.getStatus());
            orderManageDto.setResponseCode(ResponseCodes.SUCCESS_CODE);
            orderManageDto.setResponseMessage("Order getting successfully");
            return orderManageDto;
        }

        all.forEach(order -> {
            OrderDto orderDto = orderMapper.toDto(order);
            orderDto.setCustomer(customerMapper.toDto(order.getCustomer()));
            orderDto.setOrderDetailDtos(getOrderDetailsList(order));
            orderDtos.add(orderDto);
        });

        orderManageDto.setOrderList(orderDtos);
        orderManageDto.setStatus(ResponseStatus.SUCCESS.getStatus());
        orderManageDto.setResponseCode(ResponseCodes.SUCCESS_CODE);
        orderManageDto.setResponseMessage("Order getting successfully");
        log.info(LogSupport.ORDER_LOG + "end.", "getAllOrders()", orderManageDto.getUserId(), orderManageDto.getCustomerId());
        return orderManageDto;
    }
}
