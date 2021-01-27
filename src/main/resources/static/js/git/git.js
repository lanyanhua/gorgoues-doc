/**
 * 获取git信息
 */
function getGitInfo(fun) {
    $.ajax({
        type: 'get',
        url: getGitInfoUrl,
        dataType: 'json',
        success: function (data) {
            if (data.statusCode !== 200) {
                layer.msg(data.statusMsg);
                return;
            }
            $('#gitForm [name=repositoryPath]').val(data.data.repositoryPath);
            $('#gitForm [name=username]').val(data.data.username);
            $('#gitForm [name=password]').val('想不到吧');

            if (fun != null) {
                fun(data.data);
            }
        }
    })
}

//保存git信息
function uploadBean() {
    //监听提交
    let formData = new FormData();
    let $beanForm = $('#beanForm');
    let v = $beanForm.find('[name=bean]')[0];
    for (let i = 0; i < v.files.length; i++) {
        formData.append("bean", v.files[i]);
    }
    formData.append("path", $beanForm.find('[name=path]').val());
    $.ajax({
        type: 'post',
        url: uploadBeanUrl,
        data: formData,
        processData: false,
        contentType: false,
        dataType: 'json',
        success: function (data) {
            layer.msg(data.statusMsg);
        }
    })

}
