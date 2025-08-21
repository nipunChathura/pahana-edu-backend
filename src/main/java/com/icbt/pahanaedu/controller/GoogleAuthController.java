package com.icbt.pahanaedu.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.icbt.pahanaedu.common.ResponseCodes;
import com.icbt.pahanaedu.common.ResponseStatus;
import com.icbt.pahanaedu.entity.Customer;
import com.icbt.pahanaedu.request.GoogleLoginRequest;
import com.icbt.pahanaedu.response.GoogleLoginResponse;
import com.icbt.pahanaedu.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/auth")
public class GoogleAuthController {

    @Value("${app.google.clientId}")
    private String clientId;

    @Autowired
    private CustomerService customerService;

    @PostMapping("/google")
    public GoogleLoginResponse googleLogin(@RequestBody GoogleLoginRequest request) throws Exception {
        String credential = request.getCredential();
        var verifier = new GoogleIdTokenVerifier.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance()
        ).setAudience(Collections.singletonList(clientId)).build();

        GoogleIdToken idToken = verifier.verify(credential);
        GoogleLoginResponse response = new GoogleLoginResponse();
        if (idToken == null) {
            response.setStatus(ResponseStatus.FAILURE.getStatus());
            response.setResponseCode(ResponseCodes.FAILED_CODE);
            response.setResponseMessage("Invalid Google token");
        }  else {
            var payload = idToken.getPayload();
            Customer upsert = customerService.upsert(payload.getEmail(), request.getMobile(), (String) payload.get("name"), (String) payload.get("picture"));
            response.setCustomerDto(upsert);
            response.setStatus(ResponseStatus.SUCCESS.getStatus());
            response.setResponseCode(ResponseCodes.SUCCESS_CODE);
            response.setResponseMessage("Google Login Successful");
        }
        return response;
    }
}
