package com.ycsoft.module.manage.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "面源污染 - 快捷档位")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShortcutControlReqVO {
    @NotNull
    @Schema(description = "档位值", example = "1")
    private int shortcutValue;

    @NotEmpty
    @Schema(description = "快捷档位名称", example = "shortcutName")
    private String shortcutName;

    @NotEmpty
    @Schema(description = "工厂设备id", example = "ZP-YC002001")
    private String factoryDeviceId;

}
