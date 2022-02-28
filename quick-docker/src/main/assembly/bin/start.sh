#!/bin/bash
log_file=log."$(date +%Y%m%d%H%M%S)"
exec 1>>$log_file
cd `dirname $0`
BIN_DIR=`pwd`
cd ..
DEPLOY_DIR=`pwd`
CONF_DIR=$DEPLOY_DIR/conf
LOGS_DIR=$DEPLOY_DIR/logs

## 获取项目名
SERVER_NAME='test-app'

#SERVER_NAME=`sed '/disconf.app/!d;s/.*=//' conf/disconf.properties | tr -d '\r'`
#SERVER_NAME=`sed '/dubbo.application.name/!d;s/.*=//' conf/dubbo.properties | tr -d '\r'`
#SERVER_PROTOCOL=`sed '/dubbo.protocol.name/!d;s/.*=//' conf/dubbo.properties | tr -d '\r'`
#SERVER_PORT=`sed '/dubbo.protocol.port/!d;s/.*=//' conf/dubbo.properties | tr -d '\r'`
#LOGS_FILE=`sed '/dubbo.log4j.file/!d;s/.*=//' conf/dubbo.properties | tr -d '\r'`


echo "project name:$SERVER_NAME"
#echo "server protocol:$SERVER_PROTOCOL"
#echo "server port:$SERVER_PORT"

if [ -z "$SERVER_NAME" ]; then
    SERVER_NAME=`hostname`
fi

PIDS=`ps aux | grep java | grep "$CONF_DIR" |awk '{print $2}'`
if [ -n "$PIDS" ]; then
    echo "ERROR: The $SERVER_NAME already started!"
    echo "PID: $PIDS"
    exit 1
fi

#if [ -n "$SERVER_PORT" ]; then
#    SERVER_PORT_COUNT=`netstat -tln | grep $SERVER_PORT | wc -l`
#    if [ $SERVER_PORT_COUNT -gt 0 ]; then
#        echo "ERROR: The $SERVER_NAME port $SERVER_PORT already used!"
#        exit 1
#    fi
#fi

#LOGS_DIR=""
#if [ -n "$LOGS_FILE" ]; then
#    LOGS_DIR=`dirname $LOGS_FILE`
#else
#    LOGS_DIR=$DEPLOY_DIR/logs
#fi

if [ ! -d $LOGS_DIR ]; then
    mkdir $LOGS_DIR
fi
STDOUT_FILE=$LOGS_DIR/stdout.log

LIB_DIR=$DEPLOY_DIR/lib
LIB_JARS=`ls $LIB_DIR|grep .jar|awk '{print "'$LIB_DIR'/"$0}'|tr "\n" ":"`

#JAVA_OPTS=" -Djava.awt.headless=true -Djava.net.preferIPv4Stack=true "
JAVA_DEBUG_OPTS=""
if [ "$1" = "debug" ]; then
    JAVA_DEBUG_OPTS=" -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=8000,server=y,suspend=n "
fi
JAVA_JMX_OPTS=""
if [ "$1" = "jmx" ]; then
    JAVA_JMX_OPTS=" -Dcom.sun.management.jmxremote.port=1099 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false "
fi
JAVA_MEM_OPTS=""
if [ ! -n "$JAVA_OPTS" ]; then
 BITS=`java -version 2>&1 | grep -i 64-bit`
 if [ -n "$BITS" ]; then
  JAVA_MEM_OPTS=" -server -Xmx2g -Xms2g -Xmn256m -XX:PermSize=128m -Xss256k -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:LargePageSizeInBytes=128m -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 "
 else
  JAVA_MEM_OPTS=" -server -Xms1g -Xmx1g -XX:PermSize=128m -XX:SurvivorRatio=2 -XX:+UseParallelGC "
 fi
fi

echo -e "Starting the $SERVER_NAME ...\c"
#nohup java $JAVA_OPTS $JAVA_MEM_OPTS $JAVA_DEBUG_OPTS $JAVA_JMX_OPTS -classpath $CONF_DIR:$LIB_JARS com.alibaba.dubbo.container.Main > $STDOUT_FILE 2>&1 &
nohup java $JAVA_OPTS $JAVA_MEM_OPTS $JAVA_DEBUG_OPTS $JAVA_JMX_OPTS -classpath $CONF_DIR:$LIB_JARS com.docker.DockerApplication > $STDOUT_FILE 2>&1 &


echo "wait the server starting..."

COUNT=0
while [ $COUNT -lt 1 ]; do
    echo -e ".\c"
    sleep 1
    COUNT=`ps aux | grep java | grep "$DEPLOY_DIR" | awk '{print $2}' | wc -l`
    if [ $COUNT -gt 0 ]; then
        break
    fi
done

echo "OK!"
PIDS=`ps aux | grep java | grep "$DEPLOY_DIR" | awk '{print $2}'`
echo "PID: $PIDS"
echo "STDOUT: $STDOUT_FILE"


## 注意，以下命令防止容器退出
tailf /dev/null