/**
 * 获取git信息
 */
function getGitInfo(fun) {
    $.ajax({
        type: 'get',
        url: getGitInfoUrl,
        dataType: 'json',
        success: function (data) {
            if (data.statusCode !== 200) {
                layer.msg(data.statusMsg);
                return;
            }
            $('#gitForm [name=repositoryPath]').val(data.data.repositoryPath);
            $('#gitForm [name=username]').val(data.data.username);
            $('#gitForm [name=password]').val(data.data.password);

            if(fun!= null ){
                fun(data.data);
            }
        }
    })
}
//保存git信息
function saveGit(fun) {
    //监听提交
    form.on('submit(gitFormBtn)', function(data){
        $.ajax({
            type: 'put',
            url: gitSaveUrl,
            data: JSON.stringify(data.field),
            contentType: "application/json;charset=utf-8",
            dataType: 'json',
            success: function (data) {
                if (data.statusCode !== 200) {
                    layer.msg(data.statusMsg);
                    return;
                }
                if(fun!= null ){
                    fun(data.data);
                }
            }
        })
        return false;
    });

}
