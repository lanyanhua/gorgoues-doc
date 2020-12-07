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
                layui.open(data.statusMsg);
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

