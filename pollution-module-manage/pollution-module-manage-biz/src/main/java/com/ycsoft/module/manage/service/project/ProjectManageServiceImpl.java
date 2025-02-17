package com.ycsoft.module.manage.service.project;

import com.ycsoft.framework.common.pojo.PageResult;
import com.ycsoft.module.manage.controller.admin.project.vo.ProjectListReqVO;
import com.ycsoft.module.manage.controller.admin.project.vo.ProjectListRespVO;
import com.ycsoft.module.manage.dal.mysql.project.ProjectManageMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        return null;
    }
}
