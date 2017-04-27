package com.webdm.common.tools;


import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.URI;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;






public class CatchUtil {

	private static Logger logger = Logger.getLogger(CatchUtil.class);

	/**
	 * 设置请求参数
	 * @param map
	 * @return
	 */
	private static List<NameValuePair> setHttpParams(Map<String, Object> map) {
		if(map == null){
			return null;
		}
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		Set<Map.Entry<String, Object>> set = map.entrySet();
		for (Map.Entry<String, Object> entry : set) {
			formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
		}
		return formparams;
	}

    /**
     * 设置请求参数
     * @param list
     * @return
     */
    private static List<NameValuePair> setHttpParams(List<String[]> list) {
        if(list == null){
            return null;
        }
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        for (String [] obj : list) {
            if(obj != null && obj.length == 2)
                formparams.add(new BasicNameValuePair(StringUtil.toString(obj[0]), StringUtil.toString(obj[1])));
        }
        return formparams;
    }

	/**
	 * 获得响应HTTP实体内容
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	private static String GetHttpEntityContent(HttpResponse response)
			throws IOException, UnsupportedEncodingException {
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			InputStream is = entity.getContent();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String line = br.readLine();
			StringBuilder sb = new StringBuilder();
			while (line != null) {
				sb.append(line + "\n");
				line = br.readLine();
			}
			return sb.toString();
		}
		return "";
	}


	public static String post(String url, Map<String, Object> map) throws ClientProtocolException, IOException{

		return post(url, map, null);

	}
	/**
	 * 远程调用服务POST方法
	 * @param url 服务地址
	 * @param map 传递参数
	 * @return 服务器返回点JSON字符串
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String post(String url, Map<String, Object> map, Map<String, Object> headers) throws ClientProtocolException, IOException {


		return post(url, null, map, headers, false, null);
	}

	public static String post(String url, String msg) throws ClientProtocolException, IOException{

		return post(url, msg, null, false, null);
	}
	public static String post(String url, String msg, Map<String, Object> headers) throws ClientProtocolException, IOException{

		return post(url, msg, headers, false, null);
	}
	//	    public static String post(String url, String msg, Map<String, String> headers, boolean needDecode, String charset) {
//		
//		return post(url, msg, headers, needDecode, charset);
//	    }
	public static String post(String url, String msg, Map<String, Object> headers, boolean needDecode, String charset) throws ClientProtocolException, IOException {

		return post(url, msg, null, headers, needDecode, charset);
	}

	public static String post(String url, String msg, Map<String, Object> postParams, Map<String, Object> headers, boolean needDecode, String charset) throws ClientProtocolException, IOException {

		return post(url, msg, postParams, headers, needDecode, charset, false);
	}
		/**
		 * msg优先
		 */
	public static String post(String url, String msg, Map<String, Object> postParams, Map<String, Object> headers, boolean needDecode, String charset, boolean multipart) throws ClientProtocolException, IOException {

		return post(new DefaultHttpClient(), url, msg, postParams,headers, needDecode, charset, multipart);
	}

    /**
     * msg优先
     */
    public static String post(HttpClient httpclient, String url, String msg, Map<String, Object> postParams, Map<String, Object> headers, boolean needDecode, String charset, boolean multipart) throws ClientProtocolException, IOException {

        //HttpClient httpclient = new DefaultHttpClient();  ;

        if(url.toLowerCase().startsWith("https://")){

            HttpClientSendPost.enableSSL(httpclient);
        }




        httpclient.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT, 10000); //超时设置
        httpclient.getParams().setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 10000);//连接超时

        httpclient.getParams().setParameter("http.useragent", "Mozilla/5.0 (X11; U; Linux i686; zh-CN; rv:1.9.1.2) Gecko/20090803 Fedora/3.5.2-2.fc11 Firefox/3.5.2");


        HttpPost httppost = new HttpPost(url);


        if(headers != null){

            for(Map.Entry<String, Object> entry : headers.entrySet()){
                //httppost.addHeader(entry.getKey(), entry.getValue().toString());
                httppost.addHeader( entry.getKey(), entry.getValue().toString());
            }
        }


        if(msg != null){

            StringEntity stringEntity = new StringEntity(msg,  "UTF-8");
            httppost.setEntity(stringEntity);
            //stringEntity.writeTo(System.out);
        }else{

            if(multipart){


                if(postParams != null && postParams.size() > 0){

                    MultipartEntity entity = new MultipartEntity();


                    for(Map.Entry<String, Object> entry: postParams.entrySet()){
                        entity.addPart(entry.getKey(), new StringBody(StringUtil.toString(entry.getValue())));
                    }


                   // entity.writeTo(System.out);
                    httppost.setEntity(entity);
                }

            }else{

                List<NameValuePair> formparams = setHttpParams(postParams);
                UrlEncodedFormEntity param = new UrlEncodedFormEntity(formparams, "UTF-8");

                httppost.setEntity(param);
                //param.writeTo(System.out);
            }




        }





        HttpResponse response = httpclient.execute(httppost);


        return processEntry(response.getEntity(), response,  needDecode,  charset);
    }

    /**
     * msg优先
     */
    public static String post(HttpClient httpclient, String url, String msg, List<String[]> postParams, Map<String, Object> headers, boolean needDecode, String charset, boolean multipart) throws ClientProtocolException, IOException {

        //HttpClient httpclient = new DefaultHttpClient();  ;

        if(url.toLowerCase().startsWith("https://")){

            HttpClientSendPost.enableSSL(httpclient);
        }




        httpclient.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT, 10000); //超时设置
        httpclient.getParams().setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 10000);//连接超时

        httpclient.getParams().setParameter("http.useragent", "Mozilla/5.0 (X11; U; Linux i686; zh-CN; rv:1.9.1.2) Gecko/20090803 Fedora/3.5.2-2.fc11 Firefox/3.5.2");


        HttpPost httppost = new HttpPost(url);


        if(headers != null){

            for(Map.Entry<String, Object> entry : headers.entrySet()){
                //httppost.addHeader(entry.getKey(), entry.getValue().toString());
                httppost.addHeader( entry.getKey(), entry.getValue().toString());
            }
        }


        if(msg != null){

            StringEntity stringEntity = new StringEntity(msg,  "UTF-8");
            httppost.setEntity(stringEntity);
            //stringEntity.writeTo(System.out);
        }else{

            if(multipart){


                if(postParams != null && postParams.size() > 0){

                    MultipartEntity entity = new MultipartEntity();


                    for(String[] obj: postParams){
                        if(obj != null && obj.length == 2)
                        entity.addPart(StringUtil.toString(obj[0]), new StringBody(StringUtil.toString(obj[0])));
                    }


                    // entity.writeTo(System.out);
                    httppost.setEntity(entity);
                }

            }else{

                List<NameValuePair> formparams = setHttpParams(postParams);
                UrlEncodedFormEntity param = new UrlEncodedFormEntity(formparams, "UTF-8");

                httppost.setEntity(param);
                //param.writeTo(System.out);
            }




        }





        HttpResponse response = httpclient.execute(httppost);


        return processEntry(response.getEntity(), response,  needDecode,  charset);
    }






	/**
	 * HTTP DELETE方法进行删除操作
	 * @param url
	 * @param map
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String delete(String url, Map<String, Object> map) throws ClientProtocolException, IOException{
		HttpClient httpclient = new DefaultHttpClient();
		HttpDelete httpdelete= new HttpDelete();
		List<NameValuePair> formparams = setHttpParams(map);
		String param = URLEncodedUtils.format(formparams, "UTF-8");
		httpdelete.setURI(URI.create(url + "?" + param));
		HttpResponse response = httpclient.execute(httpdelete);
		String httpEntityContent = GetHttpEntityContent(response);
		httpdelete.abort();
		return httpEntityContent;
	}
	public static String  put(String url, Map<String, Object> params) throws ClientProtocolException, IOException{

		//url = JETSUM_PLATFORM_SERVER+url;
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPut httpput = new HttpPut(url);

		if(params != null){

			List<NameValuePair> formparams = setHttpParams(params);
			UrlEncodedFormEntity param = new UrlEncodedFormEntity(formparams, "UTF-8");
			httpput.setEntity(param);

		}

		HttpResponse response = httpclient.execute(httpput);
		String httpEntityContent = GetHttpEntityContent(response);
		httpput.abort();
		return httpEntityContent;

	}

	private static String processEntry(HttpEntity entity, HttpResponse response, boolean needEncode, String charset) throws IllegalStateException, IOException{

		String value = null;


		org.apache.http.Header acceptEncodingObj = response.getFirstHeader("Content-Encoding");
		String  acceptEncoding = null;

		if(acceptEncodingObj !=null){
			acceptEncoding = acceptEncodingObj.getValue();

			StringBuffer sb =new StringBuffer();



			if(acceptEncoding.toLowerCase().indexOf("gzip") > -1){

				//建立gzip解压工作流

				InputStream is = entity.getContent();

				GZIPInputStream gzin = new GZIPInputStream(is);

				//流编码
				String streamCharset = "ISO-8859-1";
				org.apache.http.Header  streamCharsetHeader =  response.getFirstHeader("Content-Type");

				if(streamCharsetHeader != null){

					String streamCharsetTemp = streamCharsetHeader.getValue();
					if(streamCharsetTemp != null){

						streamCharsetTemp = CatchUtil.getValue(streamCharsetTemp, ".*?charset=(.*?)$", 1);

						if(streamCharsetTemp != null){

							streamCharset = streamCharsetTemp;
						}
					}
				}
				InputStreamReader isr = null;

				try {
					isr = new InputStreamReader(gzin, streamCharset); // 设置读取流的编码格式，自定义编码
				} catch (Exception e) {

					e.printStackTrace();
					isr = new InputStreamReader(gzin, "ISO-8859-1");
				}

				java.io.BufferedReader br = new java.io.BufferedReader(isr);

				String tempbf;

				try {
					while((tempbf=br.readLine())!=null){

						sb.append(tempbf);

						sb.append("\r\n");

					}
				} catch (Exception e) {
					// TODO: handle exception
				}finally{
					try {
						isr.close();
					} catch (Exception e2) {
						// TODO: handle exception
					}
					try {
						gzin.close();
					} catch (Exception e2) {
						// TODO: handle exception
					}


				}

				value = sb.toString();

			}
		}


		if(value == null){
			value  = EntityUtils.toString(entity);
		}

		if(value != null && needEncode){// content="text/html; charset=utf-8" />

			String entryCharset = null;

			String contentType = entity.getContentType().getValue();
			if(charset != null){
				entryCharset = charset;
			}else{

				entryCharset = CatchUtil.getValue(value, "charset=\"?([^\"]+)\"? */?>", 1);
			}

			if(entryCharset == null){
				entryCharset = CatchUtil.getValue(value, "encoding=\"?(.*?)\"?\\?>", 1);
			}



			//chart = chart.replaceAll("charset=\"?([^\"]+)\" */?>", "$1");
			if(entryCharset != null && contentType != null && contentType.toLowerCase().contains(entryCharset.toLowerCase())){
				return value;
			}else{
				if(entryCharset == null){
					entryCharset = "utf8";
				}
				return new String(value.getBytes("ISO-8859-1") , entryCharset);
			}

		}else {
			return value;
		}
	}

	public static String getRedirectUrl(String url) {

		DefaultHttpClient httpclient = new DefaultHttpClient();

		httpclient.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT, 10000); //超时设置
		httpclient.getParams().setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 10000);//连接超时
		httpclient.getParams().setParameter(ClientPNames.HANDLE_REDIRECTS, false);
		httpclient.getParams().setParameter("http.useragent", "Mozilla/5.0 (X11; U; Linux i686; zh-CN; rv:1.9.1.2) Gecko/20090803 Fedora/3.5.2-2.fc11 Firefox/3.5.2");
		//List<BasicHeader> headers = new ArrayList<BasicHeader>();
		//headers.add(new BasicHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)"));
		// httpclient.getParams().setParameter("http.default-headers", headers);
		//httpclient.getParams().setIntParameter(HttpConnectionParams., 5000);//连接超时

		httpclient.setHttpRequestRetryHandler(new Retry());
		//HttpPost httpPost = new HttpPost(url);
		HttpGet get = new HttpGet(url);
		//httpPost.getParams().setBooleanParameter(arg0, arg1)

		logger.debug("请求地址：" + get.getURI());
		HttpResponse response = null;
		String key = "Location";
		try {
			response = httpclient.execute(get);

			Header[] headers = response.getAllHeaders();
			if(headers != null && headers.length > 0){

				for(Header header : headers){

					if(key.equals(header.getName())){
						return header.getValue();
					}
				}
			}
			// System.out.println(Arrays.toString(response.getAllHeaders()));
		} catch (IOException ex) {
			logger.error("Util#httpclient.execute(httpost) " + ex.toString());
			//ex.printStackTrace();
		} catch (Exception ex) {
			logger.error("Util#httpclient.execute(httpost) " + ex.toString());
			//ex.printStackTrace();
		}

		return null;
	}


	/**
	 * 获取远程地址的返回代码
	 * @param url 远程地址，例如http://pay.mapbar.com/index.html
	 * @return  返回值,当失败是返回null
	 */
	public static String getSrcAndSetParentUrl(String url , boolean needEncode,  String parentUrl) {

		return getSrc(url, needEncode, null, parentUrl);

	}
	/**
	 * 获取远程地址的返回代码
	 * @param url 远程地址，例如http://pay.mapbar.com/index.html
	 * @return  返回值,当失败是返回null
	 */
	public static String getSrc(String url , boolean needEncode) {

		return getSrc(url, needEncode, null);

	}

	/**
	 * 获取远程地址的返回代码
	 * @param url 远程地址，例如http://pay.mapbar.com/index.html
	 * @return  返回值,当失败是返回null
	 */
	public static String getSrc(String url , boolean needEncode, String charset) {

		return getSrc(url, needEncode, charset, null);

	}

    /**
     * 获取远程地址的返回代码
     * @param url 远程地址，例如http://pay.mapbar.com/index.html
     * @return  返回值,当失败是返回null
     */
    public static String getSrc(HttpClient httpclient, String url , boolean needEncode, String charset, String parentUrl) {

//		if(url != null && url.startsWith("file://")){
//
//			logger.debug("read local file " + url);
//			return FileUtil.readFile(url.replace("file://", ""));
//		}
        //设置代理

        httpclient.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT, 10000); //超时设置
        httpclient.getParams().setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 10000);//连接超时

        httpclient.getParams().setParameter("http.useragent", "Mozilla/5.0 (X11; U; Linux i686; zh-CN; rv:1.9.1.2) Gecko/20090803 Fedora/3.5.2-2.fc11 Firefox/3.5.2");
        //List<BasicHeader> headers = new ArrayList<BasicHeader>();
        //headers.add(new BasicHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)"));
        // httpclient.getParams().setParameter("http.default-headers", headers);
        //httpclient.getParams().setIntParameter(HttpConnectionParams., 5000);//连接超时


        if(httpclient instanceof DefaultHttpClient)
            ((DefaultHttpClient)httpclient).setHttpRequestRetryHandler(new Retry());
        //HttpPost httpPost = new HttpPost(url);
        HttpGet get = new HttpGet(url);
        //httpPost.getParams().setBooleanParameter(arg0, arg1)

        if(parentUrl != null)
            get.addHeader("Referer", parentUrl);

        logger.debug("请求地址：" + get.getURI());
        HttpResponse response = null;
        try {
			HttpClientConnectionManager connectionManager = (HttpClientConnectionManager)httpclient.getConnectionManager();
            response = httpclient.execute(get);
        } catch (IOException ex) {
            logger.error("Util#httpclient.execute(httpost) " + ex.toString());
            //ex.printStackTrace();
        } catch (Exception ex) {
            logger.error("Util#httpclient.execute(httpost) " + ex.toString());
            //ex.printStackTrace();
        }


        if (response == null) {
            logger.error("获取接口数据异常,返回值为空,url=" + url);
            return null;
        } else {
            logger.debug(response.getStatusLine() + "," + get.getURI());
        }

        //获取代码
        HttpEntity entity = null;
        try {
            entity = response.getEntity();
            if (entity != null) {

                return processEntry(entity, response, needEncode, charset);
                //System.out.println(sb);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取接口返回值异常：" + e.toString() + ",url=" + url);
        } finally {
            //关闭
//            try {
//                httpclient.getConnectionManager().shutdown();
//
//            } catch (Exception e) {
//                logger.error("关闭httpclient异常" + e.toString());
//            }
//            httpclient = null;
//            httpPost = null;
//            response = null;
//            entity = null;

        }

        return null;

    }


    /**
     * 获取远程地址的返回代码
     * @param url 远程地址，例如http://pay.mapbar.com/index.html
     * @return  返回值,当失败是返回null
     */
    public static void download( String url , File outputFile, String parentUrl) {

//		if(url != null && url.startsWith("file://")){
//
//			logger.debug("read local file " + url);
//			return FileUtil.readFile(url.replace("file://", ""));
//		}
        //设置代理

        DefaultHttpClient httpclient = new DefaultHttpClient();
		//httpclient.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT, 10000); //超时设置
		//httpclient.getParams().setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 10000);//连接超时
		HttpParams params = httpclient.getParams();
		if(params==null){
			params = new BasicHttpParams();
		}
		HttpConnectionParams.setConnectionTimeout(params, 10*1000);
		HttpConnectionParams.setSoTimeout(params, 10*1000);
//	params.setParameter(
//	CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
//	params.setParameter(
//	CoreConnectionPNames.SO_TIMEOUT, 10000);
		httpclient.setParams(params);

        httpclient.getParams().setParameter("http.useragent", "Mozilla/5.0 (X11; U; Linux i686; zh-CN; rv:1.9.1.2) Gecko/20090803 Fedora/3.5.2-2.fc11 Firefox/3.5.2");
        //List<BasicHeader> headers = new ArrayList<BasicHeader>();
        //headers.add(new BasicHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)"));
        // httpclient.getParams().setParameter("http.default-headers", headers);
        //httpclient.getParams().setIntParameter(HttpConnectionParams., 5000);//连接超时


        if(httpclient instanceof DefaultHttpClient)
            ((DefaultHttpClient)httpclient).setHttpRequestRetryHandler(new Retry());
        //HttpPost httpPost = new HttpPost(url);
        HttpGet get = new HttpGet(url);
        //httpPost.getParams().setBooleanParameter(arg0, arg1)

        if(parentUrl != null)
            get.addHeader("Referer", parentUrl);

        logger.debug("请求地址：" + get.getURI());
        HttpResponse response = null;
        try {
            response = httpclient.execute(get);
        } catch (IOException ex) {
            logger.error("Util#httpclient.execute(httpost) " + ex.toString());
            //ex.printStackTrace();
        } catch (Exception ex) {
            logger.error("Util#httpclient.execute(httpost) " + ex.toString());
            //ex.printStackTrace();
        }


        if (response == null) {
            logger.error("获取接口数据异常,返回值为空,url=" + url);
        } else {
            logger.debug(response.getStatusLine() + "," + get.getURI());
        }

        //获取代码
        HttpEntity entity = null;
        try {
            entity = response.getEntity();
            if (entity != null) {

                OutputStream outputStream = new FileOutputStream(outputFile);
                InputStream inputStream = entity.getContent();

                byte buffer[] = new byte[1024 * 10];
                int len = 0;
                while((len = inputStream.read(buffer)) > 0){
                    outputStream.write(buffer, 0, len);
                }

                outputStream.close();
                inputStream.close();
                //System.out.println(sb);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取接口返回值异常：" + e.toString() + ",url=" + url);
        } finally {
            //关闭
//            try {
//                httpclient.getConnectionManager().shutdown();
//
//            } catch (Exception e) {
//                logger.error("关闭httpclient异常" + e.toString());
//            }
//            httpclient = null;
//            httpPost = null;
//            response = null;
//            entity = null;

        }


    }

	/**
	 * 获取远程地址的返回代码
	 * @param url 远程地址，例如http://pay.mapbar.com/index.html
	 * @return  返回值,当失败是返回null
	 */
	public static String getSrc(String url , boolean needEncode, String charset, String parentUrl) {


        if(url.startsWith("https:")){

            DefaultHttpClient defaultHttpClient = new DefaultHttpClient();

            try {
                SSLContext ctx = SSLContext.getInstance("TLS");
                X509TrustManager tm = new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(X509Certificate[] arg0,
                                                   String arg1) throws CertificateException {
                    }

                    public void checkServerTrusted(X509Certificate[] arg0,
                                                   String arg1) throws CertificateException {
                    }
                };
                ctx.init(null, new TrustManager[] { tm }, null);
                SSLSocketFactory ssf = new SSLSocketFactory(ctx);
                ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

                SchemeRegistry registry = new SchemeRegistry();
                registry.register(new Scheme("https", ssf,443));

                ThreadSafeClientConnManager mgr = new ThreadSafeClientConnManager(defaultHttpClient.getParams(), registry);
                defaultHttpClient =  new DefaultHttpClient(mgr, defaultHttpClient.getParams());
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }

            return getSrc(defaultHttpClient, url, needEncode, charset, parentUrl);

        }else {
            return getSrc(new DefaultHttpClient(), url, needEncode, charset, parentUrl);
        }


	}

	public static String getValue(String src , String regex , int id){

		if(src != null && regex != null){


			Matcher matcher = Pattern.compile(regex,Pattern.DOTALL).matcher(src);



			if(matcher != null){

				if(matcher.find()){

					return matcher.group(id);
				}
			}
		}
		return null;
	}
	public static Map<String, String> getValues(String src , String regex,int urlId , int titleId){

		if(src != null && regex != null){


			Matcher matcher = Pattern.compile(regex).matcher(src);
			if(matcher != null){
				Map<String, String> map  = new LinkedHashMap<String, String>();
				while(matcher.find()){

					map.put(matcher.group(urlId), matcher.group(titleId));
				}
				return map;
			}

		}
		return null;
	}

	public static List<String> getValues(String src , String regex, int regexId){

		if(src != null && regex != null){


			Matcher matcher = Pattern.compile(regex,Pattern.DOTALL).matcher(src);
			if(matcher != null){
				List<String> list = new ArrayList<String>();
				while(matcher.find()){
					String str = matcher.group(regexId);
					//str = str.replace("<li class=\"v_title\">", "");
					list.add(str);
				}
				return list;
			}

		}
		return null;
	}

	public static List<String[]> getValues(String src , String regex, int regexId[]){

		//System.out.println(src);
		//System.out.println(regex);

		if(src != null && regex != null)
		{
			Matcher matcher = Pattern.compile(regex,Pattern.DOTALL).matcher(src);
			if(matcher != null)
			{
				List<String[]> list = new ArrayList<String[]>();
				String cur[];
				int len = regexId.length;
				while(matcher.find()){

					cur = new String[regexId.length];
					for(int i = 0; i < len; i++){

						if(regexId[i] == -1){
							cur[i] = StringUtil.NULL_STRING;
						}else{
							String tmp = matcher.group(regexId[i]);
							cur[i] = tmp;
						}

					}
					list.add(cur);
					//break;
				}
				return list;
			}

		}
		return null;
	}
	private static HttpClient gettHttpClient() {
		HttpClient httpclient = null;
		if(httpclient == null){
			try {
				X509TrustManager tm = new X509TrustManager() {
					public void checkClientTrusted(X509Certificate[] arg0,
					                               String arg1) throws CertificateException {
					}

					public void checkServerTrusted(X509Certificate[] arg0,
					                               String arg1) throws CertificateException {
					}

					public X509Certificate[] getAcceptedIssuers() {
						return null;
					}
				};
				SSLContext sslcontext = SSLContext.getInstance("TLS");
				sslcontext.init(null, new TrustManager[] { tm }, null);
				SSLSocketFactory ssf = new SSLSocketFactory(sslcontext);
				ClientConnectionManager ccm = new DefaultHttpClient().getConnectionManager();
				SchemeRegistry sr = ccm.getSchemeRegistry();
				//sr.register(new Scheme("https", 8443, ssf));
				HttpParams params = new BasicHttpParams();
				params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
				params.setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);
				httpclient = new DefaultHttpClient(ccm,params);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return httpclient;
	}
	/**
	 * @author fbj
	 * @version 2014-3-21 上午11:44:32
	 * @param args
	 * @throws ParseException
	 * @throws IOException
	 */
	public static void main(String[] args) throws ParseException, IOException {
		//http://click.tool.hexun.com/click.aspx?articleid=87783063&blogid=

//		String msg = "------WebKitFormBoundaryZxKqcqUlzcqpQ1ir\n" +
//				"Content-Disposition: form-data; name=\"x-acl-token\"\n" +
//				"\n" +
//				"xZpiPWo-/vHXmb9fXOtP3EIYUmwK\n" +
//				"------WebKitFormBoundaryZxKqcqUlzcqpQ1ir\n" +
//				"Content-Disposition: form-data; name=\"username\"\n" +
//				"\n" +
//				"fandyvon\n" +
//				"------WebKitFormBoundaryZxKqcqUlzcqpQ1ir--";
//		Map<String, Object> p = new HashMap<String, Object>();
//		p.put("usrname", "fandyvon");
//		p.put("x-acl-token", "xZpiPWo-/vHXmb9fXOtP3EIYUmwK");
//		System.out.println(
//				post("http://internalapi.csdn.net/uc/userinfo/userinfo/getdetailbyusername", msg, p,null,false,null,true)
//
//		);
        //System.out.println(getSrc("https://api.500px.com/v1/photos?rpp=100&feature=popular&image_size%5B%5D=3&image_size%5B%5D=5&image_size%5B%5D=2048&page=2&sort=&include_states=true&formats=jpeg%2Clytro&only=&authenticity_token=0o1t1SPGyFGzJXC1us5eQ5Rd3vbHwxyLMvtJgqbcYPg%3D&consumer_key=xif2NmQYbJ3d5xwrcU7yHC4ZwO8Hj3NHLmfSRHp5", false));
//    	System.out.println(getSrc("http://tudou.letv.com/playlist/p/le/psS84rawl60/play.html", true));

		//curl -XPUT  "http://123.125.89.152:9200/pid/day/1/_update" -d '{"script" : "ctx._source.text = \"some text\""}'

//	Map<String, String> map = new HashMap<String, String>();
//	map.put("-d", "{\"script\" : \"ctx._source.text\" = \"some text\"}");

//	Map<String, Object> headers = new HashMap<String, Object>();
//	headers.put("authentication-token", "bca863abd05084319371e9057c468fc2");
//	//headers.put("Accept-Encoding", "gzip, deflate");
//	//headers.put("Content-Type", "application/json; charset=utf-8");
//	System.out.println(post("https://newalitest.csdn.net/api/v3/open_knowledge/webpag_to_doc", "", headers, false , null));
//	Map<String, String> headers = new HashMap<String, String>();
//	
//	headers.put("Accept-Encoding", "gzip, deflate");
//
//	headers.put("Content-Type", "application/json; charset=utf-8");
////
////		        httppost.addHeader("X-Requested-With", "XMLHttpRequest");
////
//	headers.put("Referer", "http://www.cnblogs.com/");
		//System.out.println(post("http://www.cnblogs.com/mvc/blog/BlogPostInfo.aspx", "{\"blogId\":58166,\"postId\":3456631,\"blogApp\":\"sosoft\",\"blogUserGuid\":\"889f8683-1b69-de11-9510-001cf0cd104b\"}", headers, true, "utf8"));
//    	String log = "2012-07-05 00:00:00,\"GET /adpolestar/door_mp/;ap=8D29C93A_29C9_8B9F_E5C7_333EE1FC9902%7CD72FFEB4_CA22_091B_21F6_7207213D1E23%7CA47C4845_FB34_8D7F_8CEB_2716B1DEE2DD%7C49A905B3_95E3_0BB8_375F_1824E8268897%7C72AC7BC2_953E_11DA_45FC_E77CA2F73206%7CF0112397_6390_0E6B_E6A8_8CEDD805E951%7C4E0118F9_ACDA_5732_4498_81E9354B9793%7C03794151_1893_E56E_40F8_A520AF4AFBB4%7C086D6F20_862B_ABF4_5F98_2F076BC1A3D8%7CCD0BA071_412B_9E07_BA0A_860519F2D5FE;ext_key=vid688788,typeFrom100,cid1,pid21977,adr0000,adType3;ct=xml;pu=letv;/? HTTP/1.1\",200,\"u_id=letv;place_id=8D29C93A_29C9_8B9F_E5C7_333EE1FC9902|D72FFEB4_CA22_091B_21F6_7207213D1E23|A47C4845_FB34_8D7F_8CEB_2716B1DEE2DD|49A905B3_95E3_0BB8_375F_1824E8268897|72AC7BC2_953E_11DA_45FC_E77CA2F73206|F0112397_6390_0E6B_E6A8_8CEDD805E951|4E0118F9_ACDA_5732_4498_81E9354B9793|03794151_1893_E56E_40F8_A520AF4AFBB4|086D6F20_862B_ABF4_5F98_2F076BC1A3D8|CD0BA071_412B_9E07_BA0A_860519F2D5FE|00000000_0000_0000_0000_000000000000;ad_id=00000000_0000_0000_0000_000000000000|00000000_0000_0000_0000_000000000000|00000000_0000_0000_0000_000000000000|00000000_0000_0000_0000_000000000000|00000000_0000_0000_0000_000000000000|00000000_0000_0000_0000_000000000000|00000000_0000_0000_0000_000000000000|00000000_0000_0000_0000_000000000000|00000000_0000_0000_0000_000000000000|00000000_0000_0000_0000_000000000000|00000000_0000_0000_0000_000000000000;lang=zh-CN;reffer=http://player.letvcdn.com/p/201207/03/1737/player/zhuzhan/plugins/lead.swf;extkey=0|0|0|0|0|0|0|0|0|0|0;\",61.145.197.125,pro.letv.com,\"http://player.letvcdn.com/p/201207/03/1737/player/zhuzhan/plugins/lead.swf\",\"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)\",\"5711334766324139\"";
//    	String log1 = "2012-07-05 00:00:00,\"";
//    	int[] arr = {1,2,3,4,5,6,7,8,9}; 
//    	
////    	List<String> stra = CatchUtil.getValues(log, "(.*?),.*?\"(.*?)\".*?,(.*?),.*?\"(.*?)\".*?,(.*?),(.*?),.*?\"(.*?)\",.*?\"(.*?)\",.*?\"(.*?)\"", 9);
//    	
//    	List<String[]> ap = CatchUtil.getValues(log, "(.*?),.*?\"(.*?)\".*?,(.*?),.*?\"(.*?)\".*?,(.*?),(.*?),.*?\"(.*?)\",.*?\"(.*?)\",.*?\"(.*?)\"", arr);
////    	if(null)
////    		System.out.println("1");
////    	System.out.println(ap.isEmpty());
//    	String[] list = ap.get(0);
//    	
//    	System.out.println(list[0]);
//    	System.out.println(list[1]);
//    	System.out.println(list[2]);
//    	System.out.println(list[3]);
//    	System.out.println(list[4]);
//    	System.out.println(list[5]);
//    	System.out.println(list[6]);
//    	System.out.println(list[7]);
//    	System.out.println(list[8]);

	}



}

class Retry implements HttpRequestRetryHandler{

	public boolean retryRequest(IOException arg0, int executionCount, HttpContext arg2) {

		if (executionCount >= 3) {
			// 超过最大次数则不需要重试

			return false;
		}else {
			System.out.println("重试：" + executionCount);
			return true;
		}


	}


}
