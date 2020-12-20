let classTypeArrJson = {
    paramType: 0,
    typeMap: {},
    getTypeArrJson: function (param, type) {
        this.paramType = type;
        this.typeMap = {};
        return this.getParamAllClass(param);
    },
    /**
     * 获取参数class信息
     * @param param
     * @returns {{typeList: [], typeJson: string}}
     */
    getParamAllClass: function (param) {
        let typeArr = [];
        let jo = {};
        for (let p of param) {
            let value = jsonValue(p.type);
            if (p.typeId == null) {
                jo[p.paramName] = value;
                continue;
            }

            //是数组
            let b = isArr(p.type);
            let typeValue = this.typeMap[p.typeId];
            if (typeValue) {
                jo[p.paramName] = typeValue;
                if (b) {
                    p.type = typeValue;
                }
                continue;
            }
            let c = branchMenuData.classInfoList.find(i => i.id == p.typeId);
            if (b) {
                //数组使用范型
                let typeC = c.classFieldList[0];
                if (typeC.typeId == null) {
                    jo[p.paramName] = [jsonValue(typeC.type)];
                    continue;
                }
                c = branchMenuData.classInfoList.find(i => i.id == typeC.typeId);
                p.type = p.type + '[' + c.className + ']';
            }
            //记录当前类型防止互相依赖死循环
            this.typeMap[p.typeId] = p.type;
            let res = this.getParamAllClass(c.classFieldList);
            typeArr.push($.extend({type: this.paramType}, c));
            typeArr = typeArr.concat(res.typeList);
            //赋值json
            if (b) {
                jo[p.paramName] = [JSON.parse(res.typeJson)];
            } else {
                jo[p.paramName] = JSON.parse(res.typeJson);
            }
        }
        return {typeList: typeArr, typeJson: JSON.stringify(jo)};
    }
}


function jsonValue(type) {
    if (type == 'int' || type == 'Integer') {
        return 0;
    }
    if (type == 'double' || type == 'Double') {
        return 0.0;
    }
    if (type == 'boolean' || type == 'Boolean') {
        return false;
    }
    if (type == 'Date') {
        return dateFormat(new Date(), 'yyyy-MM-dd hh:mm:ss');
    }
    return type;
}

function dateFormat(d, fmt) {
    let o = {
        'M+': d.getMonth() + 1,
        'd+': d.getDate(),
        'h+': d.getHours(),
        'm+': d.getMinutes(),
        's+': d.getSeconds(),
        'q+': Math.floor((d.getMonth() + 3) / 3),
        S: d.getMilliseconds()
    }
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(
            RegExp.$1,
            (d.getFullYear() + '').substr(4 - RegExp.$1.length)
        )
    }
    for (let k in o) {
        if (new RegExp('(' + k + ')').test(fmt)) {
            fmt = fmt.replace(
                RegExp.$1,
                RegExp.$1.length === 1
                    ? o[k]
                    : ('00' + o[k]).substr(('' + o[k]).length)
            )
        }
    }
    return fmt
}