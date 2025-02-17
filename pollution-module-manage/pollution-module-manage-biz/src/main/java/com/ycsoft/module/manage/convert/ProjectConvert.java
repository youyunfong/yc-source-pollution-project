package com.ycsoft.module.manage.convert;

import com.ycsoft.module.manage.controller.admin.project.vo.ProjectListRespVO;
import com.ycsoft.module.manage.dal.dataopject.ProjectManageDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProjectConvert {
    ProjectConvert INSTANCE = Mappers.getMapper(ProjectConvert.class);
    ProjectListRespVO convert(ProjectManageDO source);
    List<ProjectListRespVO> convertList(List<ProjectManageDO> source);
}
