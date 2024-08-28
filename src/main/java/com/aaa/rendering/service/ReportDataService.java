package com.aaa.rendering.service;

import com.aaa.entity.ReportVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ReportDataService {
    @Autowired
    private RestTemplate restTemplate;

    public List<ReportVo> getReportData(String params, Integer cc) {
        String url = "http://business-service/cao/index?params=" + params + "&cc=" + cc;
        List<ReportVo> reportData = restTemplate.getForObject(url, List.class);
        return reportData;
    }
}
