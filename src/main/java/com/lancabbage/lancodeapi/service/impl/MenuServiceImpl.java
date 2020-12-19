package com.lancabbage.lancodeapi.service.impl;

import com.lancabbage.lancodeapi.bean.dto.ApiInfoDto;
import com.lancabbage.lancodeapi.bean.dto.MenuDto;
import com.lancabbage.lancodeapi.bean.po.ApiInfo;
import com.lancabbage.lancodeapi.bean.po.Menu;
import com.lancabbage.lancodeapi.map.MenuDtoToVo;
import com.lancabbage.lancodeapi.mapper.MenuMapper;
import com.lancabbage.lancodeapi.service.ApiInfoService;
import com.lancabbage.lancodeapi.service.MenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: lanyanhua
 * @date: 2020/12/6 12:40 上午
 * @Description:
 */
@Service
public class MenuServiceImpl implements MenuService {

    private final MenuMapper menuMapper;
    private final ApiInfoService apiInfoService;
    private final MenuDtoToVo menuDtoToVo;

    public MenuServiceImpl(MenuMapper menuMapper, ApiInfoService apiInfoService, MenuDtoToVo menuDtoToVo) {
        this.menuMapper = menuMapper;
        this.apiInfoService = apiInfoService;
        this.menuDtoToVo = menuDtoToVo;
    }

    @Transactional
    @Override
    public void addMenuList(List<MenuDto> menuDtoList, Integer projectId, Integer branchId) {
        //新增默认菜单
        Menu menu = new Menu();
        menu.setBranchId(branchId);
        menu.setMenuName("默认");
        //赋值ID
        menu.setCreateTime(new Date());
        menuMapper.insertSelective(menu);
        for (MenuDto cMenu : menuDtoList) {
            //父ID
            cMenu.setParentId(menu.getId());
            cMenu.setCreateTime(new Date());
            menuMapper.insertSelective(cMenu);
            //先保存API信息
            saveApiMenu(projectId, branchId, cMenu, cMenu.getApiInfos());
        }
    }

    @Override
    public List<MenuDto> listMenuByBranchId(Integer branchId) {
        //查询菜单数据
        Example example = new Example(Menu.class);
        example.createCriteria().andEqualTo("branchId", branchId);
        List<Menu> menuList = menuMapper.selectByExample(example);
        List<MenuDto> menuVoList = menuDtoToVo.listMenuToDto(menuList);
        //查询子级菜单
        setChildrenMenu(menuVoList);
        return menuVoList;
    }

    @Override
    public List<Menu> listApiMenuByApiId(List<Integer> aIds) {
        Example example = new Example(Menu.class);
        example.createCriteria().andIn("apiId", aIds);
        return menuMapper.selectByExample(example);
    }


    @Transactional
    @Override
    public void saveMenuList(List<MenuDto> menuDtoList, Integer projectId, Integer branchId) {
        Example example = new Example(Menu.class);
        example.createCriteria().andEqualTo("branchId", branchId)
                .andEqualTo("menuName", "默认");
        Menu menu = menuMapper.selectOneByExample(example);
        //原来的controller 菜单
        example = new Example(Menu.class);
        example.createCriteria().andEqualTo("parentId", menu.getId());
        List<Menu> menus = menuMapper.selectByExample(example);
        menuMapper.deleteByExample(example);
        //加载当前分支下所有的API API关联的菜单 class
        List<ApiInfo> apiList = apiInfoService.listApiInfoByBranchId(branchId);
        List<Integer> aIds = apiList.stream().map(ApiInfo::getId).collect(Collectors.toList());
        List<Menu> apiMenus = listApiMenuByApiId(aIds);
        //对比不相同就更新
        //主菜单 查询
        //controller 菜单 API菜单 删除
        //对现在的controller 菜单进行新增 -修改API -class -查询出现在的API菜单进行修改 从新指向 父ID为当前
        for (MenuDto cMenu : menuDtoList) {
            //父ID
            cMenu.setParentId(menu.getId());
            cMenu.setCreateTime(new Date());
            menuMapper.insertSelective(cMenu);
            //先保存API信息
            List<ApiInfoDto> apiInfos = cMenu.getApiInfos();
            if (apiInfos.isEmpty()) {
                continue;
            }

            List<ApiInfoDto> apiInfoAdds = new ArrayList<>();
            for (ApiInfoDto a : apiInfos) {
                List<ApiInfo> collect = apiList.stream()
                        .filter(i -> i.getPath().equals(a.getPath())
                                && i.getType().equals(a.getType()))
                        .collect(Collectors.toList());
                //不存在新增
                if (collect.isEmpty()) {
                    apiInfoAdds.add(a);
                    continue;
                }
                //存在修改当前API信息 已经关联的菜单信息 关联菜单是默认菜单的修改
                ApiInfo apiInfo = collect.get(0);
                // 1.当前API 名称相同（没有改过菜单）修改名称 2.同时判断是否是默认菜单修改为新菜单
                apiMenus.stream()
                        .filter(i -> i.getApiId().equals(apiInfo.getId())
                                && i.getMenuName().equals(apiInfo.getName()))
                        .peek(i -> i.setMenuName(a.getName()))
                        .filter(i -> menus.stream().anyMatch(p -> p.getId().equals(i.getParentId())))
                        .peek(i -> i.setParentId(cMenu.getId()))
                        .forEach(menuMapper::updateByPrimaryKeySelective);
                //修改API信息
                apiInfoService.updateApi(apiInfo, a);
            }
            saveApiMenu(projectId, branchId, cMenu, apiInfoAdds);
        }
    }

    /**
     * 保存API菜单
     *
     * @param projectId 项目ID
     * @param branchId  分支ID
     * @param cMenu     父ID
     * @param apiInfos  API信息
     */
    private void saveApiMenu(Integer projectId, Integer branchId, MenuDto cMenu, List<ApiInfoDto> apiInfos) {
        if (apiInfos.isEmpty()) {
            return;
        }
        apiInfoService.saveApiList(apiInfos, projectId, branchId);
        List<Menu> menuList = apiInfos.stream().map(i -> {
            Menu m = new Menu();
            m.setParentId(cMenu.getId());
            m.setCreateTime(new Date());
            m.setApiId(i.getId());
            m.setMenuName(i.getName());
            return m;
        }).collect(Collectors.toList());
        menuMapper.insertList(menuList);
    }

    /**
     * 赋值子菜单 每次加载一级的子菜单，这样递归遍历的次数是菜单的深度 减少递归查库的次数
     */
    private void setChildrenMenu(List<MenuDto> menuList) {
        //查询子菜单
        List<Integer> idList = menuList.stream().map(MenuDto::getId).collect(Collectors.toList());
        Example example = new Example(Menu.class);
        example.createCriteria().andIn("parentId", idList);
        List<Menu> menuList1 = menuMapper.selectByExample(example);
        if (menuList1.isEmpty()) {
            return;
        }
        //赋值子菜单
        List<MenuDto> childrenList = new ArrayList<>();
        for (MenuDto menu : menuList) {
            List<Menu> children = menuList1.stream().filter(i -> menu.getId().equals(i.getParentId())).collect(Collectors.toList());
            if (children.isEmpty()) {
                menu.setChildrenMenu(new ArrayList<>(0));
                continue;
            }
            List<MenuDto> menuVos = menuDtoToVo.listMenuToDto(children);
            menu.setChildrenMenu(menuVos);
            childrenList.addAll(menuVos);
            //查询API
        }
        //赋值下一级子菜单
        setChildrenMenu(childrenList);
    }
}
