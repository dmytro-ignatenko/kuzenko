#!/bin/bash

if [ -z "$CATALINA_HOME" ]; then
    echo "CATALINA_HOME must be specified"
    exit 1
fi
echo "CATALINA_HOME=[$CATALINA_HOME]"

sh "$CATALINA_HOME/bin/shutdown.sh"

rm -rf "$CATALINA_HOME/webapps/kuzenko-ws"
rm "$CATALINA_HOME/webapps/kuzenko-ws.war"

cp build/libs/kuzenko-ws.war "$CATALINA_HOME/webapps"