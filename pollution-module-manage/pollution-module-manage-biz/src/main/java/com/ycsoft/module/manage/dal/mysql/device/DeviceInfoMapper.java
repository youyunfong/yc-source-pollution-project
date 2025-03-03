package com.ycsoft.module.manage.dal.mysql.device;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ycsoft.framework.mybatis.core.mapper.BaseMapperX;
import com.ycsoft.module.manage.controller.admin.pollution.vo.PollutionListRespVO;
import com.ycsoft.module.manage.controller.admin.pollution.vo.PollutionPageReqVO;
import com.ycsoft.module.manage.controller.admin.project.vo.ProjectListRespVO;
import com.ycsoft.module.manage.dal.dataopject.DeviceInfoDO;
import com.ycsoft.module.manage.dal.dataopject.ProjectManageDO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DeviceInfoMapper extends BaseMapperX<DeviceInfoDO> {
    @Select("SELECT device_id FROM device_info ORDER BY device_id DESC LIMIT 1 FOR UPDATE")
    String getMaxId();
    @Delete("delete from device_info where device_id=#{maxId}")
    void deleteId(String maxId);

    IPage<PollutionListRespVO> getPollutionPage(@Param("page") Page<PollutionListRespVO> page, @Param("reqVO")PollutionPageReqVO reqVO);
}
