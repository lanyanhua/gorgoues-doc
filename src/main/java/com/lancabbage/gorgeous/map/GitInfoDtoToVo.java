package com.lancabbage.gorgeous.map;

import com.lancabbage.gorgeous.bean.po.GitInfo;
import com.lancabbage.gorgeous.bean.vo.git.GitInfoSaveVo;
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
