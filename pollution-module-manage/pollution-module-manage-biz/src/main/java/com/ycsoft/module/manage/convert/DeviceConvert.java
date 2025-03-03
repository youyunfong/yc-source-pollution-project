package com.ycsoft.module.manage.convert;

import com.ycsoft.module.manage.controller.admin.project.vo.GenerateNumbersRespVO;
import com.ycsoft.module.manage.controller.admin.project.vo.ProjectListRespVO;
import com.ycsoft.module.manage.dal.dataopject.DeviceInfoDO;
import com.ycsoft.module.manage.dal.dataopject.ProjectManageDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DeviceConvert {
    DeviceConvert INSTANCE = Mappers.getMapper(DeviceConvert.class);
    GenerateNumbersRespVO convert(DeviceInfoDO source);
    List<GenerateNumbersRespVO> convertList(List<DeviceInfoDO> source);
}
