#!/bin/sh

TARGET_JAR=$(ls *.jar);
TJ=${TARGET_JAR[0]}
nohup java -jar $TJ > log.log &

