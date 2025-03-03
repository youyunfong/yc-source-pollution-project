package com.ycsoft.module.manage.dal.mysql.device;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycsoft.framework.mybatis.core.mapper.BaseMapperX;
import com.ycsoft.module.manage.controller.admin.pollution.vo.PollutionListRespVO;
import com.ycsoft.module.manage.controller.admin.pollution.vo.PollutionPageReqVO;
import com.ycsoft.module.manage.dal.dataopject.DeviceCommPollutionDO;
import com.ycsoft.module.manage.dal.dataopject.DeviceInfoDO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DeviceCommPollutionMapper extends BaseMapperX<DeviceCommPollutionDO> {
}
