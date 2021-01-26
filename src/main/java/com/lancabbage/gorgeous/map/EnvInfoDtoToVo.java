package com.lancabbage.gorgeous.map;

import com.lancabbage.gorgeous.bean.po.EnvInfo;
import com.lancabbage.gorgeous.bean.vo.env.EnvSaveVo;
import org.mapstruct.Mapper;

/**
 * @author: lanyanhua
 * @date: 2020/12/7 1:13 下午
 * @Description:
 */
@Mapper(componentModel = "spring")
public interface EnvInfoDtoToVo {

    EnvInfo envSaveVoToPo(EnvSaveVo saveVo);
}
