package com.lancabbage.gorgeous.controller;

import com.github.pagehelper.PageInfo;
import com.lancabbage.gorgeous.bean.dto.MenuDto;
import com.lancabbage.gorgeous.bean.vo.base.BaseResponse;
import com.lancabbage.gorgeous.bean.vo.menu.MenuVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author: lanyanhua
 * @date: 2020/12/13 3:10 下午
 * @Description: 测试API
 */
@RestController
public class TestController  implements ITestClient{


    @PostMapping("testUpload")
    public BaseResponse<Integer> testUpload() {
        return BaseResponse.successInstance(1);
    }

    /**
     * 测试更新分支
     *
     * @return 001
     */
    @GetMapping("test01")
    public BaseResponse<Integer> test01() {
        return BaseResponse.successInstance(1);
    }

    /**
     * 测试数组参数
     *
     * @return 001
     */
    @GetMapping("testArrParam")
    public BaseResponse<Integer[]> testArrParam(Integer[] ids) {
        return BaseResponse.successInstance(ids);
    }

    /**
     * 测试数组参数Post
     *
     * @return 001
     */
    @PostMapping("testArrParamPost")
    public BaseResponse<Integer[]> testArrParamPost(@RequestBody Integer[] ids) {
        return BaseResponse.successInstance(ids);
    }

    /**
     * 测试数组参数Post
     *
     * @return 001
     */
    @GetMapping("testBaseArr")
    public BaseResponse<int[]> testBaseArr(int[] ids) {
        return BaseResponse.successInstance(ids);
    }

    /**
     * 测试数组参数复杂类型
     *
     * @return 001
     */
    @PostMapping("testArrParamPostObj")
    public BaseResponse<List<MenuVo>> testArrParamPostObj(@RequestBody List<MenuVo> ids) {
        return BaseResponse.successInstance(ids);
    }

    /**
     * 测试数组参数复杂类型
     *
     * @return 001
     */
    @PostMapping("testArrParamPostObj")
    public BaseResponse<MenuVo> testParamPostObjNoBody(MenuVo menuVo) {
        return BaseResponse.successInstance(menuVo);
    }

    /**
     * 测试文件上传
     *
     * @return 001
     */
    @PostMapping("testFile")
    public BaseResponse<String> testFile(MultipartFile[] file) {
        return BaseResponse.successInstance(file[0].getOriginalFilename());
    }


    /**
     * 测试父类字段
     *
     * @return 001
     */
    @PostMapping("testSuper")
    public BaseResponse<MenuDto> testSuper(@RequestBody MenuDto file) {
        return BaseResponse.successInstance(file);
    }


    /**
     * 测试多范型
     *
     * @return 001
     */
    @PostMapping("testDoubleParadigms")
    public BaseResponse<PageInfo<MenuDto>> testDoubleParadigms(@RequestBody List<MenuDto> menuDtos) {
        return BaseResponse.successInstance(new PageInfo<>(menuDtos));
    }
    /**
     * 测试范型不加类
     *
     * @return 001
     */
    @PostMapping("testParadigmsNotType")
    public BaseResponse<PageInfo> testParadigmsNotType(@RequestBody List menuDtos) {
        return BaseResponse.successInstance(new PageInfo(menuDtos));
    }


    @Override
    public BaseResponse<Integer> testInterface(Integer id) {
        return BaseResponse.successInstance(id);
    }
}
