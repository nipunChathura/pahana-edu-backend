package com.icbt.pahanaedu.service.impl;

import com.icbt.pahanaedu.common.Constants;
import com.icbt.pahanaedu.common.LogSupport;
import com.icbt.pahanaedu.common.ResponseCodes;
import com.icbt.pahanaedu.common.ResponseStatus;
import com.icbt.pahanaedu.dto.CustomerDto;
import com.icbt.pahanaedu.dto.CustomerMangeDto;
import com.icbt.pahanaedu.entity.Customer;
import com.icbt.pahanaedu.entity.MembershipType;
import com.icbt.pahanaedu.exception.InvalidRequestException;
import com.icbt.pahanaedu.util.mapper.CustomerMapper;
import com.icbt.pahanaedu.repository.CustomerRepository;
import com.icbt.pahanaedu.service.CustomerService;
import com.icbt.pahanaedu.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Value("${phone.number.validation.regex}")
    private String PHONE_NUMBER_VALIDATION_REGEX;

    @Value("${email.validation.regex}")
    private String EMAIL_VALIDATION_REGEX;

    private static final String PREFIX = "CUST-";

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public CustomerMangeDto addCustomer(CustomerMangeDto customerMangeDto) {
        log.info(LogSupport.CUSTOMER_LOG + "stating.", "addCustomer()", customerMangeDto.getUserId());

        if (customerMangeDto.getCustomerDto() == null) {
            log.error(LogSupport.CUSTOMER_LOG + "customer data is required.", "addCustomer()", customerMangeDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "customer data is required");
        }

        CustomerDto customerDto = customerMangeDto.getCustomerDto();

        if (customerDto.getCustomerName() == null || customerDto.getCustomerName().isEmpty()) {
            log.error(LogSupport.CUSTOMER_LOG + "customerName is required.", "addCustomer()", customerMangeDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "customerName is required");
        }

        if (customerDto.getPhoneNumber() == null || customerDto.getPhoneNumber().isEmpty()) {
            log.error(LogSupport.CUSTOMER_LOG + "phoneNumber is required.", "addCustomer()", customerMangeDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "phoneNumber is required");
        }

        if (customerDto.getEmail() != null) {
            validateEmail(customerDto.getEmail(), customerMangeDto.getUserId());
        }

        validatePhoneNumber(customerDto.getPhoneNumber(), customerMangeDto.getUserId());
        customerDto.setPhoneNumber(Utils.convertMobileNumber(customerDto.getPhoneNumber()));

        Customer customer = customerMapper.toEntity(customerDto);

        int countToday = customerRepository.countByCustomerRegNoStartingWith(PREFIX + Utils.getCurrentDate());
        customer.setCustomerRegNo(Utils.generateCustomerRegNumber((long) countToday));
        customer.setStatus(Constants.ACTIVE_STATUS);
        customer.setCreatedBy(customerMangeDto.getUserId());
        customer.setCreatedDatetime(Utils.getCurrentDateByTimeZone(Constants.TIME_ZONE));
        customer.setMembershipType(MembershipType.SILVER);

        customerRepository.save(customer);

        customerMangeDto.setCustomerId(customer.getCustomerId());
        customerMangeDto.setStatus(ResponseStatus.SUCCESS.getStatus());
        customerMangeDto.setResponseCode(ResponseCodes.SUCCESS_CODE);
        customerMangeDto.setResponseMessage("Customer saving successfully");
        log.info(LogSupport.CUSTOMER_LOG + "stating.", "addCustomer()", customerMangeDto.getUserId());
        return customerMangeDto;
    }

    private void validatePhoneNumber(String phoneNumber, Long userId) {
        log.info(LogSupport.CUSTOMER_LOG + "stating.", "validatePhoneNumber()", userId);

        if (phoneNumber.matches(PHONE_NUMBER_VALIDATION_REGEX)) {
            List<Customer> customerList = customerRepository.findAllByPhoneNumber(phoneNumber);
            if (!customerList.isEmpty()) {
                log.error(LogSupport.CUSTOMER_LOG + "phone number already existing.", "validatePhoneNumber()", userId);
                throw new InvalidRequestException(ResponseCodes.PHONE_NUMBER_ALREADY_EXISTING_CODE, "phone number already existing");
            }
        } else {
            log.error(LogSupport.CUSTOMER_LOG + "phone number not validate.", "validatePhoneNumber()", userId);
            throw new InvalidRequestException(ResponseCodes.PHONE_NUMBER_VALIDATION_FAILED_CODE, "phoneNumber validation failed");
        }
        log.info(LogSupport.CUSTOMER_LOG + "end.", "validatePhoneNumber()", userId);
    }

    private void validateEmail(String email, Long userId) {
        log.info(LogSupport.CUSTOMER_LOG + "stating.", "validateEmail()", userId);
        if (email.matches(EMAIL_VALIDATION_REGEX)) {
            List<Customer> customerList = customerRepository.findAllByEmail(email);
            if (!customerList.isEmpty()) {
                log.error(LogSupport.CUSTOMER_LOG + "email already existing.", "validateEmail()", userId);
                throw new InvalidRequestException(ResponseCodes.EMAIL_VALIDATION_FAILED_CODE, "email already existing");
            }
        } else {
            log.error(LogSupport.CUSTOMER_LOG + "email not validate.", "validateEmail()", userId);
            throw new InvalidRequestException(ResponseCodes.EMAIL_VALIDATION_FAILED_CODE, "email validation failed");
        }
        log.info(LogSupport.CUSTOMER_LOG + "end.", "validateEmail()", userId);
    }

    @Override
    public CustomerMangeDto updateCustomer(CustomerMangeDto customerMangeDto) {
        log.info(LogSupport.CUSTOMER_LOG + "stating.", "updateCustomer()", customerMangeDto.getUserId());

        if (customerMangeDto.getCustomerDto() == null) {
            log.error(LogSupport.CUSTOMER_LOG + "customer data is required.", "updateCustomer()", customerMangeDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "customer data is required");
        }

        CustomerDto customerDto = customerMangeDto.getCustomerDto();

        if (customerDto.getCustomerId() == null) {
            log.error(LogSupport.CUSTOMER_LOG + "customerId is required.", "updateCustomer()", customerMangeDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "customerId is required");
        }

        Optional<Customer> byId = customerRepository.findById(customerDto.getCustomerId());
        if (byId.isEmpty()) {
            log.error(LogSupport.CUSTOMER_LOG + "customerId is invalid.", "updateCustomer()", customerMangeDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.INVALID_CUSTOMER_ID_CODE, "customer Id is invalid");
        }

        Customer customer = byId.get();

        if (customerDto.getCustomerName() == null || customerDto.getCustomerName().isEmpty()) {
            log.error(LogSupport.CUSTOMER_LOG + "customerName is required.", "updateCustomer()", customerMangeDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "customerName is required");
        }

        if (customerDto.getEmail() == null || customerDto.getEmail().isEmpty()) {
            log.error(LogSupport.CUSTOMER_LOG + "email is required.", "updateCustomer()", customerMangeDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "email is required");
        }

        if (customerDto.getPhoneNumber() == null || customerDto.getPhoneNumber().isEmpty()) {
            log.error(LogSupport.CUSTOMER_LOG + "phoneNumber is required.", "updateCustomer()", customerMangeDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "phoneNumber is required");
        }

        if (customerDto.getStatus() == null || customerDto.getStatus().isEmpty()) {
            log.error(LogSupport.CUSTOMER_LOG + "status is required.", "updateCustomer()", customerMangeDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "status is required");
        }

        if (!customerDto.getEmail().equalsIgnoreCase(customer.getEmail())) {
            validateEmail(customerDto.getEmail(), customerMangeDto.getUserId());
            customer.setEmail(customerDto.getEmail());
        }

        if (!customerDto.getPhoneNumber().equalsIgnoreCase(customer.getPhoneNumber())) {
            validatePhoneNumber(customerDto.getPhoneNumber(), customerMangeDto.getUserId());
            customer.setPhoneNumber(Utils.convertMobileNumber(customerDto.getPhoneNumber()));
        }

        System.out.println("customerDto.getMembershipType() = " + customerDto.getMembershipType());
        customer.setMembershipType(MembershipType.valueOf(customerDto.getMembershipType()));
        customer.setCustomerName(customerDto.getCustomerName());
        customer.setStatus(customerDto.getStatus());
        customer.setModifiedBy(customerMangeDto.getUserId());
        customer.setModifiedDatetime(Utils.getCurrentDateByTimeZone(Constants.TIME_ZONE));

        customerRepository.save(customer);

        customerMangeDto.setStatus(ResponseStatus.SUCCESS.getStatus());
        customerMangeDto.setResponseCode(ResponseCodes.SUCCESS_CODE);
        customerMangeDto.setResponseMessage("Customer updating successfully");
        log.info(LogSupport.CUSTOMER_LOG + "stating.", "updateCustomer()", customerMangeDto.getUserId());
        return customerMangeDto;
    }

    @Override
    public CustomerMangeDto deleteCustomer(CustomerMangeDto customerMangeDto) {
        log.info(LogSupport.CUSTOMER_LOG + "stating.", "deleteCustomer()", customerMangeDto.getUserId());
        if (customerMangeDto.getCustomerId() == null) {
            log.error(LogSupport.CUSTOMER_LOG + "customerId is required.", "deleteCustomer()", customerMangeDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "customerId is required");
        }

        Optional<Customer> byId = customerRepository.findById(customerMangeDto.getCustomerId());
        if (byId.isEmpty()) {
            log.error(LogSupport.CUSTOMER_LOG + "customerId is invalid.", "deleteCustomer()", customerMangeDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.INVALID_CUSTOMER_ID_CODE, "customer Id is invalid");
        }

        Customer customer = byId.get();

        customer.setStatus(Constants.DELETE_STATUS);
        customer.setModifiedBy(customerMangeDto.getUserId());
        customer.setModifiedDatetime(Utils.getCurrentDateByTimeZone(Constants.TIME_ZONE));

        customerRepository.save(customer);

        customerMangeDto.setStatus(ResponseStatus.SUCCESS.getStatus());
        customerMangeDto.setResponseCode(ResponseCodes.SUCCESS_CODE);
        customerMangeDto.setResponseMessage("Customer deleting successfully");
        log.info(LogSupport.CUSTOMER_LOG + "end.", "getByCustomerId()", customerMangeDto.getUserId());
        return customerMangeDto;
    }

    @Override
    public CustomerMangeDto getByCustomerId(CustomerMangeDto customerMangeDto) {
        log.info(LogSupport.CUSTOMER_LOG + "stating.", "getByCustomerId()", customerMangeDto.getUserId());
        if (customerMangeDto.getCustomerId() == null) {
            log.error(LogSupport.CUSTOMER_LOG + "customerId is required.", "getByCustomerId()", customerMangeDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "customerId is required");
        }

        Optional<Customer> byId = customerRepository.findById(customerMangeDto.getCustomerId());
        if (byId.isEmpty()) {
            log.error(LogSupport.CUSTOMER_LOG + "customerId is invalid.", "getByCustomerId()", customerMangeDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.INVALID_CUSTOMER_ID_CODE, "customer Id is invalid");
        }

        CustomerDto customerDto = customerMapper.toDto(byId.get());

        customerMangeDto.setCustomerDto(customerDto);
        customerMangeDto.setStatus(ResponseStatus.SUCCESS.getStatus());
        customerMangeDto.setResponseCode(ResponseCodes.SUCCESS_CODE);
        customerMangeDto.setResponseMessage("Customer getting successfully");
        log.info(LogSupport.CUSTOMER_LOG + "end.", "getByCustomerId()", customerMangeDto.getUserId());
        return customerMangeDto;
    }

    @Override
    public CustomerMangeDto getAllCustomer(CustomerMangeDto customerMangeDto) {
        log.info(LogSupport.CUSTOMER_LOG + "stating.", "getAllCustomer()", customerMangeDto.getUserId());

        List<CustomerDto> customerDtoList = new ArrayList<>();

        List<Customer> all = customerRepository.findAll();
        if (all.isEmpty()) {
            log.info(LogSupport.CUSTOMER_LOG + "customers is empty", "getAllCustomer()", customerMangeDto.getUserId());
            customerMangeDto.setCustomerDtoList(customerDtoList);
            customerMangeDto.setStatus(ResponseStatus.SUCCESS.getStatus());
            customerMangeDto.setResponseCode(ResponseCodes.SUCCESS_CODE);
            customerMangeDto.setResponseMessage("Customer getting successfully");
            return customerMangeDto;
        }

        all.forEach(customer -> {
            if (!customer.getStatus().equalsIgnoreCase(Constants.DELETE_STATUS)) {
                CustomerDto customerDto = customerMapper.toDto(customer);
                customerDtoList.add(customerDto);
            }
        });

        customerMangeDto.setCustomerDtoList(customerDtoList);
        customerMangeDto.setStatus(ResponseStatus.SUCCESS.getStatus());
        customerMangeDto.setResponseCode(ResponseCodes.SUCCESS_CODE);
        customerMangeDto.setResponseMessage("Customer getting successfully");
        log.info(LogSupport.CUSTOMER_LOG + "end.", "getAllCustomer()", customerMangeDto.getUserId());
        return customerMangeDto;
    }
}
