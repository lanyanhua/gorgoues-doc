/**
 * 查询所有项目信息
 */
function projectAll(fun) {
    $.ajax({
        type: 'get',
        url: listProjectAll,
        dataType: 'json',
        success: function (data) {
            if (data.statusCode !== 200) {
                layer.msg(data.statusMsg);
                return;
            }
            if (data.data == null || data.data.length === 0) {
                //无项目信息，调整新增项目页面
                return;
            }
            fun(data.data);
        }
    })
}


//添加项目
function addProject(fun) {
    //监听提交
    form.on('submit(projectFormBtn)', function(data){
        $.ajax({
            type: 'post',
            url: addProjectUrl,
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
