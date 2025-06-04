package com.shop.clothing.common.dto;

import lombok.Builder;
import lombok.Data;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Builder
@Data
public class NotificationDto {
    @Builder.Default
    private String title="";
    @Builder.Default
    private String content="";
    @Builder.Default
    private String type = "info";
    public String toParams() {
        return String.format("ntitle=%s&ncontent=%s&ntype=%s", URLEncoder.encode(title.isBlank()?"":title, StandardCharsets.UTF_8), URLEncoder.encode(content.isBlank()?"":content,StandardCharsets.UTF_8), type);
    }
}
