package com.aaa.controller;

import com.aaa.entity.*;
import com.aaa.enumpakage.MessageEnum;
import com.aaa.mapper.CCashierMapper;
import com.aaa.mapper.CreportMapper;
import com.aaa.mapper.MessageMapper;
import com.aaa.mapper.UserMapper;
import com.aaa.service.CCashierService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("caocc")
public class CCashierController {
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private CCashierService cCashierService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CreportMapper creportMapper;
    @Autowired
    private CCashierMapper cCashierMapper;

    private RestTemplate restTemplate = new RestTemplate();

    public List<CWarehuose> fetchWarehouses() {
        String url = "http://drugstore:8083/caocc/selware";
        System.out.println("进入fetchWarehouses内部");
        // 创建 HTTP 实体
        HttpEntity<?> requestEntity = new HttpEntity<>(null);

        // 使用 RestTemplate 的 exchange 方法处理泛型
        ResponseEntity<List<CWarehuose>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<List<CWarehuose>>() {}
        );

        // 提取响应体
        return responseEntity.getBody();
    }

    public List<CPharmacy> fetchPharm(CPharmacy cPharmacy) {
        System.out.println("进入fetchPharm内部");
        String url = "http://drugstore:8083/caocc/selpharm";
        // 创建 HTTP 实体
        HttpEntity<?> requestEntity = new HttpEntity<>(cPharmacy);

        // 使用 RestTemplate 的 exchange 方法处理泛型
        ResponseEntity<List<CPharmacy>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<List<CPharmacy>>() {}
        );

        // 提取响应体
        return responseEntity.getBody();
    }

    public Integer fetchdel(CPharmacy cPharmacy)
    {
        System.out.println("进入fetchdel内部");
        String url = "http://drugstore:8083/caocc/deldrunum";
        // 创建 HTTP 实体
        HttpEntity<?> requestEntity = new HttpEntity<>(cPharmacy);

        // 使用 RestTemplate 的 exchange 方法处理泛型
        ResponseEntity<Integer> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<Integer>() {}
        );

        // 提取响应体
        return responseEntity.getBody();
    }

    public Integer fetchadd(CPharmacy cPharmacy)
    {
        System.out.println("进入fetchadd内部");
        String url = "http://drugstore:8083/caocc/adddrunum";
        // 创建 HTTP 实体
        HttpEntity<?> requestEntity = new HttpEntity<>(cPharmacy);

        // 使用 RestTemplate 的 exchange 方法处理泛型
        ResponseEntity<Integer> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<Integer>() {}
        );

        // 提取响应体
        return responseEntity.getBody();
    }
    //进入处方页面
    @PostMapping("cc")
    public Object look(Model model) {
        //查询所有药房
        List<CWarehuose> selware = fetchWarehouses();
        System.out.println("返回的selware为"+selware);
        model.addAttribute("selware", selware);
        return "cao/cashier";
    }

    //查询所有患者信息
    @RequestMapping("selpreson")
    @ResponseBody
    public Object selperson(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<ReportVo> sel = cCashierService.sel();
        PageInfo pageInfo = new PageInfo(sel);
        Map<String, Object> tableData = new HashMap<String, Object>();
        //这是layui要求返回的json数据格式，如果后台没有加上这句话的话需要在前台页面手动设置
        tableData.put("code", 0);
        tableData.put("msg", "");
        //将全部数据的条数作为count传给前台（一共多少条）
        tableData.put("count", pageInfo.getTotal());
        //将分页后的数据返回（每页要显示的数据）
        tableData.put("data", pageInfo.getList());
        return tableData;
    }

    //查询药品所有信息
    @RequestMapping("seldrug")
    @ResponseBody
    public Object seldrug(String durgname, Integer page, Integer limit, CPharmacy cPharmacy) {
        cPharmacy.setPharmacyName(durgname);
        PageHelper.startPage(page, limit);

        List<CPharmacy> selpharm = fetchPharm(cPharmacy);

        PageInfo pageInfo = new PageInfo(selpharm);
        Map<String, Object> tableData = new HashMap<String, Object>();
        //这是layui要求返回的json数据格式，如果后台没有加上这句话的话需要在前台页面手动设置
        tableData.put("code", 0);
        tableData.put("msg", "");
        //将全部数据的条数作为count传给前台（一共多少条）
        tableData.put("count", pageInfo.getTotal());
        //将分页后的数据返回（每页要显示的数据）
        tableData.put("data", pageInfo.getList());
        return tableData;
    }

    //查询处方中是否有这个药
    @RequestMapping("selchu")
    @ResponseBody
    public Object seslchu(CCashier cCashier, Integer reid, String mename) {
        cCashier.setReportId(reid);//把用户id存入数据库
        cCashier.setDurgname(mename);//把药品名称存入实体类
        Integer selcadr = cCashierService.selcadr(cCashier);
        return selcadr;
    }

    //添加处方药品
    @RequestMapping("addchu")
    @ResponseBody
    public Object addchu(CCashier cCashier, CPharmacy cPharmacy) {
        //向处方添加药品
        Integer addchu = cCashierService.addchu(cCashier);
        String pharmacyName = cCashier.getDurgname();
        cPharmacy.setPharmacyName(pharmacyName);
        //减少药房中的数量
        Integer deldrunum = fetchdel(cPharmacy);
        return addchu;
    }

    //查询该用户的处方
    @RequestMapping("selpepi")
    @ResponseBody
    public Object selpepi(Integer perid, Integer page, Integer limit) {
        List<CCashier> selpepi = cCashierService.selpepi(perid);
        PageHelper.startPage(page, limit);
        PageInfo pageInfo = new PageInfo(selpepi);
        Map<String, Object> tableData = new HashMap<String, Object>();
        //这是layui要求返回的json数据格式，如果后台没有加上这句话的话需要在前台页面手动设置
        tableData.put("code", 0);
        tableData.put("msg", "");
        //将全部数据的条数作为count传给前台（一共多少条）
        tableData.put("count", pageInfo.getTotal());
        //将分页后的数据返回（每页要显示的数据）
        tableData.put("data", pageInfo.getList());
        return tableData;
    }

    //删除处方中的药品
    @RequestMapping("del")
    @ResponseBody
    public Object del(CCashier cCashier, String durnme, Integer durnum, CPharmacy cPharmacy) {
        Integer del = cCashierService.del(cCashier);
        cPharmacy.setPharmacyName(durnme);
        cPharmacy.setDrugstorenum(durnum);
        Integer add = fetchadd(cPharmacy);
        if (del == 0) {
            return "删除失败";
        } else {
            return "删除成功";
        }
    }

    //如果处方中有该药品则修改该药品的数量和价钱
    @RequestMapping("updchu")
    @ResponseBody
    public Object updchu(CCashier cCashier, CPharmacy cPharmacy) {
        //修改处方中药品的数量
        Integer updchu = cCashierService.updchu(cCashier);
        String pharmacyName = cCashier.getDurgname();
        cPharmacy.setPharmacyName(pharmacyName);
        //修改仓库中药品的数量
        Integer deldrunum = fetchdel(cPharmacy);
        return updchu;
    }

    //模糊查询
    @RequestMapping("mohu")
    @ResponseBody
    public Object mohu(String durgname, ReportVo reportVo, Integer page, Integer limit) {
        reportVo.setReportName(durgname);
        PageHelper.startPage(page, limit);
        List<ReportVo> mohu = cCashierService.mohu(reportVo);
        PageInfo pageInfo = new PageInfo(mohu);
        Map<String, Object> tableData = new HashMap<String, Object>();
        //这是layui要求返回的json数据格式，如果后台没有加上这句话的话需要在前台页面手动设置
        tableData.put("code", 0);
        tableData.put("msg", "");
        //将全部数据的条数作为count传给前台（一共多少条）
        tableData.put("count", pageInfo.getTotal());
        //将分页后的数据返回（每页要显示的数据）
        tableData.put("data", pageInfo.getList());
        return tableData;
    }

    //添加用户病因
    @RequestMapping("addbing")
    @ResponseBody
    public Object addbing(Integer reid, String bing, CReport cReport) {
        cReport.setZhuan(bing);
        cReport.setReportId(reid);
        Integer addbing = cCashierService.addbing(cReport);
        return addbing;
    }

    //查询用户有没有填写病因
    @RequestMapping("selbing")
    @ResponseBody
    public Object selbing(Integer reid) {
        String selbing = cCashierService.selbing(reid);
        return selbing;
    }

    //查看用户的检查结果
    @RequestMapping("lookbing")
    @ResponseBody
    public Object lookbing(Integer reid) {
        String lookbing = cCashierService.lookbing(reid);
        return lookbing;
    }

    //查看该用户是否还有未交钱的项目
    @RequestMapping("seljiao")
    @ResponseBody()
    public Object seljiao(Integer reid) {
        int seljiao = cCashierService.seljiao(reid);
        return seljiao;
    }

    //查询该用户所有的处方
    @RequestMapping("selall")
    @ResponseBody
    public Object selall(Integer perid, Integer page, Integer limit) {
        List<CCashier> selall = cCashierMapper.selall(perid);
        PageHelper.startPage(page, limit);
        PageInfo pageInfo = new PageInfo(selall);
        Map<String, Object> tableData = new HashMap<String, Object>();
        //这是layui要求返回的json数据格式，如果后台没有加上这句话的话需要在前台页面手动设置
        tableData.put("code", 0);
        tableData.put("msg", "");
        //将全部数据的条数作为count传给前台（一共多少条）
        tableData.put("count", pageInfo.getTotal());
        //将分页后的数据返回（每页要显示的数据）
        tableData.put("data", pageInfo.getList());
        return tableData;
    }

    @RequestMapping("selectAll")
    @ResponseBody
    public Object selectAll( Integer page, Integer limit) {
        List<PrescriptionVo> selall = cCashierMapper.selectAll();
        PageHelper.startPage(page, limit);
        PageInfo pageInfo = new PageInfo(selall);
        Map<String, Object> tableData = new HashMap<String, Object>();
        //这是layui要求返回的json数据格式，如果后台没有加上这句话的话需要在前台页面手动设置
        tableData.put("code", 0);
        tableData.put("msg", "");
        //将全部数据的条数作为count传给前台（一共多少条）
        tableData.put("count", pageInfo.getTotal());
        //将分页后的数据返回（每页要显示的数据）
        tableData.put("data", pageInfo.getList());
        return tableData;
    }

    //查询用户所有的项目处方
    @RequestMapping("selximu")
    @ResponseBody
    public Object selximu(Integer perid, Integer page, Integer limit) {
        List<CCashier> selximu = cCashierService.selximu(perid);
        PageHelper.startPage(page, limit);
        PageInfo pageInfo = new PageInfo(selximu);
        Map<String, Object> tableData = new HashMap<String, Object>();
        //这是layui要求返回的json数据格式，如果后台没有加上这句话的话需要在前台页面手动设置
        tableData.put("code", 0);
        tableData.put("msg", "");
        //将全部数据的条数作为count传给前台（一共多少条）
        tableData.put("count", pageInfo.getTotal());
        //将分页后的数据返回（每页要显示的数据）
        tableData.put("data", pageInfo.getList());
        return tableData;
    }

    //查看该用户是否有缴费未做的项目
    @RequestMapping("selwei")
    @ResponseBody
    public Object selwei(Integer reid) {
        //查询该用户有几个做过的项目
        Integer selyi = cCashierService.selyi(reid);
        //查询该用户有几个缴费的项目
        Integer selgong = cCashierService.selgong(reid);
        if (selyi == selgong) {
            return 1;
        } else {
            return 0;
        }
    }

    //添加处方药品
    @RequestMapping("addPrescription")
    @ResponseBody
    public Object addPrescription(HttpServletRequest request, CCashier cCashier, CPharmacy cPharmacy) {
        User user = (User) request.getSession().getAttribute("user");

        //向处方添加药品
        Integer addchu = cCashierService.addchu(cCashier);
        String pharmacyName = cCashier.getDurgname();
        cPharmacy.setPharmacyName(pharmacyName);
        //减少药房中的数量
        Integer deldrunum = fetchdel(cPharmacy);
        List<ReportVo> reportVoList = creportMapper.selById(cCashier.getReportId());
        UserVo userVo = new UserVo();
        userVo.setRealName(reportVoList.get(0).getReportName());
        User user1 = userMapper.getUserByParam(userVo);
        Message doctorMessage = new Message();
        doctorMessage.setMessageType(MessageEnum.MESSAGE_NOTIFICATION.getMessageType());
        doctorMessage.setReceiverId(user1.getUserid());
        doctorMessage.setMessageTitle("消息提醒");
        doctorMessage.setMessageContent("患者：" + reportVoList.get(0).getReportName() + "您好，" + user.getRealname() + "给您开了药，尽快缴费！");
        messageMapper.insertMessage(doctorMessage);
        return addchu;
    }

    @RequestMapping("editPrescription")
    @ResponseBody
    public Object editPrescription(HttpServletRequest request,CCashier cCashier, CPharmacy cPharmacy) {
        User user = (User) request.getSession().getAttribute("user");

        //修改处方中药品的数量
        Integer updchu = cCashierService.updchu(cCashier);
        String pharmacyName = cCashier.getDurgname();
        cPharmacy.setPharmacyName(pharmacyName);
        //修改仓库中药品的数量
        Integer deldrunum = fetchdel(cPharmacy);
        List<ReportVo> reportVoList = creportMapper.selById(cCashier.getReportId());
        UserVo userVo = new UserVo();
        userVo.setRealName(reportVoList.get(0).getReportName());
        User user1 = userMapper.getUserByParam(userVo);
        Message doctorMessage = new Message();
        doctorMessage.setMessageType(MessageEnum.MESSAGE_NOTIFICATION.getMessageType());
        doctorMessage.setReceiverId(user1.getUserid());
        doctorMessage.setMessageTitle("消息提醒");
        doctorMessage.setMessageContent("患者：" + reportVoList.get(0).getReportName() + "您好，" + user.getRealname() + "给您开了药，尽快缴费！");
        messageMapper.insertMessage(doctorMessage);
        return updchu;
    }
}
