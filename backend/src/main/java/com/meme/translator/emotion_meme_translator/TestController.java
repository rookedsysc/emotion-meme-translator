package com.meme.translator.emotion_meme_translator;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("test")
@RequiredArgsConstructor
public class TestController {
  @Value("${hello}")
  private String helloString;

  @GetMapping()
  public String getMethodName() {
      return this.helloString;
  }
}
