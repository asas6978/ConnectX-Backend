package com.tave.connectX.controller;

import com.tave.connectX.dto.OAuthToken;
import com.tave.connectX.dto.OAuthUserInfo;
import com.tave.connectX.dto.User;
import com.tave.connectX.service.OAuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class LoginController {

    private final OAuthService oAuthService;

    @Operation(summary = "로그인 API", description = "accessToken, refreshToken을 받아 로그인하는 API입니다.")
    @GetMapping("/login")
    public ResponseEntity responseEntity(OAuthToken oAuthToken, HttpServletResponse response) {
        try {
            OAuthUserInfo userInfo = oAuthService.getUserInfo(oAuthToken);

            User user = new User(userInfo.getOAuthId(), userInfo.getNickName(), userInfo.getProfileImage());
            oAuthService.login(user, response);

            return ResponseEntity.ok(userInfo.getProfileImage());
        } catch (Exception e) {
            log.info("", e);
            return ResponseEntity.internalServerError().build();
        }
    }

}
