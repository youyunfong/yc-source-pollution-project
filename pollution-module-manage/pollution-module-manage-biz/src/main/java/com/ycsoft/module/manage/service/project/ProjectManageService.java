package com.ycsoft.module.manage.service.project;

import com.ycsoft.framework.common.pojo.PageResult;
import com.ycsoft.module.manage.controller.admin.project.vo.AddProjectReqVO;
import com.ycsoft.module.manage.controller.admin.project.vo.ProjectIdRespVO;
import com.ycsoft.module.manage.controller.admin.project.vo.ProjectListReqVO;
import com.ycsoft.module.manage.controller.admin.project.vo.ProjectListRespVO;

import java.util.HashMap;
import java.util.Map;

/**
 * 项目管理 Service 接口
 * 提供页面管理页面功能
 * @author 颐川科技
 */
public interface ProjectManageService {
    /**
     * 页面分页查询
     * @param reqVO
     * @return
     */
    PageResult<ProjectListRespVO> selectPage(ProjectListReqVO reqVO);

    /**
     * 创建项目id
     * @return
     */
    ProjectIdRespVO createId();

    /**
     * 添加项目
     * @param vo
     */
    void addOrUpdateProject(AddProjectReqVO vo);

    /**
     * 删除工程信息
     * @param projectId
     */
    void deleteProject(String projectId);
}
