package com.ycsoft.module.system.dal.mysql.logger;

import com.ycsoft.framework.common.pojo.PageResult;
import com.ycsoft.framework.mybatis.core.mapper.BaseMapperX;
import com.ycsoft.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.ycsoft.module.system.controller.admin.logger.vo.loginlog.LoginLogPageReqVO;
import com.ycsoft.module.system.dal.dataobject.logger.LoginLogDO;
import com.ycsoft.module.system.enums.logger.LoginResultEnum;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginLogMapper extends BaseMapperX<LoginLogDO> {

    default PageResult<LoginLogDO> selectPage(LoginLogPageReqVO reqVO) {
        LambdaQueryWrapperX<LoginLogDO> query = new LambdaQueryWrapperX<LoginLogDO>()
                .likeIfPresent(LoginLogDO::getUserIp, reqVO.getUserIp())
                .likeIfPresent(LoginLogDO::getUsername, reqVO.getUsername())
                .betweenIfPresent(LoginLogDO::getCreateTime, reqVO.getCreateTime());
        if (Boolean.TRUE.equals(reqVO.getStatus())) {
            query.eq(LoginLogDO::getResult, LoginResultEnum.SUCCESS.getResult());
        } else if (Boolean.FALSE.equals(reqVO.getStatus())) {
            query.gt(LoginLogDO::getResult, LoginResultEnum.SUCCESS.getResult());
        }
        query.orderByDesc(LoginLogDO::getId); // 降序
        return selectPage(reqVO, query);
    }

}
