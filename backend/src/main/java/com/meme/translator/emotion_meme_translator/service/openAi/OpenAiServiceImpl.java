package com.meme.translator.emotion_meme_translator.service.openAi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OpenAiServiceImpl implements OpenAiService{

    private final RestTemplate restTemplate;
    private final String apiKey;

    // gpt를 이용한 답변(유료 버전)
    public OpenAiServiceImpl(RestTemplate restTemplate, @Value("${openai.api-key}") String apiKey) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
    }

    @Override
    public String getResponseFromGPT(String prompt) {
        String url = "https://api.openai.com/v1/chat/completions";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        // GPT API에 보낼 요청 본문 생성
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("messages", new Object[]{
                Map.of("role", "user", "content", prompt)
        });

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        for (int i = 0; i < 3; i++) { // 최대 3번까지 재시도
            try {
                // OpenAI API 호출
                Map<String, Object> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class).getBody();
                if (response != null && response.containsKey("choices")) {
                    // 응답에서 첫 번째 선택지 가져오기
                    Map<String, Object> choice = ((List<Map<String, Object>>) response.get("choices")).get(0);
                    Map<String, Object> message = (Map<String, Object>) choice.get("message");

                    // 수정된 내용만 추출하여 반환
                    return (String) message.get("content");
                }
            } catch (HttpClientErrorException.TooManyRequests e) {
                System.out.println("API 할당량 초과: 잠시 대기 후 재시도합니다.");
                try {
                    Thread.sleep(10000); // 10초 대기
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "Error processing the request: " + e.getMessage();
            }
        }
        return "API 요청 실패: 할당량 초과 또는 기타 오류 발생";
    }
}
