package com.icbt.pahanaedu.service.impl;

import com.icbt.pahanaedu.common.Constants;
import com.icbt.pahanaedu.common.LogSupport;
import com.icbt.pahanaedu.common.ResponseCodes;
import com.icbt.pahanaedu.common.ResponseStatus;
import com.icbt.pahanaedu.dto.*;
import com.icbt.pahanaedu.entity.*;
import com.icbt.pahanaedu.exception.InvalidRequestException;
import com.icbt.pahanaedu.repository.*;
import com.icbt.pahanaedu.service.CategoryService;
import com.icbt.pahanaedu.service.CustomerOpenApiService;
import com.icbt.pahanaedu.service.CustomerService;
import com.icbt.pahanaedu.util.Utils;
import com.icbt.pahanaedu.util.mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class CustomerOpenApiServiceImpl implements CustomerOpenApiService {

    private static final Logger log = LoggerFactory.getLogger(CustomerOpenApiServiceImpl.class);

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private PromotionMapper promotionMapper;

    @Autowired
    private PromotionBookRepository promotionBookRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private AwardMapper awardMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private AwardRepository awardRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private CustomerService customerService;

    @Value("${catrgory.icon.public.url}")
    private String CATEGORY_PUBLIC_URL;

    @Override
    public CustomerOpenApiDto getCustomerInfo(CustomerOpenApiDto customerOpenApiDto) {
        log.info(LogSupport.CUSTOMER_OPEN_API + "starting.", "addCategory()", customerOpenApiDto.getCustomerId());
        return null;
    }

    @Override
    public CustomerOpenApiDto getCategories(CustomerOpenApiDto customerOpenApiDto) {
        Long customerId = customerOpenApiDto.getCustomerId();
        log.info(LogSupport.CUSTOMER_OPEN_API + "starting.", "getCategories()", customerId);

        List<CategoryDto> categoryDtos = new ArrayList<>();

        List<Category> categories = categoryRepository.findAllByCategoryStatus(Constants.ACTIVE_STATUS);
        if (categories.isEmpty()) {
            log.info(LogSupport.CUSTOMER_OPEN_API + "categories is empty.", "getCategoriesByStatus()", customerId);
            customerOpenApiDto.setCategoryDetailsList(categoryDtos);
            customerOpenApiDto.setStatus(ResponseStatus.SUCCESS.getStatus());
            customerOpenApiDto.setResponseCode(ResponseCodes.SUCCESS_CODE);
            customerOpenApiDto.setResponseMessage("Category getting successfully");
            return customerOpenApiDto;
        }

        categories.forEach(category -> {
            CategoryDto categoryDto = categoryMapper.toDto(category);
            categoryDto.setCategoryIconUrl(CATEGORY_PUBLIC_URL);
            categoryDtos.add(categoryDto);
        });

        customerOpenApiDto.setCategoryDetailsList(categoryDtos);
        customerOpenApiDto.setStatus(ResponseStatus.SUCCESS.getStatus());
        customerOpenApiDto.setResponseCode(ResponseCodes.SUCCESS_CODE);
        customerOpenApiDto.setResponseMessage("Category getting successfully");
        log.info(LogSupport.CUSTOMER_OPEN_API + "end.", "getCategoriesByStatus()", customerId);


        log.info(LogSupport.CUSTOMER_OPEN_API + "ending.", "getCategories()", customerId);
        return customerOpenApiDto;
    }

    @Override
    public CustomerOpenApiDto getPromotions(CustomerOpenApiDto customerOpenApiDto) {
        Long customerId = customerOpenApiDto.getCustomerId();
        log.info(LogSupport.CUSTOMER_OPEN_API + "starting.", "getPromotions()", customerId);

        List<PromotionDto> promotionDtoList =  new ArrayList<>();

        List<Promotion> all = promotionRepository.getActivePromotionNow();
        System.out.println("all.size() = " + all.size());
        if (all.isEmpty()) {
            customerOpenApiDto.setPromotionDtoList(promotionDtoList);
            customerOpenApiDto.setStatus(ResponseStatus.SUCCESS.getStatus());
            customerOpenApiDto.setResponseCode(ResponseCodes.SUCCESS_CODE);
            customerOpenApiDto.setResponseMessage("Promotion getting successfully");
            return customerOpenApiDto;
        }

        all.forEach(promotion -> {
            System.out.println("promotion = " + promotion);
            if (!promotion.getPromotionStatus().equalsIgnoreCase(Constants.DELETE_STATUS)) {
                PromotionDto promotionMapperDto = promotionMapper.toDto(promotion);
                System.out.println("promotionMapperDto = " + promotionMapperDto);
                if (customerOpenApiDto.isRequestBookDetails()) {
                    List<PromotionBook> promotionBooks = promotionBookRepository.findAllByPromotion(promotion);
                    if (!promotionBooks.isEmpty()) {
                        promotionMapperDto.setBookDetailsDtoList(mappingPromotionBooksList(promotionBooks));
                    }
                }
                promotionDtoList.add(promotionMapperDto);
            }
        });

        customerOpenApiDto.setPromotionDtoList(promotionDtoList);
        customerOpenApiDto.setStatus(ResponseStatus.SUCCESS.getStatus());
        customerOpenApiDto.setResponseCode(ResponseCodes.SUCCESS_CODE);
        customerOpenApiDto.setResponseMessage("Promotion getting successfully");

        log.info(LogSupport.CUSTOMER_OPEN_API + "ending.", "getPromotions()", customerId);
        return customerOpenApiDto;
    }

    private List<BookDetailsDto> mappingPromotionBooksList(List<PromotionBook> promotionBooks) {
        List<BookDetailsDto> bookDetailsDtoList = new ArrayList<>();
        promotionBooks.forEach(promotionBook -> {
            if (promotionBook.getStatus().equalsIgnoreCase(Constants.ACTIVE_STATUS)) {
                BookDetailsDto bookDetailsDto = bookMapper.toDto(promotionBook.getBook());
                bookDetailsDtoList.add(bookDetailsDto);
            }
        });

        return bookDetailsDtoList;
    }

    @Override
    public CustomerOpenApiDto getBooks(CustomerOpenApiDto customerOpenApiDto) {
        Long customerId = customerOpenApiDto.getCustomerId();
        log.info(LogSupport.CUSTOMER_OPEN_API + "starting.", "getBooks()", customerId);

        String requestType = customerOpenApiDto.getRequestType();

        List<BookDetailsDto> bookDetailsDtoList = new ArrayList<>();
        List<Book> bookList = new ArrayList<>();

        if (requestType != null && requestType.equalsIgnoreCase(Constants.PROMOTION_REQUEST_TYPE)) {
            bookList = getBooksByPromotion(customerOpenApiDto);
        } else if (requestType != null && requestType.equalsIgnoreCase(Constants.CATEGORY_REQUEST_TYPE)) {
            bookList = getBooksByCategory(customerOpenApiDto);
        } else {
            bookList = getBooksAll(customerOpenApiDto);
        }

        bookList.forEach(book -> {
            if (book.getBookStatus().equalsIgnoreCase(Constants.ACTIVE_STATUS)) {
                BookDetailsDto mapperDto = bookMapper.toDto(book);
                mapperDto.setCategoryName(book.getCategory().getCategoryName());
                List<Award> awardList = awardRepository.findByBook(book);
                if (!awardList.isEmpty()) {
                    mapperDto.setAwardList(awardMapper.toDtoList(awardList));
                }

                setPromotion(mapperDto.getBookId(), book.getPrice(), mapperDto);
                bookDetailsDtoList.add(mapperDto);
            }
        });

        customerOpenApiDto.setStatus(ResponseStatus.SUCCESS.getStatus());
        customerOpenApiDto.setResponseCode(ResponseCodes.SUCCESS_CODE);
        customerOpenApiDto.setResponseMessage("Book getting successfully");
        customerOpenApiDto.setBookDetailsList(bookDetailsDtoList);
        log.info(LogSupport.CUSTOMER_OPEN_API + "ending.", "getBooks()", customerId);
        return customerOpenApiDto;
    }

    @Override
    public CustomerOpenApiDto addOrder(CustomerOpenApiDto customerOpenApiDto) {
        log.info(LogSupport.CUSTOMER_OPEN_API + "starting.", "addOrder()", customerOpenApiDto.getCustomerId(), customerOpenApiDto.getCustomerId());

        if (customerOpenApiDto.getOrderDto() == null) {
            log.error(LogSupport.CUSTOMER_OPEN_API + "order data is required.", "addOrder()", customerOpenApiDto.getCustomerId(), customerOpenApiDto.getCustomerId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "order data is required");
        }
        OrderDto orderDto = customerOpenApiDto.getOrderDto();

        boolean guestCustomer = false;
        Customer customer = null;

        if (customerOpenApiDto.getCustomerId() == null) {
            if (customerOpenApiDto.getCustomerDto() == null) {
                log.error(LogSupport.CUSTOMER_OPEN_API + "customer data is required.", "addOrder()", customerOpenApiDto.getCustomerId(), null);
                throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "customer data is required");
            } else {
                String mobileNumber = Utils.convertMobileNumber(customerOpenApiDto.getCustomerDto().getPhoneNumber());
                List<Customer> customerList = customerRepository.findAllByPhoneNumber(mobileNumber);
                if (!customerList.isEmpty()) {
                    customer =  customerList.get(0);
                } else {
                    guestCustomer = true;
                }
            }
        } else {
            Optional<Customer> byId = customerRepository.findById(customerOpenApiDto.getCustomerId());
            if (byId.isEmpty()) {
                log.error(LogSupport.CUSTOMER_OPEN_API + "customerId is invalid.", "addOrder()", customerOpenApiDto.getCustomerId(), customerOpenApiDto.getCustomerId());
                throw new InvalidRequestException(ResponseCodes.INVALID_CUSTOMER_ID_CODE, "customer Id is invalid");
            }
            customer = byId.get();
        }

        if (guestCustomer) {
            CustomerDto customerDto = customerOpenApiDto.getCustomerDto();
            CustomerMangeDto customerMangeDto = new CustomerMangeDto();
            customerMangeDto.setCustomerDto(customerDto);
            CustomerMangeDto result = customerService.addCustomer(customerMangeDto);
            if (result.getStatus().equalsIgnoreCase(ResponseStatus.SUCCESS.getStatus())) {
                Optional<Customer> byId = customerRepository.findById(result.getCustomerId());
                if (byId.isPresent()) {
                    customer = byId.get();
                } else {
                    log.error(LogSupport.CUSTOMER_OPEN_API + "technical error.", "addOrder()", customerOpenApiDto.getCustomerId(), customerOpenApiDto.getCustomerId());
                    throw new InvalidRequestException(ResponseCodes.TECHNICAL_ERROR_CODE, "Technical Error Please Try Again Later");
                }
            } else {
                log.error(LogSupport.CUSTOMER_OPEN_API + "technical error.", "addOrder()", customerOpenApiDto.getCustomerId(), customerOpenApiDto.getCustomerId());
                throw new InvalidRequestException(ResponseCodes.TECHNICAL_ERROR_CODE, "Technical Error Please Try Again Later");
            }
        }


        if (orderDto.getPaidAmount() == null) {
            log.error(LogSupport.CUSTOMER_OPEN_API + "paidAmount is required.", "addOrder()", customerOpenApiDto.getCustomerId(), customerOpenApiDto.getCustomerId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "paidAmount is required");
        }

        if (orderDto.getPaymentType() == null || orderDto.getPaymentType().isEmpty()) {
            log.error(LogSupport.CUSTOMER_OPEN_API + "paymentType is required.", "addOrder()", customerOpenApiDto.getCustomerId(), customerOpenApiDto.getCustomerId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "paymentType is required");
        }

        if (orderDto.getOrderDetailDtos() == null || orderDto.getOrderDetailDtos().isEmpty()) {
            log.error(LogSupport.CUSTOMER_OPEN_API + "orderDetail is required.", "addOrder()", customerOpenApiDto.getCustomerId(), customerOpenApiDto.getCustomerId());
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
        Long orderId = order.getOrderId();
        System.out.println("orderId = " + orderId);

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

        System.out.println("orderId = " + orderId);

        // TODO: 7/18/2025 print bill

        customerOpenApiDto.setOrderId(order.getOrderId());
        customerOpenApiDto.setStatus(ResponseStatus.SUCCESS.getStatus());
        customerOpenApiDto.setResponseCode(ResponseCodes.SUCCESS_CODE);
        customerOpenApiDto.setResponseMessage("Order saving successfully");
        log.info(LogSupport.CUSTOMER_OPEN_API + "end.", "addOrder()", customerOpenApiDto.getCustomerId(), customerOpenApiDto.getCustomerId());
        return customerOpenApiDto;
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

    private List<Book> getBooksByPromotion(CustomerOpenApiDto customerOpenApiDto) {
        List<Book> bookList = bookRepository.findAllByPromotion(customerOpenApiDto.getRequestId());
        if (bookList.isEmpty()) {
            List<Book> filterBookList = new ArrayList<>();
            bookList.forEach(book -> {
                List<Promotion> activePromotionByBookNow = promotionRepository.getActivePromotionByBookNow(book.getBookId());
                if (!activePromotionByBookNow.isEmpty()) {
                    if (Objects.equals(activePromotionByBookNow.get(0).getPromotionId(), customerOpenApiDto.getRequestId())) {
                        filterBookList.add(book);
                    }
                }
            });
            return filterBookList;
        }
        return bookList;
    }

    private List<Book> getBooksByCategory(CustomerOpenApiDto customerOpenApiDto) {
        List<Book> bookList = bookRepository.findAllByCategory(customerOpenApiDto.getRequestId());
        if (bookList.isEmpty()) {
            return bookList;
        }
        return bookList;
    }

    private List<Book> getBooksAll(CustomerOpenApiDto customerOpenApiDto) {
        List<Book> bookList = bookRepository.findAllByBookStatus(Constants.ACTIVE_STATUS);
        if (bookList.isEmpty()) {
            return bookList;
        }
        return bookList;
    }

    private void setPromotion(Long bookId, BigDecimal bookPrice, BookDetailsDto bookDetailsDto) {
        List<Promotion> activePromotionByBookNow = promotionRepository.getActivePromotionByBookNow(bookId);
        if (!activePromotionByBookNow.isEmpty()) {
            Promotion activePromotion = activePromotionByBookNow.get(0);
            bookDetailsDto.setPromotionEnable(true);
            bookDetailsDto.setPromotionId(activePromotion.getPromotionId());
            bookDetailsDto.setPromotionType(activePromotion.getPromotionType());
            BigDecimal promotionPrice = activePromotion.getPromotionPrice();
            bookDetailsDto.setPromotionPrice(promotionPrice);
            if (activePromotion.getPromotionType().equalsIgnoreCase(Constants.PROMOTION_FLAT_TYPE)) {
                bookDetailsDto.setPromotionBookPrice(BigDecimal.valueOf(bookPrice.doubleValue()-promotionPrice.doubleValue()));
            } else if (activePromotion.getPromotionType().equalsIgnoreCase(Constants.PROMOTION_PERCENTAGE_TYPE)) {
                bookDetailsDto.setPromotionBookPrice(BigDecimal.valueOf(bookPrice.doubleValue() - (bookPrice.doubleValue() * promotionPrice.doubleValue())));
            }
        }
    }
}
