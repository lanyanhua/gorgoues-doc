let classTypeArrJson = {
    paramType: null,
    paramMode: null,
    typeMap: {},
    getTypeArrJson: function (c, param) {
        this.paramType = param.type;
        this.paramMode = param.paramMode;
        this.typeMap = {};
        return this.getParamAllClass(c);
    },
    /**
     * 获取参数class信息
     */
    getParamAllClass: function (c) {
        let typeArr = [];
        let jo = {};
        //数组
        if (isArr(c.className)) {
            let typeC = c.classFieldList[0];
            //基本类型数组
            if (typeC.typeId == null) {
                let value = jsonValue(typeC.type);
                let typeJson = JSON.stringify([value]);
                typeArr.push({type: this.paramType, paramMode: this.paramMode, typeJson: typeJson});
                return {typeList: typeArr, typeJson:typeJson, isBaseTypeArr: BaseType[typeC.type]};
            }
            //引用类型数组
            c = branchMenuData.classInfoList.find(i => i.id === typeC.typeId);
            //非数组添加到类型
        }
        for (let p of c.classFieldList) {
            let value = jsonValue(p.type);
            if (p.typeId == null) {
                jo[p.paramName] = value;
                continue;
            }
            //记录当前类型防止互相依赖死循环
            let typeValue = this.typeMap[p.typeId];
            debugger
            if (typeValue) {
                jo[p.paramName] = typeValue;
                continue;
            }
            this.typeMap[p.typeId] = p.type;
            //解析当前字段类型
            let c = branchMenuData.classInfoList.find(i => i.id === p.typeId);
            let res = this.getParamAllClass(c);
            typeArr = typeArr.concat(res.typeList);
            //赋值json
            jo[p.paramName] = JSON.parse(res.typeJson);
        }
        //添加自身
        typeArr.unshift($.extend({type: this.paramType, paramMode: this.paramMode, typeJson: JSON.stringify(jo)}, c));
        return {typeList: typeArr, typeJson: JSON.stringify(jo)};
    }
}


function jsonValue(type) {
    if (type === 'int' || type === 'Integer') {
        return 0;
    }
    if (type === 'double' || type === 'Double') {
        return 0.0;
    }
    if (type === 'boolean' || type === 'Boolean') {
        return false;
    }
    if (type === 'Date') {
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