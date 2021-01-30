@echo off
chcp 65001
rem Copyright 1999-2018 Alibaba Group Holding Ltd.
rem Licensed under the Apache License, Version 2.0 (the "License");
rem you may not use this file except in compliance with the License.
rem You may obtain a copy of the License at
rem
rem      http://www.apache.org/licenses/LICENSE-2.0
rem
rem Unless required by applicable law or agreed to in writing, software
rem distributed under the License is distributed on an "AS IS" BASIS,
rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
rem See the License for the specific language governing permissions and
rem limitations under the License.
if not exist "%JAVA_HOME%\bin\java.exe" echo Please set the JAVA_HOME variable in your environment, We need java(x64)! jdk8 or later is better! & EXIT /B 1
set "JAVA=%JAVA_HOME%\bin\java.exe"

setlocal enabledelayedexpansion

set BASE_DIR=%~dp0
rem added double quotation marks to avoid the issue caused by the folder names containing spaces.
rem removed the last 5 chars(which means \bin\) to get the base DIR.
set BASE_DIR="%BASE_DIR:~0,-5%"

set DEFAULT_SEARCH_LOCATIONS="classpath:/,classpath:/config/,file:./,file:./config/"
REM configuration file
set CUSTOM_SEARCH_LOCATIONS="file:%BASE_DIR%/conf/application.properties"
REM git local repository path
set REPOSITORY_PATH="%BASE_DIR%/repository"
REM database sql
set GORGEOUS_DATABASE="%BASE_DIR%/conf/gorgeous-logback.xml"
REM logback config
set GORGEOUS_LOG_CONFIG="%BASE_DIR%/conf/database.sql"
set GORGEOUS_LOG_HOME="%BASE_DIR%/logs"

set SERVER=gorgeous-doc

set "GORGEOUS_JVM_OPTS=-Xms512m -Xmx512m -Xmn256m"


rem set gorgeous options
set "GORGEOUS_OPTS=%GORGEOUS_OPTS% -Dloader.path=%BASE_DIR%/plugins/health,%BASE_DIR%/plugins/cmdb"
set "GORGEOUS_OPTS=%GORGEOUS_OPTS% -Dfile.encoding=utf-8"
set "GORGEOUS_OPTS=%GORGEOUS_OPTS% -jar %BASE_DIR%\target\%SERVER%.jar"
rem set gorgeous spring config location
set "GORGEOUS_OPTS=%GORGEOUS_OPTS% --gitInfo.repository-path=%REPOSITORY_PATH%"
set "GORGEOUS_OPTS=%GORGEOUS_OPTS% --gorgeous-database=%GORGEOUS_DATABASE%"
set "CONFIG_OPTS=--spring.config.location=%CUSTOM_SEARCH_LOCATIONS%"
REM logback config
set "JAVA_OPT=%JAVA_OPT% --logging.config=%GORGEOUS_LOG_CONFIG%"
set "JAVA_OPT=%JAVA_OPT% --GORGEOUS_LOG_HOME=%GORGEOUS_LOG_HOME%"


set COMMAND="%JAVA%" %GORGEOUS_JVM_OPTS% %GORGEOUS_OPTS% %CONFIG_OPTS% %NACOS_LOG4J_OPTS% gorgeous.gorgeous %*

rem start gorgeous command
%COMMAND%
