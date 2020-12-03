package com.lancabbage.lancodeapi.utils;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.IOException;

/**
 * @ClassName: GitUtils
 * @Description:TODO (git 仓库管理工具)
 * @author: lanyanhua
 * @date: 2020/12/1 4:15 下午
 * @Copyright:
 */
public class GitUtils {

    public String remotePath = "https://gitee.com/lanyanhua/timed_task_management_pingt.git";//远程库路径
    public String localPath = "/Users/lanyanhua/Desktop/gittest/";//下载已有仓库到本地路径
    public String project = "lan-job";
    public String branch = "master";
    public String username = "lanyanhua1024@163.com";
    public String password = "zxcvbnm5306";//下载已有仓库到本地路径

    public void cloneCode() throws GitAPIException {
        //设置远程服务器上的用户名和密码
        UsernamePasswordCredentialsProvider usernamePasswordCredentialsProvider =new
                UsernamePasswordCredentialsProvider(username,password);

        //克隆代码库命令
        CloneCommand cloneCommand = Git.cloneRepository();

        Git git= cloneCommand.setURI(remotePath) //设置远程URI
                .setBranch(branch) //设置clone下来的分支
                .setDirectory(new File(localPath+project+"/"+branch)) //设置下载存放路径
                .setCredentialsProvider(usernamePasswordCredentialsProvider) //设置权限验证
                .call();
        System.out.print(git.tag());
    }


    /**
     * 拉取远程仓库内容到本地
     */
    public void pull() throws IOException, GitAPIException {
        UsernamePasswordCredentialsProvider usernamePasswordCredentialsProvider =new
                UsernamePasswordCredentialsProvider(username,password);
        //git仓库地址
        Git git = new Git(new FileRepository(localPath+project+"/"+branch+"/.git"));
        git.pull().setRemoteBranchName(branch).
                setCredentialsProvider(usernamePasswordCredentialsProvider).call();
    }

}
