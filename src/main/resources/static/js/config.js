//菜单列表
let listMenuByBranchId = context + 'menu/listMenuByBranchId';
let listMenuTest = context + 'js/lancode.json';


//所有项目信息
let listProjectAll = context + 'project/listProjectAll';
//添加项目
let addProjectUrl = context + 'project/addProject';


//添加环境数据
let addEnv = context + 'env/addEnv';
//保存环境数据
let saveEnvUrl = context + 'env/saveEnv';
//
let deleteEnv = context + 'env/deleteEnv';
//所有环境数据
let listEnvAll = context + 'env/listEnvAll';


//获取git信息
let getGitInfoUrl = context + 'git/getGitInfo';
//保存git信息
let gitSaveUrl = context + 'git/save';


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
    "byte": 'number',
    "Byte": 'number',
    "short": 'number',
    "Short": 'number',
    "int": 'number',
    "Integer": 'number',
    "long": 'number',
    "Long": 'number',
    "double": 'number',
    "Double": 'number',
    "float": 'number',
    "Float": 'number',
    "char": 'number',
    "Char": 'number',
    "boolean": 'text',
    "Boolean": 'text',
    "Date": 'date',
    "MultipartFile": 'file',
}

/**
 * 基本数据类型
 */
let arrayType = [
    "List", "Set", "array"
]

function isArr(type) {
    return arrayType.find(i => i == type) != null
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
    let project = projectData.find(i => i.id == currProject.projectId);
    if (project == null) {
        setCurrProjectData0()
        return;
    }
    let branch = project.branchList.find(i => i.id == currProject.branchId);
    let env = envData.find(i => i.id == currProject.envId);
    if (branch == null || env == null) {
        setCurrProjectData0()
        return;
    }
    currProjectData = {
        project: project,
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
function setCurrHeaderMenuId(menuId) {
    localStorage.setItem("CurrHeaderMenuId", menuId);
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
function getCurrMenuId() {
    let temp = localStorage.getItem("CurrMenuId");
    return temp != null ? Number(temp) : null;
}

/**
 * 设置当前打开的菜单
 */
function setCurrMenuId(id) {
    localStorage.setItem("CurrMenuId", id);
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