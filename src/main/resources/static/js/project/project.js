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

//保存项目
function saveProject(fun) {
    //监听提交
    form.on('submit(projectFormBtn)', function (data) {
        NProgress.start();
        $.ajax({
            type: 'post',
            url: data.field.id ? saveProjectUrl : addProjectUrl,
            data: JSON.stringify(data.field),
            contentType: "application/json;charset=utf-8",
            dataType: 'json',
            error: ajaxError,
            success: function (data) {
                NProgress.done();
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
function addProjectBranch(projectId, name, fun) {
    NProgress.start();
    $.ajax({
        type: 'post',
        url: addProjectBranchUrl,
        data: JSON.stringify({projectId: projectId, name: name}),
        contentType: "application/json;charset=utf-8",
        dataType: 'json',
        error: ajaxError,
        success: function (data) {
            NProgress.done();
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
    NProgress.start();
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

function deleteBranch(id){
    deleteFun(deleteBranchByIdUrl,id,dataTable);
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
            , {field: 'remotePath', title: 'git地址',width: 280}
            , {field: 'branchList', title: '分支',width: 200, templet: '#branch-template'}
            , {field: 'port', title: '端口', width: 100}
            , {field: 'contextPath', title: '上下文路径', width: 160}
            , {fixed: 'right', width: 200, align: 'center', toolbar: '#project-toolbar'}
        ]],
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
                addProjectBranch(data.id, value, function () {
                    layer.closeAll();
                    dataTable.reload({});
                })
            });
        }else if (layEvent === 'edit') {
            addProjectBtn(data);
        }else if (layEvent === 'delete') {
            deleteFun(deleteByIdUrl,id,dataTable);
        }
    });
}

function addProjectBtn(data) {
    if (data == null) {
        data = {id: '', name: '', remotePath: '', header: '', branchName: ''}
        $projectForm.find('[name=branchName]').parents('.layui-form-item').show();
    }else {
        $projectForm.find('[name=branchName]').parents('.layui-form-item').hide();
    }
    $projectForm.find('[name=id]').val(data.id);
    $projectForm.find('[name=name]').val(data.name);
    $projectForm.find('[name=remotePath]').val(data.remotePath);
    $projectForm.find('[name=contextPath]').val(data.contextPath);
    $projectForm.find('[name=port]').val(data.port);

    layer.open({
        title: '添加项目'
        , type: 1
        , area: ['50%', '400px']
        , content: $projectForm
        , btn: []
    });
    $projectForm.removeClass("layui-hide");
}