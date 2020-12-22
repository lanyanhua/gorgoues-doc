//获取接口路径
function getPath(path) {
    let env = currProjectData.env;
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

function commitApi(id) {
    console.log(id)
    let $api = $('.api-tab-content-id-' + id);
    let url = $api.find('.api-path').text();
    let type = $api.find('.apiType').text();
    type = type == ApiType[0] ? "post" : type;
    // header
    let headers = {};
    $.each($api.find('.env-header'), (i, v) => {
        v = $(v);
        let key = v.attr('header-key');
        headers[key] = v.text();
    })
    //json
    let data = $api.find('.req-json').val();
    //文件
    let formData = new FormData();
    let isFile = false;
    let param_value = $api.find('.param-value');

    $.each(param_value, (i, v) => {
        v = $(v);
        let name = v.attr('name');
        let val = v.val();
        let paramMode = v.attr('api-paramMode');
        if (paramMode == ParamMode.path) {
            url.replace(name, val);
            return true;
        }
        if (paramMode == ParamMode.json) {
            url.replace(name, val);
            return true;
        }
        let type = v.attr('api-type');
        if (type == 'file') {
            isFile = true;
            val = v[0].files[0];
            formData.append(name, val);
            return true;
        }
        if (url.indexOf("?") === -1) {
            url += '?';
        }
        url += name + '=' + encodeURI(val) + '&';
    })
    let file = {
        data: formData,
        processData: false,
        contentType: false
    }
    let fun = function (data) {
        let $response = $api.find('.api-response');
        $response.removeClass("layui-hide");
        $api.find('.response-path').text(url);
        $api.find('.response-show').text(formatJson1(data));
        $('.api-body').animate({
            scrollTop: 1000
        }, 0);
    }
    let ajaxParam = {
        type: type,
        url: url,
        headers: headers,
        contentType: "application/json;charset=utf-8",
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