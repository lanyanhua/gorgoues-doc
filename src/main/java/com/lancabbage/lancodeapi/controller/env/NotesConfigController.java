package com.lancabbage.lancodeapi.controller.env;

import com.lancabbage.lancodeapi.bean.po.NotesConfig;
import com.lancabbage.lancodeapi.bean.vo.base.BaseResponse;
import com.lancabbage.lancodeapi.bean.vo.env.NotesConfigSaveVo;
import com.lancabbage.lancodeapi.map.NotesConfigDtoToVo;
import com.lancabbage.lancodeapi.service.NotesConfigService;
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
     * 查询所有配置
     */
    @GetMapping("/listNotesConfigAll")
    public BaseResponse<List<NotesConfig>> listNotesConfigAll(){
        return BaseResponse.successInstance(configService.selectAll());
    }

    /**
     * 保存注释配置
     * @return ID
     */
    @PutMapping("/save")
    public BaseResponse<Integer> saveNotesConfig(@RequestBody @Valid NotesConfigSaveVo saveVo){
        NotesConfig config = configDtoToVo.notesConfigSaveVoToPo(saveVo);
        int id =configService.saveNotesConfig(config);
        return BaseResponse.successInstance(id);
    }
}
