package com.ctp;

public class CTPTest {
    private static int reqId = 0;

    public static void main(String[] args) throws Throwable {

        System.loadLibrary("thosttraderapi");
        System.loadLibrary("thostmduserapi");
        System.loadLibrary("ctpapi_wrap");

        System.out.println("load ctp library successfully");

        CThostFtdcMdApi mdApi = CThostFtdcMdApi.CreateFtdcMdApi("/usr/src/ctp", false);
        assert mdApi != null;
        MdSpi mdSpi = new MdSpi(mdApi);
        mdApi.RegisterSpi(mdSpi);

        mdApi.RegisterFront("tcp://180.168.146.187:10010");
        System.out.println("MdAPI Version: " + CThostFtdcTraderApi.GetApiVersion());
        mdApi.Init();
        System.out.println("MdAPI Trading Day: " + mdApi.GetTradingDay());
        mdApi.Join();
    }

    static class MdSpi extends CThostFtdcMdSpi {
        final CThostFtdcMdApi mdApi;

        final static String m_BrokerId = "9999";
        final static String m_UserId = "099941";
        final static String m_PassWord = "siC3aXjp";

        String instrumentID[] = {"IC1710", "IC1712", "cu1801", "i1801"};


        MdSpi(CThostFtdcMdApi mdApi) {
            this.mdApi = mdApi;
        }

        @Override
        public void OnFrontConnected() {
            System.out.println("On Front Connected");

            CThostFtdcReqUserLoginField userLoginField = new CThostFtdcReqUserLoginField();
            userLoginField.setBrokerID(m_BrokerId);
            userLoginField.setUserID(m_UserId);
            userLoginField.setPassword(m_PassWord);
            int ret = mdApi.ReqUserLogin(userLoginField, reqId++);

            switch (ret) {
                case 0:
                    System.out.println("User login succeed");
                    break;
                default:
                    System.out.println("User login failed : " + ret);
            }
        }

        @Override
        public void OnRspUserLogin(CThostFtdcRspUserLoginField pRspUserLogin, CThostFtdcRspInfoField pRspInfo,
                                   int nRequestID, boolean bIsLast) {
            if (pRspInfo != null && pRspInfo.getErrorID() != 0)
            {
                System.out.printf("Login ErrorID[%d] ErrMsg[%s]\n", pRspInfo.getErrorID(), pRspInfo.getErrorMsg());

                return;
            }
            System.out.println("Login success!!!");
            mdApi.SubscribeMarketData(instrumentID, 4);
        }

        public void OnRspSubMarketData(CThostFtdcSpecificInstrumentField pSpecificInstrument, CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
            System.out.println("Subscribed Market Data of Symbol: " + pSpecificInstrument.getInstrumentID());
        }

        public void OnRtnDepthMarketData(CThostFtdcDepthMarketDataField pDepthMarketData) {
            System.out.println("Receive Market Data of Symbol: " + pDepthMarketData.getInstrumentID());
            System.out.println(pDepthMarketData.getActionDay() + ", ap " + pDepthMarketData.getAskPrice1() + ", bp "
                    + pDepthMarketData.getBidPrice1() + ", as " + pDepthMarketData.getAskVolume1() + ", bs "
                    + pDepthMarketData.getBidVolume1() + ", hp " + pDepthMarketData.getHighestPrice() + ", lp "
                    + pDepthMarketData.getLowestPrice() + ", openPrice " + pDepthMarketData.getOpenPrice());
        }

        @Override
        public void OnRspError(CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
            System.out.println("OnRspError");
        }
    }
}