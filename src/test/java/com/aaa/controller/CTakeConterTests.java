package com.aaa.controller;

import com.aaa.entity.ReportVo;
import com.aaa.mapper.CTakeMapper;
import com.aaa.service.CTakeService;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageHelper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CTakeConterTests {

    @InjectMocks
    private CTakeConter cTakeConter;

    @Mock
    private CTakeService cTakeService;

    @Mock
    private CTakeMapper cTakeMapper;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    // 测试 tselperson 方法的正向测试
    @Test
    public void testTselperson_Success() {
        Integer page = 1;
        Integer limit = 10;
        List<ReportVo> mockReports = new ArrayList<>();
        PageInfo<ReportVo> pageInfo = new PageInfo<>(mockReports);

        when(cTakeService.sel()).thenReturn(mockReports);

        Map<String, Object> result = (Map<String, Object>) cTakeConter.tselperson(page, limit);

        assertEquals(0, result.get("code"));
        assertEquals("", result.get("msg"));
        assertEquals(pageInfo.getTotal(), result.get("count"));
        assertEquals(pageInfo.getList(), result.get("data"));
    }

    // 测试 tselperson 方法的反向测试
    @Test
    public void testTselperson_Failure() {
        Integer page = 1;
        Integer limit = 10;
        when(cTakeService.sel()).thenReturn(Collections.emptyList());

        Map<String, Object> result = (Map<String, Object>) cTakeConter.tselperson(page, limit);

        assertEquals(0, result.get("code"));
        assertEquals("", result.get("msg"));
        assertEquals(0L, result.get("count"));
        assertTrue(((List<?>) result.get("data")).isEmpty());
    }

    // 测试 chuku 方法的正向测试
    @Test
    public void testChuku_Success() {
        ReportVo reportVo = new ReportVo();
        Integer expectedResult = 1;

        when(cTakeService.chuku(reportVo)).thenReturn(expectedResult);

        Object result = cTakeConter.chuku(reportVo);

        assertEquals(expectedResult, result);
    }

    // 测试 haun 方法
    @Test
    public void testHaun() {
        String result = (String) cTakeConter.haun();
        assertEquals("cao/chuanzhe", result);
    }

    // 测试 selhuan 方法的正向测试
    @Test
    public void testSelhuan_Success() {
        ReportVo reportVo = new ReportVo();
        Integer page = 1;
        Integer limit = 10;
        List<ReportVo> mockReports = new ArrayList<>();
        PageInfo<ReportVo> pageInfo = new PageInfo<>(mockReports);

        when(cTakeService.selhuan(reportVo)).thenReturn(mockReports);

        Map<String, Object> result = (Map<String, Object>) cTakeConter.selhuan(reportVo, page, limit);

        assertEquals(0, result.get("code"));
        assertEquals("", result.get("msg"));
        assertEquals(pageInfo.getTotal(), result.get("count"));
        assertEquals(pageInfo.getList(), result.get("data"));
    }

    // 测试 selhuan 方法的反向测试
    @Test
    public void testSelhuan_Failure() {
        ReportVo reportVo = new ReportVo();
        Integer page = 1;
        Integer limit = 10;
        when(cTakeService.selhuan(reportVo)).thenReturn(Collections.emptyList());

        Map<String, Object> result = (Map<String, Object>) cTakeConter.selhuan(reportVo, page, limit);

        assertEquals(0, result.get("code"));
        assertEquals("", result.get("msg"));
        assertEquals(0L, result.get("count"));
        assertTrue(((List<?>) result.get("data")).isEmpty());
    }

    // 测试 zong 方法
    @Test
    public void testZong() {
        Integer reid = 1;
        int expectedTotalPrice = 100;

        when(cTakeService.zong(reid)).thenReturn(expectedTotalPrice);

        Object result = cTakeConter.zong(reid);

        assertEquals(expectedTotalPrice, result);
    }
}