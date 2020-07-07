#!/bin/sh -e

AUDB=feerweb_prod

wait_postgres() {
    # Wait for postgres is ready
    until psql -h 127.0.0.1 -d $AUDB -c "select 1" &> /dev/null; do
        sleep 3
    done
}


setup_db() {
    psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
CREATE USER root SUPERUSER ENCRYPTED PASSWORD 'pass@123';
CREATE DATABASE feerweb_prod ENCODING 'utf8';
EOSQL
}

# watcher.sh $PGDATA/RESTART &

# update-conf.sh

wait_postgres

setup_db


