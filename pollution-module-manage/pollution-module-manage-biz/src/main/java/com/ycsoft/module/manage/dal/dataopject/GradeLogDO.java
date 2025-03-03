package com.ycsoft.module.manage.dal.dataopject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Data
@TableName("grade_log")
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GradeLogDO {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @Schema(description = "用户id", example = "")
    private Long userId;

    @Schema(description = "工厂id", example = "")
    private String factoryDeviceId;

    @Schema(description = "用户行为", example = "")
    private String action;

    @Schema(description = "是否成功1成功0失败", example = "")
    private int success;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "用户编号", example = "")
    private Long tenantId;

    @Schema(description = "是否删除（1表示删除）", example = "")
    @TableLogic
    private int deleted;
}