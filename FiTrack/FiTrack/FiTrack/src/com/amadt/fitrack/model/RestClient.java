package com.amadt.fitrack.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;
 
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ssl.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;
 
import android.net.Uri;
import android.util.Log;
 
public class RestClient {


	public static HttpClient getNewHttpClient() {
	    try {
	        KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
	        trustStore.load(null, null);

	        SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
	        sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

	        HttpParams params = new BasicHttpParams();
	        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
	        HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

	        SchemeRegistry registry = new SchemeRegistry();
	        registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
	        registry.register(new Scheme("https", sf, 443));

	        ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

	        return new DefaultHttpClient(ccm, params);
	    } catch (Exception e) {
	        return new DefaultHttpClient();
	    }
	}
 
	private static JSONObject getJSONObject(String message) throws RESTException {
		JSONObject json = new JSONObject();
		
		try {
			json = new JSONObject(message);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RESTException(e.getMessage());
		}
		
		return json;
	}
	
    private static String convertStreamToString(InputStream is) {
        /*
         * To convert the InputStream to String we use the BufferedReader.readLine()
         * method. We iterate until the BufferedReader return null which means
         * there's no more data to read. Each line will appended to a StringBuilder
         * and returned as String.
         */
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
 
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
    
	public static class Parameter {
		
		private String name;
		private String value;
		
		public Parameter(String name, String value) {
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}
    
    public static class RESTException extends Exception {
		/**
		 * auto generated serial UID
		 */
		private static final long serialVersionUID = 118269484402523541L;
		
		private String reason = "REST Request Failed";
		
		public RESTException(String reason) {
			this.reason = reason;
		}
		
		@Override
		public String toString() {
			return reason;
		}
		
		@Override
		public String getMessage() {
			return reason;
		}
    }
    
    private static String createRestURI(String url, String method, Parameter [] params) {
    	int i=0;
    	
    	String uri = url + method;
    	
    	for (Parameter param : params) {
    		if (i == 0) {
    			uri += "?";
    		}
    		
    		uri += String.format("%s=%s", param.getName(), Uri.encode(param.getValue()));
    		
    		++i;
    		if (i < params.length) {
    			uri += "&";
    		}
    	}
    	    	
    	return uri;
    }
    
    public static String executeMethod(String url, String method, Parameter [] params) throws RESTException {
        
        HttpClient client = getNewHttpClient();  	
    	
    	
    	String uri = createRestURI(url, method, params);
    	String result = null;
    	
    	HttpGet httpGet = new HttpGet(uri);
    	
    	HttpParams httpParameters = new BasicHttpParams();
    	
    	// Set the timeout in milliseconds until a connection is established.
    	// The default value is zero, that means the timeout is not used. 
    	int timeoutConnection = 3000;
    	HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
    	
    	// Set the default socket timeout (SO_TIMEOUT) 
    	// in milliseconds which is the timeout for waiting for data.
    	int timeoutSocket = 5000;
    	HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);


    	// DefaultHttpClient client = new DefaultHttpClient(httpParameters);

    	Log.i("FiTrack", uri);
    	
    	HttpResponse httpResponse;
    	try {
    		httpResponse = client.execute(httpGet);

    		Log.i("FiTrack", httpResponse.getStatusLine().toString());
    		Log.i("FiTrack", httpResponse.getStatusLine().getReasonPhrase());

    		HttpEntity entity = httpResponse.getEntity();
            InputStream instream = entity.getContent();
            result = convertStreamToString(instream);
    		Log.i("FiTrack", result);
    		
    		if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
    			
    			try {
    				JSONObject json = getJSONObject(result);
    				throw new RESTException(json.getString("message"));
    			}
    			catch (Exception e) {
        			throw new RestClient.RESTException(e.getMessage());
    			}
    		}
    		
        } catch (ClientProtocolException e) {
            e.printStackTrace();
			throw new RESTException("Unable to communicate with the server");
        } catch (IOException e) {
            e.printStackTrace();
			throw new RESTException("Unable to communicate with the server");
        } 
    	
    	return result;
    }
    
    public static JSONObject execute(String url, String method, Parameter [] params) throws RESTException {
    	return getJSONObject(executeMethod(url, method, params));
    }
}