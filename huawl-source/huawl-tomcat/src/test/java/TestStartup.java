import com.huawl.tomcat.processor.HelloWorldServlet;
import com.huawl.tomcat.webserver.BioTomcatWebServer;
import com.huawl.tomcat.webserver.WebServer;

/**
 * @author Luther
 * @version 1.0
 * @date 2021/8/10 18:29
 */
public class TestStartup {

    public static void main(String[] args) {
        WebServer tomcat = new BioTomcatWebServer(new HelloWorldServlet());
        tomcat.start();
    }
}
