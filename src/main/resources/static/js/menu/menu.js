let branchMenuData;
let currBranchId;

/**
 * 获取分支菜单
 * @param branchId
 */
function getMenuByBranchId(branchId) {
    if (branchMenuData == null || currBranchId !== branchId) {
        //从新加载
        currBranchId = branchId;
        $.ajax({
            // url: listMenuTest,
            url: listMenuByBranchId,
            type: "get",
            data: {branchId: branchId},
            dataType: "json",
            async: false,
            success: function (data) {
                if (data.statusCode !== 200) {
                    layer.msg(data.statusMsg);
                    return;
                }
                branchMenuData = data.data;
                setApi(branchMenuData.menuList);
            }
        });
    }
}

//设置菜单API
function setApi(menuList) {
    $.each(menuList, (i, o) => {
        if (o.apiId != null) {
            o['api'] = branchMenuData.apiInfoList.find(api => api.id === o.apiId)
        }
        if (o.childrenMenu != null && o.childrenMenu.length !== 0) {
            setApi(o.childrenMenu);
        }
    });
}

/**
 * 渲染菜单
 */
function renderMenu() {
    //加载当前
    getMenuByBranchId(currProject.branchId);
    let currHeaderMenuId = getCurrHeaderMenuId(currProject.branchId);
    if (currHeaderMenuId == null) {
        currHeaderMenuId = setCurrHeaderMenuId(branchMenuData.menuList[0].id);
    }
    let menuTemplate = $("#menuTemplate").html();
    let data = $.extend({currHeaderMenuId: currHeaderMenuId}, currProjectData, branchMenuData);
    console.log(data);
    //渲染
    laytpl(menuTemplate).render(data, (html) => $("#body").html(html));
    //事件
    layui.element.render('nav', "");
}

//切换菜单
function switchMenu(id) {
    setCurrHeaderMenuId(id);
    $('#menu-div .layui-side').addClass('layui-hide');
    $('#menu-dev-' + id).removeClass('layui-hide');
}

/**
 * 切换
 */
function openSwitch() {
    // 切换项目 分支 环境
    let data = $.extend({}, currProject, {projectList: projectData}, {envList: envData});
    console.log(data);
    let switchTemplate = $("#switchTemplate").html();
    laytpl(switchTemplate).render(data, html => {
        //弹框
        layer.open({
            title: '切换'
            , area: ['50%', '450px']
            , content: html
            , btn: ["提交"]
            , yes: function (index, layero) {
                //获取表单内容
                let projectId = Number($('#switchForm [name=projectId]').val());
                let branchId =  Number($('#switchForm [name=branchId]').val());
                let envId = Number( $('#switchForm [name=envId]').val());

                //色泽当前项目 ID
                setCurrProject(projectId, branchId, envId);
                setCurrProjectData(currProject);
                //保存环境数据
                let envMap = {}
                currProjectData.env.headerMap = [];
                $('.env-id-' + envId+' input').each((i,v)=>{
                    let key = $(v).attr('key');
                    if(key != null){
                        envMap[key] = v.value
                        currProjectData.env.headerMap.push(JSON.parse('{ "'+v.name+'" : "'+ v.value+'" }'));
                    }
                });
                setEnvMap(envMap);
                //渲染菜单
                renderMenu();
                layer.closeAll();
            }
        });
        //渲染
        layui.form.render();
        //绑定联动
        form.on('select(projectIdFilter)', function (data) {
            $('.branch-div').addClass('layui-hide');
            $('.branch-id-' + data.value).removeClass('layui-hide');
        });
        form.on('select(envIdFilter)', function (data) {
            $('.env-div').addClass('layui-hide');
            $('.env-id-' + data.value).removeClass('layui-hide');
        });
    });

}