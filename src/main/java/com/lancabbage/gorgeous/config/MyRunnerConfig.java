package com.lancabbage.gorgeous.config;

import com.lancabbage.gorgeous.utils.doc.NotesConfigUtils;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 运行配置
 */
@Component
@Order(2)
public class MyRunnerConfig implements CommandLineRunner {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final GitInfoConfig gitInfoConfig;
    private final DataSource dataSource;

    public MyRunnerConfig(GitInfoConfig gitInfoConfig, DataSource dataSource) {
        this.gitInfoConfig = gitInfoConfig;
        this.dataSource = dataSource;
    }


    @Override
    public void run(String... args) throws Exception {
        gitInfoConfig();
        datasource(args);
    }

    private void gitInfoConfig() {
        log.info("--开始检测git配置信息--");
        Assert.isTrue(!StringUtils.hasLength(gitInfoConfig.getUsername()), "git username为空，请检测application.properties");
        Assert.isTrue(!StringUtils.hasLength(gitInfoConfig.getPassword()), "git password为空，请检测application.properties");
    }

    private void datasource(String... args) throws Exception {
        log.info("--开始执行数据库初始化脚本--");
        for (String arg : args) {
            if (arg.indexOf("--") != 0) {
                continue;
            }
            String key = arg.substring(2, arg.indexOf("="));
            String value = arg.substring(arg.indexOf("=") + 1);
            log.info("key:{},value:{}", key, value);
            if ("gorgeous-database".equals(key)) {
                ScriptRunner runner = new ScriptRunner(dataSource.getConnection());
                runner.setAutoCommit(true);
                runner.setStopOnError(true);
                InputStream file = new FileInputStream(value);
                InputStreamReader isr = new InputStreamReader(file);
                runner.runScript(isr);
            }
        }
        log.info("--刷新注释配置信息--");
        NotesConfigUtils.refresh();
    }
}