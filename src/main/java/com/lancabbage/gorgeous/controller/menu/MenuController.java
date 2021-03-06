package com.lancabbage.gorgeous.controller.menu;

import com.lancabbage.gorgeous.bean.dto.MenuDto;
import com.lancabbage.gorgeous.bean.vo.api.ApiInfoVo;
import com.lancabbage.gorgeous.bean.vo.base.BaseResponse;
import com.lancabbage.gorgeous.bean.vo.classInfo.ClassInfoVo;
import com.lancabbage.gorgeous.bean.vo.menu.BranchMenuVo;
import com.lancabbage.gorgeous.bean.vo.menu.MenuVo;
import com.lancabbage.gorgeous.map.MenuDtoToVo;
import com.lancabbage.gorgeous.service.ApiInfoService;
import com.lancabbage.gorgeous.service.ClassInfoService;
import com.lancabbage.gorgeous.service.MenuService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 菜单管理
 *
 * @author: lanyanhua
 * @date: 2020/12/6 6:30 下午
 * @Description:
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    private final MenuService menuService;
    private final ApiInfoService apiInfoService;
    private final ClassInfoService classInfoService;
    private final MenuDtoToVo menuDtoToVo;

    public MenuController(MenuService menuService, ApiInfoService apiInfoService, ClassInfoService classInfoService, MenuDtoToVo menuDtoToVo) {
        this.menuService = menuService;
        this.apiInfoService = apiInfoService;
        this.classInfoService = classInfoService;
        this.menuDtoToVo = menuDtoToVo;
    }

    /**
     * 查询菜单
     *
     * @param branchId 分支ID
     * @return 菜单列表 API数据
     */
    @GetMapping("/listMenuByBranchId")
    public BaseResponse<BranchMenuVo> listMenuByBranchId(@RequestParam Integer branchId) {
        BranchMenuVo branchMenuVo = new BranchMenuVo();
        //菜单
        List<MenuDto> menuList = menuService.listMenuByBranchId(branchId);
        List<MenuVo> menuVos = menuDtoToVo.listMenuDtoToVo(menuList);
        branchMenuVo.setMenuList(menuVos);
        //API
        List<ApiInfoVo> apiInfoVos = apiInfoService.listApiByBranchId(branchId);
        branchMenuVo.setApiInfoList(apiInfoVos);
        //class
        List<ClassInfoVo> classInfoVos = classInfoService.listClassByBranchId(branchId);
        branchMenuVo.setClassInfoList(classInfoVos);
        return BaseResponse.successInstance(branchMenuVo);
    }




}
