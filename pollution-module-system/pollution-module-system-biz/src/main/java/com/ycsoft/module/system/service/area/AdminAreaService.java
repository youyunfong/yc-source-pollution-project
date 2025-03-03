package com.ycsoft.module.system.service.area;

import com.ycsoft.module.system.controller.admin.auth.vo.*;
import com.ycsoft.module.system.controller.admin.ip.vo.AdcdInfoRespVO;
import com.ycsoft.module.system.controller.admin.ip.vo.AdcdParamsReqVO;
import com.ycsoft.module.system.dal.dataobject.user.AdminUserDO;
import jakarta.validation.Valid;

import java.util.List;

/**
 * 管理后台的认证 Service 接口
 *
 * 提供用户的登录、登出的能力
 *
 * @author 颐川科技
 */
public interface AdminAreaService {
    List<AdcdInfoRespVO> getAdcdTreeList(AdcdParamsReqVO params);
}
