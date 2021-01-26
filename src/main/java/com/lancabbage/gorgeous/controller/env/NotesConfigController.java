package com.lancabbage.gorgeous.controller.env;

import com.lancabbage.gorgeous.bean.po.NotesConfig;
import com.lancabbage.gorgeous.bean.vo.base.BaseResponse;
import com.lancabbage.gorgeous.bean.vo.env.NotesConfigSaveVo;
import com.lancabbage.gorgeous.map.NotesConfigDtoToVo;
import com.lancabbage.gorgeous.service.NotesConfigService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author: lanyanhua
 * @date: 2020/12/20 10:21 下午
 * @Description: 配置管理
 */
@RestController
@RequestMapping("/config")
public class NotesConfigController {

    private final NotesConfigService configService;
    private final NotesConfigDtoToVo configDtoToVo;

    public NotesConfigController(NotesConfigService configService, NotesConfigDtoToVo configDtoToVo) {
        this.configService = configService;
        this.configDtoToVo = configDtoToVo;
    }

    /**
     * 查询配置
     * @param type 类型
     * @return 配置数据
     */
    @GetMapping("/listNotesConfigByType")
    public BaseResponse<List<NotesConfig>> listNotesConfigByType(String type){
        return BaseResponse.successInstance(configService.listNotesConfigByType(type));
    }

    /**
     * 保存注释配置
     * @return ID
     */
    @PutMapping("/save")
    public BaseResponse<Integer> saveNotesConfig(@RequestBody @Valid NotesConfigSaveVo saveVo){
        NotesConfig config = configDtoToVo.notesConfigSaveVoToPo(saveVo);
        Integer id =configService.saveNotesConfig(config);
        return BaseResponse.successInstance(id);
    }

    /**
     * 删除配置
     * @param id ID
     */
    @DeleteMapping("/delete")
    public BaseResponse<String> delete(@RequestParam Integer id){
        configService.deleteById(id);
        return BaseResponse.successInstance("成功");
    }
}
