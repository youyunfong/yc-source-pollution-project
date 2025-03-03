package com.ycsoft.module.system.service.area;

import cn.hutool.core.util.ObjectUtil;
import com.google.common.annotations.VisibleForTesting;
import com.xingyuv.captcha.model.common.ResponseModel;
import com.xingyuv.captcha.model.vo.CaptchaVO;
import com.xingyuv.captcha.service.CaptchaService;
import com.ycsoft.framework.common.enums.CommonStatusEnum;
import com.ycsoft.framework.common.enums.UserTypeEnum;
import com.ycsoft.framework.common.util.monitor.TracerUtils;
import com.ycsoft.framework.common.util.servlet.ServletUtils;
import com.ycsoft.framework.common.util.validation.ValidationUtils;
import com.ycsoft.module.system.api.logger.dto.LoginLogCreateReqDTO;
import com.ycsoft.module.system.api.sms.SmsCodeApi;
import com.ycsoft.module.system.api.sms.dto.code.SmsCodeUseReqDTO;
import com.ycsoft.module.system.api.social.dto.SocialUserBindReqDTO;
import com.ycsoft.module.system.api.social.dto.SocialUserRespDTO;
import com.ycsoft.module.system.controller.admin.auth.vo.*;
import com.ycsoft.module.system.controller.admin.ip.vo.AdcdInfoRespVO;
import com.ycsoft.module.system.controller.admin.ip.vo.AdcdParamsReqVO;
import com.ycsoft.module.system.convert.auth.AuthConvert;
import com.ycsoft.module.system.dal.dataobject.oauth2.OAuth2AccessTokenDO;
import com.ycsoft.module.system.dal.dataobject.user.AdminUserDO;
import com.ycsoft.module.system.dal.mysql.area.AdminAreaMapper;
import com.ycsoft.module.system.enums.logger.LoginLogTypeEnum;
import com.ycsoft.module.system.enums.logger.LoginResultEnum;
import com.ycsoft.module.system.enums.oauth2.OAuth2ClientConstants;
import com.ycsoft.module.system.enums.sms.SmsSceneEnum;
import com.ycsoft.module.system.service.logger.LoginLogService;
import com.ycsoft.module.system.service.member.MemberService;
import com.ycsoft.module.system.service.oauth2.OAuth2TokenService;
import com.ycsoft.module.system.service.social.SocialUserService;
import com.ycsoft.module.system.service.user.AdminUserService;
import jakarta.annotation.Resource;
import jakarta.validation.Validator;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.ycsoft.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.ycsoft.framework.common.util.servlet.ServletUtils.getClientIP;
import static com.ycsoft.module.system.enums.ErrorCodeConstants.*;

/**
 * Auth Service 实现类
 *
 * @author 颐川科技
 */
@Service
@Slf4j
public class AdminAreaServiceImpl implements AdminAreaService {
    @Resource
    private AdminAreaMapper adminAreaMapper;

//    @Override
//    public List<AdcdInfoRespVO> getAdcdTreeList(AdcdParamsReqVO params) {
//        List<AdcdInfoRespVO> list = adminAreaMapper.getAdcdTreeList(params);
//        return GetAdcdTree(list, list);
//    }
//
//    private static List<AdcdInfoRespVO> GetAdcdTree(List<AdcdInfoRespVO> flist, List<AdcdInfoRespVO> list) {
//        Iterator<AdcdInfoRespVO> it = flist.iterator();
//        while (it.hasNext()) {
//            AdcdInfoRespVO r = it.next();
//            List<AdcdInfoRespVO> cList = list.stream().filter(s -> s.fatherCode != null && s.fatherCode.equals(r.areaCode))
//                    .collect(Collectors.toList());
//            if (!cList.isEmpty()) {
//                r.children = GetAdcdTree(cList, list);
//            }
//        }
//        return flist;
//    }

    @Override
    public List<AdcdInfoRespVO> getAdcdTreeList(AdcdParamsReqVO params) {
        List<AdcdInfoRespVO> allNodes = adminAreaMapper.getAdcdTreeList(params);

        // 构建快速查找表：父级编码 -> 子节点列表
        Map<String, List<AdcdInfoRespVO>> parentMap = allNodes.stream()
                .filter(node -> node.fatherCode != null)
                .collect(Collectors.groupingBy(node -> node.fatherCode));

        // 找出所有根节点（没有父节点或父节点不存在于列表中）
        List<AdcdInfoRespVO> roots = allNodes.stream()
                .filter(node -> !parentMap.containsKey(node.areaCode))
                .collect(Collectors.toList());

        return buildTree(roots, parentMap);
    }

    private List<AdcdInfoRespVO> buildTree(List<AdcdInfoRespVO> nodes,
                                           Map<String, List<AdcdInfoRespVO>> parentMap) {
        for (AdcdInfoRespVO node : nodes) {
            List<AdcdInfoRespVO> children = parentMap.get(node.areaCode);
            if (children != null && !children.isEmpty()) {
                node.children = buildTree(children, parentMap);
            }
        }
        return nodes;
    }


}
