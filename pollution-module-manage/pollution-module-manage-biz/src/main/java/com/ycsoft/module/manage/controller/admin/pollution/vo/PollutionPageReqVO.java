package com.ycsoft.module.manage.controller.admin.pollution.vo;

import com.ycsoft.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "羡慕管理 - 列表查询参数")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PollutionPageReqVO extends PageParam {

    @Schema(description = "项目Id", example = "Project A")
    private String projectId;

    @Schema(description = "田块名称", example = "area456")
    private String deviceName;


}