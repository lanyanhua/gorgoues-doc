package com.lancabbage.lancodeapi;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * JGit API测试
 */

/**
 * 批次管理
 *
 * @author baiyiran
 * @Date 2019/7/17
 */
public class gitTest {

    public String remotePath = "https://gitee.com/lanyanhua/timed_task_management_pingt.git";//远程库路径
    public String localPath = "/Users/lanyanhua/Desktop/gittest/";//下载已有仓库到本地路径
    public String project = "lan-job";
    public String branch = "master";
    public String username = "lanyanhua1024@163.com";
    public String password = "zxcvbnm5306";//下载已有仓库到本地路径
    public String initPath = "/Users/lanyanhua/Desktop/gittest/createGit";//本地路径新建
    /**
     * 克隆远程库
     * @throws IOException
     * @throws GitAPIException
     */
    @Test
    public void testClone() throws GitAPIException {
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
    @Test
    public void testPull() throws IOException, GitAPIException {

        UsernamePasswordCredentialsProvider usernamePasswordCredentialsProvider =new
                UsernamePasswordCredentialsProvider(username,password);
        //git仓库地址
        Git git = new Git(new FileRepository(localPath+project+"/"+branch+"/.git"));
        PullResult call =git.pull().setRemoteBranchName(branch).
                setCredentialsProvider(usernamePasswordCredentialsProvider).call();
        System.out.println(call);
    }

    /**
    * 本地新建仓库
    */
    @Test
    public void testCreate() throws IOException {
        //本地新建仓库地址
        Repository newRepo = FileRepositoryBuilder.create(new File(initPath + "/.git"));
        newRepo.create();
    }

    /**
    * 本地仓库新增文件
    */
    @Test
    public void testAdd() throws IOException, GitAPIException {
        File myfile = new File(localPath + "/myfile.txt");
        myfile.createNewFile();
        //git仓库地址
        Git git = new Git(new FileRepository(localPath+"/.git"));

        //添加文件
        git.add().addFilepattern("myfile").call();
    }

    /**
    * 本地提交代码
    */
    @Test
    public void testCommit() throws IOException, GitAPIException,
            JGitInternalException {
        //git仓库地址
        Git git = new Git(new FileRepository(localPath+"/.git"));
        //提交代码
        git.commit().setMessage("test jGit").call();
    }

    /**
    * push本地代码到远程仓库地址
    */
    @Test
    public void testPush() throws IOException, JGitInternalException,
            GitAPIException {

        UsernamePasswordCredentialsProvider usernamePasswordCredentialsProvider =new
                UsernamePasswordCredentialsProvider("username","password");
        //git仓库地址
        Git git = new Git(new FileRepository(localPath+"/.git"));   
        git.push().setRemote("origin").     setCredentialsProvider(usernamePasswordCredentialsProvider).call();
    }

}