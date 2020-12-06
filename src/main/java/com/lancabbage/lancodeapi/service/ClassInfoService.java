package com.lancabbage.lancodeapi.service;

import com.lancabbage.lancodeapi.bean.dto.ClassInfoDto;

/**
 * 类service
 * @author: lanyanhua
 * @date: 2020/12/6 4:55 下午
 * @Description:
 */
public interface ClassInfoService {
    /**
     * 保存class信息
     */
    void saveClass(ClassInfoDto classInfo);

}