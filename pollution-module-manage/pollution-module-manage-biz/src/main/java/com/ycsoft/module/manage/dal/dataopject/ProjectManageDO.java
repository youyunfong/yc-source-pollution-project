package com.ycsoft.module.manage.dal.dataopject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fhs.core.trans.vo.TransPojo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@TableName("project_manage")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectManageDO implements Serializable, TransPojo {

    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "项目ID", example = "proj123")
    private String projectId;

    @Schema(description = "项目名称", example = "Project A")
    private String projectName;

    @Schema(description = "行政区域ID", example = "area456")
    private String areaId;

    @Schema(description = "项目管理员ID", example = "man789")
    private String projectMan;

    @Schema(description = "设备管理员ID", example = "dev012")
    private String deviceMan;

    @Schema(description = "项目年份", example = "2023")
    private String projectYear;

    @Schema(description = "经度", example = "123.456789")
    private String lng;

    @Schema(description = "纬度", example = "45.678901")
    private String lat;

    @Schema(description = "逻辑删除标志 (1表示删除)", example = "0")
    private Integer deleted;
}