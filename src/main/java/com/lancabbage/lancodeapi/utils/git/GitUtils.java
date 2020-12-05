package com.lancabbage.lancodeapi.utils.git;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: GitUtils
 * @Description:TODO (git 仓库管理工具)
 * @author: lanyanhua
 * @date: 2020/12/1 4:15 下午
 * @Copyright:
 */
public class GitUtils {

    /**
     * 远程库路径
     */
    private final String remotePath;
    /**
     * 下载已有仓库到本地路径
     */
    private final String localPath;
    /**
     * 项目名称
     */
    private final String project;
    /**
     * 分支
     */
    private final String branch;
    /**
     * 用户名
     */
    private final String username;
    /**
     * 密码
     */
    private final String password;

    private GitUtils(String remotePath, String localPath, String project, String branch, String username, String password) {
        this.remotePath = remotePath;
        this.localPath = localPath;
        this.project = project;
        this.branch = branch;
        this.username = username;
        this.password = password;
    }

    public static GitUtils getInstance(String remotePath, String localPath, String project, String branch, String username, String password){
        return new GitUtils(remotePath,localPath,project,branch,username,password);
    }

    public void cloneCode() throws GitAPIException {
        //设置远程服务器上的用户名和密码
        UsernamePasswordCredentialsProvider usernamePasswordCredentialsProvider = new
                UsernamePasswordCredentialsProvider(username, password);

        //克隆代码库命令
        CloneCommand cloneCommand = Git.cloneRepository();

        Git git = cloneCommand.setURI(remotePath) //设置远程URI
                .setBranch(branch) //设置clone下来的分支
                .setDirectory(new File(localPath + project + "/" + branch)) //设置下载存放路径
                .setCredentialsProvider(usernamePasswordCredentialsProvider) //设置权限验证
                .call();
        System.out.print(git.tag());
    }


    /**
     * 拉取远程仓库内容到本地
     */
    public void pull() throws IOException, GitAPIException {
        UsernamePasswordCredentialsProvider usernamePasswordCredentialsProvider = new
                UsernamePasswordCredentialsProvider(username, password);
        //git仓库地址
        Git git = new Git(new FileRepository(localPath + project + "/" + branch + "/.git"));
        PullResult call = git.pull().setRemoteBranchName(branch).
                setCredentialsProvider(usernamePasswordCredentialsProvider).call();

    }

    /**
     * 获取java文件路径
     * 项目分支下所有的java文件
     */
    public List<String> getJavaFile() throws IOException {
        String basePath = localPath + "/" + project + "/" + branch;
        File file = new File(basePath);
        return getJavaFile(file);
    }

    /**
     * 获取java文件路径
     *
     * @param file 文件夹
     */
    public static List<String> getJavaFile(File file) throws IOException {
        List<String> sources = new ArrayList<>();

        File[] list = file.listFiles();
        if (list == null || list.length == 0) {
            return sources;
        }
        for (File value : list) {

            if (value.isDirectory()) {
                sources.addAll(getJavaFile(value));
                continue;
            }
            String path = value.getCanonicalPath();
            if (path.endsWith(".java")) {
                sources.add(path);
            }
        }
        return sources;
    }


}
