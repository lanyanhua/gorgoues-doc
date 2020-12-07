//菜单列表
let listMenuByBranchId = context + 'menu/listMenuByBranchId';
let listMenuTest = context + 'js/lancode.json';


//所有项目信息
let listProjectAll = context + 'project/listProjectAll';


//添加环境数据
let addEnv = context + 'env/addEnv';
//保存环境数据
let saveEnv = context + 'env/saveEnv';
//所有环境数据
let listEnvAll = context + 'env/listEnvAll';


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
 * 获取当前项目配置
 */
function getCurrProject() {
    let temp = localStorage.getItem("CurrProject");
    return temp != null ? JSON.parse(temp) : null;
}

/**
 * 设置当前项目配置
 */
function setCurrProject(projectId, branchId, envId) {
    let temp = {projectId: projectId, branchId: branchId, envId: envId};
    localStorage.setItem("CurrProject", JSON.stringify(temp));
    return temp;
}

/**
 * 获取当前选中抬头菜单
 */
function getCurrHeaderMenuId() {
    let temp = localStorage.getItem("CurrHeaderMenuId");
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
    $.extend(envMap,envValue);
    localStorage.setItem("EnvMap", JSON.stringify(envMap));
    return envMap;
}