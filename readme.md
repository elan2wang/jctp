# Download CTP

<http://www.sfit.com.cn/5_2_DocumentDown.htm>

# Create .i file

> /* File : ctp.i */
> %module ctp
> %{
> #include "ThostFtdcMdApi.h"
> %}
> %include "ThostFtdcMdApi.h"
> %{
> #include "ThostFtdcTraderApi.h"
> %}
> %include "ThostFtdcTraderApi.h"
> %{
> #include "ThostFtdcUserApiDataType.h"
> %}
> %include "ThostFtdcUserApiDataType.h"
> %{
> #include "ThostFtdcUserApiStruct.h"
> %}
> %include "ThostFtdcUserApiStruct.h" 

# Generate cxx file

> swig -c++ -java -package com.ctp -outdir ../main/com/ctp -I./ ctp.i

# Compile cxx file

> g++ -c ctp_wrap.cxx -I/Library/Java/JavaVirtualMachines/jdk1.8.0_11.jdk/Contents/Home/include -I/Library/Java/JavaVirtualMachines/jdk1.8.0_11.jdk/Contents/Home/include/darwin

# Link 

> g++ -shared thostmduserapi.so thosttraderapi.so ctp_wrap.o -o ctp.so


<http://www.swig.org/Doc1.3/Java.html#compilation_problems_cpp>
