package com.ycsoft.module.manage.controller.admin.project;

import com.ycsoft.framework.common.pojo.CommonResult;
import com.ycsoft.framework.common.pojo.PageResult;
import com.ycsoft.module.manage.controller.admin.project.vo.*;
import com.ycsoft.module.manage.service.device.DeviceInfoService;
import com.ycsoft.module.manage.service.project.ProjectManageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;


import static com.ycsoft.framework.common.pojo.CommonResult.success;

@Tag(name = "系统功能-项目管理")
@RequestMapping("/project/manage")
@RestController
@Validated
@Slf4j
public class ProjectController {
    @Resource
    private ProjectManageService projectManageService;
    @Resource
    private DeviceInfoService deviceInfoService;

    @PostMapping("/page")
    @PermitAll
    @Operation(summary = "项目管理-列表查询")
    public CommonResult<PageResult<ProjectListRespVO>> selectPage(@RequestBody @Valid ProjectListReqVO reqVO) {
        return success(projectManageService.selectPage(reqVO));
    }

    @GetMapping("/create-id")
    @PermitAll
    @Operation(summary = "项目管理-创建项目编号")
    public CommonResult<ProjectIdRespVO> createId() {
        return success( projectManageService.createId());
    }

    @GetMapping("/generate-numbers")
    @PermitAll
    @Operation(summary = "项目管理-生成设备编号")
    public CommonResult<GenerateNumbersRespVO> generateNumbers(String projectId) {
        return success(deviceInfoService.generateNumbers(projectId));
    }



    @GetMapping("/device-info")
    @PermitAll
    @Operation(summary = "项目管理-查询设备列表")
    public CommonResult<List<GenerateNumbersRespVO>> getDeviceInfo(String projectId) {
        return success(deviceInfoService.getDeviceInfo(projectId));
    }

    @PostMapping("/addOrUpdate-project")
    @PermitAll
    @Operation(summary = "项目管理-添加或修改项目")
    public CommonResult<Boolean> addOrUpdateProject(@RequestBody @Valid AddProjectReqVO vo) {
        projectManageService.addOrUpdateProject(vo);
        return success(true);
    }

    @GetMapping("/delete-deviceInfo")
    @PermitAll
    @Operation(summary = "项目管理-根据项目id删除对应全部设备信息")
    public CommonResult<Boolean> deleteProjectDeviceInfo(String projectId) {
        deviceInfoService.deleteProjectDeviceInfo(projectId);
        return success(true);
    }

    @GetMapping("/delete-project")
    @PermitAll
    @Operation(summary = "项目管理-删除项目信息")
    public CommonResult<Boolean> deleteProject(String projectId) {
        projectManageService.deleteProject(projectId);
        return success(true);
    }

}
