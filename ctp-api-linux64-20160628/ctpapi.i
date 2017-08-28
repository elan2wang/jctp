%module(directors="1") ctpapi 
%include "various.i"
%apply char **STRING_ARRAY { char *ppInstrumentID[] }

%{ 
#include "ThostFtdcTraderApi.h" 
#include "ThostFtdcMdApi.h"
%} 
%feature("director") CThostFtdcTraderSpi; 
%feature("director") CThostFtdcMdSpi;
%include "ThostFtdcUserApiDataType.h" 
%include "ThostFtdcUserApiStruct.h"
%include "ThostFtdcTraderApi.h"
%include "ThostFtdcMdApi.h"
