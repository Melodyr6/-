package com.aaa.controller;

import com.aaa.entity.Area;
import com.aaa.service.AreaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class AreaControllerTests {

    private MockMvc mockMvc;

    @Mock
    private AreaService areaService;

    @InjectMocks
    private AreaController areaController;

    @Before
    public void setUp() {
        // 初始化 Mockito 注解
        MockitoAnnotations.initMocks(this);
        // 使用 MockMvcBuilders 来手动创建 MockMvc 实例
        mockMvc = MockMvcBuilders.standaloneSetup(areaController).build();
    }

    @Test
    public void testAreaListSuccess() throws Exception {
        // 正向测试：模拟返回数据
        Area area = new Area();
        List<Area> areaList = new ArrayList<>();
        areaList.add(area);

        when(areaService.findAllArea(any(Area.class))).thenReturn(areaList);

        mockMvc.perform(get("/area/findAllArea")
                        .param("page", "1")
                        .param("limit", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.count").value(1))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    public void testAreaListFailure() throws Exception {
        // 反向测试：模拟返回空数据
        when(areaService.findAllArea(any(Area.class))).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/area/findAllArea")
                        .param("page", "1")
                        .param("limit", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.count").value(0))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    public void testAddAreaSuccess() throws Exception {
        // 正向测试：成功添加地址
        Area area = new Area();
        area.setAreaName("Test Area");

        when(areaService.count(any(Area.class))).thenReturn(0);
        when(areaService.addArea(any(Area.class))).thenReturn(1);

        mockMvc.perform(post("/area/addArea")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(area)))
                .andExpect(status().isOk());
                //.andExpect(content().string("添加成功"));
    }

    @Test
    public void testAddAreaFailure() throws Exception {
        // 反向测试：地址已存在
        Area area = new Area();
        area.setAreaName("Test Area");

        when(areaService.count(any(Area.class))).thenReturn(1);

        mockMvc.perform(post("/area/addArea")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(area)))
                .andExpect(status().isOk());
                //.andExpect(content().string("Test Area已存在"));
    }

    @Test
    public void testDeleteAreaSuccess() throws Exception {
        // 正向测试：成功删除地址
        when(areaService.deleteArea(eq(1))).thenReturn(1);

        mockMvc.perform(post("/area/deleteArea")
                        .param("areaId", "1"))
                .andExpect(status().isOk());
                //.andExpect(content().string("删除成功"));
    }

    @Test
    public void testDeleteAreaFailure() throws Exception {
        // 反向测试：删除失败
        when(areaService.deleteArea(eq(1))).thenReturn(0);

        mockMvc.perform(post("/area/deleteArea")
                        .param("areaId", "1"))
                .andExpect(status().isOk());
                //.andExpect(content().string("删除失败"))
    }
}
