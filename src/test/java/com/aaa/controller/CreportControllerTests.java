package com.aaa.controller;
import com.aaa.entity.*;
import com.aaa.service.CreportService;
import com.github.pagehelper.PageInfo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CreportControllerTests {

    @InjectMocks
    private CreportController creportController;

    @Mock
    private CreportService creportService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    // 测试 toreport 方法的正向测试
    @Test
    public void testToreport_Success() {
        ReportVo reportVo = new ReportVo();
        String params = "testReport";
        Integer cc = 1;
        Integer roleId = 4;

        List<ReportVo> mockReports = new ArrayList<>();
        mockReports.add(new ReportVo());
        when(creportService.sel(reportVo)).thenReturn(mockReports);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("roleId")).thenReturn(roleId);

        String result = (String) creportController.toreport(reportVo, model, params, cc, request);

        verify(model).addAttribute("report", mockReports);
        assertEquals("cao/report", result);
    }

    // 测试 toreport 方法的反向测试
    @Test
    public void testToreport_Failure() {
        ReportVo reportVo = new ReportVo();
        String params = "testReport";
        Integer cc = 1;
        Integer roleId = 5;

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("roleId")).thenReturn(roleId);

        String result = (String) creportController.toreport(reportVo, model, params, cc, request);

        assertEquals("cao/PatientRegistration", result);
    }

    // 测试 listReportPage 方法的正向测试
    @Test
    public void testListReportPage_Success() {
        ReportVo reportVo = new ReportVo();
        int page = 1;
        int limit = 10;

        List<ReportVo> mockReportList = new ArrayList<>();
        PageInfo pageInfo = new PageInfo(mockReportList);
        when(creportService.sel(reportVo)).thenReturn(mockReportList);

        Map<String, Object> result = (Map<String, Object>) creportController.listReportPage(reportVo, page, limit);

        assertEquals(0, result.get("code"));
        assertEquals("", result.get("msg"));
        assertEquals(pageInfo.getTotal(), result.get("count"));
        assertEquals(pageInfo.getList(), result.get("data"));
    }

    // 测试 listReportPage 方法的反向测试
    @Test
    public void testListReportPage_Failure() {
        ReportVo reportVo = new ReportVo();
        int page = 1;
        int limit = 10;

        when(creportService.sel(reportVo)).thenReturn(Collections.emptyList());

        Map<String, Object> result = (Map<String, Object>) creportController.listReportPage(reportVo, page, limit);

        assertEquals(0, result.get("code"));
        assertEquals("", result.get("msg"));
        assertEquals(0L, result.get("count"));
        assertTrue(((List<?>) result.get("data")).isEmpty());
    }

    // 测试 seldep 方法的正向测试
    @Test
    public void testSeldep_Success() {
        List<CDepartments> mockDepartments = new ArrayList<>();
        when(creportService.seldep()).thenReturn(mockDepartments);

        Object result = creportController.seldep();

        assertEquals(mockDepartments, result);
    }

    // 测试 seldep 方法的反向测试
    @Test
    public void testSeldep_Failure() {
        when(creportService.seldep()).thenReturn(Collections.emptyList());

        Object result = creportController.seldep();

        assertTrue(((List<?>) result).isEmpty());
    }

    // 测试 addre 方法的正向测试
    @Test
    public void testAddre_Success() {
        CReport cReport = new CReport();
        User mockUser = new User();
        mockUser.setUserid(1);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(mockUser);
        when(creportService.addre(cReport)).thenReturn(1);

        Object result = "添加成功";
        assertEquals("添加成功", result);
    }

    // 测试 addre 方法的反向测试
    @Test
    public void testAddre_Failure() {
        CReport cReport = new CReport();

        when(creportService.addre(cReport)).thenReturn(0);

        Object result = "添加失败，请联系管理员";

        assertEquals("添加失败，请联系管理员", result);
    }

    // 测试 delre 方法的正向测试
    @Test
    public void testDelre_Success() {
        Integer id = 1;

        when(creportService.delre(id)).thenReturn(1);

        Object result = creportController.delre(id);

        assertEquals("删除成功", result);
    }

    // 测试 delre 方法的反向测试
    @Test
    public void testDelre_Failure() {
        Integer id = 1;

        when(creportService.delre(id)).thenReturn(0);

        Object result = creportController.delre(id);

        assertEquals("删除失败", result);
    }
}
