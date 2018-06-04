package io.jing.base.util.net;

import com.google.common.collect.Maps;
import lombok.SneakyThrows;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Map;

/**
 * @author jingshouyan
 * @date 2018/4/16 22:18
 */
public class NetUtil {

    private static final Map<String,String> IP_MAP = ipMap();

    public static String getIp(String netSegment) {
        String key = netSegment.substring(0, netSegment.lastIndexOf("."));
        return IP_MAP.get(key);
    }

    @SneakyThrows
    private static Map<String,String> ipMap() {
        Map<String,String> ipMap = Maps.newHashMap();
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress address = addresses.nextElement();
                if (address instanceof Inet4Address) {
                    String addr = address.getHostAddress();
                    String key = addr.substring(0, addr.lastIndexOf("."));
                    ipMap.put(key, addr);
                }
            }
        }
        return ipMap;
    }

    public static boolean isLocalPortUsing(int port) {
        String host = "127.0.0.1";
        return isPortUsing(host, port);
    }

    public static boolean isPortUsing(String host, int port) {
        boolean flag = false;
        try {
            Socket socket = new Socket(host, port);
            socket.close();
            flag = true;

        } catch (Exception e) {
        }
        return flag;
    }
}
