package com.aaa.rendering.controller;

import com.aaa.rendering.service.ReportDataService;
import com.aaa.entity.ReportVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("render")
public class ReportRenderingController {
    @Autowired
    private ReportDataService reportDataService;

    // 渲染报告页面
    @RequestMapping("index")
    public String renderReportPage(Model model, String params, Integer cc, HttpServletRequest request) {
        List<ReportVo> reportData = reportDataService.getReportData(params, cc);
        model.addAttribute("report", reportData);
        Integer roleId = (Integer) request.getSession().getAttribute("roleId");
        if (roleId != 5) {
            return "reportPage"; // 对应 Thymeleaf 模板名称
        }
        return "patientRegistration"; // 对应 Thymeleaf 模板名称
    }

    // 渲染其他页面，如患者注册页面等
    @RequestMapping("patientRegistration")
    public String renderPatientRegistrationPage() {
        return "patientRegistration";
    }
}
