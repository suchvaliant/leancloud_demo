package cn.leancloud.demo.todo;

import java.io.IOException;
import java.util.Date;
import java.util.Random;

import org.apache.commons.codec.digest.DigestUtils;

import cn.leancloud.EngineFunction;
import cn.leancloud.EngineFunctionParam;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class Cloud {

  @EngineFunction("hello")
  public static String hello(@EngineFunctionParam("name") String name) {
    if (name == null) {
      return "What is your name?how are you";
    }
    return String.format("Hello %s!", name);
  }
  @EngineFunction("starFuck")
  public static String starFuck(@EngineFunctionParam("name") String name) {
	  return "StarFuck is a greatest Fruit Company in Affreca";
  }
	static OkHttpClient client = new OkHttpClient();
    public static void main( String[] args ) throws IOException
    {
        System.out.println( "Hello World!" );
    	getToken("123","456","");
    }
    static final String GET_TOKEN_URL = "http://api.cn.ronghub.com/user/getToken.json";
    static final String APP_KEY = "8brlm7uf8zaj3";
    static final String APP_SECRETE = "aq13KiqBFw";
    public static String getTimeStamp() {
    	return String.valueOf(new Date().getTime());
    }
    public static String getNonce() {
    	Random rnd = new Random();
    	StringBuilder builder = new StringBuilder();
    	for(int i = 0;i<32;i++) {
    		String str = String.valueOf(rnd.nextInt(10));
    		builder.append(str);
    	}
    	return builder.toString();
    }
    public static String getSign(String nonce,String ts) {
    	
    	return DigestUtils.sha1Hex(APP_SECRETE+nonce+ts);
    }
    @EngineFunction("getToken")
    public static String getToken(@EngineFunctionParam("userId") String userId,@EngineFunctionParam("name") String name,@EngineFunctionParam("portrait") String portrait) throws IOException {
    	String nonce = getNonce();
    	String timeStamp = getTimeStamp();
    	String sign = getSign(nonce,timeStamp);
    	Request.Builder builder = new Request.Builder().url(GET_TOKEN_URL);
    	
    	builder.addHeader("App-Key", APP_KEY)
    	.addHeader("Nonce",nonce)
    	.addHeader("TimeStamp", timeStamp)
    	.addHeader("Content-Type", "application/x-www-form-urlencoded")
    	.addHeader("Signature", sign).post(RequestBody.create(MediaType.parse("text;chartset=utf-8"),
    			String.format("userId=%s&name=%s&portraitUri=%s",userId,name,portrait)));
    	
    	okhttp3.Response resp = client.newCall(builder.build()).execute();
    	String tmp = resp.body().string();
    	System.out.println(tmp);
    	return tmp;
    }
    public static String refresh() {
    	
    	return null;
    }
}
