package com.ycsoft.module.system.api.logger;

import com.ycsoft.framework.common.pojo.PageResult;
import com.ycsoft.framework.common.util.object.BeanUtils;
import com.ycsoft.module.system.api.logger.dto.OperateLogCreateReqDTO;
import com.ycsoft.module.system.api.logger.dto.OperateLogPageReqDTO;
import com.ycsoft.module.system.api.logger.dto.OperateLogRespDTO;
import com.ycsoft.module.system.dal.dataobject.logger.OperateLogDO;
import com.ycsoft.module.system.service.logger.OperateLogService;
import com.fhs.core.trans.anno.TransMethodResult;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * 操作日志 API 实现类
 *
 * @author 颐川科技
 */
@Service
@Validated
public class OperateLogApiImpl implements OperateLogApi {

    @Resource
    private OperateLogService operateLogService;

    @Override
    public void createOperateLog(OperateLogCreateReqDTO createReqDTO) {
        operateLogService.createOperateLog(createReqDTO);
    }

    @Override
    @TransMethodResult
    public PageResult<OperateLogRespDTO> getOperateLogPage(OperateLogPageReqDTO pageReqDTO) {
        PageResult<OperateLogDO> operateLogPage = operateLogService.getOperateLogPage(pageReqDTO);
        return BeanUtils.toBean(operateLogPage, OperateLogRespDTO.class);
    }

}
