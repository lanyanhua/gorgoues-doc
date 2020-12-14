function getParamAllClass(param, type) {
    let typeArr = [];
    let jo = {};
    for (let p of param) {
        let value = jsonValue(p.type);
        if (p.typeId != null) {
            let c = branchMenuData.classInfoList.find(i => i.id == p.typeId);
            if (isArr(p.type)) {
                let listType = branchMenuData.classInfoList.find(i => i.id == c.classFieldList[0].typeId);
                p.type = p.type+'['+listType.className+']';
                let res = getParamAllClass(listType.classFieldList, type);
                // listType.className = '['+listType.className+']';
                typeArr.push($.extend({type: type}, listType));
                typeArr = typeArr.concat(res.typeList);
                value = [JSON.parse(res.typeJson)];
            } else {
                let res = getParamAllClass(c.classFieldList, type);
                typeArr.push($.extend({type: type}, c));
                typeArr = typeArr.concat(res.typeList);
                value = JSON.parse(res.typeJson);
            }
        }
        jo[p.paramName] = value
    }
    return {typeList: typeArr, typeJson: JSON.stringify(jo)};
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