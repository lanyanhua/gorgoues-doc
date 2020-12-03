package com.lancabbage.lancodeapi.dto;

import com.lancabbage.lancodeapi.entity.ApiInfo;
import com.lancabbage.lancodeapi.entity.ApiParam;

import java.util.List;

/**
 * @ClassName: ApiInfoDto
 * @Description:TODO ()
 * @author: lanyanhua
 * @date: 2020/12/3 8:25 上午
 * @Copyright:
 */
public class ApiInfoDto extends ApiInfo {

    private List<ApiParam> apiParams;

    public List<ApiParam> getApiParams() {
        return apiParams;
    }

    public void setApiParams(List<ApiParam> apiParams) {
        this.apiParams = apiParams;
    }
}
