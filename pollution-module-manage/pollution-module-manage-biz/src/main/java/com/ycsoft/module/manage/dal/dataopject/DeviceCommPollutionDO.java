package com.ycsoft.module.manage.dal.dataopject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Data
@TableName("device_comm_pollution")
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceCommPollutionDO {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @Schema(description = "厂家id", example = "")
    @JsonProperty("factory_device_id")
    private String factoryDeviceId;

    @Schema(description = "传感器设备ID", example = "12345")
    @JsonProperty("sensor_device_id")
    private int sensorDeviceId;

    @Schema(description = "信息类型（10出水阀高位板档位状态，1出水阀低位板开关状态，5水位传感器，0电磁流量计）", example = "10")
    @JsonProperty("port_id")
    private int portId;

    @Schema(description = "传感器数据", example = "10")
    @JsonProperty("sdata")
    private String sdata;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime insertTime = LocalDateTime.now();


}