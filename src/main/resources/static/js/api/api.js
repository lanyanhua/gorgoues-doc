//获取接口路径
function getPath(path) {
    let domain = currProjectData.env.domain;
    if (domain.endsWith("/")) {
        domain = domain.substr(0, domain.length - 1);
    }
    if (path.startsWith("/")) {
        return domain + path;
    }
    return domain + '/' + path;
}

//参数变更事件
function apiParamChange() {

}


function commitApi(id) {
    console.log(id)
    let $api = $('.api-tab-content-id-' + id);
    let url = $api.find('.api-path').text();
    // header
    let headers = {};
    $.each($api.find('.env-header'), (i, v) => {
        v = $(v);
        let key = v.attr('header-key');
        headers[key] = v.text();
    })
    //json
    let data = $api.find('.req-json').text();
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
            return;
        }
        let type = v.attr('api-type');
        if (type == 'file') {
            isFile = true;
            val = v[0].files
        }
        formData.append(name, val);
    })
    let file = {
        data: formData,
        processData: false,
        contentType: false
    }
    let ajaxParam = {
        type: $api.find('.apiType').text(),
        url: url,
        headers: headers,
        data: param_value.length > 0 ? formData : data,
        dataType: 'json',
        success: function (data) {

        },
        error: function (data) {

        }
    }
    if (isFile) {
        ajaxParam = $.extend(ajaxParam, file);
    }
    debugger
    // $.ajax(ajaxParam);
}

