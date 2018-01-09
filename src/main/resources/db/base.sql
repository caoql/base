/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2017/12/26 17:55:15                          */
/*==============================================================*/


drop table if exists SYSTEM_DATA_CODE;

drop table if exists SYSTEM_ORGANIZATION;

drop table if exists SYSTEM_RESOURCE;

drop table if exists SYSTEM_ROLE;

drop table if exists SYSTEM_ROLE_RESOURCE;

drop table if exists SYSTEM_USER;

drop table if exists SYSTEM_USER_DATA_AUTH;

drop table if exists SYSTEM_USER_ORGANIZATION;

drop table if exists SYSTEM_USER_ROLE;

/*==============================================================*/
/* Table: SYSTEM_DATA_CODE                                      */
/*==============================================================*/
create table SYSTEM_DATA_CODE
(
   ID                   varchar(32) not null comment '主键',
   DOMAIN_ID            varchar(40) comment '类型ID',
   DATA_CODE            varchar(40) comment '数据编码',
   DATA_NAME            varchar(40) comment '数据名称',
   REMARK               varchar(255) comment '备注',
   CREATE_TIME          timestamp comment '创建时间',
   CREATOR              varchar(32) comment '创建人',
   UPDATE_TIME          timestamp comment '修改时间',
   UPDATOR              varchar(32) comment '修改人'
);

alter table SYSTEM_DATA_CODE comment '数据码表';

/*==============================================================*/
/* Table: SYSTEM_ORGANIZATION                                   */
/*==============================================================*/
create table SYSTEM_ORGANIZATION
(
   ORGANIZATION_ID      varchar(32) not null comment '主键',
   CODE                 varchar(40) comment '组织编码',
   NAME                 varchar(40) comment '名字',
   ADDRESS              varchar(60) comment '地址',
   PID                  varchar(32) comment '父级组织ID',
   SYSCODE              varchar(32) comment '系统编码',
   ORG_ID               varchar(32) comment '组织编码',
   IS_ENABLED           tinyint(1) comment '是否有效(1:是,0:否)',
   REMARK               varchar(255) comment '备注',
   CREATE_TIME          timestamp comment '创建时间',
   CREATOR              varchar(32) comment '创建人',
   UPDATE_TIME          timestamp comment '修改时间',
   UPDATOR              varchar(32) comment '修改人'
);

alter table SYSTEM_ORGANIZATION comment '组织信息';

/*==============================================================*/
/* Table: SYSTEM_RESOURCE                                       */
/*==============================================================*/
create table SYSTEM_RESOURCE
(
   RESOURCE_ID          varchar(32) not null comment '主键',
   NAME                 varchar(40) comment '资源名称',
   URL                  varchar(100) comment '资源路径',
   TYPE                 char(1) comment '资源类型',
   DESCRIPTION          varchar(100) comment '资源描述',
   NODE_ORDER           tinyint(3) comment '资源顺序',
   PID                  varchar(32) comment '父级资源ID',
   IS_ENABLED           tinyint(1) comment '是否有效(1:是,0:否)',
   REMARK               varchar(255) comment '备注',
   CREATE_TIME          timestamp comment '创建时间',
   CREATOR              varchar(32) comment '创建人',
   UPDATE_TIME          timestamp comment '修改时间',
   UPDATOR              varchar(32) comment '修改人',
   primary key (RESOURCE_ID)
);

alter table SYSTEM_RESOURCE comment '资源信息';

/*==============================================================*/
/* Table: SYSTEM_ROLE                                           */
/*==============================================================*/
create table SYSTEM_ROLE
(
   ROLE_ID              varchar(32) not null comment '主键',
   NAME                 varchar(40) comment '角色名字',
   DESCRIPTION          varchar(100) comment '角色描述',
   SYSCODE              varchar(32) comment '系统编码',
   ORG_ID               varchar(32) comment '组织编码',
   IS_ENABLED           tinyint(1) comment '是否有效(1:是,0:否)',
   REMARK               varchar(255) comment '备注',
   CREATE_TIME          timestamp comment '创建时间',
   CREATOR              varchar(32) comment '创建人',
   UPDATE_TIME          timestamp comment '修改时间',
   UPDATOR              varchar(32) comment '修改人',
   primary key (ROLE_ID)
);

alter table SYSTEM_ROLE comment '角色信息';

/*==============================================================*/
/* Table: SYSTEM_ROLE_RESOURCE                                  */
/*==============================================================*/
create table SYSTEM_ROLE_RESOURCE
(
   ID                   varchar(32) not null comment '主键',
   ROLE_ID              varchar(32) comment '角色ID',
   RESOURCE_ID          varchar(32) comment '资源ID',
   primary key (ID)
);

alter table SYSTEM_ROLE_RESOURCE comment '资源角色关联表';

/*==============================================================*/
/* Table: SYSTEM_USER                                           */
/*==============================================================*/
create table SYSTEM_USER
(
   USER_ID              varchar(32) not null comment '主键',
   ACCOUNT              varchar(40) comment '账号',
   NAME                 varchar(40) comment '姓名',
   PASSWORD             varchar(64) comment '密码',
   PHONE                varchar(15) comment '手机号码',
   SEX                  char(1) comment '性别(m:男,w:女,t:其他)',
   SYSCODE              varchar(32) comment '系统编码',
   ORG_ID               varchar(32) comment '组织编码',
   IS_ENABLED           tinyint(1) comment '是否有效(1:是,0:否)',
   REMARK               varchar(255) comment '备注',
   CREATE_TIME          timestamp comment '创建时间',
   CREATOR              varchar(32) comment '创建人',
   UPDATE_TIME          timestamp comment '修改时间',
   UPDATOR              varchar(32) comment '修改人',
   primary key (USER_ID)
);

alter table SYSTEM_USER comment '用户信息SYSTEM_USER';

/*==============================================================*/
/* Table: SYSTEM_USER_DATA_AUTH                                 */
/*==============================================================*/
create table SYSTEM_USER_DATA_AUTH
(
   ID                   varchar(32) not null comment '主键',
   USER_ID              varchar(32) comment '用户ID',
   BUSINESS_CODE        varchar(40) comment '权限编码',
   REMARK               varchar(255) comment '备注',
   CREATE_TIME          timestamp comment '创建时间',
   CREATOR              varchar(32) comment '创建人',
   UPDATE_TIME          timestamp comment '修改时间',
   UPDATOR              varchar(32) comment '修改人'
);

alter table SYSTEM_USER_DATA_AUTH comment '用户数据权限表';

/*==============================================================*/
/* Table: SYSTEM_USER_ORGANIZATION                              */
/*==============================================================*/
create table SYSTEM_USER_ORGANIZATION
(
   ID                   varchar(32) not null comment '主键',
   USER_ID              varchar(32) comment '用户ID',
   ORGANIZATION_ID      varchar(32) comment '组织ID',
   primary key (ID)
);

alter table SYSTEM_USER_ORGANIZATION comment '用户组织表';

/*==============================================================*/
/* Table: SYSTEM_USER_ROLE                                      */
/*==============================================================*/
create table SYSTEM_USER_ROLE
(
   ID                   varchar(32) not null comment '主键',
   USER_ID              varchar(32) comment '用户ID',
   ROLE_ID              varchar(32) comment '角色ID',
   primary key (ID)
);

alter table SYSTEM_USER_ROLE comment '用户角色表';

