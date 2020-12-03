package com.lancabbage.lancodeapi.dto;

import com.lancabbage.lancodeapi.entity.ApiInfo;
import com.lancabbage.lancodeapi.entity.Menu;

import java.util.List;

/**
 * @ClassName: MenuDto
 * @Description:TODO ()
 * @author: lanyanhua
 * @date: 2020/12/2 6:47 下午
 * @Copyright:
 */
public class MenuDto extends Menu {

    private List<ApiInfoDto> apiInfos;

    public List<ApiInfoDto> getApiInfos() {
        return apiInfos;
    }

    public void setApiInfos(List<ApiInfoDto> apiInfos) {
        this.apiInfos = apiInfos;
    }
}
