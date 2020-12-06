package com.lancabbage.lancodeapi.map;

import com.lancabbage.lancodeapi.bean.dto.MenuDto;
import com.lancabbage.lancodeapi.bean.po.Menu;
import org.mapstruct.Mapper;

/**
 * @author: lanyanhua
 * @date: 2020/12/5 3:48 下午
 * @Description:
 */
@Mapper(componentModel = "spring")
public interface MenuDtoToVo {

    Menu menuDtoToPo(MenuDto menuDto);
}
