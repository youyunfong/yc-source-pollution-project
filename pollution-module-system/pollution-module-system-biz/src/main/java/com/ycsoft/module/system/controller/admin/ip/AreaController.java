package com.ycsoft.module.system.controller.admin.ip;

import cn.hutool.core.lang.Assert;
import com.ycsoft.framework.common.pojo.CommonResult;
import com.ycsoft.framework.common.util.object.BeanUtils;
import com.ycsoft.framework.ip.core.Area;
import com.ycsoft.framework.ip.core.utils.AreaUtils;
import com.ycsoft.framework.ip.core.utils.IPUtils;
import com.ycsoft.module.system.controller.admin.ip.vo.AdcdInfoRespVO;
import com.ycsoft.module.system.controller.admin.ip.vo.AdcdParamsReqVO;
import com.ycsoft.module.system.controller.admin.ip.vo.AreaNodeRespVO;
import com.ycsoft.module.system.service.area.AdminAreaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import jodd.util.StringUtil;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ycsoft.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 地区")
@RestController
@RequestMapping("/system/area")
@Validated
public class AreaController {
    @Resource
    private AdminAreaService adminAreaService;
    @GetMapping("/tree")
    @Operation(summary = "获得地区树")
    public CommonResult<List<AreaNodeRespVO>> getAreaTree() {
        Area area = AreaUtils.getArea(Area.ID_CHINA);
        Assert.notNull(area, "获取不到中国");
        return success(BeanUtils.toBean(area.getChildren(), AreaNodeRespVO.class));
    }

    @GetMapping("/get-by-ip")
    @Operation(summary = "获得 IP 对应的地区名")
    @Parameter(name = "ip", description = "IP", required = true)
    public CommonResult<String> getAreaByIp(@RequestParam("ip") String ip) {
        // 获得城市
        Area area = IPUtils.getArea(ip);
        if (area == null) {
            return success("未知");
        }
        // 格式化返回
        return success(AreaUtils.format(area.getId()));
    }


    @PostMapping("/AdcdTreeList")
    @Operation(summary = "浙江行政区划-树形列表")
    @PermitAll
    public CommonResult<List<AdcdInfoRespVO>> getAdcdTreeList(@RequestBody AdcdParamsReqVO params){
        List<AdcdInfoRespVO> list = adminAreaService.getAdcdTreeList(params);
        return success(list);
    }
}
