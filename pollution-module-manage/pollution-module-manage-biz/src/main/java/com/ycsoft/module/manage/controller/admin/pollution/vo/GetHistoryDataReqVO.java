package com.ycsoft.module.manage.controller.admin.pollution.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "面源污染 - 历史数据")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetHistoryDataReqVO {
    @NotEmpty
    @Schema(description = "工厂设备编号", example = "area456")
    private String factoryDeviceId;

    @NotEmpty
    @Schema(description = "开始时间", example = "2023-02-18 17:36:00")
    private String startTime;

    @NotEmpty
    @Schema(description = "结束时间", example = "2023-02-18 17:36:00")
    private String endTime;

    @NotNull
    @Schema(description = "查询状态1按天查询0默认查询", example = "0")
    private Integer status;
}
