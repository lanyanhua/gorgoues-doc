create database `lan-code-api`;
use `lan-code-api`;

create table api_info
(
    id          int auto_increment comment 'ID'
        primary key,
    name        varchar(128) null comment '接口名称',
    type        int          null comment '接口类型：0:all 1:post 2:get 3:delete 4:put',
    method      varchar(128) null comment '方法名',
    create_time datetime     null comment '创建时间',
    path        varchar(128) null comment '访问路径',
    project_id  int          null comment '项目ID',
    branch_id   int          null comment '分支ID'
);

create table api_param
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

create table class_field
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

create table class_info
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

create table env_info
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

create table git_info
(
    id              int auto_increment comment 'ID'
        primary key,
    username        varchar(128) null comment 'git用户名',
    password        varchar(128) null comment '密码',
    repository_path varchar(128) null comment '本地仓库地址',
    create_time     datetime     null comment '创建时间'
)
    comment '项目信息';

create table menu
(
    id          int auto_increment comment 'ID'
        primary key,
    menu_name   varchar(128) null comment '菜单名称',
    class_name  varchar(128) null comment 'controller',
    create_time datetime     null comment '创建时间',
    parent_id   int          null comment '父菜单ID',
    api_id      int          null comment '当前菜单对应API ID',
    branch_id   int          null comment '分支ID'
)
    comment '菜单';

create table notes_config
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

create table project
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

create table project_branch
(
    id          int auto_increment comment 'ID'
        primary key,
    name        varchar(28) null comment '分支名称',
    create_time datetime    null comment '创建时间',
    project_id  int         null comment '项目ID'
)
    comment '项目分支';


INSERT INTO notes_config (id, type, notes, create_time) VALUES (1, 'classTag', '@Description:', '2020-12-20 22:15:28');
INSERT INTO notes_config (id, type, notes, create_time) VALUES (2, 'methodParamTag', '@param', '2020-12-20 22:15:28');
INSERT INTO notes_config (id, type, notes, create_time) VALUES (3, 'methodReturnTag', '@return', '2020-12-20 22:15:28');
INSERT INTO notes_config (id, type, notes, create_time) VALUES (4, 'classAnnotation', '@Api(tags)', '2020-12-20 22:15:28');
INSERT INTO notes_config (id, type, notes, create_time) VALUES (5, 'methodAnnotation', '@ApiOperation(value)', '2020-12-20 22:15:28');
INSERT INTO notes_config (id, type, notes, create_time) VALUES (6, 'fieldAnnotation', '@ApiModelProperty(value)', '2020-12-20 22:15:28');