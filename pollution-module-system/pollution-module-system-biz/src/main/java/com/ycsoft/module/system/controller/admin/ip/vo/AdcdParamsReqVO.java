package com.ycsoft.module.system.controller.admin.ip.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class AdcdParamsReqVO implements Serializable {

    @Schema( description = "1（查询市） 2 (市、区)  3（市、区、街道）",example = "2")
    private String type;

}
