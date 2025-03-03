package com.ycsoft.module.manage.dal.dataopject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

@Data
@TableName("grade_realtime")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class GradeRealtimeDO implements Serializable {

    @TableId(type = IdType.AUTO)
    @Schema(description = "厂家设备id", example = "dev123")
    private String factoryDeviceId;

    @Schema(description = "档位", example = "10")
    private Integer grade;

    @Schema(description = "插入时间", example = "2023-01-01 00:00:00")
    private String tm;

}