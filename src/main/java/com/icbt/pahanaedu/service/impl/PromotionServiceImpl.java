package com.icbt.pahanaedu.service.impl;

import com.icbt.pahanaedu.common.Constants;
import com.icbt.pahanaedu.common.LogSupport;
import com.icbt.pahanaedu.common.ResponseCodes;
import com.icbt.pahanaedu.common.ResponseStatus;
import com.icbt.pahanaedu.dto.BookDetailsDto;
import com.icbt.pahanaedu.dto.PromotionDto;
import com.icbt.pahanaedu.dto.PromotionManageDto;
import com.icbt.pahanaedu.entity.Book;
import com.icbt.pahanaedu.entity.Promotion;
import com.icbt.pahanaedu.entity.PromotionBook;
import com.icbt.pahanaedu.exception.InvalidRequestException;
import com.icbt.pahanaedu.util.mapper.BookMapper;
import com.icbt.pahanaedu.util.mapper.PromotionMapper;
import com.icbt.pahanaedu.repository.BookRepository;
import com.icbt.pahanaedu.repository.PromotionBookRepository;
import com.icbt.pahanaedu.repository.PromotionRepository;
import com.icbt.pahanaedu.service.PromotionService;
import com.icbt.pahanaedu.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PromotionServiceImpl implements PromotionService {

    private static final Logger log = LoggerFactory.getLogger(PromotionServiceImpl.class);

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PromotionMapper promotionMapper;

    @Autowired
    private PromotionBookRepository promotionBookRepository;

    @Autowired
    private BookMapper bookMapper;

    @Override
    @Transactional
    public PromotionManageDto addPromotion(PromotionManageDto promotionManageDto) {
        log.info(LogSupport.PROMOTION_LOG + "starting.", "addPromotion()", promotionManageDto.getUserId());

        if (promotionManageDto.getPromotionDto() == null) {
            log.error(LogSupport.PROMOTION_LOG + "promotion data is required.", "addPromotion()", promotionManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "promotion data is required");
        }
        PromotionDto promotionDto = promotionManageDto.getPromotionDto();

        if (promotionDto.getPromotionName() == null || promotionDto.getPromotionName().isEmpty()) {
            log.error(LogSupport.PROMOTION_LOG + "promotionName is required.", "addPromotion()", promotionManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "promotionName is required");
        }

        if (promotionDto.getPromotionStartDate() == null) {
            log.error(LogSupport.PROMOTION_LOG + "promotionStartDate is required.", "addPromotion()", promotionManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "promotionStartDate is required");
        }

        if (promotionDto.getPromotionEndDate() == null) {
            log.error(LogSupport.PROMOTION_LOG + "promotionEndDate is required.", "addPromotion()", promotionManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "promotionEndDate is required");
        }

        if (promotionDto.getPromotionType() == null || promotionDto.getPromotionType().isEmpty()) {
            log.error(LogSupport.PROMOTION_LOG + "promotionType is required.", "addPromotion()", promotionManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "promotionType is required");
        }

        if (promotionDto.getPromotionPrice() == null) {
            log.error(LogSupport.PROMOTION_LOG + "promotionPrice is required.", "addPromotion()", promotionManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "promotionPrice is required");
        }

        if (promotionDto.getPriority() == null) {
            log.info(LogSupport.PROMOTION_LOG + "set default promotion priority", "addPromotion()", promotionManageDto.getUserId());
            promotionDto.setPriority(3);
        }

        if (promotionDto.getBookIds() == null || promotionDto.getBookIds().isEmpty()) {
            log.error(LogSupport.PROMOTION_LOG + "bookIds is required.", "addPromotion()", promotionManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "bookIds is required");
        }

        List<Book> books = validateBookIds(promotionDto.getBookIds(), promotionManageDto.getUserId());
        if (books.isEmpty()) {
            log.error(LogSupport.PROMOTION_LOG + "bookIds is validation failed.", "addPromotion()", promotionManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "bookIds is validation failed.");
        }
        System.out.println("books.size() = " + books.size());
        books.forEach(book -> {
            System.out.println("book = " + book);
        });

        System.out.println("promotionDto.getPromotionStartDate = " + promotionDto.getPromotionStartDate());
        System.out.println("promotionDto.getPromotionEndDate = " + promotionDto.getPromotionEndDate());

        Promotion promotion = promotionMapper.toEntity(promotionDto);
        promotion.setPromotionStartDate(Utils.convetPromotionDate(promotionDto.getPromotionStartDate()));
        promotion.setPromotionEndDate(Utils.convetPromotionDate(promotionDto.getPromotionEndDate()));
        promotion.setCreatedBy(promotionManageDto.getUserId());
        promotion.setPromotionStatus(Constants.ACTIVE_STATUS);
        promotion.setCreatedDatetime(Utils.getCurrentDateByTimeZone(Constants.TIME_ZONE));

        promotionRepository.save(promotion);

        books.forEach(book -> {
            PromotionBook promotionBook = new PromotionBook();
            promotionBook.setPromotion(promotion);
            promotionBook.setBook(book);
            promotionBook.setStatus(Constants.ACTIVE_STATUS);

            promotionBookRepository.save(promotionBook);
        });

        promotionManageDto.setStatus(ResponseStatus.SUCCESS.getStatus());
        promotionManageDto.setResponseCode(ResponseCodes.SUCCESS_CODE);
        promotionManageDto.setResponseMessage("Promotion saving successfully");
        log.info(LogSupport.PROMOTION_LOG + "end.", "addPromotion()", promotionManageDto.getUserId());
        return promotionManageDto;
    }

    private List<Book> validateBookIds(List<Long> ids, Long userId) {
        log.info(LogSupport.PROMOTION_LOG + "starting.", "validateBookIds()", userId);
        List<Book> books = new ArrayList<>();

        ids.forEach(aLong -> {
            log.info(LogSupport.PROMOTION_LOG + "get book by id = {}.", "validateBookIds()", userId, aLong);
            Optional<Book> byId = bookRepository.findById(aLong);
            if (byId.isPresent()) {
                log.info(LogSupport.PROMOTION_LOG + "validate success book by id = {}.", "validateBookIds()", userId, aLong);
                books.add(byId.get());
            } else {
                log.error(LogSupport.PROMOTION_LOG + "validate failed book by id = {}.", "validateBookIds()", userId, aLong);
            }
        });
        log.info(LogSupport.PROMOTION_LOG + "end.", "validateBookIds()", userId);
        return books;
    }

    @Override
    public PromotionManageDto updatePromotion(PromotionManageDto promotionManageDto) {
        log.info(LogSupport.PROMOTION_LOG + "starting.", "updatePromotion()", promotionManageDto.getUserId());

        if (promotionManageDto.getPromotionDto() == null) {
            log.error(LogSupport.PROMOTION_LOG + "promotion data is required.", "updatePromotion()", promotionManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "promotion data is required");
        }
        PromotionDto promotionDto = promotionManageDto.getPromotionDto();

        if (promotionDto.getPromotionId() == null) {
            log.error(LogSupport.PROMOTION_LOG + "promotionId is required.", "updatePromotion()", promotionManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "promotionId is required");
        }

        Optional<Promotion> byId = promotionRepository.findById(promotionDto.getPromotionId());
        if (byId.isEmpty()) {
            log.error(LogSupport.PROMOTION_LOG + "invalid promotion id.", "updatePromotion()", promotionManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.INVALID_PROMOTION_ID_CODE, "invalid promotion id");
        }

        Promotion promotion = byId.get();

        if (promotionDto.getPromotionName() == null || promotionDto.getPromotionName().isEmpty()) {
            log.error(LogSupport.PROMOTION_LOG + "promotionName is required.", "updatePromotion()", promotionManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "promotionName is required");
        }

        if (promotionDto.getPromotionStartDate() == null) {
            log.error(LogSupport.PROMOTION_LOG + "promotionStartDate is required.", "updatePromotion()", promotionManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "promotionStartDate is required");
        }

        if (promotionDto.getPromotionEndDate() == null) {
            log.error(LogSupport.PROMOTION_LOG + "promotionEndDate is required.", "updatePromotion()", promotionManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "promotionEndDate is required");
        }

        if (promotionDto.getPromotionStatus() == null) {
            log.error(LogSupport.PROMOTION_LOG + "promotionStatus is required.", "updatePromotion()", promotionManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "promotionStatus is required");
        }

        List<Long> bookIds = promotionDto.getBookIds();
        System.out.println("bookIds.size() = " + bookIds.size());
        List<PromotionBook> promotionBooks = promotionBookRepository.findAllByPromotion(promotion);
        promotionBookRepository.deleteAll(promotionBooks);

        if (!bookIds.isEmpty()) {
            List<Book> books = validateBookIds(promotionDto.getBookIds(), promotionManageDto.getUserId());
            books.forEach(book -> {
                PromotionBook promotionBook = new PromotionBook();
                promotionBook.setPromotion(promotion);
                promotionBook.setBook(book);
                promotionBook.setStatus(Constants.ACTIVE_STATUS);

                promotionBookRepository.save(promotionBook);
            });
        }

        promotion.setPromotionName(promotionDto.getPromotionName());
        promotion.setPromotionStartDate(Utils.convetPromotionDate(promotionDto.getPromotionStartDate()));
        promotion.setPromotionEndDate(Utils.convetPromotionDate(promotionDto.getPromotionEndDate()));
        promotion.setPromotionStatus(promotionDto.getPromotionStatus());
        promotion.setModifiedBy(promotionManageDto.getUserId());
        promotion.setModifiedDatetime(Utils.getCurrentDateByTimeZone(Constants.TIME_ZONE));

        promotionRepository.save(promotion);

        promotionManageDto.setStatus(ResponseStatus.SUCCESS.getStatus());
        promotionManageDto.setResponseCode(ResponseCodes.SUCCESS_CODE);
        promotionManageDto.setResponseMessage("Promotion updating successfully");
        log.info(LogSupport.PROMOTION_LOG + "end.", "updatePromotion()", promotionManageDto.getUserId());
        return promotionManageDto;

    }

    @Override
    public PromotionManageDto getByPromotionId(PromotionManageDto promotionManageDto) {
        log.info(LogSupport.PROMOTION_LOG + "starting.", "getByPromotionId()", promotionManageDto.getUserId());

        if (promotionManageDto.getPromotionId() == null) {
            log.error(LogSupport.PROMOTION_LOG + "promotionId is required.", "getByPromotionId()", promotionManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "promotionId is required");
        }

        Optional<Promotion> byId = promotionRepository.findById(promotionManageDto.getPromotionId());
        if (byId.isEmpty()) {
            log.error(LogSupport.PROMOTION_LOG + "invalid promotion id.", "getByPromotionId()", promotionManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.INVALID_PROMOTION_ID_CODE, "invalid promotion id");
        }

        PromotionDto promotionMapperDto = promotionMapper.toDto(byId.get());

        if (promotionManageDto.isRequestBookDetails()) {
            List<PromotionBook> promotionBooks = promotionBookRepository.findAllByPromotion(byId.get());
            List<BookDetailsDto> bookDetailsDtoList = new ArrayList<>();
            if (!promotionBooks.isEmpty()) {
                promotionBooks.forEach(promotionBook -> {
                    BookDetailsDto dto = bookMapper.toDto(promotionBook.getBook());
                    bookDetailsDtoList.add(dto);
                });
                promotionMapperDto.setBookDetailsDtoList(bookDetailsDtoList);
            }
        }

        promotionManageDto.setPromotionDto(promotionMapperDto);
        promotionManageDto.setStatus(ResponseStatus.SUCCESS.getStatus());
        promotionManageDto.setResponseCode(ResponseCodes.SUCCESS_CODE);
        promotionManageDto.setResponseMessage("Promotion getting successfully");
        log.info(LogSupport.PROMOTION_LOG + "end.", "getByPromotionId()", promotionManageDto.getUserId());

        return promotionManageDto;
    }

    @Override
    public PromotionManageDto deletePromotion(PromotionManageDto promotionManageDto) {
        log.info(LogSupport.PROMOTION_LOG + "starting.", "deletePromotion()", promotionManageDto.getUserId());

        if (promotionManageDto.getPromotionId() == null) {
            log.error(LogSupport.PROMOTION_LOG + "promotionId is required.", "deletePromotion()", promotionManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "promotionId is required");
        }

        Optional<Promotion> byId = promotionRepository.findById(promotionManageDto.getPromotionId());
        if (byId.isEmpty()) {
            log.error(LogSupport.PROMOTION_LOG + "invalid promotion id.", "deletePromotion()", promotionManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.INVALID_PROMOTION_ID_CODE, "invalid promotion id");
        }

        Promotion promotion = byId.get();
        promotion.setPromotionStatus(Constants.DELETE_STATUS);
        promotion.setModifiedBy(promotionManageDto.getUserId());
        promotion.setModifiedDatetime(Utils.getCurrentDateByTimeZone(Constants.TIME_ZONE));

        promotionRepository.save(promotion);

        promotionManageDto.setStatus(ResponseStatus.SUCCESS.getStatus());
        promotionManageDto.setResponseCode(ResponseCodes.SUCCESS_CODE);
        promotionManageDto.setResponseMessage("Promotion delete successfully");
        log.info(LogSupport.PROMOTION_LOG + "end.", "getByPromotionId()", promotionManageDto.getUserId());

        return promotionManageDto;
    }

    @Override
    public PromotionManageDto getAllPromotion(PromotionManageDto promotionManageDto) {
        log.info(LogSupport.PROMOTION_LOG + "starting.", "getAllPromotion()", promotionManageDto.getUserId());

        List<PromotionDto> promotionDtoList =  new ArrayList<>();

        List<Promotion> all = promotionRepository.findAll();
        if (all.isEmpty()) {
            promotionManageDto.setPromotionDtoList(promotionDtoList);
            promotionManageDto.setStatus(ResponseStatus.SUCCESS.getStatus());
            promotionManageDto.setResponseCode(ResponseCodes.SUCCESS_CODE);
            promotionManageDto.setResponseMessage("Promotion getting successfully");
            return promotionManageDto;
        }

        all.forEach(promotion -> {
            if (!promotion.getPromotionStatus().equalsIgnoreCase(Constants.DELETE_STATUS)) {
                PromotionDto promotionMapperDto = promotionMapper.toDto(promotion);
                if (promotionManageDto.isRequestBookDetails()) {
                    List<PromotionBook> promotionBooks = promotionBookRepository.findAllByPromotion(promotion);
                    if (!promotionBooks.isEmpty()) {
                        promotionMapperDto.setBookDetailsDtoList(mappingPromotionBooksList(promotionBooks));
                    }
                }
                promotionDtoList.add(promotionMapperDto);
            }
        });

        promotionManageDto.setPromotionDtoList(promotionDtoList);
        promotionManageDto.setStatus(ResponseStatus.SUCCESS.getStatus());
        promotionManageDto.setResponseCode(ResponseCodes.SUCCESS_CODE);
        promotionManageDto.setResponseMessage("Promotion getting successfully");
        log.info(LogSupport.PROMOTION_LOG + "end.", "getAllPromotion()", promotionManageDto.getUserId());
        return promotionManageDto;
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
    public PromotionManageDto addBookByExistingPromotion(PromotionManageDto promotionManageDto) {
        log.info(LogSupport.PROMOTION_LOG + "stating.", "addBookByExistingPromotion()", promotionManageDto.getUserId());

        if (promotionManageDto.getPromotionId() == null) {
            log.error(LogSupport.PROMOTION_LOG + "promotionId is required.", "addBookByExistingPromotion()", promotionManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "promotionId is required");
        }

        Optional<Promotion> byId = promotionRepository.findById(promotionManageDto.getPromotionId());
        if (byId.isEmpty()) {
            log.error(LogSupport.PROMOTION_LOG + "invalid promotion id.", "addBookByExistingPromotion()", promotionManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.INVALID_PROMOTION_ID_CODE, "invalid promotion id");
        }

        Promotion promotion = byId.get();

        if (promotionManageDto.getBookId() == null) {
            log.error(LogSupport.PROMOTION_LOG + "bookId is required.", "addBookByExistingPromotion()", promotionManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "bookId is required");
        }

        Optional<Book> optionalBook = bookRepository.findById(promotionManageDto.getBookId());
        if (optionalBook.isEmpty()) {
            log.error(LogSupport.PROMOTION_LOG + "invalid book id.", "addBookByExistingPromotion()", promotionManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.INVALID_BOOK_ID_CODE, "invalid book id");
        }

        Book book = optionalBook.get();

        PromotionBook promotionBook = new PromotionBook();
        promotionBook.setPromotion(promotion);
        promotionBook.setBook(book);
        promotionBook.setStatus(Constants.ACTIVE_STATUS);

        promotionBookRepository.save(promotionBook);

        promotionManageDto.setStatus(ResponseStatus.SUCCESS.getStatus());
        promotionManageDto.setResponseCode(ResponseCodes.SUCCESS_CODE);
        promotionManageDto.setResponseMessage("Added new Book for Promotion successfully");
        log.info(LogSupport.PROMOTION_LOG + "end.", "addBookByExistingPromotion()", promotionManageDto.getUserId());
        return promotionManageDto;
    }

    @Override
    public PromotionManageDto removeBookByExistingPromotion(PromotionManageDto promotionManageDto) {
        log.info(LogSupport.PROMOTION_LOG + "starting.", "removeBookByExistingPromotion()", promotionManageDto.getUserId());
        if (promotionManageDto.getPromotionId() == null) {
            log.error(LogSupport.PROMOTION_LOG + "promotionId is required.", "removeBookByExistingPromotion()", promotionManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "promotionId is required");
        }

        Optional<Promotion> byId = promotionRepository.findById(promotionManageDto.getPromotionId());
        if (byId.isEmpty()) {
            log.error(LogSupport.PROMOTION_LOG + "invalid promotion id.", "removeBookByExistingPromotion()", promotionManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.INVALID_PROMOTION_ID_CODE, "invalid promotion id");
        }

        Promotion promotion = byId.get();

        if (promotionManageDto.getBookId() == null) {
            log.error(LogSupport.PROMOTION_LOG + "bookId is required.", "removeBookByExistingPromotion()", promotionManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "bookId is required");
        }

        Optional<Book> optionalBook = bookRepository.findById(promotionManageDto.getBookId());
        if (optionalBook.isEmpty()) {
            log.error(LogSupport.PROMOTION_LOG + "invalid book id.", "removeBookByExistingPromotion()", promotionManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.INVALID_BOOK_ID_CODE, "invalid book id");
        }

        Book book = optionalBook.get();

        PromotionBook byBookAndPromotion = promotionBookRepository.findByBookAndPromotion(book, promotion);
        if (byBookAndPromotion == null) {
            log.error(LogSupport.PROMOTION_LOG + "invalid promotion id.", "removeBookByExistingPromotion()", promotionManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.INVALID_PROMOTION_ID_CODE, "invalid promotion id");
        }

        byBookAndPromotion.setStatus(Constants.DELETE_STATUS);
        promotionBookRepository.save(byBookAndPromotion);

        promotionManageDto.setStatus(ResponseStatus.SUCCESS.getStatus());
        promotionManageDto.setResponseCode(ResponseCodes.SUCCESS_CODE);
        promotionManageDto.setResponseMessage("Remove Book for Promotion successfully");
        log.info(LogSupport.PROMOTION_LOG + "end.", "removeBookByExistingPromotion()", promotionManageDto.getUserId());
        return promotionManageDto;
    }
}
