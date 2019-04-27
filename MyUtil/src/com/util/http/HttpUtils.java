package com.util.http;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class HttpUtils {
	public static  final  String HTTP="http://";
	private static final String URL_PARAM_CONNECT_FLAG = "&";
	private static final int SIZE 	= 1024 * 1024;

	private HttpUtils() {
	}

	/**
	 * GET METHOD
	 * @param strUrl String
	 * @param map Map
	 * @throws IOException
	 * @return List
	 */
	public static List URLGet(String strUrl, Map map) throws IOException {
		String strtTotalURL = "";
		List result = new ArrayList();
		if(strtTotalURL.indexOf("?") == -1) {
			strtTotalURL = strUrl + "?" + getUrl(map);
		} else {
			strtTotalURL = strUrl + "&" + getUrl(map);
		}
		URL url = new URL(strtTotalURL);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setUseCaches(false);
		con.setFollowRedirects(true);
		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()),SIZE);
		while (true) {
			String line = in.readLine();
			if (line == null) {
				break;
			}
			else {
				result.add(line);
			}
		}
		in.close();
		return (result);
	}

	/**
	 * POST METHOD
	 * @param strUrl String
	 * @param
	 * @throws IOException
	 * @return List
	 */
	public static List URLPost(String strUrl, Map map) throws IOException {

		String content = "";
		content = getUrl(map);
		String totalURL = null;
		if(strUrl.indexOf("?") == -1) {
			totalURL = strUrl + "?" + content;
		} else {
			totalURL = strUrl + "&" + content;
		}
		URL url = new URL(strUrl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setAllowUserInteraction(false);
		con.setUseCaches(false);
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=GBK");
		BufferedWriter bout = new BufferedWriter(new OutputStreamWriter(con.
				getOutputStream()));
		bout.write(content);
		bout.flush();
		bout.close();
		BufferedReader bin = new BufferedReader(new InputStreamReader(con.
				getInputStream()),SIZE);
		List result = new ArrayList();
		while (true) {
			String line = bin.readLine();
			if (line == null) {
				break;
			}
			else {
				result.add(line);
			}
		}
		return (result);
	}

	/**
	 * URL
	 * @param map Map
	 * @return String
	 */
	private static String getUrl(Map map) {
		if (null == map || map.keySet().size() == 0) {
			return ("");
		}
		StringBuffer url = new StringBuffer();
		Set keys = map.keySet();
		for (Iterator i = keys.iterator(); i.hasNext(); ) {
			String key = String.valueOf(i.next());
			if (map.containsKey(key)) {
				Object val = map.get(key);
				String str = val!=null?val.toString():"";
				try {
					str = URLEncoder.encode(str, "GBK");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				url.append(key).append("=").append(str).
						append(URL_PARAM_CONNECT_FLAG);
			}
		}
		String strURL = "";
		strURL = url.toString();
		if (URL_PARAM_CONNECT_FLAG.equals("" + strURL.charAt(strURL.length() - 1))) {
			strURL = strURL.substring(0, strURL.length() - 1);
		}
		return (strURL);
	}
	public static String sendPost(String url, String param) throws Exception{
		PrintWriter out = null;
		InputStream in = null;
		String result = "";
		try {
			Map map = new HashMap();
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();

			// 设置通用的请求属性
			conn.setRequestProperty("contentType", "utf-8");
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			/*conn.setRequestProperty("contentType", "utf-8");
			conn.setConnectTimeout(5 * 1000);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			*/
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			if (param != null) {
				out.print(param);
			}
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			conn.connect();
			//System.out.println("conn" + conn.getInputStream());
			in = conn.getInputStream();
			int all = in.available();
			byte[] b = new byte[all];
			in.read(b);
			result = new String(b, "UTF-8");
			in.close();
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			throw new Exception("请求中南接口失败！");
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	public static String getMD5(String str) {
		String reStr = null;

		try {
			// 创建具有指定算法名称的信息
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());// 使用指定的字节更新摘要。
			byte ss[] = md.digest();// 通过执行诸如填充之类的最终操作完成哈希计算
			reStr = bytes2String(ss);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return reStr;
	}

	// 将字节数组转换为字符串
	private static String bytes2String(byte[] aa) {
		String hash = "";
		for (int i = 0; i < aa.length; i++) {// 循环数组
			int temp;
			if (aa[i] < 0) // 如果小于零，将其变为正数
				temp = 256 + aa[i];
			else
				temp = aa[i];
			if (temp < 16)
				hash += "0";
			hash += Integer.toString(temp, 16);// 转换为16进制
		}
		hash = hash.toUpperCase();// 全部转换为大写
		return hash;
	}
	
	public static String buildHtml(Map<String,String> sPara,String formMethod,String action){
		List<String> keys = new ArrayList<String>(sPara.keySet());

		StringBuffer sbHtml = new StringBuffer();
		sbHtml.append("<form id=\"gatewayegsubmit\" name=\"gatewayegsubmit\" action=\"" + action+ "\" method=\"" + formMethod+ "\">");
				
		for (int i = 0; i < keys.size(); i++) {
			String name = (String) keys.get(i);
			String value = (String) sPara.get(name);

			sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
		}

		//submit按钮控件请不要含有name属性
		sbHtml.append("<input type=\"submit\" value=\"确定\" style=\"display:none;\"></form>");
		sbHtml.append("<script>document.forms['gatewayegsubmit'].submit();</script>");
		System.out.println(sbHtml);
		return sbHtml.toString();
	}

	public static void main(String[] args) {
		System.out.println(URLEncoder.encode("abcd1234"));
	}
}
