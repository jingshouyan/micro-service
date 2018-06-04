package io.jing.server.ip;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * @author jingshouyan
 * @date 2018/4/16 22:28
 */
public class IPTest {
    public static void main(String[] args) throws Exception {
//       Map<String,String> map =  NetUtil.ipMap();
//        System.out.println(map);
        t();
    }

    public static void t1(){
        // TODO Auto-generated method stub
        InetAddress ia=null;
        try {
            ia=InetAddress.getLocalHost();

            String localname=ia.getHostName();
            String localip=ia.getHostAddress();
            System.out.println("本机名称是："+ localname);
            System.out.println("本机的ip是 ："+localip);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void t(){
        String localhostIp = "127.0.0.1";
        try{
            for(Enumeration<NetworkInterface> ni = NetworkInterface.getNetworkInterfaces(); ni.hasMoreElements();){
                NetworkInterface eth=ni.nextElement();
                for(Enumeration<InetAddress> add = eth.getInetAddresses();add.hasMoreElements();){
                    InetAddress i=add.nextElement();
                    if(i instanceof Inet4Address){
                        if(i.isSiteLocalAddress()){
                            System.out.println(i.getHostAddress());
                            localhostIp = i.getHostAddress();
                        }
                    }
                }
            }
        }catch(SocketException e){
            System.out.println("get local ip address failed:"+e.getMessage());
        }
    }
}
