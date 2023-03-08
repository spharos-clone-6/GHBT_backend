package com.ghbt.ghbt_starbucks.swagger;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Swagger에서 인증 유저를 사용하기위한 API - 사용 x")
@RestController
@RequestMapping("/api")
public class HeaderReaderController {

  @GetMapping
  public ResponseEntity authHeaderChecker(HttpServletRequest request) {
    String authorizationHeaderValue = request.getHeader("Authorization");
    HashMap<String, String> response = new HashMap<>() {{
      put("Authorization", authorizationHeaderValue);
    }};
    return ResponseEntity.status(HttpStatus.OK)
        .body(response);
  }
}
