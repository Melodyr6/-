package com.aaa.controller;

import com.aaa.entity.Departments;
import com.aaa.entity.Doctor;
import com.aaa.entity.Paiban;
import com.aaa.entity.Registeredtype;
import com.aaa.service.DoctorService;
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

public class DoctorControllerTests {

    @InjectMocks
    private DoctorController doctorController;

    @Mock
    private DoctorService doctorService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // 测试 doctorList 方法的正向测试
    @Test
    public void testDoctorList_Success() {
        Doctor doctor = new Doctor();
        Integer page = 1;
        Integer limit = 10;
        List<Doctor> mockDoctorsList = new ArrayList<>();
        PageInfo<Doctor> pageInfo = new PageInfo<>(mockDoctorsList);

        when(doctorService.doctorList(doctor)).thenReturn(mockDoctorsList);

        Map<String, Object> result = (Map<String, Object>) doctorController.doctorList(doctor, page, limit);

        assertEquals(0, result.get("code"));
        assertEquals("", result.get("msg"));
        assertEquals(pageInfo.getTotal(), result.get("count"));
        assertEquals(pageInfo.getList(), result.get("data"));
    }

    // 测试 doctorList 方法的反向测试
    @Test
    public void testDoctorList_Failure() {
        Doctor doctor = new Doctor();
        Integer page = 1;
        Integer limit = 10;

        when(doctorService.doctorList(doctor)).thenReturn(Collections.emptyList());

        Map<String, Object> result = (Map<String, Object>) doctorController.doctorList(doctor, page, limit);

        assertEquals(0, result.get("code"));
        assertEquals("", result.get("msg"));
        assertEquals(0L, result.get("count"));
        assertTrue(((List<?>) result.get("data")).isEmpty());
    }

    // 测试 addDoctor 方法的正向测试
    @Test
    public void testAddDoctor_Success() {
        Doctor doctor = new Doctor();
        doctor.setDoctorName("New Doctor");
        Paiban paiban = new Paiban();
        when(doctorService.count(doctor)).thenReturn(0);
        when(doctorService.addDoctor(doctor)).thenReturn(1);

        Object result = doctorController.addDoctor(doctor, paiban);

        assertEquals("添加成功", result);
    }

    // 测试 addDoctor 方法的科室已存在情况
    @Test
    public void testAddDoctor_AlreadyExists() {
        Doctor doctor = new Doctor();
        doctor.setDoctorName("Existing Doctor");
        Paiban paiban = new Paiban();
        when(doctorService.count(doctor)).thenReturn(1);

        Object result = doctorController.addDoctor(doctor, paiban);

        assertEquals("Existing Doctor已存在", result);
    }

    // 测试 addDoctor 方法的失败情况
    @Test
    public void testAddDoctor_Failure() {
        Doctor doctor = new Doctor();
        doctor.setDoctorName("New Doctor");
        Paiban paiban = new Paiban();
        when(doctorService.count(doctor)).thenReturn(0);
        when(doctorService.addDoctor(doctor)).thenReturn(0);

        Object result = doctorController.addDoctor(doctor, paiban);

        assertEquals("添加失败", result);
    }

    // 测试 editDoctor 方法的正向测试
    @Test
    public void testEditDoctor_Success() {
        Doctor doctor = new Doctor();
        when(doctorService.editDoctor(doctor)).thenReturn(1);

        Object result = doctorController.editDoctor(doctor);

        assertEquals("修改成功", result);
    }

    // 测试 editDoctor 方法的失败测试
    @Test
    public void testEditDoctor_Failure() {
        Doctor doctor = new Doctor();
        when(doctorService.editDoctor(doctor)).thenReturn(0);

        Object result = doctorController.editDoctor(doctor);

        assertEquals("修改失败", result);
    }

    // 测试 deleteDoctor 方法的正向测试
    @Test
    public void testDeleteDoctor_Success() {
        Integer doctorId = 1;
        when(doctorService.checkCount(doctorId)).thenReturn(0);
        when(doctorService.deleteDoctor(doctorId)).thenReturn(1);

        Object result = doctorController.deleteDoctor(doctorId);

        assertEquals("删除成功", result);
    }

    // 测试 deleteDoctor 方法的病人存在的情况
    @Test
    public void testDeleteDoctor_HasPatients() {
        Integer doctorId = 1;
        when(doctorService.checkCount(doctorId)).thenReturn(1);

        Object result = doctorController.deleteDoctor(doctorId);

        assertEquals("该医生还有病人", result);
    }

    // 测试 deleteDoctor 方法的失败测试
    @Test
    public void testDeleteDoctor_Failure() {
        Integer doctorId = 1;
        when(doctorService.checkCount(doctorId)).thenReturn(0);
        when(doctorService.deleteDoctor(doctorId)).thenReturn(0);

        Object result = doctorController.deleteDoctor(doctorId);

        assertEquals("删除失败", result);
    }

    // 测试 findAllDepartments 方法的正向测试
    @Test
    public void testFindAllDepartments_Success() {
        List<Departments> mockDepartmentsList = new ArrayList<>();
        when(doctorService.findAllDepartments()).thenReturn(mockDepartmentsList);

        Object result = doctorController.findAllDepartments();

        assertEquals(mockDepartmentsList, result);
    }

    // 测试 findAllDepartments 方法的失败测试
    @Test
    public void testFindAllDepartments_Failure() {
        when(doctorService.findAllDepartments()).thenReturn(Collections.emptyList());

        Object result = doctorController.findAllDepartments();

        assertTrue(((List<?>) result).isEmpty());
    }

    // 测试 findAllRegisteredtype 方法的正向测试
    @Test
    public void testFindAllRegisteredtype_Success() {
        List<Registeredtype> mockRegisteredtypeList = new ArrayList<>();
        when(doctorService.findAllRegisteredtype()).thenReturn(mockRegisteredtypeList);

        Object result = doctorController.findAllRegisteredtype();

        assertEquals(mockRegisteredtypeList, result);
    }

    // 测试 findAllRegisteredtype 方法的失败测试
    @Test
    public void testFindAllRegisteredtype_Failure() {
        when(doctorService.findAllRegisteredtype()).thenReturn(Collections.emptyList());

        Object result = doctorController.findAllRegisteredtype();

        assertTrue(((List<?>) result).isEmpty());
    }
}
