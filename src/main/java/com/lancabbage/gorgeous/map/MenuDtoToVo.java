package com.lancabbage.gorgeous.map;

import com.lancabbage.gorgeous.bean.dto.MenuDto;
import com.lancabbage.gorgeous.bean.po.Menu;
import com.lancabbage.gorgeous.bean.vo.menu.MenuVo;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author: lanyanhua
 * @date: 2020/12/5 3:48 下午
 * @Description:
 */
@Mapper(componentModel = "spring")
public interface MenuDtoToVo {
    List<MenuVo> listMenuDtoToVo(List<MenuDto> menuList);

    List<MenuDto> listMenuToDto(List<Menu> children);

    MenuDto menuToDto(Menu menu);

}
