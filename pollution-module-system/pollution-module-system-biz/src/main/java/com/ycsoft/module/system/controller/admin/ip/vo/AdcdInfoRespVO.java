package com.ycsoft.module.system.controller.admin.ip.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Schema(description = "管理后台 - 行政区域管理 Response VO")
@Data
public class AdcdInfoRespVO implements Serializable {

    /**
     * 行政区ID
     */
    @Schema(description = "行政区划代码", example = "99135604637328111")
    public String areaId;

    /**
     * 行政区编码
     */
    @Schema(description = "行政区编码", example = "3301")
    public String areaCode;

    /**
     * 地区名称
     */
    @Schema(description = "行政区编码", example = "杭州市")
    public String areaName;

    /**
     * 父级编码
     */
    @Schema(description = "父级编码", example = "33")
    public String fatherCode;

    @Schema(description = "完整的行政区划名称", example = "完整的行政区划名称")
    private String completeAreaName;

    /**
     * 行政区等级（1-区，2-镇，3-村）
     */
    @Schema(description = "行政区等级(0-省，1-市，2-区，3-镇街)", example = "2")
    private Integer areaLevel;

    /**
     * 子集
     */
    @Schema(description = "子集")
    public List<AdcdInfoRespVO> children;
}
