package com.ycsoft.module.manage.service.project;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycsoft.framework.common.pojo.PageResult;
import com.ycsoft.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.ycsoft.module.manage.controller.admin.project.vo.AddProjectReqVO;
import com.ycsoft.module.manage.controller.admin.project.vo.ProjectIdRespVO;
import com.ycsoft.module.manage.controller.admin.project.vo.ProjectListReqVO;
import com.ycsoft.module.manage.controller.admin.project.vo.ProjectListRespVO;
import com.ycsoft.module.manage.convert.ProjectConvert;
import com.ycsoft.module.manage.dal.dataopject.ProjectManageDO;
import com.ycsoft.module.manage.dal.mysql.device.DeviceInfoMapper;
import com.ycsoft.module.manage.dal.mysql.project.ProjectManageMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Auth Service 实现类
 *
 * @author 颐川科技
 */
@Service
@Transactional
@Slf4j
public class ProjectManageServiceImpl implements ProjectManageService {
    @Resource
    private ProjectManageMapper projectManageMapper;
    @Resource
    private DeviceInfoMapper deviceInfoMapper;
    @Override
    public PageResult<ProjectListRespVO> selectPage(ProjectListReqVO reqVO) {
        Page<ProjectListRespVO> page = new Page<>(reqVO.getPageNo(), reqVO.getPageSize());
        IPage<ProjectListRespVO> pageResult = projectManageMapper.getProjectPage(page, reqVO);
        return new PageResult<>(pageResult.getRecords(),pageResult.getTotal());
    }

    @Override
    public ProjectIdRespVO createId() {
        String projectId = IdUtil.nanoId();
        return ProjectIdRespVO.builder().projectId(projectId).build();
    }

    @Override
    public void addOrUpdateProject(AddProjectReqVO vo) {
        ProjectManageDO projectManageDO = new ProjectManageDO();
        BeanUtils.copyProperties(vo,projectManageDO);
        projectManageMapper.insertOrUpdate(projectManageDO);
    }
    @Override
    public void deleteProject(String projectId) {
        deviceInfoMapper.delete("project_id", projectId);
        projectManageMapper.deleteById(projectId);
    }

}
