package com.lancabbage.lancodeapi.map;

import com.lancabbage.lancodeapi.bean.po.GitInfo;
import com.lancabbage.lancodeapi.bean.vo.git.GitInfoSaveVo;
import org.mapstruct.Mapper;

/**
 * @author: lanyanhua
 * @date: 2020/12/7 12:49 下午
 * @Description:
 */
@Mapper(componentModel = "spring")
public interface GitInfoDtoToVo {
    GitInfo gitInfoSaveVoToPo(GitInfoSaveVo vo);
}
