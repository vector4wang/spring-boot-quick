FROM java:8
COPY *.zip /app/app.zip
RUN unzip -o /app/app.zip -d /app/ && rm -rf /app/app.zip

WORKDIR /app/
CMD ["sh","bin/start.sh"]