 
#!/bin/sh

# Place configuration updates here.
# Run before every postgres start, should be idempotent.

pgconf=$PGDATA/postgresql.conf

if [ -f $pgconf ]; then
    # replace max_connections ...
    sed -i -e '/^max_connections.*=/ s/=.*/= 400  # AuCoremaxconnections/' $pgconf
    # ... or add if no replacement
    grep AuCoremaxconnections $pgconf || (echo "max_connections = 400  # AuCoremaxconnections" >> $pgconf)
fi
