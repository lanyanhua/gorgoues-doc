package com.lancabbage.gorgeous.bean.dto;

import com.lancabbage.gorgeous.bean.po.ApiInfo;

import java.util.List;

/**
 * @ClassName: ApiInfoDto
 * @Description:TODO ()
 * @author: lanyanhua
 * @date: 2020/12/3 8:25 上午
 * @Copyright:
 */
public class ApiInfoDto extends ApiInfo {

    private List<ApiParamDto> apiParams;

    public List<ApiParamDto> getApiParams() {
        return apiParams;
    }

    public void setApiParams(List<ApiParamDto> apiParams) {
        this.apiParams = apiParams;
    }


}
