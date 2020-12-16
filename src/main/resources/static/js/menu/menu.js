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
            url: listMenuByBranchId,
            type: "get",
            data: {branchId: branchId},
            dataType: "json",
            async: false,
            error: ajaxError,
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

function findMenu(branchMenuData, id) {
    for (let m of branchMenuData) {
        if (m.id === id) {
            return m;
        }
        if (m.childrenMenu != null && m.childrenMenu.length !== 0) {
            let cm = findMenu(m.childrenMenu, id);
            if (cm != null) {
                return cm;
            }
        }
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
    element.render('nav', "");
    element.init();
}

//点击左边菜单在右边添加选项卡
function openTab(id) {
    //去重复选项卡
    let $lapi = $('.api-tab-content');
    for (let i = 0; i < $lapi.length; i++) {
        if ($lapi.eq(i).attr('api-id') == id) {
            element.tabChange("api-tab", id);
            return;
        }
    }
    //添加选项卡
    let menu = findMenu(branchMenuData.menuList, id);
    // class 入参 字段 - 出参
    let typeArr = [];
    for (let p of menu.api.apiParamList) {
        if (p.classId != null) {
            let c = branchMenuData.classInfoList.find(i => i.id == p.classId);
            p.className = c.className;
            let res = getParamAllClass(c.classFieldList, p.type);
            typeArr.push($.extend({type: p.type, paramMode: p.paramMode, typeJson: res.typeJson}, c));
            typeArr = typeArr.concat(res.typeList);
        }
    }
    let data = $.extend({typeList: typeArr}, menu);
    console.log(data);
    let apiTemplate = $("#apiTemplate").html();
    laytpl(apiTemplate).render(data, html => {
        element.tabAdd("api-tab", {
            title: menu.menuName,
            content: html,
            id: id
        });

        // 切换选项卡
        element.tabChange("api-tab", id);
    });
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
            , yes: function () {
                //获取表单内容
                let projectId = Number($('#switchForm [name=projectId]').val());
                let branchId = Number($('#switchForm [name=branchId]').val());
                let envId = Number($('#switchForm [name=envId]').val());

                //色泽当前项目 ID
                setCurrProject(projectId, branchId, envId);
                setCurrProjectData(currProject);
                //保存环境数据
                let envMap = {}
                currProjectData.env.headerMap = [];
                $('.env-id-' + envId + ' input').each((i, v) => {
                    let key = $(v).attr('key');
                    if (key != null) {
                        envMap[key] = v.value
                        currProjectData.env.headerMap.push(JSON.parse('{ "' + v.name + '" : "' + v.value + '" }'));
                        // debugger
                        //修改页面上显示的 值
                        $('.show-' + v.name).text(v.value);
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

//菜单过滤
function menuFilter(t) {
    let content = $(t).val();
    let $menu = $('#menu-div>div.layui-show');
    if (!content) {
        $menu.find('.layui-nav-item').removeClass('layui-hide').addClass('layui-show layui-nav-itemed');
        $menu.find('.layui-nav-item>.layui-nav-child>dd').removeClass('layui-hide').addClass('layui-show');
        return;
    }
    // 菜单名称 controller类名 接口名称 接口路径
    $menu.find('.layui-nav-item').each((i, v) => {
        let m = $(v);
        let a = m.children('a');
        let isShow = false;
        if( a.data('class').indexOf(content) !== -1 || a.data('name').indexOf(content) !== -1){
            m.removeClass('layui-hide').addClass('layui-show layui-nav-itemed');
            m.children('.layui-nav-child>dd').removeClass('layui-hide ').addClass('layui-show');
            return ;
        }
        for(let ci of m.find('.layui-nav-child>dd')) {
            let c = $(ci);
            let api = c.find('api');
            if (api.data('menuname').indexOf(content) !== -1 || api.data('name').indexOf(content) !== -1
                || api.data('method').indexOf(content) !== -1 || api.data('path').indexOf(content) !== -1) {
                c.removeClass('layui-hide').addClass('layui-show');
                isShow = true;
            } else {
                c.removeClass('layui-show').addClass('layui-hide');
            }
        }
        if(isShow){
            m.removeClass('layui-hide').addClass('layui-show layui-nav-itemed');
        }else{
            m.removeClass('layui-show layui-nav-itemed').addClass('layui-hide');
        }
    })
}

