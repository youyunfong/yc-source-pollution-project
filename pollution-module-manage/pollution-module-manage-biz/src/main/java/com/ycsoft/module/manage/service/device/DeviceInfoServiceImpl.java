package com.ycsoft.module.manage.service.device;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.thread.ThreadUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycsoft.framework.common.pojo.PageResult;
import com.ycsoft.framework.security.core.LoginUser;
import com.ycsoft.framework.security.core.util.SecurityFrameworkUtils;
import com.ycsoft.module.manage.controller.admin.project.vo.ShortcutControlReqVO;
import com.ycsoft.module.manage.dal.dataopject.*;
import com.ycsoft.module.manage.dal.mysql.grade.GradeLogMapper;
import com.ycsoft.module.manage.dal.mysql.grade.GradeRealtimeMapper;
import com.ycsoft.module.manage.service.mqtt.MqttSubscribeService;
import com.ycsoft.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.ycsoft.module.manage.controller.admin.pollution.vo.*;
import com.ycsoft.module.manage.controller.admin.project.vo.ControlGradeReqVO;
import com.ycsoft.module.manage.controller.admin.project.vo.GenerateNumbersRespVO;
import com.ycsoft.module.manage.controller.admin.project.vo.ProjectIdRespVO;
import com.ycsoft.module.manage.convert.DeviceConvert;
import com.ycsoft.module.manage.dal.mysql.device.DeviceInfoMapper;
import com.ycsoft.module.manage.dal.mysql.flow.FlowHistoryMapper;
import com.ycsoft.module.manage.dal.mysql.grade.GradeHistoryMapper;
import com.ycsoft.module.manage.dal.mysql.project.ProjectManageMapper;
import com.ycsoft.module.manage.dal.mysql.water.WaterHistoryMapper;
import com.ycsoft.module.manage.service.mqtt.MqttProducerService;
import jakarta.annotation.Resource;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Auth Service 实现类
 *
 * @author 颐川科技
 */
@Service
@Transactional
@Slf4j
public class DeviceInfoServiceImpl implements DeviceInfoService {
    @Resource
    private DeviceInfoMapper deviceInfoMapper;
    @Resource
    private ProjectManageMapper projectManageMapper;
    @Resource
    private WaterHistoryMapper waterHistoryMapper;
    @Resource
    private FlowHistoryMapper flowHistoryMapper;
    @Resource
    private GradeHistoryMapper gradeHistoryMapper;
    @Resource
    private MqttProducerService mqttProducerService;
    @Resource
    private GradeLogMapper gradeLogMapper;
    @Autowired
    private GradeRealtimeMapper gradeRealtimeMapper;


    public void generateNumbers(String projectId) {
            String maxId = deviceInfoMapper.getMaxId();
            //当数据库里面没有数据时，插第一条
            if (StringUtil.isEmpty(maxId)){
                deviceInfoMapper.insert(DeviceInfoDO.builder()
                        .deviceId("4GMQTT1")
                        .insertTime(DateUtil.now())
                        .projectId(projectId)
                        .build());
                //有数据拿到最大的数据并+1添加到数据库
            }else {
                String numberStr =maxId.substring(6);
                if (numberStr.matches("\\d+")) { // 验证是否为纯数字
                    int numberId = Integer.parseInt(numberStr)+1;
                    deviceInfoMapper.insert(DeviceInfoDO.builder()
                            .deviceId("4GMQTT"+numberId)
                            .insertTime(DateUtil.now())
                            .projectId(projectId)
                            .build());
                }else {
                    deviceInfoMapper.deleteId(maxId);
                    throw new RuntimeException("数据设备编号为："+maxId+"异常已删除");
                }
            }

    }

    public void deleteDeviceId(String deviceId) {
        deviceInfoMapper.deleteById(deviceId);
    }


    public List<GenerateNumbersRespVO> getDeviceInfo(String projectId) {
        List<DeviceInfoDO> deviceInfoDOList = deviceInfoMapper.selectList(new LambdaQueryWrapperX<DeviceInfoDO>()
                .eq(DeviceInfoDO::getProjectId, projectId));
            return DeviceConvert.INSTANCE.convertList(deviceInfoDOList);
    }


    public void deleteProjectDeviceInfo(String projectId) {
        deviceInfoMapper.delete("project_id", projectId);
    }


    public PageResult<PollutionListRespVO> selectPage(PollutionPageReqVO reqVO) {
        Page<PollutionListRespVO> page = new Page<>(reqVO.getPageNo(), reqVO.getPageSize());
        IPage<PollutionListRespVO> pageResult = deviceInfoMapper.getPollutionPage(page, reqVO);
        return new PageResult<>(pageResult.getRecords(),pageResult.getTotal());
    }

    public List<DeviceIdRespVO> getNotIdList(String projectId) {
        return deviceInfoMapper.selectList(new LambdaQueryWrapperX<DeviceInfoDO>()
                .eqIfPresent(DeviceInfoDO::getProjectId, projectId)
                .isNull(DeviceInfoDO::getFactoryDeviceId))
                .stream().map(d-> DeviceIdRespVO.builder()
                        .deviceId(d.getDeviceId())
                        .build()).toList();
    }

    @Override
    public List<ProjectIdRespVO> getProjectId() {
        return projectManageMapper.getProjectId();
    }

    @Override
    public void addOrUpdateFields(AddDeviceReqVO vo) {
        deviceInfoMapper.updateById(DeviceInfoDO.builder()
                .factoryDeviceId(vo.getFactoryDeviceId())
                .deviceId(vo.getDeviceId())
                .deviceName(vo.getDeviceName())
                .projectId(vo.getProjectId())
                .lng(vo.getLng())
                .lat(vo.getLat())
                .deviceType(vo.getDeviceType())
                .shortcutList(vo.getShortcutList())
                .build());
    }

    @Override
    public List<WaterHistoryDO> getWaterHistory(GetHistoryDataReqVO vo) {
        List<WaterHistoryDO> resultList = null;
        if (vo.getStatus() == 1) {
            resultList = waterHistoryMapper.getWaterHistoryByDay(vo);
        }else{
            resultList = waterHistoryMapper.selectList(new LambdaQueryWrapperX<WaterHistoryDO>()
                    .eq(WaterHistoryDO::getFactoryDeviceId, vo.getFactoryDeviceId())
                    .between(WaterHistoryDO::getTm, vo.getStartTime(), vo.getEndTime())
                    .orderByDesc(WaterHistoryDO::getTm));
        }
        return resultList;
    }

    @Override
    public List<FlowHistoryDO> getFlowHistory(GetHistoryDataReqVO vo) {
        List<FlowHistoryDO> resultList = null;
        if (vo.getStatus() == 1) {
            resultList = flowHistoryMapper.getFlowHistoryDay(vo);
        }else{
            resultList = flowHistoryMapper.selectList(new LambdaQueryWrapperX<FlowHistoryDO>()
                    .eq(FlowHistoryDO::getFactoryDeviceId, vo.getFactoryDeviceId())
                    .between(FlowHistoryDO::getTm, vo.getStartTime(), vo.getEndTime())
                    .orderByDesc(FlowHistoryDO::getTm));
        }
        return resultList;
    }

    @Override
    public List<GradeHistoryDO> getGradeHistory(GetHistoryDataReqVO vo) {
        List<GradeHistoryDO> resultList = null;
        if (vo.getStatus() == 1) {
            resultList = gradeHistoryMapper.getGradeHistoryDay(vo);
        }else{
            resultList = gradeHistoryMapper.selectList(new LambdaQueryWrapperX<GradeHistoryDO>()
                    .eq(GradeHistoryDO::getFactoryDeviceId, vo.getFactoryDeviceId())
                    .between(GradeHistoryDO::getTm, vo.getStartTime(), vo.getEndTime())
                    .orderByDesc(GradeHistoryDO::getTm));
        }
        return resultList;
    }

    // 新增设备锁容器（放在类成员变量位置）
    private final Map<String, Object> deviceLocks = new ConcurrentHashMap<>();

    @Override
    public void GearsControl(ControlGradeReqVO vo) {
        String factoryDeviceId = vo.getFactoryDeviceId();
        // 获取设备专属锁（双重检查锁保证线程安全）
        Object lock = deviceLocks.computeIfAbsent(factoryDeviceId, k -> new Object());

        synchronized (lock) {
            try {
                LoginUser loginUser = SecurityFrameworkUtils.getLoginUser();
                double sdata = vo.getStatus() == 0 ? 2.0 : 1.0;
                String action = vo.getStatus() == 0 ? "降低" : "升高";

                // 发送控制指令
                sendControlCommand(factoryDeviceId, sdata);
                //记录档位
                logGradeChange(loginUser, factoryDeviceId, action + "1档");
            } finally {
//                 移除无用锁（可选，根据业务场景决定是否保留）
                 deviceLocks.remove(factoryDeviceId);
            }
        }
    }

    @Override
    @Async
    public void shortcutControl(ShortcutControlReqVO vo) {
        String factoryDeviceId = vo.getFactoryDeviceId();
        Object lock = deviceLocks.computeIfAbsent(factoryDeviceId, k -> new Object());

        synchronized (lock) {
            try {
                LoginUser loginUser = SecurityFrameworkUtils.getLoginUser();
                ThreadUtil.sleep(1500); // 保留原有延时逻辑

                GradeRealtimeDO realtimeDO = gradeRealtimeMapper.selectById(factoryDeviceId);
                if (realtimeDO == null) return;

                int currentGrade = realtimeDO.getGrade();
                int targetGrade = vo.getShortcutValue();

                if (targetGrade == currentGrade) return;

                int step = targetGrade > currentGrade ? 1 : -1;

                for (int i = currentGrade + step; step > 0 ? i <= targetGrade : i >= targetGrade; i += step) {
                    sendControlCommand(factoryDeviceId, step > 0 ? 1.0 : 2.0);
                    ThreadUtil.sleep(2000); // 保持原有间隔
                }

                // 记录最终日志
                logGradeChange(loginUser, factoryDeviceId, String.format("使用了%s",vo.getShortcutName()));
            } finally {
                 deviceLocks.remove(factoryDeviceId); // 可选移除
            }
        }
    }

    // 提取公共指令发送方法
    private void sendControlCommand(String factoryDeviceId, double sdata) {
        mqttProducerService.sendMessage(
                "/server/coo/" + factoryDeviceId,
                String.format("{\"sensor_device_id\":0,\"port_id\":10,\"sdata\":%.1f}",sdata)
        );
    }

    // 提取日志记录公共方法
    private void logGradeChange(LoginUser loginUser, String factoryDeviceId, String actionDesc) {
        Long userId = null;
        String userName = null;
        Long tenantId = null;

        if (loginUser != null) {
            userId = loginUser.getId();
            userName = loginUser.getInfo().get(LoginUser.INFO_KEY_NICKNAME);
            tenantId = loginUser.getTenantId();
        }

        gradeLogMapper.insert(GradeLogDO.builder()
                .userId(userId)
                .factoryDeviceId(factoryDeviceId)
                .action(userName + actionDesc)
                .success(1)
                .createTime(LocalDateTime.now())
                .tenantId(tenantId)
                .build());
    }

}

