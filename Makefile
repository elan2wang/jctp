IDIR=/usr/lib/jvm/java-8-oracle/include
CFLAGS=-I${IDIR} -I$(IDIR)/linux
SRCDIR=./ctp-api-linux64-20160628

libctp.so: /usr/lib/libthostmduserapi.so /usr/lib/libthosttraderapi.so $(SRCDIR)/ctp_wrap.o
	cd $(SRCDIR); g++ -shared ctp_wrap.o -o libctp.so -Wl,--enable-new-dtags,-rpath,/usr/lib -L/usr/lib -lthostmduserapi -lthosttraderapi

$(SRCDIR)/ctp_wrap.o: $(SRCDIR)/ctp_wrap.cxx
	cd $(SRCDIR); g++ -c -fpic ctp_wrap.cxx -o ctp_wrap.o $(CFLAGS)

clean: 
	cd $(SRCDIR); rm ctp_wrap.o
