package com.aaa.controller;

import com.aaa.entity.*;
import com.aaa.service.CCashierService;
import com.github.pagehelper.PageInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class CCashierControllerTests {

    @InjectMocks
    private CCashierController cCashierController;

    @Mock
    private CCashierService cCashierService;

    @Mock
    private Model model;

    @Mock
    private HttpServletRequest request;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLook() {
        List<CWarehuose> selware = Arrays.asList(new CWarehuose(), new CWarehuose());
        when(cCashierService.selware()).thenReturn(selware);

        Object result = cCashierController.look(model);

        assertEquals("cao/cashier", result);
        verify(model, times(1)).addAttribute("selware", selware);
    }

    @Test
    public void testSelperson() {
        Integer page = 1, limit = 10;
        List<ReportVo> reportVos = Arrays.asList(new ReportVo(), new ReportVo());
        PageInfo<ReportVo> pageInfo = new PageInfo<>(reportVos);
        when(cCashierService.sel()).thenReturn(reportVos);

        Object result = cCashierController.selperson(page, limit);

        assertNotNull(result);
        verify(cCashierService, times(1)).sel();
    }

    @Test
    public void testSeldrug() {
        String durgname = "Paracetamol";
        Integer page = 1, limit = 10;
        List<CPharmacy> pharmacies = Arrays.asList(new CPharmacy(), new CPharmacy());
        PageInfo<CPharmacy> pageInfo = new PageInfo<>(pharmacies);
        CPharmacy cPharmacy = new CPharmacy();
        cPharmacy.setPharmacyName(durgname);
        when(cCashierService.selpharm(any(CPharmacy.class))).thenReturn(pharmacies);

        Object result = cCashierController.seldrug(durgname, page, limit, cPharmacy);

        assertNotNull(result);
        verify(cCashierService, times(1)).selpharm(any(CPharmacy.class));
    }

    @Test
    public void testSeslchu() {
        CCashier cCashier = new CCashier();
        Integer reid = 1;
        String mename = "Paracetamol";
        when(cCashierService.selcadr(cCashier)).thenReturn(1);

        Object result = cCashierController.seslchu(cCashier, reid, mename);

        assertEquals(1, result);
        verify(cCashierService, times(1)).selcadr(cCashier);
    }

    @Test
    public void testAddchu() {
        CCashier cCashier = new CCashier();
        CPharmacy cPharmacy = new CPharmacy();
        when(cCashierService.addchu(cCashier)).thenReturn(1);
        when(cCashierService.deldrunum(cPharmacy)).thenReturn(1);

        Object result = cCashierController.addchu(cCashier, cPharmacy);

        assertEquals(1, result);
        verify(cCashierService, times(1)).addchu(cCashier);
        verify(cCashierService, times(1)).deldrunum(cPharmacy);
    }

    @Test
    public void testSelpepi() {
        Integer perid = 1;
        Integer page = 1, limit = 10;
        List<CCashier> cCashiers = Arrays.asList(new CCashier(), new CCashier());
        when(cCashierService.selpepi(perid)).thenReturn(cCashiers);

        Object result = cCashierController.selpepi(perid, page, limit);

        assertNotNull(result);
        verify(cCashierService, times(1)).selpepi(perid);
    }

    @Test
    public void testDel() {
        CCashier cCashier = new CCashier();
        String durnme = "Paracetamol";
        Integer durnum = 1;
        CPharmacy cPharmacy = new CPharmacy();
        when(cCashierService.del(cCashier)).thenReturn(1);

        Object result = cCashierController.del(cCashier, durnme, durnum, cPharmacy);

        assertEquals("删除成功", result);
        verify(cCashierService, times(1)).del(cCashier);
    }

    @Test
    public void testUpdchu() {
        CCashier cCashier = new CCashier();
        CPharmacy cPharmacy = new CPharmacy();
        when(cCashierService.updchu(cCashier)).thenReturn(1);
        when(cCashierService.deldrunum(cPharmacy)).thenReturn(1);

        Object result = cCashierController.updchu(cCashier, cPharmacy);

        assertEquals(1, result);
        verify(cCashierService, times(1)).updchu(cCashier);
        verify(cCashierService, times(1)).deldrunum(cPharmacy);
    }

    @Test
    public void testMohu() {
        String durgname = "Paracetamol";
        Integer page = 1, limit = 10;
        List<ReportVo> reportVos = Arrays.asList(new ReportVo(), new ReportVo());
        when(cCashierService.mohu(any(ReportVo.class))).thenReturn(reportVos);

        Object result = cCashierController.mohu(durgname, new ReportVo(), page, limit);

        assertNotNull(result);
        verify(cCashierService, times(1)).mohu(any(ReportVo.class));
    }

    @Test
    public void testAddbing() {
        Integer reid = 1;
        String bing = "Flu";
        CReport cReport = new CReport();
        when(cCashierService.addbing(cReport)).thenReturn(1);

        Object result = cCashierController.addbing(reid, bing, cReport);

        assertEquals(1, result);
        verify(cCashierService, times(1)).addbing(cReport);
    }

    @Test
    public void testSelbing() {
        Integer reid = 1;
        when(cCashierService.selbing(reid)).thenReturn("Flu");

        Object result = cCashierController.selbing(reid);

        assertEquals("Flu", result);
        verify(cCashierService, times(1)).selbing(reid);
    }

    @Test
    public void testLookbing() {
        Integer reid = 1;
        when(cCashierService.lookbing(reid)).thenReturn("Flu");

        Object result = cCashierController.lookbing(reid);

        assertEquals("Flu", result);
        verify(cCashierService, times(1)).lookbing(reid);
    }

    @Test
    public void testSeljiao() {
        Integer reid = 1;
        when(cCashierService.seljiao(reid)).thenReturn(1);

        Object result = cCashierController.seljiao(reid);

        assertEquals(1, result);
        verify(cCashierService, times(1)).seljiao(reid);
    }

    @Test
    public void testSelall() {
        Integer perid = 1;
        Integer page = 1, limit = 10;
        List<CCashier> cCashiers = Arrays.asList(new CCashier(), new CCashier());
//        when(cCashierMapper.selall(perid)).thenReturn(cCashiers);

//        Object result = cCashierController.selall(perid, page, limit);

//        assertNotNull(result);
//        verify(cCashierMapper, times(1)).selall(perid);
    }

    @Test
    public void testSelectAll() {
        Integer page = 1, limit = 10;
        List<PrescriptionVo> prescriptions = Arrays.asList(new PrescriptionVo(), new PrescriptionVo());
//        when(cCashierMapper.selectAll()).thenReturn(prescriptions);

//        Object result = cCashierController.selectAll(page, limit);

//        assertNotNull(result);
//        verify(cCashierMapper, times(1)).selectAll();
    }

    @Test
    public void testSelximu() {
        Integer perid = 1;
        Integer page = 1, limit = 10;
//        List<CCheck> cChecks = Arrays.asList(new CCheck(), new CCheck());
//        when(cCashierMapper.selximu(perid)).thenReturn(cChecks);

        Object result = cCashierController.selximu(perid, page, limit);

        assertNotNull(result);
//        verify(cCashierMapper, times(1)).selximu(perid);
    }
}
