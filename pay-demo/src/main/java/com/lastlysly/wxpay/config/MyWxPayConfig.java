package com.lastlysly.wxpay.config;

import com.github.wxpay.sdk.IWXPayDomain;
import com.github.wxpay.sdk.WXPayConfig;
import com.github.wxpay.sdk.WXPayConstants;

import java.io.*;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-02-14 09:35
 **/
public class MyWxPayConfig extends WXPayConfig {

    private String appId;

    private String mchId;

    private String appKey;

    public MyWxPayConfig(String appId, String mchId, String appKey) {
        this.appId = appId;
        this.mchId = mchId;
        this.appKey = appKey;
    }

    /**
     * 获取 App ID
     *
     * @return App ID
     */
    @Override
    public String getAppID() {
        return this.appId;
    }

    /**
     * 获取 Mch ID
     *
     * @return Mch ID
     */
    @Override
    public String getMchID() {
        return this.mchId;
    }

    /**
     * 获取 API 密钥
     *
     * @return API密钥
     */
    @Override
    public String getKey() {
        return this.appKey;
    }

    /**
     * 获取商户证书内容
     *
     * @return 商户证书内容
     */
    @Override
    public InputStream getCertStream() throws IOException {

        String certPath = "/path/to/apiclient_cert.p12";
        File file = new File(certPath);
        InputStream certStream = new FileInputStream(file);
        byte[] certData = new byte[(int) file.length()];
        certStream.read(certData);
        certStream.close();
        ByteArrayInputStream certBis = new ByteArrayInputStream(certData);
        return certBis;
    }

    /**
     * 获取WXPayDomain, 用于多域名容灾自动切换
     *
     * @return
     */
    @Override
    public IWXPayDomain getWXPayDomain() {

//        return WXPayDomainSimpleImpl.instance();


        IWXPayDomain iwxPayDomain = new IWXPayDomain() {
            @Override
            public void report(String domain, long elapsedTimeMillis, Exception ex) {

            }
            @Override
            public DomainInfo getDomain(WXPayConfig config) {
                return new IWXPayDomain.DomainInfo(WXPayConstants.DOMAIN_API, true);
            }
        };
        return iwxPayDomain;

    }
}
