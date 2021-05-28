/**
 * 查询所有项目信息
 */
function projectAll(fun) {
    $.ajax({
        type: 'get',
        url: listProjectAll,
        async: false,
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

/**
 * 查询项目信息
 */
function getProjectConfig(id,fun) {
    $.ajax({
        type: 'get',
        url: listProjectConfigByIdUrl+"?id="+id,
        async: false,
        dataType: 'json',
        error: ajaxError,
        success: function (data) {
            if (data.statusCode !== 200) {
                layer.msg(data.statusMsg);
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
            url: addProjectUrl,
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

/**
 * 更新项目分支
 * @param projectId
 * @param branchId
 */
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

//删除分支
function deleteBranch(id) {
    deleteFun(deleteBranchByIdUrl, id, dataTable);
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
            , {field: 'remotePath', title: 'git地址', width: 280}
            , {field: 'branchList', title: '分支', width: 200, templet: d => d.branchList.map(i => i.name)}
            , {
                field: 'projectConfigs', title: '项目配置(模块名｜端口｜上下文路径)', templet: d => {
                    let s = '<table class="layui-table layui-form" style="width:97%;margin-left: 5px;" lay-size="sm">';
                    d.projectConfigs.forEach(v => {
                        s += '<tr>' +
                            '     <td>' + (v.name || v.menuName) + '</td>' +
                            '     <td>' + (v.port || '无') + '</td>' +
                            '     <td>' + (v.contextPath || '无') + '</td>' +
                            '</tr>';
                    })
                    s += '</table>'
                    return s;
                }
            }
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
                NProgress.start();
                addProjectBranch(data.id, value, function () {
                    NProgress.done();
                    layer.closeAll();
                    dataTable.reload({});
                })
            });
        } else if (layEvent === 'edit') {
            editProject(data);
        } else if (layEvent === 'delete') {
            deleteFun(deleteByIdUrl, data.id, dataTable);
        }
    });
}

/**
 * 添加项目
 */
function addProjectBtn() {
    $projectForm.find('[name=name]').val('');
    $projectForm.find('[name=remotePath]').val('');
    $projectForm.find('[name=branchName]').val('');
    layer.open({
        title: '添加项目'
        , type: 1
        , area: ['50%', '400px']
        , content: $projectForm
        , btn: []
    });
    $projectForm.removeClass("layui-hide");
}

/**
 * 编辑项目
 * @param data
 */
function editProject(data){
    let projectEditTemplate = $("#projectEditTemplate").html();
    console.log(data);
    laytpl(projectEditTemplate).render(data, html => {
        //弹框
        layer.open({
            title: '切换'
            , area: ['50%', '450px']
            , content: html
            , btn: ["提交"]
            , yes: function () {
                NProgress.start();
                //获取表单内容
                let $form = $('#projectEditForm');
                let data = {
                    id : $form.find('[name=id]').val(),
                    name : $form.find('[name=projectName]').val(),
                    remotePath : $form.find('[name=remotePath]').val(),
                    projectConfigs: []
                }
                $.each($form.find('.projectConfigTable tr:gt(0)'),(i,v)=>{
                    v = $(v);
                    data.projectConfigs.push({
                        id: v.attr('data-id'),
                        menuName: v.find('[name=menuName]').val(),
                        name: v.find('[name=name]').val(),
                        port: v.find('[name=port]').val(),
                        contextPath: v.find('[name=contextPath]').val(),
                    })
                });
                $.ajax({
                    type: 'post',
                    url: saveProjectUrl,
                    data: JSON.stringify(data),
                    contentType: "application/json;charset=utf-8",
                    dataType: 'json',
                    error: ajaxError,
                    success: function (data) {
                        NProgress.done();
                        if (data.statusCode !== 200) {
                            layer.msg(data.statusMsg);
                            return;
                        }
                        layer.closeAll();
                        dataTable.reload();
                    }
                })
            }
        });
    });
}