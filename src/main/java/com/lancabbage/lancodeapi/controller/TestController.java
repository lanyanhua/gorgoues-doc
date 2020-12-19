package com.lancabbage.lancodeapi.controller;

import com.lancabbage.lancodeapi.bean.vo.base.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: lanyanhua
 * @date: 2020/12/13 3:10 下午
 * @Description: 测试API
 */
@RestController
public class TestController {


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
     * 测试文件上传
     *
     * @return 001
     */
    @GetMapping("testFile")
    public BaseResponse<String> testFile(MultipartFile file) {

        return BaseResponse.successInstance(file.getOriginalFilename());
    }

}
