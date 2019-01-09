import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import utils.HttpUtils;
import utils.SignUtils;

import java.util.*;

public class TestTrans {



//    private String url = "http://sandbox.starpos.com.cn/adpweb/ehpspos3/pubSigPay.json";//公众号/小程序支付

    /**
     * 请求头参数
     */
    public static Map headString(){
        Map<String,String> params=new HashMap<String,String>();
        params.put("opSys","3");//操作系统
        params.put("characterSet","00");//字符集
        params.put("orgNo","11658");//机构号
        params.put("mercId","800290000007906");//商户号
        params.put("trmNo","XB006439");//设备号
        params.put("tradeNo","20190804103115431295");//商户订单号
        params.put("txnTime","20190107153245");//设备端交易时间
        params.put("signType","MD5");//签名方式
        params.put("version","V1.0.0");//版本号
        return params;
    }
    /**
     * 扫码支付,商户主扫
     signStr:113453758386726997000800290000007906311658WXPAYMD522220190804103115431293XB006439P20190107153245V1.0.09FF13E7726C4DFEB3BED750779F59711
     json:{"mercId":"800290000007906","amount":"1","characterSet":"00","authCode":"134537583867269970","tradeNo":"20190804103115431293","trmNo":"XB006439","version":"V1.0.0","signValue":"4a77f04c32f0db31a8e1375595493c42","orgNo":"11658","total_amount":"222","trmTyp":"P","txnTime":"20190107153245","signType":"MD5","payChannel":"WXPAY","opSys":"3"}
     result:{"total_amount":"222","amount":"1","message":"%E7%AD%89%E5%BE%85%E6%94%AF%E4%BB%98","sysTime":"20190108185251","result":"A","returnCode":"000000","orderNo":"201901080013547459","logNo":"201901080013547459","tradeNo":"20190804103115431293","mercId":"800290000007906","signValue":"5c2f8445c4fb7fa73a07c2390f692e64"}
    */
     public void testSdkBarcodePay(){
        String url = "http://sandbox.starpos.com.cn/adpweb/ehpspos3/sdkBarcodePay.json";//扫码支付-商户主扫
        Map mapHead = TestTrans.headString();
        Map<String,String> params = new HashMap<String, String>();
        params.putAll(mapHead);
        params.put("trmTyp","P");//设备号
        params.put("amount","1");//实付金额
        params.put("total_amount","222");//订单总金额
        params.put("authCode","134521941179567482");//扫码支付授权码
        params.put("payChannel","WXPAY");//支付渠道

        String signStr = SignUtils.stringSort(params) + "9FF13E7726C4DFEB3BED750779F59711";
        params.put("signValue", DigestUtils.md5Hex(signStr.getBytes()));
        System.out.println("signStr:"+signStr);

        System.out.println("json:"+JSONObject.toJSONString(params));
        String result = HttpUtils.postJson(url,JSONObject.toJSONString(params));
        System.out.println("result:"+result);
    }

    /**
     * 扫码支付-客户主扫
     * signStr:20000800290000007906311658WXPAY20180804103115431298MD522220190804103115431299XB00643920190107153245V1.0.09FF13E7726C4DFEB3BED750779F59711
     * json:{"mercId":"800290000007906","amount":"200","characterSet":"00","tradeNo":"20190804103115431299","trmNo":"XB006439","version":"V1.0.0","signValue":"9b4467d88f366fc1537e0401ad67ad3e","orgNo":"11658","total_amount":"222","selOrderNo":"20180804103115431298","txnTime":"20190107153245","signType":"MD5","payChannel":"WXPAY","opSys":"3"}
     * result:{"total_amount":"222","returnCode":"000000","result":"S","payCode":"weixin://wxpay/s/An4baqw","logNo":"201901080013546842","tradeNo":"20190804103115431299","selOrderNo":"20180804103115431298","mercId":"800290000007906","signValue":"91143c421c9c581e4a0a4e7a4e5ed668","sysTime":"20190108173558","message":"%E4%BA%A4%E6%98%93%E6%88%90%E5%8A%9F","amount":"200","orderNo":"signStr:20000800290000007906311658WXPAY20180804103115431298MD522220190804103115431299XB00643920190107153245V1.0.09FF13E7726C4DFEB3BED750779F59711
     * json:{"mercId":"800290000007906","amount":"200","characterSet":"00","tradeNo":"20190804103115431299","trmNo":"XB006439","version":"V1.0.0","signValue":"9b4467d88f366fc1537e0401ad67ad3e","orgNo":"11658","total_amount":"222","selOrderNo":"20180804103115431298","txnTime":"20190107153245","signType":"MD5","payChannel":"WXPAY","opSys":"3"}
     * result:{"total_amount":"222","returnCode":"000000","result":"S","payCode":"weixin://wxpay/s/An4baqw","logNo":"201901080013546842","tradeNo":"20190804103115431299","selOrderNo":"20180804103115431298","mercId":"800290000007906","signValue":"91143c421c9c581e4a0a4e7a4e5ed668","sysTime":"20190108173558","message":"%E4%BA%A4%E6%98%93%E6%88%90%E5%8A%9F","amount":"200","orderNo":"201901080013546842"}"}
    **/
    public void testSdkBarcodePosPay(){
        String url = "http://sandbox.starpos.com.cn/adpweb/ehpspos3/sdkBarcodePosPay.json";//扫码支付-商户主扫
        Map mapHead = TestTrans.headString();
        Map<String,String> params = new HashMap<String, String>();
        params.putAll(mapHead);
        params.put("amount","200");//实付金额
        params.put("total_amount","222");//订单总金额
        params.put("payChannel","WXPAY");//支付渠道
        params.put("selOrderNo","20180804103115431298");//订货订单号

        String signStr = SignUtils.stringSort(params) + "9FF13E7726C4DFEB3BED750779F59711";
        params.put("signValue", DigestUtils.md5Hex(signStr.getBytes()));
        System.out.println("signStr:"+signStr);

        System.out.println("json:"+JSONObject.toJSONString(params));
        String result = HttpUtils.postJson(url,JSONObject.toJSONString(params));
        System.out.println("result:"+result);
    }

    /**
     * 订单查询
     signStr:00800290000007906311658201901080013547459MD520190804103115431294XB00643920190107153245V1.0.09FF13E7726C4DFEB3BED750779F59711
     json:{"mercId":"800290000007906","characterSet":"00","orgNo":"11658","tradeNo":"20190804103115431294","qryNo":"201901080013547459","trmNo":"XB006439","txnTime":"20190107153245","signType":"MD5","opSys":"3","version":"V1.0.0","signValue":"48c85ab3a6e6584f0e04b68e2f494fcb"}
     result:{"total_amount":"222","goodsTag":"","returnCode":"000000","result":"S","logNo":"201901080013547459","selOrderNo":"","tradeNo":"20190804103115431294","subject":"","mercId":"800290000007906","signValue":"341b7e9c17cc9dea9462e7fed3bb94cc","payChannel":"WXPAY","sysTime":"20190108185251","message":"%E4%BA%A4%E6%98%93%E6%88%90%E5%8A%9F","amount":"1","orderNo":"20190108185218008711","attach":""}
     */
     public void testSdkQryBarcodePay(){
        String url = "http://sandbox.starpos.com.cn/adpweb/ehpspos3/sdkQryBarcodePay.json";//订单查询
        Map mapHead = TestTrans.headString();
        Map<String,String> params = new HashMap<String, String>();
        params.putAll(mapHead);
        params.put("qryNo","201901080013547459");//支付渠道订单号
//        params.put("txnAmt","100");//退款金额

        String signStr = SignUtils.stringSort(params) + "9FF13E7726C4DFEB3BED750779F59711";
        params.put("signValue", DigestUtils.md5Hex(signStr.getBytes()));
        System.out.println("signStr:"+signStr);

        System.out.println("json:"+JSONObject.toJSONString(params));
        String result = HttpUtils.postJson(url,JSONObject.toJSONString(params));
        System.out.println("result:"+result);
    }
    /**
     * 退款
     * signStr:0080029000000790632019010818521800871111658MD520190804103115431295XB00643920190107153245V1.0.09FF13E7726C4DFEB3BED750779F59711
     * json:{"mercId":"800290000007906","characterSet":"00","orderNo":"20190108185218008711","orgNo":"11658","tradeNo":"20190804103115431295","trmNo":"XB006439","txnTime":"20190107153245","signType":"MD5","opSys":"3","version":"V1.0.0","signValue":"3abd315796f9db483b624d42a9cd145f"}
     * result:{"total_amount":"222","goodsTag":"","returnCode":"000000","result":"S","logNo":"201901080013547472","selOrderNo":"","tradeNo":"20190804103115431295","subject":"","mercId":"800290000007906","signValue":"cce814db5e92907d02bcfdb40579446f","txnAmt":"1","sysTime":"20190108185611","message":"%E4%BA%A4%E6%98%93%E9%80%80%E6%AC%BE%E6%88%90%E5%8A%9F","amount":"1","attach":""}
     */
    public void testSdkRefundBarcodePay(){
        String url = "http://sandbox.starpos.com.cn/adpweb/ehpspos3/sdkRefundBarcodePay.json";//退款
        Map mapHead = TestTrans.headString();
        Map<String,String> params = new HashMap<String, String>();
        params.putAll(mapHead);
        params.put("orderNo","20190108185218008711");//支付渠道订单号
//        params.put("txnAmt","100");//退款金额

        String signStr = SignUtils.stringSort(params) + "9FF13E7726C4DFEB3BED750779F59711";
        params.put("signValue", DigestUtils.md5Hex(signStr.getBytes()));
        System.out.println("signStr:"+signStr);

        System.out.println("json:"+JSONObject.toJSONString(params));
        String result = HttpUtils.postJson(url,JSONObject.toJSONString(params));
        System.out.println("result:"+result);
    }

    public static void main(String[] args) {
        new TestTrans().testSdkBarcodePay();
//        new TestTrans().testSdkBarcodePosPay();
//        new TestTrans().testSdkQryBarcodePay();
//        new TestTrans().testSdkRefundBarcodePay();

    }
}
