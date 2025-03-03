package com.ycsoft.module.manage.dal.mysql.project;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycsoft.framework.mybatis.core.mapper.BaseMapperX;
import com.ycsoft.module.manage.controller.admin.project.vo.ProjectIdRespVO;
import com.ycsoft.module.manage.controller.admin.project.vo.ProjectListReqVO;
import com.ycsoft.module.manage.controller.admin.project.vo.ProjectListRespVO;
import com.ycsoft.module.manage.dal.dataopject.ProjectManageDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProjectManageMapper extends BaseMapperX<ProjectManageDO> {
    IPage<ProjectListRespVO> getProjectPage(@Param("page") Page<ProjectListRespVO> page, @Param("reqVO") ProjectListReqVO reqVO);
    @Select("SELECT project_id FROM project_manage where deleted = 0")
    List<ProjectIdRespVO> getProjectId();
}
