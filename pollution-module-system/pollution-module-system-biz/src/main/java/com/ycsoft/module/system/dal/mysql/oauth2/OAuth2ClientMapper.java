package com.ycsoft.module.system.dal.mysql.oauth2;

import com.ycsoft.framework.common.pojo.PageResult;
import com.ycsoft.framework.mybatis.core.mapper.BaseMapperX;
import com.ycsoft.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.ycsoft.module.system.controller.admin.oauth2.vo.client.OAuth2ClientPageReqVO;
import com.ycsoft.module.system.dal.dataobject.oauth2.OAuth2ClientDO;
import org.apache.ibatis.annotations.Mapper;


/**
 * OAuth2 客户端 Mapper
 *
 * @author 颐川科技
 */
@Mapper
public interface OAuth2ClientMapper extends BaseMapperX<OAuth2ClientDO> {

    default PageResult<OAuth2ClientDO> selectPage(OAuth2ClientPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OAuth2ClientDO>()
                .likeIfPresent(OAuth2ClientDO::getName, reqVO.getName())
                .eqIfPresent(OAuth2ClientDO::getStatus, reqVO.getStatus())
                .orderByDesc(OAuth2ClientDO::getId));
    }

    default OAuth2ClientDO selectByClientId(String clientId) {
        return selectOne(OAuth2ClientDO::getClientId, clientId);
    }

}
