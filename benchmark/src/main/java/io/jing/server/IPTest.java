package io.jing.server;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class IPTest {

    private static String getLocalAddr() {
        Enumeration<NetworkInterface> interfaces = null;
        try {
            interfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
            return null;
        }

        while (interfaces.hasMoreElements()) {
            NetworkInterface ifc = interfaces.nextElement();
            Enumeration<InetAddress> addressesOfAnInterface = ifc.getInetAddresses();

            while (addressesOfAnInterface.hasMoreElements()) {

                InetAddress address = addressesOfAnInterface.nextElement();
                System.out.println(address);
                if (address.isSiteLocalAddress()) {
//                    return address.getHostAddress();
                }
            }
        }

        return null;
    }

    public static void main(String[] args) {
        String addr = getLocalAddr();
        System.out.println(addr);

    }
}
