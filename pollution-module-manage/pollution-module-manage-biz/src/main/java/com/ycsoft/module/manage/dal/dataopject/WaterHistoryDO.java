package com.ycsoft.module.manage.dal.dataopject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fhs.core.trans.vo.TransPojo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@TableName("water_history")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class WaterHistoryDO implements Serializable {

    @TableId(type = IdType.AUTO)
    @Schema(description = "厂家设备id", example = "dev123")
    private String factoryDeviceId;

    @Schema(description = "水位", example = "3.2")
    private BigDecimal water;

    @Schema(description = "插入时间", example = "2023-01-01 00:00:00")
    private String tm;

}