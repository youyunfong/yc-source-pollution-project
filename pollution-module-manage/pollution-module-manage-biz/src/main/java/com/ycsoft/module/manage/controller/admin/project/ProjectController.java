package com.ycsoft.module.manage.controller.admin.project;


import com.ycsoft.framework.common.pojo.CommonResult;
import com.ycsoft.framework.common.pojo.PageResult;
import com.ycsoft.module.manage.controller.admin.project.vo.ProjectListReqVO;
import com.ycsoft.module.manage.controller.admin.project.vo.ProjectListRespVO;
import com.ycsoft.module.manage.service.project.ProjectManageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.ycsoft.framework.common.pojo.CommonResult.success;

@Tag(name = "项目管理")
@RestController
@RequestMapping("/manage/project")
@Validated
@Slf4j
public class ProjectController {
    @Resource
    private ProjectManageService projectManageService;

    @PostMapping("/page")
    @Operation(summary = "列表查询")
    public CommonResult<PageResult<ProjectListRespVO>> selectPage(@RequestBody ProjectListReqVO reqVO) {
        return success(projectManageService.selectPage(reqVO));
    }
}
