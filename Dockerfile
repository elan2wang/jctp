## This Dockerfile is a merge of the following images: gcc:4.9 and oracle-java8 

FROM gcc:4.9

RUN apt-get update && apt-get install -y --no-install-recommends \
		bzip2 \
		unzip \
		xz-utils \
	&& rm -rf /var/lib/apt/lists/*

RUN echo 'deb http://deb.debian.org/debian jessie-backports main' > /etc/apt/sources.list.d/jessie-backports.list

# Default to UTF-8 file.encoding
ENV LANG C.UTF-8

RUN set -x \
	&& echo "deb http://ppa.launchpad.net/webupd8team/java/ubuntu xenial main" | \
		tee /etc/apt/sources.list.d/webupd8team-java.list \
	&& echo "deb-src http://ppa.launchpad.net/webupd8team/java/ubuntu xenial main" | \
		tee -a /etc/apt/sources.list.d/webupd8team-java.list \
	&& apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv-keys EEA14886 \
	&& apt-get update \
	&& echo "oracle-java8-installer shared/accepted-oracle-license-v1-1 select true" | debconf-set-selections \
	&& apt-get install -y oracle-java8-installer \ 
	&& rm -rf /var/lib/apt/lists/*

# Build CTP
COPY ./out/artifacts/jctp_jar/jctp.jar /usr/src/jctp.jar
COPY ./ctp-api-linux64-20160628/* /usr/src/ctp/
COPY ./ctp-api-linux64-20160628/*.so /usr/lib/

WORKDIR /usr/src/ctp

RUN make

RUN make install

CMD java -jar /usr/src/jctp.jar
