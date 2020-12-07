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
                    layui.open(data.statusMsg);
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

//显示菜单
function viewMenu(branchId) {
    //加载当前
    getMenuByBranchId(branchId);
    let currHeaderMenuId = getCurrHeaderMenuId();
    if (currHeaderMenuId == null) {
        currHeaderMenuId = setCurrHeaderMenuId(branchMenuData.menuList[0].id);
    }
    let menuTemplate = $("#menuTemplate").html();
    let data = $.extend({currHeaderMenuId: currHeaderMenuId},currProjectData, branchMenuData);
    console.log(data);
    laytpl(menuTemplate).render(data, (html) => $("#body").html(html));
    // let layFilter = $("#admin-side").attr('lay-filter');
    layui.element.render('nav', "");
}
//切换菜单
function switchMenu(id){
    setCurrHeaderMenuId(id);
    $('#menu-div .layui-side').addClass('layui-hide');
    $('#menu-dev-'+id).removeClass('layui-hide');
}

function openSwitch(){
    // 切换项目 分支
    let data = $.extend({},currProject, {projectList: projectData},{envList:envData});
    console.log(data);
    let switchTemplate = $("#switchTemplate").html();
    laytpl(switchTemplate).render(data, html => {
        layer.open({
            title: '切换'
            ,area: ['50%', '450px']
            ,content: html
        });
        layui.form.render();
    });

}