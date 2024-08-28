package com.aaa.controller;

import com.aaa.entity.Lcheckup;
import com.aaa.service.CheckupService;
import com.github.pagehelper.PageInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class CheckupControllerTests {

    @InjectMocks
    private CheckupController checkupController;

    @Mock
    private CheckupService checkupService;

    @Mock
    private Model model;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCheckup() {
        String result = checkupController.checkup();
        assertEquals("liao/checkup", result);
    }

    @Test
    public void testAddCheckupSuccess() {
        Lcheckup lcheckup = new Lcheckup();
        when(checkupService.addCheckup(lcheckup)).thenReturn(1);

        Object result = checkupController.addCheckup(lcheckup);

        assertNull(result);
        verify(checkupService, times(1)).addCheckup(lcheckup);
        verify(checkupService, times(1)).updPrice(lcheckup);
    }

    @Test
    public void testAddCheckupFailure() {
        Lcheckup lcheckup = new Lcheckup();
        when(checkupService.addCheckup(lcheckup)).thenReturn(0);

        Object result = checkupController.addCheckup(lcheckup);

        assertEquals("添加异常", result);
        verify(checkupService, times(1)).addCheckup(lcheckup);
        verify(checkupService, times(0)).updPrice(lcheckup);
    }

    @Test
    public void testSelCheckupSuccess() {
        Integer page = 1, limit = 10;
        Lcheckup lcheckup = new Lcheckup();
        List<Lcheckup> lcheckups = Arrays.asList(new Lcheckup(), new Lcheckup());
        PageInfo<Lcheckup> pageInfo = new PageInfo<>(lcheckups);

        when(checkupService.selCheckup(lcheckup)).thenReturn(lcheckups);

        Map<String, Object> result = (Map<String, Object>) checkupController.selCheckup(page, limit, lcheckup);

        assertEquals(0, result.get("code"));
        assertEquals("", result.get("msg"));
        assertEquals(pageInfo.getTotal(), result.get("count"));
        assertEquals(pageInfo.getList(), result.get("data"));
        verify(checkupService, times(1)).selCheckup(lcheckup);
    }

    @Test
    public void testSelCheckupFailure() {
        Integer page = 1, limit = 10;
        Lcheckup lcheckup = new Lcheckup();

        when(checkupService.selCheckup(lcheckup)).thenReturn(null);

        Map<String, Object> result = (Map<String, Object>) checkupController.selCheckup(page, limit, lcheckup);

        assertEquals(0, result.get("code"));
        assertEquals("", result.get("msg"));
        assertEquals(0L, result.get("count"));
        assertEquals(null, result.get("data"));
        verify(checkupService, times(1)).selCheckup(lcheckup);
    }
}
