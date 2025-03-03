package com.ycsoft.module.manage.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "项目管理 - 列表查询")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenerateNumbersRespVO {
    @Schema(description = "项目ID", example = "proj123")
    private String deviceId;

    @Schema(description = "设备名称", example = "proj123")
    private String deviceName;

    @Schema(description = "插入时间", example = "proj123")
    private String insertTime;
}