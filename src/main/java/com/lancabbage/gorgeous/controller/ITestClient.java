package com.lancabbage.gorgeous.controller;

import com.lancabbage.gorgeous.bean.vo.base.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author: lanyanhua
 * @date: 2020/12/26 2:57 下午
 * @Description: feign调用service
 */
//@FeignClient(
//        value = "${feignClient.application.user}",
//        contextId = "TestService"
//)
public interface ITestClient {

    /**
     * 测试实现接口 API
     * @param id 参数
     * @return 返回值
     */
    @GetMapping("/testInterface")
    BaseResponse<Integer> testInterface(Integer id);
}
