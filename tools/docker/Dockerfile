FROM adoptopenjdk:8

#HEALTHCHECK --interval=60s --retries=10 CMD curl -f http://localhost:8081/actuator/health || exit 1

#ARG IMG_VER
#ENV IMG_VER ${IMG_VER:-1.0.0}

RUN useradd -m -d /work -s /bin/bash app \
  && mkdir -p /work/config \
  && mkdir -p /work/log \
  && chown -R app.app /work \
  && ln -sf /usr/share/zoneinfo/Europe/Moscow /etc/localtime && echo "Europe/Moscow" > /etc/timezone && dpkg-reconfigure -f noninteractive tzdata

COPY target/ezrfsd.jar /work/app.jar

USER app

ENV HOME=/work

WORKDIR /work

ENV JAVA_OPTS=""

CMD java $JAVA_OPTS -jar /work/app.jar

