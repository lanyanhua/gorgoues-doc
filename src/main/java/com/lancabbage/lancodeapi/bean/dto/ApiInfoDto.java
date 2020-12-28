package com.lancabbage.lancodeapi.bean.dto;

import com.lancabbage.lancodeapi.bean.po.ApiInfo;

import java.util.List;
import java.util.Objects;

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
