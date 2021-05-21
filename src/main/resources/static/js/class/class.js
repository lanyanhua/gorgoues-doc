let classTypeArrJson = {
    type: null,
    paramMode: null,
    typeMap: {},
    classSet: {},
    getTypeArrJson: function (c, param) {
        this.type = param.type;
        this.paramMode = param.paramMode;
        this.typeMap = {};
        this.classSet = {};
        let res=  this.getParamAllClass(c);
        //将json串结合参数类型、传参方式组成一个数据类型添加到 类型数组
        res.typeList.push({type: this.type, paramMode: this.paramMode, typeJson: res.typeJson});
        // res.typeList.dis
        return res;
    },
    /**
     * 获取参数class信息
     * 这段代码不少递归，算是一个小型三级缓冲防止数据类型自身引用死循环，以及处理数组类型，拼接整个类的json字符串
     * @param c class对象
     * @return typeList: 类内部所有的数据类型 typeJson: 类型的json串 isBaseTypeArr: 基本数据类型数组
     */
    getParamAllClass: function (c) {
        //记录当前类内部所有引用类型展示用
        let typeArr = [];
        //最终的json串对象
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
                //递归查询 当前数组数据类型
                let res = this.getParamAllClass(c);
                typeArr = typeArr.concat(res.typeList);
                jo = [res.typeJson];
            }
            return {typeList: typeArr, typeJson: jo, isBaseTypeArr: typeC.type};
        }
        //遍历所有字段
        for (let p of c.classFieldList) {
            let value = jsonValue(p.type);
            //类型ID为空 基本数据类型 拼接json串结束
            if (p.typeId == null) {
                jo[p.paramName] = value;
                continue;
            }
            //记录当前类型防止互相依赖死循环
            //已存在类型从缓存中取
            let typeValue = this.typeMap[p.typeId];
            if (typeValue) {
                jo[p.paramName] = typeValue;
                continue;
            }
            //缓存类型，给个规定的json类型值后面再遇到就直接取当前值
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
            typeArr.unshift($.extend({type: this.type,paramMode: this.paramMode}, c));
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