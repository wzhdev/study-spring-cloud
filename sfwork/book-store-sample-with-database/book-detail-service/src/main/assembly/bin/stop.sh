#!/bin/bash
cd `dirname $0`
BIN_DIR=`pwd`
cd ..

DEPLOY_DIR=`pwd`
LOGS_DIR=$DEPLOY_DIR/logs

log_file=$LOGS_DIR/log."$(date +%Y%m%d%H%M%S)"
echo "Log: $log_file "
exec 1>>$log_file

echo DEPLOY_DIR=$DEPLOY_DIR
echo BIN_DIR=$BIN_DIR
echo LOGS_DIR=$LOGS_DIR

JAR_COUNT=`find *.jar|wc -l`
JAR_FILE=`find *.jar`
if [ "$JAR_COUNT" -gt 1 ]; then
    echo -e "More then one jar file!"
    exit 1
fi
if [ "$JAR_FILE" = "" ]; then
    echo -e "Can not find jar file!"
    exit 1
fi

PIDS=`ps aux | grep java | grep "$JAR_FILE" |awk '{print $2}'`
if [ -z "$PIDS" ]; then
    echo "ERROR: The App does not started!"
    exit 1
fi

echo -e "Stopping the App ...\c"
for PID in $PIDS ; do
    kill $PID > /dev/null 2>&1
done

echo "wait stop server..."

COUNT=0
while [ $COUNT -lt 1 ]; do
    echo -e ".\c"
    sleep 1
    COUNT=1
    for PID in $PIDS ; do
        PID_EXIST=`ps -f -p $PID | grep java`
        if [ -n "$PID_EXIST" ]; then
            COUNT=0
            break
        fi
    done
done

echo "OK!"
echo "PID: $PIDS"

