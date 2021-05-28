//获取接口路径
function getPath(path, env) {
    //不传环境信息就取全局
    env = env || currProjectData.env;
    let domain = pathFormat(env.domain);
    if (env.isPort && currProjectData.project.port) {
        domain = domain + ':' + currProjectData.project.port;
    }
    if (env.isContextPath && currProjectData.project.contextPath != null) {
        domain = domain + '/' + pathFormat(currProjectData.project.contextPath);
    }
    path = pathFormat(path);
    return domain + '/' + path;
}

function pathFormat(s) {
    if (s.startsWith("/")) {
        s = s.substr(1);
    }
    if (s.endsWith("/")) {
        s = s.substr(0, s.length - 1);
    }
    return s;
}

/**
 * 接口提交方法
 * @param id
 */
function commitApi(id) {
    NProgress.start();
    let $api = $('.api-tab-content-id-' + id);
    let type = $api.find('.apiType').val();
    let url = $api.find('.apiPath').val();
    // header
    let headers = {
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Methods': 'GET,POST,PUT,DELETE',
    };
    //遍历tr
    $.each($api.find('.req-param-header tr'), (i, v) => {
        v = $(v);
        let enable = v.find('input[name="enable"]');
        let headerKey = v.find('input[name="headerKey"]').val();
        let headerValue = v.find('input[name="headerValue"]').val();
        //启用 && 值不为空
        if (enable.length > 0 && enable[0].checked && headerValue) {
            headers[headerKey] = headerValue;
        }
    })
    //form data
    let isFile = false;
    let formData = new FormData();
    $.each($api.find('.req-param-form-data tr'), (i, v) => {
        v = $(v);
        let enable = v.find('input[name="enable"]');
        let paramName = v.find('input[name="paramName"]').val();
        let paramValue = v.find('input[name="paramValue"]');
        if (enable.length < 1 || !enable[0].checked || paramValue.length < 1) {
            return true;
        }
        //参数类型 为文件
        let paramType = v.find('[name="paramType"]').val();
        if (paramType == 'File') {
            isFile = true;
            for (let i = 0; i < paramValue[0].files.length; i++) {
                formData.append(paramName, paramValue[0].files[i]);
            }
            return true;
        }
        paramValue = paramValue.val();
        //参数传输方式
        let paramMode = v.attr('data-paramMode');
        if (paramMode == ParamMode.path) {
            url = url.replace('{' + paramName + '}', paramValue);
            return true;
        }
        if (url.indexOf("?") === -1) {
            url += '?';
        }
        url += paramName + '=' + encodeURI(paramValue) + '&';
    })
    //json
    let contentType;
    let data = $api.find('.req-param-json').val();
    if (data) {
        try {
            let jsonObj = JSON.parse(data);
            data = JSON.stringify(jsonObj);
        } catch (e) {
            console.log('JSON 字符串格式不对～')
        }
        contentType = 'application/json;charset=utf-8'
    }
    //回调函数
    let fun = function (data) {
        // debugger
        //最好这里成判断ajax返回的格式（下载文件）
        let $response = $api.find('.responseJson');
        $response.text(formatJson1(data));
        NProgress.done();
    }
    //上传文件参数
    let file = {
        data: formData,
        processData: false,
        contentType: false
    }
    let ajaxParam = {
        type: type,
        url: url,
        headers: headers,
        contentType: contentType,
        xhrFields: {withCredentials: true},
        data: data,
        dataType: 'json',
        error: fun,
        success: fun,
    }
    if (isFile) {
        ajaxParam = $.extend(ajaxParam, file);
    }
    $.ajax(ajaxParam);
}

/**
 * 添加from data tr
 */
function addFromDataTr() {
    let $table = $(this.event.currentTarget).parents('table');
    $table.append('<tr data-paramMode="0">' +
        '    <td><input type="checkbox" name="enable" lay-skin="switch" checked="checked"></td>' +
        '    <td><input class="layui-input param-value" api-paramMode="" api-type=""' +
        '               name="paramName" type="text" value=""/>' +
        '    </td>' +
        '    <td><input class="layui-input param-value" api-paramMode="" api-type=""' +
        '               name="paramValue" type="text" value=""/>' +
        '    </td>' +
        '    <td>' +
        '        <select class="input-sm" name="paramType">' +
        '            <option value="Text">Text</option>' +
        '            <option value="File">File</option>' +
        '        </select>' +
        '    </td>' +
        '    <td>' +
        '    </td>' +
        '</tr>');
    form.render();
}

/**
 * 添加Header data tr
 */
function addHeaderTr() {
    let $table = $(this.event.currentTarget).parents('table');
    $table.append('<tr class="header-data-key">' +
        '    <td><input type="checkbox" name="enable" lay-skin="switch" checked="true"></td>' +
        '    <td><input class="layui-input header-key" ' +
        '               name="headerKey" type="text" value=""/>' +
        '    </td>' +
        '    <td><input class="layui-input header-value" ' +
        '               name="headerValue" type="text" value=""/>' +
        '    </td>' +
        '</tr>');
    form.render();
}

/**
 * 环境选择时更新header data值
 */
function envSelectHeaderData() {
    form.on('select(envApiFilter)', function (data) {
        console.log('api env select ', data.value)
        let $env = $(data.elem);
        let $apiPath = $env.parent().next().find('.apiPath');
        //当前选择的环境
        let currId = $env.data('id');
        let currEnv = envData.find(i => i.id == data.value);
        //获取访问路径
        let path = getPath($apiPath.data('path'), currEnv);
        $apiPath.val(path);
        //处理环境header data
        let reqDiv = 'tab-req-param-' + currId;
        element.tabChange(reqDiv, 'header');
        let $header = $('.' + reqDiv + ' .req-param-header');
        //展示在前面
        let $headerTr = $header.find('tr:eq(0)');
        $.each((currEnv.headerMap || []), (i, v) => {
            let $key = $header.find('.header-data-' + v.key);
            if ($key.length > 0) {
                $headerTr = $key;
                $key.find('.header-value').val(v.value)
            } else {
                $headerTr.after(
                    ' <tr class="header-data-' + v.key + '">' +
                    '    <td><input type="checkbox" name="enable" lay-skin="switch" checked="true"></td>' +
                    '    <td><input class="layui-input header-key" api-paramMode="" api-type=""' +
                    '               name="headerKey" type="text" value="' + v.key + '"/>' +
                    '    </td>' +
                    '    <td><input class="layui-input header-value" api-paramMode="" api-type=""' +
                    '               name="headerValue" type="text" value="' + v.value + '"/>' +
                    '    </td>' +
                    '</tr>');
                $headerTr = $header.find('.header-data-' + v.key);
                form.render('checkbox');
            }
        });
    });
}