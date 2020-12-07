/**
 * 查询所有环境信息
 */
function getEnvAll(fun) {
    $.ajax({
        type: 'get',
        url: listEnvAll,
        async: false,
        dataType: 'json',
        success: function (data) {
            if (data.statusCode !== 200) {
                layui.open(data.statusMsg);
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