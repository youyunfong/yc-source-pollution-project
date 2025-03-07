package com.ycsoft.module.manage.dal.dataopject;

import com.baomidou.mybatisplus.annotation.*;
import com.fhs.core.trans.vo.TransPojo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@TableName("device_info")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class DeviceInfoDO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    @Schema(description = "设备编号", example = "dev123")
    private String deviceId;

    @Schema(description = "项目id", example = "Project A")
    private String projectId;

    @Schema(description = "厂家设备id", example = "factoryDev123")
    private String factoryDeviceId;

    @Schema(description = "设备名称", example = "Device A")
    private String deviceName;

    @Schema(description = "插入时间", example = "2023-01-01 00:00:00")
    private String insertTime;

    @Schema(description = "修改时间", example = "2023-01-01 00:00:00")
    private String updateTime;

    @Schema(description = "设备类型", example = "typeA")
    private String deviceType;

    @Schema(description = "视频监控", example = "http://example.com/video")
    private String video;

    @Schema(description = "图片")
    private String pic;

    @Schema(description = "经度", example = "123.456789")
    private String lng;

    @Schema(description = "纬度", example = "45.678901")
    private String lat;

    @Schema(description = "状态（1在线0离线）", example = "1")
    private Integer status;

    @Schema(description = "快捷档位列表", example = "Remark A")
    private String shortcut;

    @Schema(description = "是否删除 (1表示删除)", example = "0")
    @TableLogic
    private Integer deleted;
}