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
    form.on('submit(projectFormBtn)', function(data){
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
                if(fun!= null ){
                    fun(data.data);
                }
            }
        })
        return false;
    });

}


function renderTable(){
    table.render({
        elem: '#project-table',
        id: "showdata",
        url: listProjectAll,
        request: {},
        cols: [[
            {type: 'checkbox'}
            , {field: 'id', title: 'ID', sort: true, width: 60}
            , {field: 'name', title: '名称' ,width: 160}
            , {field: 'remotePath', title: 'git地址'}
            , {field: 'branchList', title: '分支', templet: d=>{
                return d.branchList.map(i=>i.name).join(",")
                }}
        ]],
        done: function (res, curr, count) {
            $("#countNum").text(count);
        },
        parseData: parseData
    });
}