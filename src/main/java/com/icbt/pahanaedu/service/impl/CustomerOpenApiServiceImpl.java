package com.icbt.pahanaedu.service.impl;

import com.icbt.pahanaedu.common.Constants;
import com.icbt.pahanaedu.common.LogSupport;
import com.icbt.pahanaedu.common.ResponseCodes;
import com.icbt.pahanaedu.common.ResponseStatus;
import com.icbt.pahanaedu.dto.BookDetailsDto;
import com.icbt.pahanaedu.dto.CategoryDto;
import com.icbt.pahanaedu.dto.CustomerOpenApiDto;
import com.icbt.pahanaedu.dto.PromotionDto;
import com.icbt.pahanaedu.entity.*;
import com.icbt.pahanaedu.repository.*;
import com.icbt.pahanaedu.service.CategoryService;
import com.icbt.pahanaedu.service.CustomerOpenApiService;
import com.icbt.pahanaedu.util.mapper.AwardMapper;
import com.icbt.pahanaedu.util.mapper.BookMapper;
import com.icbt.pahanaedu.util.mapper.CategoryMapper;
import com.icbt.pahanaedu.util.mapper.PromotionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private AwardRepository awardRepository;

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
        if (all.isEmpty()) {
            customerOpenApiDto.setPromotionDtoList(promotionDtoList);
            customerOpenApiDto.setStatus(ResponseStatus.SUCCESS.getStatus());
            customerOpenApiDto.setResponseCode(ResponseCodes.SUCCESS_CODE);
            customerOpenApiDto.setResponseMessage("Promotion getting successfully");
            return customerOpenApiDto;
        }

        all.forEach(promotion -> {
            if (!promotion.getPromotionStatus().equalsIgnoreCase(Constants.DELETE_STATUS)) {
                PromotionDto promotionMapperDto = promotionMapper.toDto(promotion);
                promotionMapperDto.setPromotionUrl("/assets/scroll/scroll_4.jpg");
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
