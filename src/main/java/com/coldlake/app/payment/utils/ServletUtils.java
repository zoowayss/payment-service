package com.coldlake.app.payment.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ServletUtils {


    public static final String BID = "bid";

    private ServletUtils() {

    }

    /**
     * 客户端版本号
     * https://www.tapd.cn/57806632/markdown_wikis/#1157806632001000172
     */
    private final static String APP_VERSION = "appVersion";


    /**
     * https://www.tapd.cn/57806632/markdown_wikis/#1157806632001000172
     */
    private final static String AUTHORIZATION = "authorization";


    /**
     * https://www.tapd.cn/57806632/markdown_wikis/#1157806632001000172
     */
    private final static String CLIENT_SYS = "clientSys";

    /**
     * 获取系统版本号
     */
    private final static String OS_VERSION = "osVersion";

    /**
     * 设备名
     * 例：iOS10.1...
     */
    private final static String DEVICE_NAME = "deviceName";

    /**
     * 设备ID
     * 默认值10000000000000000000000000001521
     */
    private final static String DEVICE_ID = "deviceId";

    /**
     * 客户端类型, 如手机版, HD版	ios/ios1...
     */
    private final static String AID = "aid";

    /**
     * 客户端的渠道id
     */
    private final static String CHANNEL = "chan";

    /**
     * 客户端多语言类型
     */
    private final static String LOCALE = "locale";

    /**
     * 获取host信息
     */
    private final static String HOST = "host";

    private static String getHeaderValue(HttpServletRequest request, String headerName) {
        if (request == null) {
            return "";
        }
        String val = request.getHeader(headerName);
        return val == null ? "" : val;
    }

    private static String getHeaderValue(String headerName) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes == null) {
            return "";
        }
        HttpServletRequest request = servletRequestAttributes.getRequest();
        return getHeaderValue(request, headerName);
    }

    /**
     * 从请求头中获取用户真实IP地址
     *
     * @return IP地址
     */
    public static String getClientIp() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes == null) {
            return "0.0.0.0";
        }

        return getClientIp(servletRequestAttributes.getRequest());
    }

    /**
     * 从请求头中获取IP地址
     *
     * @param httpServletRequest httpServletRequest
     * @return IP地址
     */
    public static String getClientIp(HttpServletRequest httpServletRequest) {
        if (httpServletRequest == null) {
            return "0.0.0.0";
        }

        String ip;
        if (!StringUtils.isEmpty(httpServletRequest.getHeader("cdn-src-ip"))) {
            ip = httpServletRequest.getHeader("cdn-src-ip");
        } else if (!StringUtils.isEmpty(httpServletRequest.getHeader("x-real-ip"))) {
            ip = httpServletRequest.getHeader("x-real-ip");
        } else if (!StringUtils.isEmpty(httpServletRequest.getHeader("x-forwarded-for"))) {
            ip = httpServletRequest.getHeader("x-forwarded-for");
        } else if (!StringUtils.isEmpty(httpServletRequest.getRemoteHost())) {
            ip = httpServletRequest.getRemoteHost();
        } else {
            ip = "0.0.0.0";
        }

        return parseIp(ip);
    }

    /**
     * 客户端传的clientSys，web端没有这个值
     * https://www.tapd.cn/57806632/markdown_wikis/#1157806632001000172
     *
     * @return clientSys
     */
    public static String appClientSys(HttpServletRequest request) {
        return getHeaderValue(request, CLIENT_SYS);
    }

    /**
     * 客户端传的clientSys，web端没有这个值
     * https://www.tapd.cn/57806632/markdown_wikis/#1157806632001000172
     *
     * @return clientSys
     */
    public static String appClientSys() {
        return getHeaderValue(CLIENT_SYS);
    }

    /*
     * 客户端的渠道id(如安卓包的各大应用市场)
     */
    public static String appChannel(HttpServletRequest request) {
        return getHeaderValue(request, CHANNEL);
    }


    /**
     * APP客户端获取 did
     *
     * @return did
     */
    public static String appDeviceId(HttpServletRequest request) {
        return deviceIdValid(getHeaderValue(request, DEVICE_ID));
    }

    /**
     * APP客户端获取 did
     *
     * @return did
     */
    public static String appDeviceId() {
        return deviceIdValid(getHeaderValue(DEVICE_ID));
    }


    private static String deviceIdValid(String deviceId) {
        if (StringUtils.isEmpty(deviceId)) {
            return deviceId.trim();
        }
        String deviceId2 = deviceId.trim();
        final String match = "[a-zA-Z0-9_\\-\\:]*";
        if (deviceId2.matches(match)) {
            return deviceId2;
        }
        return "";
    }


    /**
     * APP客户端获取 客户端版本号，只有客户端有，web端没有
     *
     * @return appVersion
     */
    public static String appVersion() {
        return getHeaderValue(APP_VERSION);
    }

    /**
     * APP客户端获取 客户端版本号，只有客户端有，web端没有
     *
     * @return appVersion
     */
    public static String appVersion(HttpServletRequest request) {
        return getHeaderValue(request, APP_VERSION);
    }

    /**
     * 客户端类型, 如手机版, HD版	ios/ios1...
     *
     * @param request 请求
     * @return aid
     */
    public static String appAid(HttpServletRequest request) {
        return getHeaderValue(request, AID);
    }


    /**
     * 客户端类型, 如手机版, HD版	ios/ios1...
     *
     * @return aid
     */
    public static String appAid() {
        return getHeaderValue(AID);
    }


    /**
     * 设备名
     *
     * @return 设备名 iPhone X...
     */
    public static String appDeviceName() {
        return getHeaderValue(DEVICE_NAME);
    }

    /**
     * 设备名
     *
     * @param request 请求
     * @return 设备名 iPhone X...
     */
    public static String appDeviceName(HttpServletRequest request) {
        return getHeaderValue(request, DEVICE_NAME);
    }


    /**
     * 获取客户端登录态
     *
     * @return 获取客户端登录态
     */
    public static String appAuthorization() {
        return getHeaderValue(AUTHORIZATION);
    }

    /**
     * 获取客户端登录态
     *
     * @param request 请求参数
     * @return 获取客户端登录态
     */
    public static String appAuthorization(HttpServletRequest request) {
        return getHeaderValue(request, AUTHORIZATION);
    }


    /**
     * 系统版本
     *
     * @return 获取    系统版本
     */
    public static String appOsVersion() {
        return getHeaderValue(OS_VERSION);
    }

    /**
     * 获取	系统版本
     *
     * @param request 请求参数
     * @return 获取    系统版本
     */
    public static String appOsVersion(HttpServletRequest request) {
        return getHeaderValue(request, OS_VERSION);
    }


    /**
     * 客户端多语言类型，en，zh
     *
     * @param request 请求
     * @return locale
     */
    public static String appLocale(HttpServletRequest request) {
        return getHeaderValue(request, LOCALE);
    }


    public static String dump(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = new HashMap<>();
        context.put("requestURI", request.getRequestURI());
        context.put("requestMethod", request.getMethod());
        context.put("requestHeader", dumpRequestHeader(request));
        context.put("requestParameterMap", request.getParameterMap());
        context.put("requestCookies", dumpRequestCookies(request));
        return JsonUtils.toJson(context);
    }

    public static String dumpRequestHeader(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        if (request.getHeaderNames() != null) {
            Enumeration<String> iterator = request.getHeaderNames();
            if (iterator != null) {
                while (iterator.hasMoreElements()) {
                    String key = iterator.nextElement();
                    String value = request.getHeader(key);
                    map.put(key, value);
                }
            }
        }
        return JsonUtils.toJson(map);
    }

    public static Map<String, String> dumpRequestCookies(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                String key = cookie.getName();
                String value = cookie.getValue();
                map.put(key, value);
            }
        }
        return map;
    }


    private static void debug(String msg, Object... args) {
        if (log.isDebugEnabled()) {
            log.debug(msg, args);
        }
    }

    /**
     * 获取header info
     *
     * @param httpServletRequest
     * @return
     */
    private static Map<String, String> getHeadersInfo(HttpServletRequest httpServletRequest) {
        Map<String, String> map = new HashMap<String, String>();
        Enumeration headerNames = httpServletRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = httpServletRequest.getHeader(key);
            map.put(key, value);
        }
        return map;
    }

    private static String parseIp(String ip) {
        if (!ip.contains(",")) {
            return ip;
        }
        return ip.substring(0, ip.indexOf(","));
    }

    /**
     * 获取登录态
     * 优先尝试 header 头，其次尝试cookie
     *
     * @param request 请求参数
     * @return 获取登录态
     */
    public static String getAuthorization(HttpServletRequest request) {
        String headerAuthorization = getHeaderValue(request, AUTHORIZATION);
        if (StringUtils.hasText(headerAuthorization)) {
            return headerAuthorization;
        }
        String cookieAuthorization = getCookieValue(request, AUTHORIZATION);
        return cookieAuthorization;
    }

    private static String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie.getValue();
                }
            }
        }
        return "";
    }

    /**
     * 获取User-Agent
     *
     * @param request
     * @return User-Agent
     */
    public static String getUA(HttpServletRequest request) {
        String res = request.getHeader("User-Agent");
        return org.springframework.util.StringUtils.hasText(res) ? res : "";
    }


    /**
     * 获取请求的scheme
     *
     * @param request
     * @return
     */
    public static String getScheme(HttpServletRequest request) {
        return request.getScheme();
    }


    /**
     * 获取请求的域名
     *
     * @param request
     * @return
     */
    public static String getRequestPrefix(HttpServletRequest request) {
        String cm = "getRequestPrefix@ServletUtils";
        String host = request.getHeader("Host");
        log.info("{} host: {}", cm, host);
        return org.springframework.util.StringUtils.hasText(host) ? ("http://" + host) : "";
    }

    /**
     * 获取主机信息
     *
     * @return
     */
    public static String getHost() {
        return getHeaderValue(HOST);
    }

    public static String getBidOrDefault() {
        String defaultBid = "";
        String tmpBid = getHeaderValue(BID);
        if (!"".equals(tmpBid)) {
            defaultBid = tmpBid;
        }
        return defaultBid;
    }
}
