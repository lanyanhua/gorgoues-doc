let classTypeArrJson = {
    paramType: null,
    paramMode: null,
    typeMap: {},
    classSet: {},
    getTypeArrJson: function (c, param) {
        this.paramType = param.type;
        this.paramMode = param.paramMode;
        this.typeMap = {};
        this.classSet = {};
        let res=  this.getParamAllClass(c);
        res.typeList.push({type: this.paramType, paramMode: this.paramMode, typeJson: res.typeJson});
        // res.typeList.dis
        return res;
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
            if (typeC.typeId == null) {
                //基本类型数组
                let value = jsonValue(typeC.type);
                jo = [value];
            } else {
                //引用类型数组
                c = branchMenuData.classInfoList.find(i => i.id === typeC.typeId);
                let res = this.getParamAllClass(c);
                typeArr = typeArr.concat(res.typeList);
                jo = [res.typeJson];
            }
            return {typeList: typeArr, typeJson: jo, isBaseTypeArr: BaseType[typeC.type]};
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
            if (typeValue) {
                jo[p.paramName] = typeValue;
                continue;
            }
            this.typeMap[p.typeId] = value;
            //解析当前字段类型
            let c = branchMenuData.classInfoList.find(i => i.id === p.typeId);
            let res = this.getParamAllClass(c);
            typeArr = typeArr.concat(res.typeList);
            //赋值json
            jo[p.paramName] = res.typeJson;
        }
        //添加自身
        if(this.classSet[c.id]==null){
            typeArr.unshift($.extend({type: this.paramType,paramMode: this.paramMode}, c));
            this.classSet[c.id] = 1;
        }
        return {typeList: typeArr, typeJson: jo};
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
    if (type === 'Map') {
        return {};
    }
    if (isArr(type)) {
        return [];
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