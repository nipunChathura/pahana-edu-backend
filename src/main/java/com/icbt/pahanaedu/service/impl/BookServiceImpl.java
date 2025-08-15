package com.icbt.pahanaedu.service.impl;

import com.icbt.pahanaedu.common.Constants;
import com.icbt.pahanaedu.common.LogSupport;
import com.icbt.pahanaedu.common.ResponseCodes;
import com.icbt.pahanaedu.common.ResponseStatus;
import com.icbt.pahanaedu.dto.AwardDto;
import com.icbt.pahanaedu.dto.BookDetailsDto;
import com.icbt.pahanaedu.dto.BookManageDto;
import com.icbt.pahanaedu.entity.Award;
import com.icbt.pahanaedu.entity.Book;
import com.icbt.pahanaedu.entity.Category;
import com.icbt.pahanaedu.entity.Promotion;
import com.icbt.pahanaedu.exception.InvalidRequestException;
import com.icbt.pahanaedu.util.mapper.AwardMapper;
import com.icbt.pahanaedu.util.mapper.BookMapper;
import com.icbt.pahanaedu.util.mapper.PromotionMapper;
import com.icbt.pahanaedu.repository.AwardRepository;
import com.icbt.pahanaedu.repository.BookRepository;
import com.icbt.pahanaedu.repository.CategoryRepository;
import com.icbt.pahanaedu.repository.PromotionRepository;
import com.icbt.pahanaedu.service.BookService;
import com.icbt.pahanaedu.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private static final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private AwardMapper awardMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AwardRepository awardRepository;

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private PromotionMapper promotionMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public BookManageDto addBook(BookManageDto bookManageDto) {
        log.info(LogSupport.BOOK_LOG + "starting.", "addBook()", bookManageDto.getUserId());
        if (bookManageDto.getBookDetail() == null) {
            log.error(LogSupport.BOOK_LOG + "book data is required.", "addBook()", bookManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "book data is required");
        }
        BookDetailsDto bookDetailsDto = bookManageDto.getBookDetail();

        if (bookDetailsDto.getName() == null || bookDetailsDto.getName().isEmpty()) {
            log.error(LogSupport.BOOK_LOG + "name is required.", "addBook()", bookManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "name is required");
        }

        if (bookDetailsDto.getCategoryId() == null) {
            log.error(LogSupport.BOOK_LOG + "categoryId is required.", "addBook()", bookManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "categoryId is required");
        }

        Optional<Category> categoryOptional = categoryRepository.findById(bookDetailsDto.getCategoryId());
        if (categoryOptional.isEmpty()) {
            log.error(LogSupport.BOOK_LOG + "invalid categoryId.", "addBook()", bookManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.INVALID_CATEGORY_ID_CODE, "Invalid Category Id");
        }

        Category category = categoryOptional.get();

        if (bookDetailsDto.getLanguage() == null || bookDetailsDto.getLanguage().isEmpty()) {
            log.error(LogSupport.BOOK_LOG + "language is required.", "addBook()", bookManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "language is required");
        }

        if (bookDetailsDto.getAuthor() == null || bookDetailsDto.getAuthor().isEmpty()) {
            log.error(LogSupport.BOOK_LOG + "author is required.", "addBook()", bookManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "author is required");
        }

        if (bookDetailsDto.getPublisher() == null || bookDetailsDto.getPublisher().isEmpty()) {
            log.error(LogSupport.BOOK_LOG + "publisher is required.", "addBook()", bookManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "publisher is required");
        }

        if (bookDetailsDto.getPublishDate() == null) {
            log.error(LogSupport.BOOK_LOG + "publishDate is required.", "addBook()", bookManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "publishDate is required");
        }

        if (bookDetailsDto.getIsbn() == null || bookDetailsDto.getIsbn().isEmpty()) {
            log.error(LogSupport.BOOK_LOG + "isbn is required.", "addBook()", bookManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "isbn is required");
        }

        if (bookDetailsDto.getPrice() == null) {
            log.error(LogSupport.BOOK_LOG + "price is required.", "addBook()", bookManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "price is required");
        }

        if (bookDetailsDto.getQuantity() == null) {
            log.error(LogSupport.BOOK_LOG + "quantity is required.", "addBook()", bookManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "quantity is required");
        }

        Book book = bookMapper.toEntity(bookDetailsDto);
        book.setCategory(category);
        book.setBookStatus(Constants.ACTIVE_STATUS);
        book.setCreatedDatetime(Utils.getCurrentDateByTimeZone(Constants.TIME_ZONE));

        bookRepository.save(book);

        if (!bookDetailsDto.getAwardList().isEmpty()) {
            log.info(LogSupport.BOOK_LOG + "award adding starting.", "addBook()", bookManageDto.getUserId());
            bookDetailsDto.getAwardList().forEach(awardDto -> {
                Award award = awardMapper.toEntity(awardDto);
                award.setBook(book);
                award.setCreatedBy(bookManageDto.getUserId());
                award.setCreatedDatetime(Utils.getCurrentDateByTimeZone(Constants.TIME_ZONE));

                awardRepository.save(award);
            });
            log.info(LogSupport.BOOK_LOG + "award added successfully.", "addBook()", bookManageDto.getUserId());
        }

        bookDetailsDto.setBookId(book.getBookId());
        bookManageDto.setBookDetail(bookDetailsDto);
        bookManageDto.setStatus(ResponseStatus.SUCCESS.getStatus());
        bookManageDto.setResponseCode(ResponseCodes.SUCCESS_CODE);
        bookManageDto.setResponseMessage("Book saving successfully");

        log.info(LogSupport.BOOK_LOG + "end.", "addBook()", bookManageDto.getUserId());
        return bookManageDto;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public BookManageDto updateBook(BookManageDto bookManageDto) {
        log.info(LogSupport.BOOK_LOG + "starting.", "updateBook()", bookManageDto.getUserId());
        if (bookManageDto.getBookDetail() == null) {
            log.error(LogSupport.BOOK_LOG + "book data is required.", "addBook()", bookManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "book data is required");
        }
        BookDetailsDto bookDetailsDto = bookManageDto.getBookDetail();

        if (bookDetailsDto.getBookId() == null) {
            log.error(LogSupport.BOOK_LOG + "bookId is required.", "updateBook()", bookManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "bookId is required");
        }

        Optional<Book> optionalBook = bookRepository.findById(bookDetailsDto.getBookId());
        if (optionalBook.isEmpty()) {
            log.error(LogSupport.BOOK_LOG + "invalid bookId.", "updateBook()", bookManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.INVALID_BOOK_ID_CODE, "Invalid Book Id");
        }

        if (bookDetailsDto.getName() == null || bookDetailsDto.getName().isEmpty()) {
            log.error(LogSupport.BOOK_LOG + "name is required.", "updateBook()", bookManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "name is required");
        }

        if (bookDetailsDto.getCategoryId() == null) {
            log.error(LogSupport.BOOK_LOG + "categoryId is required.", "updateBook()", bookManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "categoryId is required");
        }

        Optional<Category> categoryOptional = categoryRepository.findById(bookDetailsDto.getCategoryId());
        if (categoryOptional.isEmpty()) {
            log.error(LogSupport.BOOK_LOG + "invalid categoryId.", "updateBook()", bookManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.INVALID_CATEGORY_ID_CODE, "Invalid Category Id");
        }

        Category category = categoryOptional.get();

        if (bookDetailsDto.getLanguage() == null || bookDetailsDto.getLanguage().isEmpty()) {
            log.error(LogSupport.BOOK_LOG + "language is required.", "updateBook()", bookManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "language is required");
        }

        if (bookDetailsDto.getAuthor() == null || bookDetailsDto.getAuthor().isEmpty()) {
            log.error(LogSupport.BOOK_LOG + "author is required.", "updateBook()", bookManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "author is required");
        }

        if (bookDetailsDto.getPublisher() == null || bookDetailsDto.getPublisher().isEmpty()) {
            log.error(LogSupport.BOOK_LOG + "publisher is required.", "updateBook()", bookManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "publisher is required");
        }

        if (bookDetailsDto.getPublishDate() == null) {
            log.error(LogSupport.BOOK_LOG + "publishDate is required.", "updateBook()", bookManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "publishDate is required");
        }

        if (bookDetailsDto.getIsbn() == null || bookDetailsDto.getIsbn().isEmpty()) {
            log.error(LogSupport.BOOK_LOG + "isbn is required.", "updateBook()", bookManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "isbn is required");
        }

        if (bookDetailsDto.getPrice() == null) {
            log.error(LogSupport.BOOK_LOG + "price is required.", "updateBook()", bookManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "price is required");
        }

        if (bookDetailsDto.getQuantity() == null) {
            log.error(LogSupport.BOOK_LOG + "quantity is required.", "updateBook()", bookManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "quantity is required");
        }
        Book book = optionalBook.get();
        if (!bookDetailsDto.getAwardList().isEmpty()) {
            List<Award> awardList = awardRepository.findByBook(book);
            awardRepository.deleteAll(awardList);
            List<AwardDto> awardDtos = bookDetailsDto.getAwardList();
            awardDtos.forEach(awardDto -> {
                Award award = awardMapper.toEntity(awardDto);
                award.setAwardId(null);
                award.setBook(book);
                award.setCreatedBy(bookManageDto.getUserId());
                award.setCreatedDatetime(Utils.getCurrentDateByTimeZone(Constants.TIME_ZONE));

                awardRepository.save(award);
            });
        }

        mappingUpdateBook(book, bookDetailsDto);
        book.setCategory(category);
        book.setModifiedDatetime(Utils.getCurrentDateByTimeZone(Constants.TIME_ZONE));

        bookRepository.save(book);

        bookDetailsDto.setBookId(book.getBookId());
        bookManageDto.setBookDetail(bookDetailsDto);
        bookManageDto.setStatus(ResponseStatus.SUCCESS.getStatus());
        bookManageDto.setResponseCode(ResponseCodes.SUCCESS_CODE);
        bookManageDto.setResponseMessage("Book updating successfully");

        log.info(LogSupport.BOOK_LOG + "end.", "updateBook()", bookManageDto.getUserId());
        return bookManageDto;
    }

    private void mappingUpdateBook(Book book, BookDetailsDto bookDetailsDto) {
        book.setName(bookDetailsDto.getName());
        book.setDescription(bookDetailsDto.getDescription());
        book.setAuthor(bookDetailsDto.getAuthor());
        book.setLanguage(bookDetailsDto.getLanguage());
        book.setPublisher(bookDetailsDto.getPublisher());
        book.setPublishDate(bookDetailsDto.getPublishDate());
        book.setIsbn(bookDetailsDto.getIsbn());
        book.setPrice(bookDetailsDto.getPrice());
        book.setQuantity(bookDetailsDto.getQuantity());
        book.setImageUrl(bookDetailsDto.getImageUrl());
    }

    @Override
    public BookManageDto getBookById(BookManageDto bookManageDto) {
        log.info(LogSupport.BOOK_LOG + "starting.", "getBookById()", bookManageDto.getUserId());
        if (bookManageDto.getBookDetail() == null) {
            log.error(LogSupport.BOOK_LOG + "book data is required.", "getBookById()", bookManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "book data is required");
        }
        BookDetailsDto bookDetailsDto = bookManageDto.getBookDetail();

        if (bookDetailsDto.getBookId() == null) {
            log.error(LogSupport.BOOK_LOG + "bookId is required.", "getBookById()", bookManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "bookId is required");
        }

        Optional<Book> optionalBook = bookRepository.findById(bookDetailsDto.getBookId());
        if (optionalBook.isEmpty()) {
            log.error(LogSupport.BOOK_LOG + "invalid bookId.", "getBookById()", bookManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.INVALID_BOOK_ID_CODE, "Invalid Book Id");
        }

        Book book = optionalBook.get();
        BookDetailsDto bookMapperDto = bookMapper.toDto(book);
        bookMapperDto.setCategoryId(book.getCategory().getCategoryId());
        bookMapperDto.setCategoryName(book.getCategory().getCategoryName());

        List<Award> awardList = awardRepository.findByBook(book);
        if (!awardList.isEmpty()) {
            bookMapperDto.setAwardList(awardMapper.toDtoList(awardList));
        }

        // TODO: 7/11/2025 set promotion
        setPromotion(bookMapperDto.getBookId(), book.getPrice(), bookMapperDto);

        bookManageDto.setBookDetail(bookMapperDto);
        bookManageDto.setStatus(ResponseStatus.SUCCESS.getStatus());
        bookManageDto.setResponseCode(ResponseCodes.SUCCESS_CODE);
        bookManageDto.setResponseMessage("Book finding successfully");

        log.info(LogSupport.BOOK_LOG + "end.", "getBookById()", bookManageDto.getUserId());
        return bookManageDto;
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

    @Override
    public BookManageDto getBooks(BookManageDto bookManageDto) {
        log.info(LogSupport.BOOK_LOG + "starting.", "getBooks()", bookManageDto.getUserId());
        List<BookDetailsDto> bookDetailsDtoList = new ArrayList<>();

        List<Book> bookList = bookRepository.findAll();
        if (bookList == null || bookList.isEmpty()) {
            bookManageDto.setStatus(ResponseStatus.SUCCESS.getStatus());
            bookManageDto.setResponseCode(ResponseCodes.SUCCESS_CODE);
            bookManageDto.setResponseMessage("Book getting successfully");
            bookManageDto.setBookDetailsList(bookDetailsDtoList);
            return bookManageDto;
        }

        bookList.forEach(book -> {
            if (book.getBookStatus().equalsIgnoreCase(Constants.ACTIVE_STATUS)) {
                BookDetailsDto mapperDto = bookMapper.toDto(book);
                mapperDto.setCategoryName(book.getCategory().getCategoryName());
                List<Award> awardList = awardRepository.findByBook(book);
                if (!awardList.isEmpty()) {
                    mapperDto.setAwardList(awardMapper.toDtoList(awardList));
                }

                // TODO: 7/11/2025 set promotion
                setPromotion(mapperDto.getBookId(), book.getPrice(), mapperDto);
                bookDetailsDtoList.add(mapperDto);
            }
        });

        bookManageDto.setStatus(ResponseStatus.SUCCESS.getStatus());
        bookManageDto.setResponseCode(ResponseCodes.SUCCESS_CODE);
        bookManageDto.setResponseMessage("Book getting successfully");
        bookManageDto.setBookDetailsList(bookDetailsDtoList);
        log.info(LogSupport.BOOK_LOG + "end.", "getBooks()", bookManageDto.getUserId());

        return bookManageDto;
    }

    @Override
    public BookManageDto searchBooks(BookManageDto bookManageDto) {
        return null;
    }

    @Override
    public BookManageDto deleteBooks(BookManageDto bookManageDto) {
        log.info(LogSupport.BOOK_LOG + "starting.", "deleteBooks()", bookManageDto.getUserId());
        if (bookManageDto.getBookDetail() == null) {
            log.error(LogSupport.BOOK_LOG + "book data is required.", "deleteBooks()", bookManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "book data is required");
        }
        BookDetailsDto bookDetailsDto = bookManageDto.getBookDetail();

        if (bookDetailsDto.getBookId() == null) {
            log.error(LogSupport.BOOK_LOG + "bookId is required.", "deleteBooks()", bookManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "bookId is required");
        }

        Optional<Book> optionalBook = bookRepository.findById(bookDetailsDto.getBookId());
        if (optionalBook.isEmpty()) {
            log.error(LogSupport.BOOK_LOG + "invalid bookId.", "deleteBooks()", bookManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.INVALID_BOOK_ID_CODE, "Invalid Book Id");
        }

        Book book = optionalBook.get();
        book.setBookStatus(Constants.DELETE_STATUS);
        bookRepository.save(book);

        bookManageDto.setStatus(ResponseStatus.SUCCESS.getStatus());
        bookManageDto.setResponseCode(ResponseCodes.SUCCESS_CODE);
        bookManageDto.setResponseMessage("Book delete successfully");

        log.info(LogSupport.BOOK_LOG + "end.", "deleteBooks()", bookManageDto.getUserId());
        return bookManageDto;
    }
}
