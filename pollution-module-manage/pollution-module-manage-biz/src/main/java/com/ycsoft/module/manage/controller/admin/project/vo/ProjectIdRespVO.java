package com.ycsoft.module.manage.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "项目管理 - 工程id")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectIdRespVO {
    @Schema(description = "工程id", example = "proj123")
    private String projectId;
}
