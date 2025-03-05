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
public class ProjectListRespVO {
    @Schema(description = "项目ID", example = "proj123")
    private String projectId;

    @Schema(description = "项目名称", example = "Project A")
    private String projectName;

    @Schema(description = "行政区域", example = "area456")
    private String areaName;

    @Schema(description = "行政区域ID", example = "1234567890")
    private String areaId;

    @Schema(description = "项目管理员名称", example = "man789")
    private String projectManName;

    @Schema(description = "项目管理员ID", example = "man789")
    private Integer projectMan;

    @Schema(description = "设备管理员名称", example = "dev012")
    private String deviceManName;

    @Schema(description = "设备管理员ID", example = "dev012")
    private Integer deviceMan;

    @Schema(description = "项目年份", example = "2023")
    private String projectYear;

    @Schema(description = "经度", example = "123.456789")
    private String lng;

    @Schema(description = "纬度", example = "45.678901")
    private String lat;
}