package com.ycsoft.module.manage.controller.admin.pollution;

import com.ycsoft.framework.common.pojo.CommonResult;
import com.ycsoft.framework.common.pojo.PageResult;
import com.ycsoft.module.manage.controller.admin.pollution.vo.*;
import com.ycsoft.module.manage.controller.admin.project.vo.ControlGradeReqVO;
import com.ycsoft.module.manage.controller.admin.project.vo.ProjectIdRespVO;
import com.ycsoft.module.manage.controller.admin.project.vo.ShortcutControlReqVO;
import com.ycsoft.module.manage.dal.dataopject.FlowHistoryDO;
import com.ycsoft.module.manage.dal.dataopject.GradeHistoryDO;
import com.ycsoft.module.manage.dal.dataopject.WaterHistoryDO;
import com.ycsoft.module.manage.service.device.DeviceInfoService;
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

@Tag(name = "系统功能-面源污染")
@RequestMapping("/source/pollution")
@RestController
@Validated
@Slf4j
public class PollutionController {
    @Resource
    private DeviceInfoService deviceInfoService;

    @PostMapping("/page")
    @PermitAll
    @Operation(summary = "面源污染-列表分页")
    public CommonResult<PageResult<PollutionListRespVO>> selectPage(@RequestBody PollutionPageReqVO reqVO) {
        return success( deviceInfoService.selectPage(reqVO));
    }

    @GetMapping("/notId-list")
    @PermitAll
    @Operation(summary = "面源污染-查询没有厂家id的设备编号")
    public CommonResult<List<DeviceIdRespVO>> getNotIdList(String projectId) {
        return success( deviceInfoService.getNotIdList(projectId));
    }

    @GetMapping("/select-projectId")
    @PermitAll
    @Operation(summary = "面源污染-查询项目id")
    public CommonResult<List<ProjectIdRespVO>> getProjectId() {
        return success( deviceInfoService.getProjectId());
    }

    @PostMapping("/delete-deviceId")
    @PermitAll
    @Operation(summary = "面源污染-批量删除设备编号")
    public CommonResult<Boolean> deleteDeviceId(@RequestBody List<String> deviceIds) {
        deviceInfoService.deleteDeviceIds(deviceIds);
        return success(true);
    }

    @PostMapping("/addOrUpdate-fields")
    @PermitAll
    @Operation(summary = "面源污染-新建或编辑田块")
    public CommonResult<Boolean> addOrUpdateFields(@RequestBody @Valid AddDeviceReqVO vo) {
        deviceInfoService.addOrUpdateFields(vo);
        return success(true);
    }

    @PostMapping("/water-history")
    @PermitAll
    @Operation(summary = "面源污染-查看水位历史数据")
    public CommonResult<List<WaterHistoryDO>> getWaterHistory(@RequestBody @Valid GetHistoryDataReqVO vo) {
        return success(deviceInfoService.getWaterHistory(vo));
    }

    @PostMapping("/flow-history")
    @PermitAll
    @Operation(summary = "面源污染-查看流量历史数据")
    public CommonResult<List<FlowHistoryDO>> getFlowHistory(@RequestBody @Valid GetHistoryDataReqVO vo) {
        return success(deviceInfoService.getFlowHistory(vo));
    }

    @PostMapping("/grade-history")
    @PermitAll
    @Operation(summary = "面源污染-查看档位历史数据")
    public CommonResult<List<GradeHistoryDO>> getGradeHistory(@RequestBody @Valid GetHistoryDataReqVO vo) {
        return success(deviceInfoService.getGradeHistory(vo));
    }

    @PostMapping("/gears-control")
    @PermitAll
    @Operation(summary = "面源污染-加减档位")
    public CommonResult<Boolean> gearsControl(@RequestBody @Valid ControlGradeReqVO vo) {
        deviceInfoService.GearsControl(vo);
        return success(true);
    }

    @PostMapping("/shortcut-control")
    @PermitAll
    @Operation(summary = "面源污染-快捷档位控制")
    public CommonResult<Boolean> shortcutControl(@RequestBody @Valid ShortcutControlReqVO vo) {
        deviceInfoService.shortcutControl(vo);
        return success(true);
    }


    @GetMapping("/unbind-number")
    @Operation(summary = "面源污染-解绑厂家编号")
    public CommonResult<Boolean> unbindNumber(String deviceId) {
        deviceInfoService.unbindNumber(deviceId);
        return success(true);
    }

//    @PostMapping("/debug-device")
//    @Operation(summary = "面源污染-调试设备")
//    public CommonResult<DebugWebSocketRespVO> debugDevice(@RequestBody DebugWebSocketReqVO vo) {
//        return success(deviceInfoService.debugDevice(vo));
//    }
}
