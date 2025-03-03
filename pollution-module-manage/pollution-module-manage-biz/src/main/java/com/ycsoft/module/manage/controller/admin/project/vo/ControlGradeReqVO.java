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

@Schema(description = "面源污染 - 控制加减档")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ControlGradeReqVO {
    @NotNull
    @Min(0)
    @Max(1)
    @Schema(description = "控制状态1加档0减档", example = "1")
    private int status;

    @NotEmpty
    @Schema(description = "厂家id", example = "ZP-YC002001")
    private String factoryDeviceId;

}
