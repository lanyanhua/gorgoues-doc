package com.lancabbage.lancodeapi.controller.env;

import com.lancabbage.lancodeapi.bean.po.EnvInfo;
import com.lancabbage.lancodeapi.bean.vo.base.BaseResponse;
import com.lancabbage.lancodeapi.bean.vo.env.EnvSaveVo;
import com.lancabbage.lancodeapi.map.EnvInfoDtoToVo;
import com.lancabbage.lancodeapi.service.EnvInfoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: lanyanhua
 * @date: 2020/12/7 1:05 下午
 * @Description: 环境信息
 */
@RestController
@RequestMapping("/env")
public class EnvController {

    private final EnvInfoService envInfoService;
    private final EnvInfoDtoToVo envInfoDtoToVo;

    public EnvController(EnvInfoService envInfoService, EnvInfoDtoToVo envInfoDtoToVo) {
        this.envInfoService = envInfoService;
        this.envInfoDtoToVo = envInfoDtoToVo;
    }

    /**
     * 保存环境信息
     *
     * @return ID
     */
    @PutMapping("/saveEnv")
    public BaseResponse<Integer> saveEnv(@RequestBody EnvSaveVo saveVo) {
        EnvInfo envInfo = envInfoDtoToVo.envSaveVoToPo(saveVo);
        int id = envInfoService.saveEnv(envInfo);
        return BaseResponse.successInstance(id);
    }


    /**
     * 删除环境信息
     *
     * @return ID
     */
    @DeleteMapping("/deleteEnvById")
    public BaseResponse<String> deleteEnvById(@RequestParam Integer id) {
        envInfoService.deleteEnvById(id);
        return BaseResponse.successInstance("成功");
    }


    /**
     * 获取所有环境信息
     *
     * @return 环境列表
     */
    @GetMapping("/listEnvAll")
    public BaseResponse<List<EnvInfo>> listEnvAll() {
        List<EnvInfo> envInfoList = envInfoService.listEnvAll();
        return BaseResponse.successInstance(envInfoList);
    }
}
