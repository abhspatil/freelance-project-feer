#!/bin/sh

REQUEST=$1
WAIT=3

while true
do
    if [ -f "$REQUEST" ]; then
        echo "[watcher] $REQUEST requested ..."
        rm -f $REQUEST
        su - postgres -c "pg_ctl stop -D $PGDATA"
    fi

    sleep $WAIT
done
