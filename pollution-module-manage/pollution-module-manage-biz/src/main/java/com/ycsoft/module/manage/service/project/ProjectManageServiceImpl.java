package com.ycsoft.module.manage.service.project;

import com.ycsoft.framework.common.pojo.PageResult;
import com.ycsoft.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.ycsoft.module.manage.controller.admin.project.vo.ProjectListReqVO;
import com.ycsoft.module.manage.controller.admin.project.vo.ProjectListRespVO;
import com.ycsoft.module.manage.convert.ProjectConvert;
import com.ycsoft.module.manage.dal.dataopject.ProjectManageDO;
import com.ycsoft.module.manage.dal.mysql.project.ProjectManageMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Auth Service 实现类
 *
 * @author 颐川科技
 */
@Service
@Slf4j
public class ProjectManageServiceImpl implements ProjectManageService {
    @Resource
    private ProjectManageMapper projectManageMapper;
    @Override
    public PageResult<ProjectListRespVO> selectPage(ProjectListReqVO reqVO) {
        PageResult<ProjectManageDO> projectManageResult = projectManageMapper.selectPage(reqVO, new LambdaQueryWrapperX<ProjectManageDO>()
                .eqIfPresent(ProjectManageDO::getProjectName, reqVO.getProjectName())
                .eqIfPresent(ProjectManageDO::getAreaId, reqVO.getAreaId())
                .eqIfPresent(ProjectManageDO::getProjectMan, reqVO.getProjectMan()));
        return new PageResult<>(
                ProjectConvert.INSTANCE.convertList(projectManageResult.getList()),
                projectManageResult.getTotal()
        );
    }
}
