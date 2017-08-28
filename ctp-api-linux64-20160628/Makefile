JAVA_HOME=/usr/lib/jvm/java-8-oracle
INCLUDE=-I${JAVA_HOME}/include -I${JAVA_HOME}/include/linux
TARGET=libctpapi_wrap.so
CPPFLAG=-shared -fPIC
OBJS=ctpapi_wrap.o
CC=g++
LDLIB=-L. -lthosttraderapi -lthostmduserapi

${TARGET}: ${OBJS} 
	${CC} ${CPPFLAG} ${INCLUDE} -o ${TARGET} ${OBJS} ${LDLIB}

${OBJS}: %.o : %.cpp 
	${CC} -c -fPIC ${INCLUDE} $< -o $@

clean: 
	rm -f ${OBJS}
	rm -f ${TARGET}

install:
	cp ${TARGET} /usr/lib/
