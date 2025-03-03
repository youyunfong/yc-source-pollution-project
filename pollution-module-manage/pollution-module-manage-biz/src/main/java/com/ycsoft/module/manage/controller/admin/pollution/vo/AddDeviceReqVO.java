package com.ycsoft.module.manage.controller.admin.pollution.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ycsoft.module.manage.dal.dataopject.Shortcut;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "项目管理 - 列表查询")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddDeviceReqVO {
    @NotEmpty
    @Schema(description = "设备编号", example = "area456")
    private String deviceId;
    @NotEmpty
    @Schema(description = "田块名称", example = "Project A")
    private String deviceName;
    @NotEmpty
    @Schema(description = "工厂设备ID", example = "123")
    private String factoryDeviceId;

    @Schema(description = "经度", example = "123.456789")
    private String lng;

    @Schema(description = "纬度", example = "45.678901")
    private String lat;

    @Schema(description = "设备类型")
    private String deviceType;


    @Schema(description = "项目id", example = "123123")
    private String projectId;

    @Schema(description = "快捷档位列表", example = "Remark A")
    private List<Shortcut> shortcutList;


}