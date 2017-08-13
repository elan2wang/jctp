IDIR=/usr/lib/jvm/java-8-oracle/include
CFLAGS=-I${IDIR}
SRCDIR=./ctp-api-linux64-20160628

ctp_wrap.o: $(SRCDIR)/ctp_wrap.cxx
	ls -lR /usr/lib/jvm/; cd $(SRCDIR); g++ -c ctp_wrap.cxx -o ctp_wrap.o $(CFLAGS)

ctp.so: $(SRCDIR)/thostmduserapi.so $(SRCDIR)/thosttraderapi.so $(SRCDIR)/ctp_wrap.o 
	cd $(SRCDIR); g++ -shared thostmduserapi.so thosttraderapi.so ctp_wrap.o -o ctp.so

clean: 
	cd $(SRCDIR); rm ctp_wrap.o
