package com.lancabbage.lancodeapi.map;

import com.lancabbage.lancodeapi.bean.po.NotesConfig;
import com.lancabbage.lancodeapi.bean.vo.env.NotesConfigSaveVo;
import org.mapstruct.Mapper;

/**
 * @author: lanyanhua
 * @date: 2020/12/21 9:45 下午
 * @Description:
 */
@Mapper(componentModel = "spring")
public interface NotesConfigDtoToVo {
    NotesConfig notesConfigSaveVoToPo(NotesConfigSaveVo saveVo);
}
