/**
 * 查询所有项目信息
 */
function projectAll(fun) {
    $.ajax({
        type: 'get',
        url: listProjectAll,
        dataType: 'json',
        error: ajaxError,
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
    form.on('submit(projectFormBtn)', function (data) {
        $.ajax({
            type: 'post',
            url: addProjectUrl,
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

//添加项目分支
function addProjectBranch(projectId, name,fun) {
    $.ajax({
        type: 'post',
        url: addProjectBranchUrl,
        data: JSON.stringify({projectId: projectId, name: name}),
        contentType: "application/json;charset=utf-8",
        dataType: 'json',
        error: ajaxError,
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

function pullProjectBranch(projectId, branchId) {
    $.ajax({
        type: "POST",
        url: pullProjectBranchUrl + "?projectId=" + projectId + "&branchId=" + branchId,
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function (data) {
            if (data.statusCode !== 200) {
                layer.msg(data.statusMsg);
                return;
            }
            location.reload();
        }
    })
}

let dataTable;
let $projectForm = $('#projectForm');

//项目数据表格
function renderProjectTable() {
    dataTable = table.render({
        elem: '#project-table',
        id: "showdata",
        url: listProjectAll,
        request: {},
        cols: [[
            {type: 'checkbox'}
            , {field: 'id', title: 'ID', sort: true, width: 60}
            , {field: 'name', title: '名称', width: 160}
            , {field: 'contextPath', title: '上下文路径'}
            , {field: 'port', title: '端口'}
            , {field: 'remotePath', title: 'git地址'}
            , {
                field: 'branchList', title: '分支', templet: d => {
                    return d.branchList.map(i => i.name).join(",")
                }
            }
            , {fixed: 'right', width: 150, align: 'center', toolbar: '#project-toolbar'}
        ]],
        done: function (res, curr, count) {
            $("#countNum").text(count);
        },
        parseData: parseData
    });
    table.on('tool(project-table-filter)', function (obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
        let data = obj.data; //获得当前行数据
        let layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        let tr = obj.tr; //获得当前行 tr 的 DOM 对象（如果有的话）
        if (layEvent === 'addBranch') {
            layer.prompt({
                title: '添加分支',
                area: ['50%', '300px']
            }, function (value, index, elem) {
                addProjectBranch(data.id,value,function (){
                    layer.closeAll();
                    dataTable.reload({});
                })
            });
        }
    });
}

function addProjectBtn() {
    layer.open({
        title: '添加项目'
        , type: 1
        , area: ['50%', '300px']
        , content: $projectForm
        , btn: []
    });
    $projectForm.removeClass("layui-hide");
}