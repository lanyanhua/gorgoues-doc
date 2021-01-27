### 当前脚本会在项目启动时运行 ###
# 创建库
# create database `gorgeous-doc` character set utf8 collate utf8_general_ci;
#建表

create table if not exists api_info
(
    id          int auto_increment comment 'ID'
        primary key,
    name        varchar(512) null comment '接口名称',
    type        int          null comment '接口类型：0:all 1:post 2:get 3:delete 4:put',
    method      varchar(128) null comment '方法名',
    create_time datetime     null comment '创建时间',
    path        varchar(128) null comment '访问路径',
    project_id  int          null comment '项目ID',
    branch_id   int          null comment '分支ID'
);

create table if not exists api_param
(
    id             int auto_increment comment 'ID'
        primary key,
    type           int          null comment '参数类型：1：入参 2：出参',
    class_id       int          null comment '参数对应的 class',
    param_mode     int          null comment '传参方式，0：form-data 1：post json格式 2：path {id}',
    param_name     varchar(128) null comment '参数名称',
    param_describe varchar(128) null comment '参数描述',
    create_time    datetime     null comment '创建时间',
    api_id         int          null comment 'api_info_id',
    data_type      varchar(128) null comment '基本数据类型'
)
    comment '接口参数';

create table if not exists class_field
(
    id             int auto_increment comment 'ID'
        primary key,
    class_id       int          null comment 'classId',
    param_name     varchar(128) null comment '字段名称',
    param_describe varchar(256) null comment '描述',
    type           varchar(256) null comment '数据类型',
    type_id        int          null comment 'ID不为null 关联class表',
    create_time    datetime     null comment '创建时间'
)
    comment '类信息';

create table if not exists class_info
(
    id             int auto_increment comment 'ID'
        primary key,
    class_name     varchar(128) null comment '类名',
    class_describe varchar(128) null comment '类名',
    package_path   varchar(256) null comment '包名',
    class_path     varchar(256) null comment '类路径',
    create_time    datetime     null comment '创建时间',
    project_id     int          null comment '项目ID',
    branch_id      int          null comment '分支ID'
)
    comment '类信息';

create table if not exists env_info
(
    id              int auto_increment comment 'ID'
        primary key,
    name            varchar(128) null comment '名称',
    domain          varchar(128) null comment '域名',
    header          varchar(128) null comment 'header参数，多个逗号隔开',
    create_time     datetime     null comment '创建时间',
    is_port         tinyint(1)   null comment '是否使用项目端口：1：是 0：否',
    is_context_path tinyint(1)   null comment '是否使用项目上下文路径'
)
    comment '环境配置';


create table if not exists menu
(
    id          int auto_increment comment 'ID'
        primary key,
    menu_name   varchar(512) null comment '菜单名称',
    class_name  varchar(128) null comment 'controller',
    create_time datetime     null comment '创建时间',
    parent_id   int          null comment '父菜单ID',
    api_id      int          null comment '当前菜单对应API ID',
    branch_id   int          null comment '分支ID'
)
    comment '菜单';

create table if not exists notes_config
(
    id          int auto_increment comment 'ID'
        primary key,
    type        varchar(28)  null comment '自定义：
注释 @Description:
classTag
methodTag
methodParamTag
methodReturnTag
fieldTag
注解 @Api(tags)
classAnnotation
methodAnnotation
fieldAnnotation',
    notes       varchar(128) null comment '注释',
    create_time datetime     null comment '创建时间'
)
    comment '注释配置';

create table if not exists project
(
    id           int auto_increment comment 'ID'
        primary key,
    name         varchar(28)  null comment '项目名称',
    remote_path  varchar(128) null comment '远程库路径',
    create_time  datetime     null comment '创建时间',
    context_path varchar(128) null comment '上下文路径',
    port         int          null comment '端口'
)
    comment '项目信息';

create table if not exists project_branch
(
    id          int auto_increment comment 'ID'
        primary key,
    name        varchar(28) null comment '分支名称',
    create_time datetime    null comment '创建时间',
    project_id  int         null comment '项目ID'
)
    comment '项目分支';

create table if not exists project_config
(
    id           int auto_increment
        primary key,
    project_id   int          null comment '项目ID',
    name         varchar(128) null comment '名称',
    context_path varchar(128) null comment '上下文路径',
    port         int          null comment '端口',
    create_time  datetime     null comment '创建时间'
)
    comment '项目配置';

### 注释读取默认配置 ###
insert into notes_config ( type, notes, create_time)
select type,notes,now()
from (
select 'classTag' as type,'@Description:' as notes union
select 'methodParamTag','@param' union
select 'methodReturnTag','@return' union
select 'classAnnotation','@Api(tags)' union
select 'methodAnnotation','@ApiOperation(value)' union
select 'fieldAnnotation','@ApiModelProperty(value)' union
select 'baseDataType','void' union
select 'baseDataType','String' union
select 'baseDataType','Object' union
select 'baseDataType','byte' union
select 'baseDataType','Byte' union
select 'baseDataType','short' union
select 'baseDataType','Short' union
select 'baseDataType','int' union
select 'baseDataType','Integer' union
select 'baseDataType','long' union
select 'baseDataType','Long' union
select 'baseDataType','double' union
select 'baseDataType','Double' union
select 'baseDataType','float' union
select 'baseDataType','Float' union
select 'baseDataType','char' union
select 'baseDataType','Char' union
select 'baseDataType','boolean' union
select 'baseDataType','Boolean' union
select 'baseDataType','Date' union
select 'baseDataType','MultipartFile' union
select 'baseDataType','BigDecimal' union
select 'baseDataType','URL' union
select 'baseDataType','HttpServletResponse' union
select 'baseDataType','HttpServletRequest' union
select 'baseDataType','LinkedHashMap' union
select 'baseDataType','HashMap' union
select 'baseDataType','Map' union
select 'arrayType','List' union
select 'arrayType','List' union
select 'arrayType','Set') data
where (type,notes) not in (select nc.type,nc.notes from notes_config nc);
