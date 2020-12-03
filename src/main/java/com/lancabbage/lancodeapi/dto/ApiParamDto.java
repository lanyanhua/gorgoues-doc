package com.lancabbage.lancodeapi.dto;

import com.lancabbage.lancodeapi.entity.ApiParam;

/**
 * @ClassName: ApiParamDto
 * @Description:TODO ()
 * @author: lanyanhua
 * @date: 2020/12/3 8:36 上午
 * @Copyright:
 */
public class ApiParamDto extends ApiParam {

    private ClassInfoDto classInfo;

    public ClassInfoDto getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(ClassInfoDto classInfo) {
        this.classInfo = classInfo;
    }
}
