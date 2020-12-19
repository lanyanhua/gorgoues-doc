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


//添加环境信息
function saveEnv(fun) {
    //监听提交
    form.on('submit(envFormBtn)', function (data) {
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
        return false;
    });

}



let dataTableEnv;
let $envForm = $('#envForm');

//环境数据表格
function renderEnvTable() {
    dataTableEnv = table.render({
        elem: '#env-table',
        id: "env-table",
        url: listEnvAll,
        request: {},
        cols: [[
            {type: 'checkbox'}
            , {field: 'id', title: 'ID', sort: true, width: 60}
            , {field: 'name', title: '名称', width: 160}
            , {field: 'header', title: 'header'}
            , {fixed: 'right', width: 150, align: 'center', toolbar: '#env-toolbar'}
        ]],
        done: function (res, curr, count) {
            $("#countNum").text(count);
        },
        parseData: parseData
    });
    table.on('tool(env-table-filter)', function (obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
        let data = obj.data; //获得当前行数据
        let layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        let tr = obj.tr; //获得当前行 tr 的 DOM 对象（如果有的话）
        if (layEvent === 'edit') {
            debugger
            openEnvForm(data);
        }
    });
}

function openEnvForm(data) {
    if(data == null){
        data ={id: '', name :'', domain :'', header :'',}
    }
    $envForm.find('[name=id]').val(data.id);
    $envForm.find('[name=name]').val(data.name);
    $envForm.find('[name=domain]').val(data.domain);
    $envForm.find('[name=header]').val(data.header);
    $envForm.removeClass("layui-hide");
    layer.open({
        title: '添加项目'
        , type: 1
        , area: ['50%', '300px']
        , content: $envForm
        , btn: []
    });
}