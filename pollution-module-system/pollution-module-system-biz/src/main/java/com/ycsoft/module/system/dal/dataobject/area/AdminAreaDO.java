package com.ycsoft.module.system.dal.dataobject.area;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("admin_area")
public class AdminAreaDO implements Serializable {

    @TableId(value = "area_id", type = IdType.ASSIGN_ID)
    private String areaId;


    private String areaName;


    private String areaCode;


    private String fatherCode;


    private Integer areaLevel;


    private String completeAreaName;

    private Integer orderId;

    private String updateTime;

    @TableLogic
    private Integer deleted;
}
