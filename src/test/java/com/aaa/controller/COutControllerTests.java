package com.aaa.controller;

import com.aaa.entity.CCashier;
import com.aaa.entity.CPharmacy;
import com.aaa.entity.Coutpatienttype;
import com.aaa.entity.ReportVo;
import com.aaa.service.COutService;

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
public class COutControllerTests {

    @InjectMocks
    private COutController cOutController;

    @Mock
    private COutService cOutService;

    @Mock
    private Model model;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testXiang() {
        String result = (String) cOutController.xiang(model);
        assertEquals("cao/Cxiangmu", result);
    }

    @Test
    public void testLook() {
        String result = (String) cOutController.look(model);
        assertEquals("cao/Ctoll", result);
    }

    @Test
    public void testSeloutSuccess() {
        Coutpatienttype coutpatienttype = new Coutpatienttype();
        Integer page = 1, limit = 10;
        String projectName = "TestProject";
        List<CPharmacy> cPharmacyList = Arrays.asList(new CPharmacy(), new CPharmacy());

        when(cOutService.selout(coutpatienttype)).thenReturn(cPharmacyList);

        Map<String, Object> result = (Map<String, Object>) cOutController.seldrug(coutpatienttype, page, limit, projectName);

        assertEquals(0, result.get("code"));
        assertEquals("", result.get("msg"));
        assertEquals(cPharmacyList.size(), ((List<?>) result.get("data")).size());
        verify(cOutService, times(1)).selout(coutpatienttype);
    }

    @Test
    public void testSeloutFailure() {
        Coutpatienttype coutpatienttype = new Coutpatienttype();
        Integer page = 1, limit = 10;
        String projectName = "TestProject";

        when(cOutService.selout(coutpatienttype)).thenReturn(null);

        Map<String, Object> result = (Map<String, Object>) cOutController.seldrug(coutpatienttype, page, limit, projectName);

        assertEquals(0, result.get("code"));
        assertEquals("", result.get("msg"));
        assertNull(result.get("data"));
        verify(cOutService, times(1)).selout(coutpatienttype);
    }

    @Test
    public void testSelchuo() {
        CCashier cCashier = new CCashier();
        Integer reid = 1;
        String mename = "TestDrug";

        when(cOutService.selchuo(cCashier)).thenReturn(1);

        Integer result = (Integer) cOutController.seslchuo(cCashier, reid, mename);

        assertEquals(Integer.valueOf(1), result);
        verify(cOutService, times(1)).selchuo(cCashier);
    }

    @Test
    public void testAddchuo() {
        CCashier cCashier = new CCashier();
        Integer ostate = 1;

        when(cOutService.addchuo(cCashier)).thenReturn(1);

        Integer result = (Integer) cOutController.addchuo(cCashier, ostate);

        assertEquals(Integer.valueOf(1), result);
        verify(cOutService, times(1)).addchuo(cCashier);
    }

    @Test
    public void testUpdchuo() {
        CCashier cCashier = new CCashier();
        CPharmacy cPharmacy = new CPharmacy();

        when(cOutService.updchuo(cCashier)).thenReturn(1);

        Integer result = (Integer) cOutController.updchuo(cCashier, cPharmacy);

        assertEquals(Integer.valueOf(1), result);
        verify(cOutService, times(1)).updchuo(cCashier);
    }

    @Test
    public void testDelSuccess() {
        CCashier cCashier = new CCashier();

        when(cOutService.delo(cCashier)).thenReturn(1);

        String result = (String) cOutController.del(cCashier);

        assertEquals("删除成功", result);
        verify(cOutService, times(1)).delo(cCashier);
    }

    @Test
    public void testDelFailure() {
        CCashier cCashier = new CCashier();

        when(cOutService.delo(cCashier)).thenReturn(0);

        String result = (String) cOutController.del(cCashier);

        assertEquals("删除失败", result);
        verify(cOutService, times(1)).delo(cCashier);
    }

    @Test
    public void testSelch() {
        CCashier cCashier = new CCashier();

        when(cOutService.selch(cCashier)).thenReturn(100.0);

        Double result = (Double) cOutController.selch(cCashier);

        assertEquals(Double.valueOf(100.0), result);
        verify(cOutService, times(1)).selch(cCashier);
    }

    @Test
    public void testShoufei() {
        ReportVo reportVo = new ReportVo();

        when(cOutService.shoufei(reportVo)).thenReturn(1);
        when(cOutService.guafei(reportVo)).thenReturn(1);

        Integer result = (Integer) cOutController.shoufei(reportVo);

        assertEquals(Integer.valueOf(1), result);
        verify(cOutService, times(1)).shoufei(reportVo);
        verify(cOutService, times(1)).guafei(reportVo);
    }
}
