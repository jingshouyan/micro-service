package io.jing.ip.test;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.*;

import java.io.File;
import java.net.InetAddress;
import java.net.URL;

/**
 * @author jingshouyan
 * @date 2018/4/19 14:59
 */
public class IPTest {

    public static void main(String[] args) throws Exception {

        URL url =  IPTest.class.getClassLoader().getResource("GeoLite2-City.mmdb");
    File database = new File(url.toURI());
    //        File database = new File("D:\\download\\GeoLite2-City.mmdb");
    DatabaseReader reader = new DatabaseReader.Builder(database).build();

    InetAddress ipAddress = InetAddress.getByName("1.1.1.1");

    // Replace "city" with the appropriate method for your database, e.g.,
// "country".
    CityResponse response = reader.city(ipAddress);

    Country country = response.getCountry();
        System.out.println(country.getIsoCode());            // 'US'
        System.out.println(country.getName());               // 'United States'
        System.out.println(country.getNames().get("zh-CN")); // '美国'

    Subdivision subdivision = response.getMostSpecificSubdivision();
        System.out.println(subdivision.getNames().get("zh-CN"));    // 'Minnesota'
        System.out.println(subdivision.getIsoCode()); // 'MN'

    City city = response.getCity();
        System.out.println(city.getName()); // 'Minneapolis'

    Postal postal = response.getPostal();
        System.out.println(postal.getCode()); // '55455'

    Location location = response.getLocation();
        System.out.println(location.getLatitude());  // 44.9733
        System.out.println(location.getLongitude()); // -93.2323
}

}
