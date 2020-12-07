package com.lancabbage.lancodeapi.map;

import com.lancabbage.lancodeapi.bean.po.EnvInfo;
import com.lancabbage.lancodeapi.bean.vo.env.EnvAddVo;
import com.lancabbage.lancodeapi.bean.vo.env.EnvSaveVo;
import org.mapstruct.Mapper;

/**
 * @author: lanyanhua
 * @date: 2020/12/7 1:13 下午
 * @Description:
 */
@Mapper(componentModel = "spring")
public interface EnvInfoDtoToVo {
    EnvInfo envAddVoToPo(EnvAddVo addVo);

    EnvInfo envSaveVoToPo(EnvSaveVo saveVo);
}
