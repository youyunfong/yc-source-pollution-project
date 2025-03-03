package com.ycsoft.module.manage.controller.admin.pollution.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "项目管理 - 列表查询")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PollutionListRespVO {
    @Schema(description = "设备ID", example = "4GMQTT1")
    private String deviceId;

    @Schema(description = "项目ID", example = "MF8PYD9BtYF")
    private String projectId;
    @Schema(description = "项目名称", example = "测试工程")
    private String projectName;

    @Schema(description = "工厂设备ID", example = "123")
    private String factoryDeviceId;

    @Schema(description = "设备名称", example = "测试一号田块")
    private String deviceName;

    @Schema(description = "插入时间", example = "2025-02-18 17:36:00")
    private String insertTime;

    @Schema(description = "设备类型")
    private String deviceType;

    @Schema(description = "视频")
    private String video;

    @Schema(description = "经度")
    private String lng;

    @Schema(description = "纬度")
    private String lat;

    @Schema(description = "水位")
    private BigDecimal water;

    @Schema(description = "水位更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime waterTm;

    @Schema(description = "流量")
    private BigDecimal flow;

    @Schema(description = "流量更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime flowTm;

    @Schema(description = "水阀档位")
    private Integer grade;

    @Schema(description = "水阀档位更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime gradeTm;

    @Schema(description = "1在线0离线")
    private Integer status;
}