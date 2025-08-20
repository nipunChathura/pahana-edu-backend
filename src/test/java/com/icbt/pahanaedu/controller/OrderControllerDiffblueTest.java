package com.icbt.pahanaedu.controller;

import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icbt.pahanaedu.dto.CustomerDto;
import com.icbt.pahanaedu.dto.OrderDto;
import com.icbt.pahanaedu.dto.OrderManageDto;
import com.icbt.pahanaedu.request.OrderRequest;
import com.icbt.pahanaedu.service.OrderService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {OrderController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class OrderControllerDiffblueTest {
    @Autowired
    private OrderController orderController;

    @MockBean
    private OrderService orderService;

    /**
     * Method under test: {@link OrderController#addOrder(OrderRequest)}
     */
    @Test
    void testAddOrder() throws Exception {
        // Arrange
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomerId(1L);
        customerDto.setCustomerName("Customer Name");
        customerDto.setCustomerRegNo("Customer Reg No");
        customerDto.setEmail("jane.doe@example.org");
        customerDto.setMembershipType("Membership Type");
        customerDto.setPhoneNumber("6625550144");
        customerDto.setStatus("Status");

        CustomerDto customer = new CustomerDto();
        customer.setCustomerId(1L);
        customer.setCustomerName("Customer Name");
        customer.setCustomerRegNo("Customer Reg No");
        customer.setEmail("jane.doe@example.org");
        customer.setMembershipType("Membership Type");
        customer.setPhoneNumber("6625550144");
        customer.setStatus("Status");

        OrderDto orderDto = new OrderDto();
        orderDto.setCustomer(customer);
        orderDto.setCustomerId(1L);
        orderDto.setDiscountAmount(new BigDecimal("2.3"));
        orderDto.setOrderDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        orderDto.setOrderDetailDtos(new ArrayList<>());
        orderDto.setOrderId(1L);
        orderDto.setPaidAmount(new BigDecimal("2.3"));
        orderDto.setPaymentType("Payment Type");
        orderDto.setTotalAmount(new BigDecimal("2.3"));

        OrderManageDto orderManageDto = new OrderManageDto();
        orderManageDto.setCustomerDto(customerDto);
        orderManageDto.setCustomerId(1L);
        orderManageDto.setDetailDetailList(new ArrayList<>());
        orderManageDto.setDetailsRequested(true);
        orderManageDto.setOrderDto(orderDto);
        orderManageDto.setOrderId(1L);
        orderManageDto.setOrderList(new ArrayList<>());
        orderManageDto.setResponseCode("Response Code");
        orderManageDto.setResponseMessage("Response Message");
        orderManageDto.setStatus("Status");
        orderManageDto.setUserId(1L);
        when(orderService.addOrder(Mockito.<OrderManageDto>any())).thenReturn(orderManageDto);

        CustomerDto customerDto2 = new CustomerDto();
        customerDto2.setCustomerId(1L);
        customerDto2.setCustomerName("Customer Name");
        customerDto2.setCustomerRegNo("Customer Reg No");
        customerDto2.setEmail("jane.doe@example.org");
        customerDto2.setMembershipType("Membership Type");
        customerDto2.setPhoneNumber("6625550144");
        customerDto2.setStatus("Status");

        CustomerDto customer2 = new CustomerDto();
        customer2.setCustomerId(1L);
        customer2.setCustomerName("Customer Name");
        customer2.setCustomerRegNo("Customer Reg No");
        customer2.setEmail("jane.doe@example.org");
        customer2.setMembershipType("Membership Type");
        customer2.setPhoneNumber("6625550144");
        customer2.setStatus("Status");

        OrderDto orderDto2 = new OrderDto();
        orderDto2.setCustomer(customer2);
        orderDto2.setCustomerId(1L);
        orderDto2.setDiscountAmount(new BigDecimal("2.3"));
        orderDto2.setOrderDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        orderDto2.setOrderDetailDtos(new ArrayList<>());
        orderDto2.setOrderId(1L);
        orderDto2.setPaidAmount(new BigDecimal("2.3"));
        orderDto2.setPaymentType("Payment Type");
        orderDto2.setTotalAmount(new BigDecimal("2.3"));

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setCustomerDto(customerDto2);
        orderRequest.setCustomerId(1L);
        orderRequest.setDetailDetailList(new ArrayList<>());
        orderRequest.setDetailsRequested(true);
        orderRequest.setOrderDto(orderDto2);
        orderRequest.setOrderId(1L);
        orderRequest.setOrderList(new ArrayList<>());
        orderRequest.setUserId(1L);
        String content = (new ObjectMapper()).writeValueAsString(orderRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/orders/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(orderController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"status\":\"Status\",\"responseCode\":\"Response Code\",\"responseMessage\":\"Response Message\",\"orderId\":1}"));
    }

    /**
     * Method under test: {@link OrderController#getAllOrders(Long, boolean)}
     */
    @Test
    void testGetAllOrders() throws Exception {
        // Arrange
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomerId(1L);
        customerDto.setCustomerName("Customer Name");
        customerDto.setCustomerRegNo("Customer Reg No");
        customerDto.setEmail("jane.doe@example.org");
        customerDto.setMembershipType("Membership Type");
        customerDto.setPhoneNumber("6625550144");
        customerDto.setStatus("Status");

        CustomerDto customer = new CustomerDto();
        customer.setCustomerId(1L);
        customer.setCustomerName("Customer Name");
        customer.setCustomerRegNo("Customer Reg No");
        customer.setEmail("jane.doe@example.org");
        customer.setMembershipType("Membership Type");
        customer.setPhoneNumber("6625550144");
        customer.setStatus("Status");

        OrderDto orderDto = new OrderDto();
        orderDto.setCustomer(customer);
        orderDto.setCustomerId(1L);
        orderDto.setDiscountAmount(new BigDecimal("2.3"));
        orderDto.setOrderDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        orderDto.setOrderDetailDtos(new ArrayList<>());
        orderDto.setOrderId(1L);
        orderDto.setPaidAmount(new BigDecimal("2.3"));
        orderDto.setPaymentType("Payment Type");
        orderDto.setTotalAmount(new BigDecimal("2.3"));

        OrderManageDto orderManageDto = new OrderManageDto();
        orderManageDto.setCustomerDto(customerDto);
        orderManageDto.setCustomerId(1L);
        orderManageDto.setDetailDetailList(new ArrayList<>());
        orderManageDto.setDetailsRequested(true);
        orderManageDto.setOrderDto(orderDto);
        orderManageDto.setOrderId(1L);
        orderManageDto.setOrderList(new ArrayList<>());
        orderManageDto.setResponseCode("Response Code");
        orderManageDto.setResponseMessage("Response Message");
        orderManageDto.setStatus("Status");
        orderManageDto.setUserId(1L);
        when(orderService.getAllOrders(Mockito.<OrderManageDto>any())).thenReturn(orderManageDto);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/orders/all");
        MockHttpServletRequestBuilder paramResult = getResult.param("detailsRequested", String.valueOf(true));
        MockHttpServletRequestBuilder requestBuilder = paramResult.param("userId", String.valueOf(1L));

        // Act and Assert
        MockMvcBuilders.standaloneSetup(orderController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"status\":\"Status\",\"responseCode\":\"Response Code\",\"responseMessage\":\"Response Message\",\"orderList"
                                        + "\":[]}"));
    }
}
