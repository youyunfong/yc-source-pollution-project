package com.ycsoft.module.manage.controller.admin.pollution.vo;

import com.ycsoft.module.manage.dal.dataopject.Shortcut;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "面源污染 - 调试设备")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DebugWebSocketReqVO {
    @NotEmpty
    @Schema(description = "工厂设备ID", example = "123")
    private String factoryDeviceId;
    @NotEmpty
    @Schema(description = "内容", example = "")
    private String text;
}