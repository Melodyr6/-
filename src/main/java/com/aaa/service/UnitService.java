package com.aaa.service;

import com.aaa.entity.Unit;

import java.util.List;

public interface UnitService {
    //单位的增删改查
    List<Unit> findAllUnit(Unit unit);
    int deleteUnit(Integer unitId);
    int addUnit(Unit unit);
    int count(Unit unit);
}
