package com.aaa.controller;

import com.aaa.entity.Departments;
import com.aaa.service.DepartmentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class DepartmentControllerTests {

    @InjectMocks
    private DepartmentController departmentController;

    @Mock
    private DepartmentService departmentService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // 测试 DepartmentList 方法的正向测试
    @Test
    public void testDepartmentList_Success() {
        Departments departments = new Departments();
        Integer page = 1;
        Integer limit = 10;
        List<Departments> mockDepartmentsList = new ArrayList<>();
        PageInfo<Departments> pageInfo = new PageInfo<>(mockDepartmentsList);

        when(departmentService.departmentList(departments)).thenReturn(mockDepartmentsList);

        Map<String, Object> result = (Map<String, Object>) departmentController.DepartmentList(departments, page, limit);

        assertEquals(0, result.get("code"));
        assertEquals("", result.get("msg"));
        assertEquals(pageInfo.getTotal(), result.get("count"));
        assertEquals(pageInfo.getList(), result.get("data"));
    }

    // 测试 DepartmentList 方法的反向测试
    @Test
    public void testDepartmentList_Failure() {
        Departments departments = new Departments();
        Integer page = 1;
        Integer limit = 10;

        when(departmentService.departmentList(departments)).thenReturn(Collections.emptyList());

        Map<String, Object> result = (Map<String, Object>) departmentController.DepartmentList(departments, page, limit);

        assertEquals(0, result.get("code"));
        assertEquals("", result.get("msg"));
        assertEquals(0L, result.get("count"));
        assertTrue(((List<?>) result.get("data")).isEmpty());
    }

    // 测试 addDepartment 方法的正向测试
    @Test
    public void testAddDepartment_Success() {
        Departments departments = new Departments();
        departments.setDepartment("Test Department");
        when(departmentService.count(departments)).thenReturn(0);
        when(departmentService.addDepartment(departments)).thenReturn(1);

        Object result = departmentController.addDepartment(departments);

        assertEquals("添加成功", result);
    }

    // 测试 addDepartment 方法的科室已存在的情况
    @Test
    public void testAddDepartment_AlreadyExists() {
        Departments departments = new Departments();
        departments.setDepartment("Existing Department");
        when(departmentService.count(departments)).thenReturn(1);

        Object result = departmentController.addDepartment(departments);

        assertEquals("Existing Department已存在", result);
    }

    // 测试 addDepartment 方法的失败测试
    @Test
    public void testAddDepartment_Failure() {
        Departments departments = new Departments();
        departments.setDepartment("Test Department");
        when(departmentService.count(departments)).thenReturn(0);
        when(departmentService.addDepartment(departments)).thenReturn(0);

        Object result = departmentController.addDepartment(departments);

        assertEquals("添加失败", result);
    }

    // 测试 deleteDepartment 方法的正向测试
    @Test
    public void testDeleteDepartment_Success() {
        Integer departmentId = 1;
        when(departmentService.deleteDepartment(departmentId)).thenReturn(1);

        Object result = departmentController.deleteDepartment(departmentId);

        assertEquals("删除成功", result);
    }

    // 测试 deleteDepartment 方法的失败测试
    @Test
    public void testDeleteDepartment_Failure() {
        Integer departmentId = 1;
        when(departmentService.deleteDepartment(departmentId)).thenReturn(0);

        Object result = departmentController.deleteDepartment(departmentId);

        assertEquals("删除失败", result);
    }
}
