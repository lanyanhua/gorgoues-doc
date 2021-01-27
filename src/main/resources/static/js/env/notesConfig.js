

function saveConfigForm(fun){
    //监听提交
    form.on('submit(saveConfigFormBtn)', function (data) {
        $.ajax({
            type: 'put',
            url: saveConfigUrl,
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

let configDataTable;
let $configForm = $('#configForm');

//项目数据表格
function renderConfigTable() {
    configDataTable = table.render({
        elem: '#config-table',
        type: 'get',
        url: listNotesConfigByTypeUrl,
        request: {},
        cols: [[
            {type: 'checkbox'}
            , {field: 'id', title: 'ID', sort: true, width: 60}
            , {field: 'type', title: '类型', width: 160}
            , {field: 'notes', title: '值', }
            , {fixed: 'right', width: 150, align: 'center', toolbar: '#config-toolbar'}
        ]],
        parseData: parseData
    });
    table.on('tool(config-table-filter)', function (obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
        let data = obj.data; //获得当前行数据
        let layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        let tr = obj.tr; //获得当前行 tr 的 DOM 对象（如果有的话）
        if (layEvent === 'edit') {
            saveConfigBtn(data);
        }
        if (layEvent === 'delete') {
            deleteFun(deleteConfigUrl,data.id,configDataTable);
        }
    });
}

function saveConfigBtn(data) {
    if (data == null) {
        data = {id: '', type: '', notes: ''}
    }
    $configForm.find('[name=id]').val(data.id);
    $configForm.find('[name=type]').val(data.type);
    $configForm.find('[name=notes]').val(data.notes);

    //渲染
    form.render('select');
    layer.open({
        title: '添加配置'
        , type: 1
        , area: ['50%', '400px']
        , content: $configForm
        , btn: []
    });
    $configForm.removeClass("layui-hide");
}
