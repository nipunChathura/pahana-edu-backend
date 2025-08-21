package com.icbt.pahanaedu.controller;

import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icbt.pahanaedu.dto.CustomerDto;
import com.icbt.pahanaedu.dto.CustomerMangeDto;
import com.icbt.pahanaedu.request.CustomerRequest;
import com.icbt.pahanaedu.service.CustomerService;
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

@ContextConfiguration(classes = {CustomerController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class CustomerControllerDiffblueTest {
    @Autowired
    private CustomerController customerController;

    @MockBean
    private CustomerService customerService;

    /**
     * Method under test: {@link CustomerController#addCustomer(CustomerRequest)}
     */
    @Test
    void testAddCustomer() throws Exception {
        // Arrange
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomerId(1L);
        customerDto.setCustomerName("Customer Name");
        customerDto.setCustomerRegNo("Customer Reg No");
        customerDto.setEmail("jane.doe@example.org");
        customerDto.setMembershipType("Membership Type");
        customerDto.setPhoneNumber("6625550144");
        customerDto.setStatus("Status");

        CustomerMangeDto customerMangeDto = new CustomerMangeDto();
        customerMangeDto.setCustomerDto(customerDto);
        customerMangeDto.setCustomerId(1L);
        customerMangeDto.setResponseCode("Response Code");
        customerMangeDto.setResponseMessage("Response Message");
        customerMangeDto.setStatus("Status");
        customerMangeDto.setUserId(1L);
        when(customerService.addCustomer(Mockito.<CustomerMangeDto>any())).thenReturn(customerMangeDto);

        CustomerDto customerDto2 = new CustomerDto();
        customerDto2.setCustomerId(1L);
        customerDto2.setCustomerName("Customer Name");
        customerDto2.setCustomerRegNo("Customer Reg No");
        customerDto2.setEmail("jane.doe@example.org");
        customerDto2.setMembershipType("Membership Type");
        customerDto2.setPhoneNumber("6625550144");
        customerDto2.setStatus("Status");

        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setCustomerDto(customerDto2);
        customerRequest.setCustomerId(1L);
        customerRequest.setUserId(1L);
        String content = (new ObjectMapper()).writeValueAsString(customerRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/customers/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"status\":\"Status\",\"responseCode\":\"Response Code\",\"responseMessage\":\"Response Message\"}"));
    }

    /**
     * Method under test: {@link CustomerController#updateCustomer(CustomerRequest)}
     */
    @Test
    void testUpdateCustomer() throws Exception {
        // Arrange
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomerId(1L);
        customerDto.setCustomerName("Customer Name");
        customerDto.setCustomerRegNo("Customer Reg No");
        customerDto.setEmail("jane.doe@example.org");
        customerDto.setMembershipType("Membership Type");
        customerDto.setPhoneNumber("6625550144");
        customerDto.setStatus("Status");

        CustomerMangeDto customerMangeDto = new CustomerMangeDto();
        customerMangeDto.setCustomerDto(customerDto);
        customerMangeDto.setCustomerId(1L);
        customerMangeDto.setResponseCode("Response Code");
        customerMangeDto.setResponseMessage("Response Message");
        customerMangeDto.setStatus("Status");
        customerMangeDto.setUserId(1L);
        when(customerService.updateCustomer(Mockito.<CustomerMangeDto>any())).thenReturn(customerMangeDto);

        CustomerDto customerDto2 = new CustomerDto();
        customerDto2.setCustomerId(1L);
        customerDto2.setCustomerName("Customer Name");
        customerDto2.setCustomerRegNo("Customer Reg No");
        customerDto2.setEmail("jane.doe@example.org");
        customerDto2.setMembershipType("Membership Type");
        customerDto2.setPhoneNumber("6625550144");
        customerDto2.setStatus("Status");

        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setCustomerDto(customerDto2);
        customerRequest.setCustomerId(1L);
        customerRequest.setUserId(1L);
        String content = (new ObjectMapper()).writeValueAsString(customerRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/customers/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"status\":\"Status\",\"responseCode\":\"Response Code\",\"responseMessage\":\"Response Message\"}"));
    }

    /**
     * Method under test: {@link CustomerController#getByCustomerId(Long, Long)}
     */
    @Test
    void testGetByCustomerId2() throws Exception {
        // Arrange
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomerId(1L);
        customerDto.setCustomerName("Customer Name");
        customerDto.setCustomerRegNo("Customer Reg No");
        customerDto.setEmail("jane.doe@example.org");
        customerDto.setMembershipType("Membership Type");
        customerDto.setPhoneNumber("6625550144");
        customerDto.setStatus("Status");

        CustomerMangeDto customerMangeDto = new CustomerMangeDto();
        customerMangeDto.setCustomerDto(customerDto);
        customerMangeDto.setCustomerId(1L);
        customerMangeDto.setResponseCode("Response Code");
        customerMangeDto.setResponseMessage("Response Message");
        customerMangeDto.setStatus("Status");
        customerMangeDto.setUserId(1L);
        when(customerService.getByCustomerId(Mockito.<CustomerMangeDto>any())).thenReturn(customerMangeDto);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/customers/id/{userId}", 1L);
        MockHttpServletRequestBuilder requestBuilder = getResult.param("customerId", String.valueOf(1L));

        // Act and Assert
        MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"status\":\"Status\",\"responseCode\":\"Response Code\",\"responseMessage\":\"Response Message\",\"customerDto\""
                                        + ":{\"customerId\":1,\"customerRegNo\":\"Customer Reg No\",\"customerName\":\"Customer Name\",\"email\":\"jane.doe"
                                        + "@example.org\",\"phoneNumber\":\"6625550144\",\"membershipType\":\"Membership Type\",\"status\":\"Status\"}}"));
    }

    /**
     * Method under test: {@link CustomerController#deleteCustomer(CustomerRequest)}
     */
    @Test
    void testDeleteCustomer() throws Exception {
        // Arrange
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomerId(1L);
        customerDto.setCustomerName("Customer Name");
        customerDto.setCustomerRegNo("Customer Reg No");
        customerDto.setEmail("jane.doe@example.org");
        customerDto.setMembershipType("Membership Type");
        customerDto.setPhoneNumber("6625550144");
        customerDto.setStatus("Status");

        CustomerMangeDto customerMangeDto = new CustomerMangeDto();
        customerMangeDto.setCustomerDto(customerDto);
        customerMangeDto.setCustomerId(1L);
        customerMangeDto.setResponseCode("Response Code");
        customerMangeDto.setResponseMessage("Response Message");
        customerMangeDto.setStatus("Status");
        customerMangeDto.setUserId(1L);
        when(customerService.deleteCustomer(Mockito.<CustomerMangeDto>any())).thenReturn(customerMangeDto);

        CustomerDto customerDto2 = new CustomerDto();
        customerDto2.setCustomerId(1L);
        customerDto2.setCustomerName("Customer Name");
        customerDto2.setCustomerRegNo("Customer Reg No");
        customerDto2.setEmail("jane.doe@example.org");
        customerDto2.setMembershipType("Membership Type");
        customerDto2.setPhoneNumber("6625550144");
        customerDto2.setStatus("Status");

        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setCustomerDto(customerDto2);
        customerRequest.setCustomerId(1L);
        customerRequest.setUserId(1L);
        String content = (new ObjectMapper()).writeValueAsString(customerRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/customers/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"status\":\"Status\",\"responseCode\":\"Response Code\",\"responseMessage\":\"Response Message\"}"));
    }

    /**
     * Method under test: {@link CustomerController#getByCustomerId(Long)}
     */
    @Test
    void testGetByCustomerId() throws Exception {
        // Arrange
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomerId(1L);
        customerDto.setCustomerName("Customer Name");
        customerDto.setCustomerRegNo("Customer Reg No");
        customerDto.setEmail("jane.doe@example.org");
        customerDto.setMembershipType("Membership Type");
        customerDto.setPhoneNumber("6625550144");
        customerDto.setStatus("Status");

        CustomerMangeDto customerMangeDto = new CustomerMangeDto();
        customerMangeDto.setCustomerDto(customerDto);
        customerMangeDto.setCustomerId(1L);
        customerMangeDto.setResponseCode("Response Code");
        customerMangeDto.setResponseMessage("Response Message");
        customerMangeDto.setStatus("Status");
        customerMangeDto.setUserId(1L);
        when(customerService.getAllCustomer(Mockito.<CustomerMangeDto>any())).thenReturn(customerMangeDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/customers/all/{userId}", 1L);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"status\":\"Status\",\"responseCode\":\"Response Code\",\"responseMessage\":\"Response Message\"}"));
    }
}
