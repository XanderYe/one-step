package cn.xanderye.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * http请求工具
 *
 * @author XanderYe
 * @date 2020/2/4
 */
public class HttpUtil {
    /**
     * 是否使用代理
     */
    private static boolean useProxy = false;
    /**
     * socket连接超时
     */
    private static final int DEFAULT_SOCKET_TIMEOUT = 15000;
    /**
     * 请求超时
     */
    private static final int DEFAULT_CONNECT_TIMEOUT = 30000;
    /**
     * 默认编码
     */
    private static final String CHARSET = "UTF-8";
    /**
     * 默认请求头
     */
    private static final String DEFAULT_USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36";

    private static CloseableHttpClient httpClient;

    private static CookieStore cookieStore;

    // 静态代码块初始化配置
    static {
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(DEFAULT_CONNECT_TIMEOUT)
                .setSocketTimeout(DEFAULT_SOCKET_TIMEOUT)
                .setCookieSpec(CookieSpecs.STANDARD)
                .build();
        cookieStore = new BasicCookieStore();
        httpClient = custom().setDefaultCookieStore(cookieStore)
                .setDefaultRequestConfig(config).build();
    }


    /**
     * 创建httpClientBuilder
     *
     * @return org.apache.http.impl.client.HttpClientBuilder
     * @author XanderYe
     * @date 2020/2/14
     */
    private static HttpClientBuilder custom() {
        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        // 忽略证书
        httpClientBuilder.setSSLSocketFactory(ignoreCertificates());
        if (useProxy) {
            // 使用代理
            httpClientBuilder.setProxy(new HttpHost("127.0.0.1", 8888));
        }
        return httpClientBuilder;
    }

    /**
     * GET请求
     *
     * @param url
     * @param params
     * @return java.lang.String
     * @author XanderYe
     * @date 2020-03-15
     */
    public static String doGet(String url, Map<String, Object> params) {
        return doGet(url, null, null, params);
    }


    /**
     * POST请求
     *
     * @param url
     * @param params
     * @return java.lang.String
     * @author XanderYe
     * @date 2020-03-15
     */
    public static String doPost(String url, Map<String, Object> params) {
        return doPost(url, null, null, params);
    }

    /**
     * get请求基础方法
     *
     * @param url
     * @param headers
     * @param params
     * @return java.lang.String
     * @author XanderYe
     * @date 2020/2/4
     */
    public static String doGet(String url, Map<String, Object> headers, Map<String, Object> cookies, Map<String, Object> params) {
        // 拼接参数
        if (params != null && !params.isEmpty()) {
            List<NameValuePair> pairs = new ArrayList<>(params.size());
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                String value = entry.getValue() == null ? null : (entry.getValue()).toString();
                if (value != null) {
                    pairs.add(new BasicNameValuePair(entry.getKey(), value));
                }
            }
            try {
                String parameters = EntityUtils.toString(new UrlEncodedFormEntity(pairs, CHARSET));
                String symbol = url.contains("?") ? "&" : "?";
                // 判断是否已带参数
                url += symbol + parameters;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        HttpGet httpGet = new HttpGet(url);
        // 添加headers
        addHeaders(httpGet, headers);
        // 添加cookies
        addCookies(httpGet, cookies);
        CloseableHttpResponse response = null;
        HttpEntity resultEntity = null;
        try {
            HttpClientContext httpClientContext = new HttpClientContext();
            response = httpClient.execute(httpGet, httpClientContext);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                resultEntity = response.getEntity();
                if (resultEntity != null) {
                    return EntityUtils.toString(resultEntity, CHARSET);
                }
            } else {
                throw new RuntimeException("error status code :" + statusCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultEntity != null) {
                    EntityUtils.consume(resultEntity);
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * post请求基础方法
     *
     * @param url
     * @param headers
     * @param params
     * @return java.lang.String
     * @author XanderYe
     * @date 2020/2/4
     */
    public static String doPost(String url, Map<String, Object> headers, Map<String, Object> cookies, Map<String, Object> params) {
        HttpPost httpPost = new HttpPost(url);
        // 拼接参数
        if (params != null && !params.isEmpty()) {
            List<NameValuePair> pairs = new ArrayList<>(params.size());
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                String value = entry.getValue() == null ? null : (entry.getValue()).toString();
                if (value != null) {
                    pairs.add(new BasicNameValuePair(entry.getKey(), value));
                }
            }
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(pairs, CHARSET));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 添加headers
        addHeaders(httpPost, headers);
        // 添加cookies
        addCookies(httpPost, cookies);
        CloseableHttpResponse response = null;
        HttpEntity resultEntity = null;
        try {
            HttpClientContext httpClientContext = new HttpClientContext();
            response = httpClient.execute(httpPost, httpClientContext);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                resultEntity = response.getEntity();
                if (resultEntity != null) {
                    return EntityUtils.toString(resultEntity, CHARSET);
                }
            } else {
                throw new RuntimeException("error status code :" + statusCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultEntity != null) {
                    EntityUtils.consume(resultEntity);
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * get下载基础方法
     *
     * @param url
     * @param headers
     * @param params
     * @return byte[]
     * @author XanderYe
     * @date 2020/2/4
     */
    public static byte[] doDownload(String url, Map<String, Object> headers, Map<String, Object> cookies, Map<String, Object> params) {
        // 拼接参数
        if (params != null && !params.isEmpty()) {
            List<NameValuePair> pairs = new ArrayList<>(params.size());
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                String value = entry.getValue() == null ? null : (entry.getValue()).toString();
                if (value != null) {
                    pairs.add(new BasicNameValuePair(entry.getKey(), value));
                }
            }
            try {
                String parameters = EntityUtils.toString(new UrlEncodedFormEntity(pairs, CHARSET));
                String symbol = url.contains("?") ? "&" : "?";
                // 判断是否已带参数
                url += symbol + parameters;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        HttpGet httpGet = new HttpGet(url);
        // 添加headers
        addHeaders(httpGet, headers);
        // 添加cookies
        addCookies(httpGet, cookies);
        CloseableHttpResponse response = null;
        HttpEntity resultEntity = null;
        try {
            HttpClientContext httpClientContext = new HttpClientContext();
            response = httpClient.execute(httpGet, httpClientContext);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                resultEntity = response.getEntity();
                if (resultEntity != null) {
                    return EntityUtils.toByteArray(resultEntity);
                }
            } else {
                throw new RuntimeException("error status code :" + statusCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultEntity != null) {
                    EntityUtils.consume(resultEntity);
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * POST提交JSON基础方法
     *
     * @param url
     * @param headers
     * @param json
     * @return org.apache.http.HttpEntity
     * @author XanderYe
     * @date 2020/2/4
     */
    public static String doPostJson(String url, Map<String, Object> headers, Map<String, Object> cookies, String json) {
        HttpPost httpPost = new HttpPost(url);
        // 拼接参数
        if (json != null && !"".equals(json)) {
            StringEntity requestEntity = new StringEntity(json, CHARSET);
            requestEntity.setContentEncoding(CHARSET);
            requestEntity.setContentType("application/json");
            httpPost.setEntity(requestEntity);
        }
        // 添加headers
        addHeaders(httpPost, headers);
        // 添加cookies
        addCookies(httpPost, cookies);
        CloseableHttpResponse response = null;
        HttpEntity resultEntity = null;
        try {
            HttpClientContext httpClientContext = new HttpClientContext();
            response = httpClient.execute(httpPost, httpClientContext);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                resultEntity = response.getEntity();
                if (resultEntity != null) {
                    return EntityUtils.toString(resultEntity, CHARSET);
                }
            } else {
                throw new RuntimeException("error status code :" + statusCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultEntity != null) {
                    EntityUtils.consume(resultEntity);
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 添加cookie
     *
     * @param httpRequestBase
     * @return void
     * @author XanderYe
     * @date 2020-03-15
     */
    private static void addHeaders(HttpRequestBase httpRequestBase, Map<String, Object> headers) {
        // 设置默认UA
        httpRequestBase.setHeader("User-Agent", DEFAULT_USER_AGENT);
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue() == null ? "" : String.valueOf(entry.getValue());
                httpRequestBase.setHeader(key, value);
            }
        }
    }

    /**
     * 添加cookie
     *
     * @param cookies
     * @return void
     * @author XanderYe
     * @date 2020-03-15
     */
    private static void addCookies(HttpRequestBase httpRequestBase, Map<String, Object> cookies) {
        // 清空cookie
        cookieStore.clear();
        if (cookies != null && !cookies.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Map.Entry<String, Object> entry : cookies.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue() == null ? "" : String.valueOf(entry.getValue());
                stringBuilder.append(key).append("=").append(value).append("; ");
            }
            httpRequestBase.addHeader("Cookie", stringBuilder.toString());
        }
    }

    /**
     * 获取请求的cookie
     *
     * @param
     * @return java.util.Map<java.lang.String, java.lang.String>
     * @author XanderYe
     * @date 2020/2/4
     */
    public static Map<String, String> getCookies() {
        List<Cookie> basicCookies = cookieStore.getCookies();
        if (!basicCookies.isEmpty()) {
            Map<String, String> cookies = new HashMap<>(16);
            for (Cookie cookie : basicCookies) {
                cookies.put(cookie.getName(), cookie.getValue());
            }
            return cookies;
        }
        return null;
    }

    /**
     * 获取请求的cookie
     *
     * @param
     * @return java.util.Map<java.lang.String, java.lang.String>
     * @author XanderYe
     * @date 2020/2/4
     */
    public static Map<String, Object> getObjectCookies() {
        List<Cookie> basicCookies = cookieStore.getCookies();
        if (basicCookies.size() > 0) {
            Map<String, Object> cookies = new HashMap<>(16);
            for (Cookie cookie : basicCookies) {
                cookies.put(cookie.getName(), cookie.getValue());
            }
            return cookies;
        }
        return null;
    }

    /**
     * 忽略证数配置
     *
     * @param
     * @return org.apache.http.conn.ssl.SSLConnectionSocketFactory
     * @author XanderYe
     * @date 2020/2/14
     */
    private static SSLConnectionSocketFactory ignoreCertificates() {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, (chain, authType) -> true).build();
            return new SSLConnectionSocketFactory(sslContext);
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 格式化请求头
     *
     * @param headerString
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author XanderYe
     * @date 2020/4/1
     */
    public static Map<String, Object> formatHeaders(String headerString) {
        if (headerString != null && !"".equals(headerString)) {
            String[] headers = headerString.split(";");
            if (headers.length > 0) {
                Map<String, Object> headerMap = new HashMap<>(16);
                for (String header : headers) {
                    String[] value = header.split(":");
                    String k = value[0].trim();
                    String v = null;
                    if (value.length == 2) {
                        v = value[1].trim();
                    }
                    headerMap.put(k, v);
                }
                return headerMap;
            }
        }
        return null;
    }

    /**
     * 格式化cookie
     *
     * @param cookieString
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author XanderYe
     * @date 2020/4/1
     */
    public static Map<String, Object> formatCookies(String cookieString) {
        if (cookieString != null && !"".equals(cookieString)) {
            String[] cookies = cookieString.split(";");
            if (cookies.length > 0) {
                Map<String, Object> cookieMap = new HashMap<>(16);
                for (String parameter : cookies) {
                    String[] value = parameter.split("=");
                    String k = value[0].trim();
                    String v = null;
                    if (value.length == 2) {
                        v = value[1].trim();
                    }
                    cookieMap.put(k, v);
                }
                return cookieMap;
            }
        }
        return null;
    }

    /**
     * 格式化请求体
     *
     * @param parameterString
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author XanderYe
     * @date 2020/4/1
     */
    public static Map<String, Object> formatParameters(String parameterString) {
        if (parameterString != null && !"".equals(parameterString)) {
            String[] parameters = parameterString.split("&");
            if (parameters.length > 0) {
                Map<String, Object> paramMap = new HashMap<>(16);
                for (String parameter : parameters) {
                    String[] value = parameter.split("=");
                    String k = value[0].trim();
                    String v = null;
                    if (value.length == 2) {
                        v = value[1].trim();
                    }
                    paramMap.put(k, v);
                }
                return paramMap;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Map<String, Object> test = formatParameters("gameId=&sArea=&iSex=&sRoleId=&iGender=&sServiceType=tgclub&objCustomMsg=&areaname=&roleid=&rolelevel=&rolename=&areaid=&iActivityId=290824&iFlowId=649015&g_tk=1842395457&e_code=0&g_code=0&eas_url=http%3A%2F%2Fxinyue.qq.com%2Fact%2Fa20200303dnfbjbb%2Findex_h5.html&eas_refer=http%3A%2F%2Fnoreferrer%2F%3Freqid%3D${uuid}%26version%3D22&sServiceDepartment=xinyue");
    }
}
