package com.lancabbage.gorgeous.controller.classInfo;

import com.lancabbage.gorgeous.service.ClassInfoService;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: lanyanhua
 * @date: 2020/12/10 9:02 下午
 * @Description: 类信息管理
 */
@RestController
public class ClassInfoController {

    private final ClassInfoService classInfoService;

    public ClassInfoController(ClassInfoService classInfoService) {
        this.classInfoService = classInfoService;
    }

//    /**
//     * 查询类信息
//     * @param branchId 分支ID
//     * @return 类信息字段
//     */
//    @GetMapping("/listClassByBranchId")
//    public BaseResponse<ClassInfoVo> listClassByBranchId(Integer branchId){
//        return BaseResponse.successInstance(classInfoService.listClassByBranchId(branchId));
//    }

}
