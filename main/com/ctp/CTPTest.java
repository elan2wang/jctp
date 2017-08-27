package com.ctp;

public class CTPTest {
    private static int reqId = 0;

    public static void main(String[] args) throws Throwable {

        System.loadLibrary("thostmduserapi");
        System.loadLibrary("thosttraderapi");
        System.loadLibrary("ctp");

        System.out.println("load ctp library successfully");

        CThostFtdcMdApi mdApi = CThostFtdcMdApi.CreateFtdcMdApi("/usr/src/ctp", false);
        assert mdApi != null;
        MdSpi mdSpi = new MdSpi(mdApi);
        mdApi.RegisterSpi(mdSpi);

        mdApi.RegisterFront("tcp://180.168.146.187:10030");
        System.out.println("MdAPI Version: " + CThostFtdcMdApi.GetApiVersion());
        mdApi.Init();
        System.out.println("MdAPI Trading Day: " + mdApi.GetTradingDay());
        mdApi.Join();
    }

    static class MdSpi extends CThostFtdcMdSpi {
        final CThostFtdcMdApi mdApi;

        final static String m_BrokerId = "9999";
        final static String m_UserId = "099941";
        final static String m_PassWord = "siC3aXjp";

        MdSpi(CThostFtdcMdApi mdApi) {
            this.mdApi = mdApi;
        }

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

        public void OnRspUserLogin(CThostFtdcRspUserLoginField pRspUserLogin, CThostFtdcRspInfoField pRspInfo,
                                   int nRequestID, boolean bIsLast) {
            if (pRspInfo != null && pRspInfo.getErrorID() != 0)
            {
                System.out.printf("Login ErrorID[%d] ErrMsg[%s]\n", pRspInfo.getErrorID(), pRspInfo.getErrorMsg());

                return;
            }
            System.out.println("Login success!!!");
        }

        public void OnRspError(CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
            System.out.println("OnRspError");
        }
    }
}