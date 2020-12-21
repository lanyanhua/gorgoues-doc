package com.lancabbage.lancodeapi.controller.env;

import com.lancabbage.lancodeapi.service.NotesConfigService;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: lanyanhua
 * @date: 2020/12/20 10:21 下午
 * @Description: 配置管理
 */
@RestController
public class NotesConfigController {

    private final NotesConfigService configService;

    public NotesConfigController(NotesConfigService configService) {
        this.configService = configService;
    }
}
