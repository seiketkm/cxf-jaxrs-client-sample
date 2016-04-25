package example;

import javax.net.ssl.HttpsURLConnection;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

public class Main {
    public static void main(String[] args) throws Exception{
        //デフォルトのSSLSocketFactoryを変える。
        //HttpsURLConnection.setDefaultSSLSocketFactory(new MySSLSocketFactory());

        // 起動時刻
        long startTime = System.currentTimeMillis();
        while(true) {
            long currentTime = System.currentTimeMillis();
            if(currentTime - startTime > 5000) // 5秒間でループ抜ける
                break;
            // cxf client
            Client client = ClientBuilder.newBuilder().newClient();
            WebTarget target = client.target(args[0] != null ? args[0] : "https://www.google.co.jp/");
            Invocation.Builder builder = target.request();
            Response response = builder.get();

            System.out.println(response);

            Thread.sleep(800);
        }
    }
}
