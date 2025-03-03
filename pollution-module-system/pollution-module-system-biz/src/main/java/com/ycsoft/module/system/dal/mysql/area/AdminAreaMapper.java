package com.ycsoft.module.system.dal.mysql.area;

import com.ycsoft.framework.mybatis.core.mapper.BaseMapperX;
import com.ycsoft.module.system.controller.admin.ip.vo.AdcdInfoRespVO;
import com.ycsoft.module.system.controller.admin.ip.vo.AdcdParamsReqVO;
import com.ycsoft.module.system.dal.dataobject.area.AdminAreaDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminAreaMapper extends BaseMapperX<AdminAreaDO> {
    List<AdcdInfoRespVO> getAdcdTreeList(@Param("params")AdcdParamsReqVO params);
}