package com.ycsoft.module.manage.dal.mysql.water;

import com.ycsoft.framework.mybatis.core.mapper.BaseMapperX;
import com.ycsoft.module.manage.controller.admin.pollution.vo.GetHistoryDataReqVO;
import com.ycsoft.module.manage.dal.dataopject.WaterHistoryDO;
import com.ycsoft.module.manage.dal.dataopject.WaterRealtimeDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WaterRealtimeMapper extends BaseMapperX<WaterRealtimeDO> {
}
