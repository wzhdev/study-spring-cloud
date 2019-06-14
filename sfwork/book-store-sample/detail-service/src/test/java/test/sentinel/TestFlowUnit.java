package test.sentinel;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;


public class TestFlowUnit {

    @Test
    public void testRestrictFlow(){
        //设置qps
        int qps=3;

        int count=0;
        String clusterApiUrl="https://pub-int-gw.intsit.sfdc.com.cn:8080/book-store-app/api/v1/product/1";
        HttpClient httpClient= new HttpsSSLClient().createSSLInsecureClient();
        HttpGet httpGet=new HttpGet(clusterApiUrl);
        HttpEntity entity =null;
        try {

            while(true){
                HttpResponse execute = httpClient.execute(httpGet);
                entity = execute.getEntity();
                String res=EntityUtils.toString(entity);
                System.out.println(res);
                count++;
                if(count>qps){
                    System.out.println("request count > qps,sleep 1 second");
                    Thread.sleep(1000);
                    count=0;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                EntityUtils.consume(entity);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }




    class HttpsSSLClient {
        /**
         * 获取Https 请求客户端
         * @return
         */
        public CloseableHttpClient createSSLInsecureClient() {
            SSLContext sslcontext = createSSLContext();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new HostnameVerifier() {

                @Override
                public boolean verify(String paramString, SSLSession paramSSLSession) {
                    return true;
                }
            });
            CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
            return httpclient;
        }

        /**
         * 获取初始化SslContext
         * @return
         */
        private SSLContext createSSLContext() {
            SSLContext sslcontext = null;
            try {
                sslcontext = SSLContext.getInstance("TLS");
                sslcontext.init(null, new TrustManager[] {new TrustAnyTrustManager()}, new SecureRandom());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }
            return sslcontext;
        }

        /**
         * 自定义静态私有类
         */
        private class TrustAnyTrustManager implements X509TrustManager {

            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}

            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}

            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[] {};
            }
        }

    }
}
