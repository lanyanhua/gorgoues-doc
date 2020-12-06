package com.lancabbage.lancodeapi.bean.dto;

import com.lancabbage.lancodeapi.bean.po.ApiInfo;
import com.lancabbage.lancodeapi.bean.po.ApiParam;

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
