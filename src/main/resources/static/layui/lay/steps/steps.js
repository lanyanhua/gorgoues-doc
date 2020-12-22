layui.link(document.currentScript.src + '/../steps.css');
layui.define(['jquery'], function (exports) {
    'use strict';
    var $ = layui.jquery;
    var obj = {
        data: [],
        ele: null,
        current: 0,
        render: function (config) {
            this.ele = config.ele;
            this.data = config.data;
            this.current = config.current || 0;
            this.make();
        },
        make: function () {
            var data = this.data;
            var current = this.current;
            var html = '', data_length = data.length, percentage = 100 / (data_length + 0.85);
            html = '<div class="step-item" style="width: ' + (percentage * 0.85) + '%;"></div>';
            current = current || 0;
            for (var i = 0; i < data_length; i++) {
                var icon = '', tail = '';
                if (i < current) {
                    icon = 'layui-icon-ok';
                    tail = 'step-item-tail-done';
                } else if (i == current) {
                    icon = 'layui-icon-loading';
                    tail = '';
                }
                var temp = '<div class="step-item" style="width: ' + percentage + '%;">';
                if (parseInt(i) + 1 < data_length) {
                    temp += '<div class="step-item-tail"><i class="' + tail + '"></i></div>';
                }
                if (icon) {
                    if (i == current) {
                        temp += '<div class="step-item-head layui-anim layui-anim-rotate layui-anim-loop"><i class="layui-icon ' + icon + '"></i></div>';
                    } else {
                        temp += '<div class="step-item-head"><i class="layui-icon ' + icon + '"></i></div>';
                    }
                } else {
                    temp += '<div class="step-item-head step-item-head-active"><i class="layui-icon">' + (parseInt(i) + 1) + '</i></div>'
                }
                var desc = data[i].desc ? '<div class="step-item-main-desc">' + data[i].desc + '</div>' : '';
                temp += '<div class="step-item-main"><div class="step-item-main-title">' + data[i].title + '</div>' + desc + '</div></div>';

                html += temp;
            }
            $(this.ele).empty().append(html);
        },
        next: function (i) {
            i = i || 1;
            this.current += i;
            if (this.current >= this.data.length) {
                this.current = this.data.length-1;
                return;
            }
            if (this.current < 0) {
                this.current = 0;
                return;
            }
            $('.steps-plant').addClass("layui-hide")
            $('.steps-plant-' + this.current).removeClass("layui-hide");
            this.make();
        },
        show: function (index) {
            this.current = index;
            $('.steps-plant').addClass("layui-hide")
            $('.steps-plant-' + this.current).removeClass("layui-hide");
            this.make();
        }
    };
    exports('steps', obj);
});