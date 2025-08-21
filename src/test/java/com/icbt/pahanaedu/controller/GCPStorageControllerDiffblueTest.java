package com.icbt.pahanaedu.controller;

import static org.mockito.Mockito.when;

import com.icbt.pahanaedu.dto.GcpDto;
import com.icbt.pahanaedu.service.GcpStorageService;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

@ContextConfiguration(classes = {GCPStorageController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class GCPStorageControllerDiffblueTest {
    @Autowired
    private GCPStorageController gCPStorageController;

    @MockBean
    private GcpStorageService gcpStorageService;

    /**
     * Method under test: {@link GCPStorageController#deleteImage(String)}
     */
    @Test
    void testDeleteImage() throws Exception {
        // Arrange
        GcpDto gcpDto = new GcpDto();
        gcpDto.setDeleted(true);
        gcpDto.setFile(new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));
        gcpDto.setFileName("foo.txt");
        gcpDto.setImageUrl("https://example.org/example");
        gcpDto.setResponseCode("Response Code");
        gcpDto.setResponseMessage("Response Message");
        gcpDto.setStatus("Status");
        gcpDto.setUserId(1L);
        when(gcpStorageService.deleteFile(Mockito.<GcpDto>any())).thenReturn(gcpDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/gcp/delete")
                .param("file", "foo");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(gCPStorageController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"status\":\"Status\",\"responseCode\":\"Response Code\",\"responseMessage\":\"Response Message\",\"imageUrl\":"
                                        + "\"https://example.org/example\",\"deleted\":false}"));
    }

    /**
     * Method under test: {@link GCPStorageController#uploadImage(MultipartFile)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUploadImage() throws IOException {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   jakarta.servlet.ServletException: Request processing failed: org.springframework.web.multipart.MultipartException: Current request is not a multipart request
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:590)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)
        //   org.springframework.web.multipart.MultipartException: Current request is not a multipart request
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:590)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange
        GCPStorageController gcpStorageController = new GCPStorageController();

        // Act
        gcpStorageController
                .uploadImage(new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));
    }
}
