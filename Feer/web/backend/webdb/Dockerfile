FROM mdillon/postgis:10-alpine

RUN apk update && apk upgrade && \
        apk add --no-cache openssl

COPY overlay /

ENTRYPOINT ["entrypoint.sh"]

CMD ["postgres"]