package com.icbt.pahanaedu.controller;

import com.icbt.pahanaedu.request.GoogleLoginRequest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class GoogleAuthControllerDiffblueTest {
    /**
     * Method under test:
     * {@link GoogleAuthController#googleLogin(GoogleLoginRequest)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGoogleLogin() throws Exception {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   jakarta.servlet.ServletException: Request processing failed: java.lang.IllegalArgumentException
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:590)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)
        //   java.lang.IllegalArgumentException
        //       at com.google.common.base.Preconditions.checkArgument(Preconditions.java:129)
        //       at com.google.api.client.util.Preconditions.checkArgument(Preconditions.java:35)
        //       at com.google.api.client.json.webtoken.JsonWebSignature$Parser.parse(JsonWebSignature.java:544)
        //       at com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.parse(GoogleIdToken.java:53)
        //       at com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier.verify(GoogleIdTokenVerifier.java:187)
        //       at com.icbt.pahanaedu.controller.GoogleAuthController.googleLogin(GoogleAuthController.java:38)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:590)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange
        GoogleAuthController googleAuthController = new GoogleAuthController();

        GoogleLoginRequest request = new GoogleLoginRequest();
        request.setCredential("Credential");
        request.setMobile("Mobile");

        // Act
        googleAuthController.googleLogin(request);
    }
}
