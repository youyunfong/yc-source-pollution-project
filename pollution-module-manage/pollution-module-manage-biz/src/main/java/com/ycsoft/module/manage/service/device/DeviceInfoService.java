package com.ycsoft.module.manage.service.device;

import com.ycsoft.framework.common.pojo.PageResult;
import com.ycsoft.module.manage.controller.admin.pollution.vo.*;
import com.ycsoft.module.manage.controller.admin.project.vo.*;
import com.ycsoft.module.manage.dal.dataopject.FlowHistoryDO;
import com.ycsoft.module.manage.dal.dataopject.GradeHistoryDO;
import com.ycsoft.module.manage.dal.dataopject.WaterHistoryDO;
import jakarta.validation.Valid;

import java.util.List;

/**
 * 项目管理 Service 接口
 * 提供页面管理页面功能
 * @author 颐川科技
 */
public interface DeviceInfoService {
    /**
     * 创建设备编号
     * @param projectId 项目ID
     */
    GenerateNumbersRespVO generateNumbers(String projectId);

    /**
     * 删除设备管理
     * @param deviceIds
     */
    void deleteDeviceIds(List<String> deviceIds);

    /**
     * 根据项目id获取设备编号
     * @param projectId
     * @return
     */
    List<GenerateNumbersRespVO> getDeviceInfo(String projectId);

    /**
     * 删除对应工程id的所有设备信息
     * @param projectId
     */
    void deleteProjectDeviceInfo(String projectId);

    /**
     * 面源污染设备列表
     * @param reqVO
     * @return
     */
    PageResult<PollutionListRespVO> selectPage( PollutionPageReqVO reqVO);

    /**
     * 面源污染
     * @return
     */
    List<DeviceIdRespVO> getNotIdList(String projectId);

    /**
     * 查询项目id
     * @return
     */
    List<ProjectIdRespVO> getProjectId();

    /**
     * 新建田块
     * @param vo
     */
    void addOrUpdateFields(@Valid AddDeviceReqVO vo);

    /**
     * 水位历史数据
     * @param vo
     * @return
     */
    List<WaterHistoryDO> getWaterHistory(@Valid GetHistoryDataReqVO vo);

    /**
     * 流量历史数据
     * @param vo
     * @return
     */
    List<FlowHistoryDO> getFlowHistory(@Valid GetHistoryDataReqVO vo);

    /**
     * 档位历史数据
     * @param vo
     * @return
     */
    List<GradeHistoryDO> getGradeHistory(@Valid GetHistoryDataReqVO vo);

    /**
     * 加减档位
     * @param vo
     * @return
     */
    void GearsControl(@Valid ControlGradeReqVO vo);

    /**
     * 切换快捷档位
     * @param vo
     */
    void shortcutControl(@Valid ShortcutControlReqVO vo);

    /**
     * 解绑厂家编号
     * @param deviceId
     */
    void unbindNumber(String deviceId);
}
