/**
 * 查询所有环境信息
 */
function getEnvAll(fun) {
    $.ajax({
        type: 'get',
        url: listEnvAll,
        async: false,
        dataType: 'json',
        error: ajaxError,
        success: function (data) {
            if (data.statusCode !== 200) {
                layer.msg(data.statusMsg);
                return;
            }
            let envMap = getEnvMap();
            $.each(data.data, (i, v) => {
                let map = [];
                if (v.header != null) {
                    v.header.split(",").forEach((h) => {
                        map.push({key: h, value: envMap['env-' + v.id + '-' + h] || ''});
                    })
                }
                v['headerMap'] = map;
            });
            fun(data.data);
        }
    })
}


//保存环境信息
function saveEnv(fun) {
    $.ajax({
        type: 'put',
        url: saveEnvUrl,
        data: JSON.stringify(data.field),
        contentType: "application/json;charset=utf-8",
        dataType: 'json',
        error: ajaxError,
        success: function (data) {
            if (data.statusCode !== 200) {
                layer.msg(data.statusMsg);
                return;
            }
            if (fun != null) {
                fun(data.data);
            }
        }
    })
}

//添加环境信息
function addEnv(fun) {
    //监听提交
    form.on('submit(envFormBtn)', function (data) {
        $.ajax({
            type: 'post',
            url: addEnvUrl,
            data: JSON.stringify(data.field),
            contentType: "application/json;charset=utf-8",
            dataType: 'json',
            error: ajaxError,
            success: function (data) {
                if (data.statusCode !== 200) {
                    layer.msg(data.statusMsg);
                    return;
                }
                if (fun != null) {
                    fun(data.data);
                }
            }
        })
        return false;
    });

}
