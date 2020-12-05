let ApiType = {
    0: 'ALL',
    1: 'POST',
    2: 'GET',
    3: 'DELETE',
    4: 'PUT',
};

$(function (){

    $.ajax({
        url: "js/lancode.json",
        type: "get",
        dataType: "json",
        success: function (data) {
            let html1 = $("#template").html();
            layui.laytpl(html1).render(data, function(html){
                $("#menu-dev").html( html)
            });
            let layFilter = $("#admin-side").attr('lay-filter');
            layui.element.render('nav', layFilter);
        }
    });

})