#!/bin/sh

TARGET_NAME=`pwd|awk -F "/" '{print $(NF)}'`
TARGET_PROCESS=`ps -ef | grep "${TARGET_NAME}"| grep -v 'grep' | awk '{print $2}'`
if [ ! $TARGET_PROCESS ]; then
        echo "process has stop"
else
        echo "start kill process"
        kill -9 $TARGET_PROCESS
        echo "process has killed"
fi