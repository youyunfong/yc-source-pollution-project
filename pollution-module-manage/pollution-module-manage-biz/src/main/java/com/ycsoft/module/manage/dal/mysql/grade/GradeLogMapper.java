package com.ycsoft.module.manage.dal.mysql.grade;

import com.ycsoft.framework.mybatis.core.mapper.BaseMapperX;
import com.ycsoft.module.manage.controller.admin.pollution.vo.GetHistoryDataReqVO;
import com.ycsoft.module.manage.dal.dataopject.GradeHistoryDO;
import com.ycsoft.module.manage.dal.dataopject.GradeLogDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GradeLogMapper extends BaseMapperX<GradeLogDO> {
}
