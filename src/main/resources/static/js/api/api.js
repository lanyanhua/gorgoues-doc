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
            return;
        }
        let type = v.attr('api-type');
        if (type == 'file') {
            isFile = true;
            debugger
            val = v[0].files[0];
            formData.append(name, val);
            return;
        }
        if (url.indexOf("?") === -1) {
            url += '?';
        }
        url += name + '=' + val + '&';
    })
    let file = {
        data: formData,
        processData: false,
        contentType: false
    }
    let fun = function (data) {
        $api.find('.api-response').removeClass("layui-hide");
        $api.find('.response-path').text(url);
        $api.find('.response-show').text(formatJson1(data));
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
    debugger
    $.ajax(ajaxParam);


}

function test() {
    $.ajax({
        type: 'post'
        ,
        url: 'https://test.jaagro.com:9030/externalApi/schemeReportExcel'
        ,
        headers: {token: 'eyJ0eXBlIjoiSldUIiwiYWxnIjoiSFMyNTYifQ.eyJ1c2VyVHlwZSI6ImVtcGxveWVlIiwidXNlciI6IjEiLCJpYXQiOjE2MDY5NjE3MTl9.ZiD_iVKXqyLUECsX5zi8Nmn4zK8siQDreZ_AkwcA34g'}
        ,
        contentType: 'application/json;charset=UTF-8'
        ,
        dataType: 'json'
        ,
        data: JSON.stringify({
            "startTime": "2020-12-01 00:00:00",
            "endTime": "2021-01-30 00:00:00",
            "adminPassword": "124"
        })
        ,
        success: data => {
            console.log('success')
            console.log(data)
        }
        ,
        error: data => {
            console.log('error')
            console.log(data)
        }
    })
}