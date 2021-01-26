package com.lancabbage.gorgeous.bean.dto;

import com.lancabbage.gorgeous.bean.po.ApiParam;

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
