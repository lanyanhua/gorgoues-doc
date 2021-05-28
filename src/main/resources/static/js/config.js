//菜单列表
let listMenuByBranchId = context + 'menu/listMenuByBranchId';
let listMenuTest = context + 'js/lancode.json';


//所有项目信息
let listProjectAll = context + 'project/listProjectAll';
//添加项目
let addProjectUrl = context + 'project/addProject';
//保存项目
let saveProjectUrl = context + 'project/saveProject';
//查询项目配置信息
let listProjectConfigByIdUrl = context + 'project/listProjectConfigById';
//删除项目
let deleteByIdUrl = context + 'project/deleteById';
//添加分支信息
let addProjectBranchUrl = context + 'branch/addProjectBranch';
//更新分支信息
let pullProjectBranchUrl = context + 'branch/pullProjectBranch';
//删除分支信息
let deleteBranchByIdUrl = context + 'branch/deleteBranchById';

//保存环境数据
let saveEnvUrl = context + 'env/saveEnv';
//删除环境信息
let deleteEnvUrl = context + 'env/deleteEnvById';
//所有环境数据
let listEnvAll = context + 'env/listEnvAll';

//查询所有配置
let listNotesConfigAllUrl = context + 'config/listNotesConfigAll';
//查询配置
let listNotesConfigByTypeUrl = context + 'config/listNotesConfigByType';
//删除配置
let deleteConfigUrl = context + 'config/delete';
//保存注释配置
let saveConfigUrl = context + 'config/save';


//获取git信息
let getGitInfoUrl = context + 'git/getGitInfo';
//保存git信息
let gitSaveUrl = context + 'git/save';
//上传类
let uploadBeanUrl = context + 'git/uploadBean';


/**
 * API类型
 * @type {{"0": string, "1": string, "2": string, "3": string, "4": string}}
 */
let ApiType = {
    0: 'ALL',
    1: 'POST',
    2: 'GET',
    3: 'DELETE',
    4: 'PUT',
};
/**
 * 基本数据类型
 */
let BaseType = {
    "String": 'text',
    "Object": 'text',
    "byte": 'text',
    "Byte": 'text',
    "short": 'text',
    "Short": 'text',
    "int": 'text',
    "Integer": 'text',
    "long": 'text',
    "Long": 'text',
    "double": 'text',
    "Double": 'text',
    "float": 'text',
    "Float": 'text',
    "char": 'text',
    "Char": 'text',
    "BigDecimal": 'text',
    "boolean": 'text',
    "Boolean": 'text',
    "Date": 'text',
    "MultipartFile": 'file',
    "URL": 'text',
}

//类型是否是数组
function isArr(type) {
    return type.indexOf('[]') !== -1;
}

/**
 * 参数类型
 */
let ParamMode = {
    form_data: 0,
    json: 1,
    path: 2,
};

/**
 * 获取当前项目配置
 */
function getCurrProject() {
    let temp = localStorage.getItem("CurrProject");
    return temp != null ? JSON.parse(temp) : null;
}

/**
 * 设置当前项目ID
 */
function setCurrProject(projectId, branchId, envId) {
    currProject = {projectId: projectId, branchId: branchId, envId: envId};
    localStorage.setItem("CurrProject", JSON.stringify(currProject));
    return currProject;
}

/**
 * 设置当前项目数据
 */
function setCurrProjectData(currProject) {
    if (currProject == null) {
        setCurrProjectData0()
        return;
    }
    //项目
    let project = projectData.find(i => i.id == currProject.projectId);
    if (project == null) {
        setCurrProjectData0()
        return;
    }
    //分支 环境
    let branch = project.branchList.find(i => i.id == currProject.branchId);
    let env = envData.find(i => i.id == currProject.envId);
    if (branch == null || env == null) {
        setCurrProjectData0()
        return;
    }
    let projectConfig = [];
    //项目配置
    getProjectConfig(currProject.projectId, data => projectConfig=data)
    currProjectData = {
        project: project,
        projectConfig: projectConfig,
        branch: branch,
        env: env,
    }
}

function setCurrProjectData0() {
    currProjectData = {
        project: projectData[0],
        branch: projectData[0].branchList[0],
        env: envData[0],
    }
    setCurrProject(currProjectData.project.id, currProjectData.branch.id, currProjectData.env.id);
}

/**
 * 获取当前选中抬头菜单
 */
function getCurrHeaderMenuId(branchId) {
    let temp = localStorage.getItem("CurrHeaderMenuId_" + branchId);
    return temp != null ? Number(temp) : null;
}

/**
 * 设置当前抬头菜单
 */
function setCurrHeaderMenuId(branchId, menuId) {
    localStorage.setItem("CurrHeaderMenuId_" + branchId, menuId);
    return menuId;
}


/**
 * 获取环境配置
 */
function getEnvMap() {
    let temp = localStorage.getItem("EnvMap");
    return temp != null ? JSON.parse(temp) : {};
}

/**
 * 设置当前抬头菜单
 */
function setEnvMap(envValue) {
    let envMap = getEnvMap();
    $.extend(envMap, envValue);
    localStorage.setItem("EnvMap", JSON.stringify(envMap));
    return envMap;
}


/**
 * 获取当前打开的菜单
 */
function getCurrMenuId(branchId) {
    let temp = localStorage.getItem("CurrMenuId_" + branchId);
    return temp != null ? Number(temp) : null;
}

/**
 * 设置当前打开的菜单
 */
function setCurrMenuId(branchId, id) {
    localStorage.setItem("CurrMenuId_" + branchId, id);
}

/**
 * 格式化json
 */
function formatJson1(json) {
    if (!json) {
        return "";
    }
    if (typeof json == 'string') {
        json = JSON.parse(json);
    }
    return JSON.stringify(json, null, 2)
}

/**
 * 格式化json
 */
function formatJson(json) {
    let outStr = '',     //转换后的json字符串
        padIdx = 0,         //换行后是否增减PADDING的标识
        space = '    ';   //4个空格符
    if (typeof json !== 'string') {
        json = JSON.stringify(json);
    }
    json = json
        .replace(/([\{\[])/g, ' $1\r\n')
        .replace(/([\}\]])/g, '\r\n$1')
        .replace(/(\,)/g, '$1\r\n')
        .replace(/:/g, ': ')
        .replace(/(\r\n\r\n)/g, '\r\n');
    (json.split('\r\n')).forEach(function (node, index) {
        let indent = 0,
            padding = '';
        if (node.match(/[\{\[]/)) {
            indent = 1;
        } else if (node.match(/[\}\]]/)) {
            padIdx = padIdx !== 0 ? --padIdx : padIdx;
        } else {
            indent = 0;
        }
        for (let i = 0; i < padIdx; i++) {
            padding += space;
        }
        outStr += padding + node + '\r\n';
        padIdx += indent;
    });
    return outStr;
}

function ajaxError(data) {
    NProgress.done();
    layer.msg(data.responseJSON.message);
}

function parseData(data) {
    return {
        'code': data.statusCode == 200 ? 0 : 1,
        'msg': data.statusMsg,
        'data': data.data
    };
}

function deleteFun(url, id, dataTable) {
    layer.confirm('确定要删除吗？', {btn: ['确定']}, () => {
        $.ajax({
            type: 'delete',
            url: url,
            data: {id: id},
            dataType: 'json',
            error: ajaxError,
            success: function (data) {
                if (data.statusCode !== 200) {
                    layer.msg(data.statusMsg);
                    return;
                }
                layer.closeAll();
                dataTable.reload();
            }
        })
    })
}