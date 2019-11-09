package com.jwell.classifiedProtection.controller.backend;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jwell.classifiedProtection.commons.ResultObject;
import com.jwell.classifiedProtection.controller.common.BaseController;
import com.jwell.classifiedProtection.entry.ArchiveMaterialAudit;
import com.jwell.classifiedProtection.entry.vo.ArchiveMaterialAuditVo;
import com.jwell.classifiedProtection.service.IArchiveMaterialAuditService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 材料审核 前端控制器
 * </p>
 *
 * @author RonnieXu
 * @since 2019-08-29
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Api(value = "备案材料审核任务接口", tags = "备案材料审核任务接口")
@RestController
@RequestMapping("/backend/archive-material-audit")
public class ArchiveMaterialAuditController extends BaseController {

    @Autowired
    private IArchiveMaterialAuditService iArchiveMaterialAuditService;

    /**
     * 材料审核任务分页列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "材料审核任务分页列表", notes = "获取材料审核任务分页列表")
    @RequestMapping(value = "/paging", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "systemId", value = "系统ID", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "creatorId", value = "创建人", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "enterpriseId", value = "企业ID", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "createTime", value = "时间", dataType = "LocalDateTime", required = false),
            @ApiImplicitParam(paramType = "query", name = "auditState", value = "状态", dataType = "String", required = false),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "页长", dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "页码", dataType = "Integer"),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject paging(
            Integer systemId, Integer creatorId, Integer enterpriseId, String auditState,
            @RequestParam(value = "createTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime createTime,
            Integer pageNum, Integer pageSize) {

        ResultObject resultObject = ResultObject.failure();

        try {
            IPage iPage = null;
            if (pageNum != null && pageSize != null) {
                iPage = new Page(pageNum, pageSize);
            } else {
                resultObject.setMsg("分页页长和页码不能为空1");
                return resultObject;
            }

            ArchiveMaterialAudit archiveMaterialAudit = new ArchiveMaterialAudit();
            archiveMaterialAudit.setSystemId(systemId);
            archiveMaterialAudit.setCreatorId(creatorId);
            archiveMaterialAudit.setEnterpriseId(enterpriseId);
            archiveMaterialAudit.setAuditState(auditState);
            archiveMaterialAudit.setCreateTime(createTime);

            IPage<ArchiveMaterialAuditVo> materialAuditIPage = iArchiveMaterialAuditService.selectVoPage(iPage, archiveMaterialAudit);
            resultObject = ResultObject.success();
            resultObject.setData(materialAuditIPage);

        } catch (Exception e) {
            resultObject.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return resultObject;

    }

    /**
     * 查询材料审核任务详情
     *
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "查询材料审核任务", notes = "查询材料审核任务详情")
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id", value = "材料审核任务ID", required = false),
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject detail(@RequestParam(value = "id") Serializable id) {

        ResultObject resultObject = new ResultObject();
        try {

            if (id != null) {

                ArchiveMaterialAudit ArchiveMaterialAudit = iArchiveMaterialAuditService.getDetailById(id);
                resultObject.setCode(HttpServletResponse.SC_OK);
                resultObject.setData(ArchiveMaterialAudit);
            } else {
                resultObject.setCode(HttpServletResponse.SC_BAD_REQUEST);
                resultObject.setMsg("参数不能为空");
            }

        } catch (Exception e) {
            resultObject.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resultObject.setMsg("查询失败");
            e.printStackTrace();
        }

        return resultObject;
    }

    @ApiOperation(value = "材料审核任务新增或者修改", notes = "材料审核任务新增或者修改")
    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "Authorization", value = "token", required = false)
    }
    )
    public ResultObject saveOrUpdate(@RequestBody ArchiveMaterialAudit archiveMaterialAudit) {

        ResultObject resultObject = new ResultObject();

        try {

            boolean b = iArchiveMaterialAuditService.saveOrUpdate(archiveMaterialAudit);
            resultObject.setCode(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            resultObject.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return resultObject;

    }
}
