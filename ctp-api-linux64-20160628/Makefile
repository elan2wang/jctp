IDIR=/usr/lib/jvm/java-8-oracle/include
LDIR=/usr/lib
CFLAGS=-I${IDIR} -I$(IDIR)/linux

libctp.so: ${LDIR}/libthostmduserapi.so ${LDIR}/libthosttraderapi.so ctp_wrap.o 
	g++ -shared ctp_wrap.o -o libctp.so -Wl,--enable-new-dtags,-rpath,${LDIR} -L${LDIR} -lthostmduserapi -lthosttraderapi

ctp_wrap.o: ctp_wrap.cxx
	g++ -c -fpic ctp_wrap.cxx -o ctp_wrap.o $(CFLAGS)

clean: 
	rm ctp_wrap.o
