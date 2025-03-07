package com.ycsoft.module.manage.controller.admin.pollution.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "面源污染 - 调试设备返回数据")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DebugWebSocketRespVO {
    @NotEmpty
    @Schema(description = "主题", example = "123")
    private String topic;
    @NotEmpty
    @Schema(description = "内容", example = "")
    private String text;
}