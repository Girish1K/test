/*
 * Class Name    : IPUtil
 * Description   : This class is used to find out the IPAdress for the request
 * Process       : 
 * Author        : Sangram G
 * Since         : 
 * Modify        : 
 */



import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

public class IPUtil {
	
	public static void main(String arg[]){
	
	}
	
	
	public static String getIPAdress(HttpServletRequest request)throws UnknownHostException
	{
		String ipAdress =null;
		try {
			 ipAdress = InetAddress.getByName(request.getRemoteAddr()).toString().substring(1, InetAddress.getByName(request.getRemoteAddr()).toString().length());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ipAdress;
	}
	

}
