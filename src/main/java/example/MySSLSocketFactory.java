package example;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

public final class MySSLSocketFactory extends SSLSocketFactory {
    /** SSLSocketFactory partionkey:SSLSocketFactory */
    private ConcurrentMap<String, SSLSocketFactory> ssfMap = new ConcurrentHashMap<String, SSLSocketFactory>();

    private SSLSocketFactory findSocketFactoryCacheByTime() {
        String partitionName = partitionByCurrentTime();
		return ssfMap.computeIfAbsent(partitionName, (x) -> createSsf(x));
    }

    private SSLSocketFactory createSsf(String alias) {
        try {
            SSLContext context = SSLContext.getInstance("SSL");
            context.init(null, null, null);
            return context.getSocketFactory();
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    private String partitionByCurrentTime(){
        long partition = System.currentTimeMillis() % (3000L);
        if(partition < 1000L)
            return "hoge";
        else if(partition < 2000L)
            return "fuga";
        else
            return "piyo";
    }
    @Override
    public Socket createSocket(Socket s, String host, int port,
            boolean autoClose) throws IOException {
    	SSLSocketFactory ssf = findSocketFactoryCacheByTime();
        return ssf.createSocket(s, host, port, autoClose) ;
    }

    @Override
    public String[] getDefaultCipherSuites() {
    	SSLSocketFactory ssf = findSocketFactoryCacheByTime();
    	return ssf.getDefaultCipherSuites();
    }

    @Override
    public String[] getSupportedCipherSuites() {
       	SSLSocketFactory ssf = findSocketFactoryCacheByTime();
        return ssf.getSupportedCipherSuites();
    }

    @Override
    public Socket createSocket(InetAddress address, int port,
            InetAddress localAddress, int localPort) throws IOException {
       	SSLSocketFactory ssf = findSocketFactoryCacheByTime();
        return ssf.createSocket(address, port, localAddress, localPort);
    }

    @Override
    public Socket createSocket(InetAddress host, int port) throws IOException {
       	SSLSocketFactory ssf = findSocketFactoryCacheByTime();
        return ssf.createSocket(host, port);
    }

    @Override
    public Socket createSocket(String host, int port, InetAddress localHost,
            int localPort) throws IOException {
       	SSLSocketFactory ssf = findSocketFactoryCacheByTime();
        return ssf.createSocket(host, port, localHost, localPort);
    }

    @Override
    public Socket createSocket(String host, int port) throws IOException{
       	SSLSocketFactory ssf = findSocketFactoryCacheByTime();
    	return ssf.createSocket(host, port);
    }
}
