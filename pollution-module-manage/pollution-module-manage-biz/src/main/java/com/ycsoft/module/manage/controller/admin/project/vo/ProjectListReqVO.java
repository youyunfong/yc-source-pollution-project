package com.ycsoft.module.manage.controller.admin.project.vo;

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
public class ProjectListReqVO extends PageParam {

    @Schema(description = "项目名称", example = "Project A")
    private String projectName;

    @Schema(description = "行政区域ID", example = "area456")
    private String areaId;

    @Schema(description = "项目管理员ID", example = "man789")
    private String projectMan;

}