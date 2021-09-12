package com.ivan;

import com.ivan.pojo.ServicePojo;
import com.ivan.server.RequestProcessor;
import com.ivan.servlet.HttpServlet;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author hylu.ivan
 * @date 2021/9/9 下午7:26
 * @description
 */
public class Bootstrap {

    private int port = 8080;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    /**
     * minicat启动初始化
     */
    public void start() {
        try {
            // 读取server.xml中所有的Service信息
            List<ServicePojo> servicePojos = loadInfo("server.xml");
            for (ServicePojo servicePojo : servicePojos) {
                // 根据service路径进行包下web.xml配置文件读取以及映射关系建立
                dealWebapps(servicePojo.getAppBase());
            }


            // 定义一个线程池
            int corePoolSize = 10;
            int maximumPoolSize =50;
            long keepAliveTime = 100L;
            TimeUnit unit = TimeUnit.SECONDS;
            BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(50);
            ThreadFactory threadFactory = Executors.defaultThreadFactory();
            RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();

            ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                    corePoolSize,
                    maximumPoolSize,
                    keepAliveTime,
                    unit,
                    workQueue,
                    threadFactory,
                    handler
            );

            // 浏览器请求返回一个hello
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("minicat start on " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                RequestProcessor requestProcessor = new RequestProcessor(socket, servletMap);
                threadPoolExecutor.execute(requestProcessor);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private Map<String, HttpServlet> servletMap = new HashMap<String,HttpServlet>();

    /**
     * 加载解析web.xml，初始化Servlet
     */
    private void loadServlet(String filename) {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(filename);

        SAXReader saxReader = new SAXReader();

        try {
            Document document = Bootstrap.parse2Document(filename);
            Element rootElement = document.getRootElement();

            List<Element> selectNodes = rootElement.selectNodes("//servlet");
            for (int i = 0; i < selectNodes.size(); i++) {
                Element element =  selectNodes.get(i);
                // <servlet-name>lagou</servlet-name>
                Element servletnameElement = (Element) element.selectSingleNode("servlet-name");
                String servletName = servletnameElement.getStringValue();
                // <servlet-class>server.LagouServlet</servlet-class>
                Element servletclassElement = (Element) element.selectSingleNode("servlet-class");
                String servletClass = servletclassElement.getStringValue();


                // 根据servlet-name的值找到url-pattern
                Element servletMapping = (Element) rootElement.selectSingleNode("/web-app/servlet-mapping[servlet-name='" + servletName + "']");
                // /lagou
                String urlPattern = servletMapping.selectSingleNode("url-pattern").getStringValue();
                System.out.println(servletClass);
                servletMap.put(urlPattern, (HttpServlet) Class.forName(servletClass).newInstance());

            }

        }  catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * 加载解析server.xml，读取配置信息
     */
    private List<ServicePojo> loadInfo(String filename) {

        List<ServicePojo> res = new ArrayList<>();
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(filename);
        SAXReader saxReader = new SAXReader();

        try {
            Document document = saxReader.read(resourceAsStream);
            Element rootElement = document.getRootElement();
            List<Element> selectNodes = rootElement.selectNodes("//Service");
            for (int i = 0; i < selectNodes.size(); i++) {

                Element element =  selectNodes.get(i);
                // <Concentor port="8080"></Concentor>
                Element connector = (Element) element.selectSingleNode("Concentor");
                String port = connector.attributeValue("port");


                // 遍历Engine下所有的host节点
                Element engine = (Element)element.selectSingleNode("Engine");
                List<Element> hosts = engine.selectNodes("Host");
                for (Element host : hosts) {
                    // <Host name="localhost" appBase="/xxx"></Host>
                    String name = host.attributeValue("name");
                    String appBase = host.attributeValue("appBase");
                    ServicePojo servicePojo = new ServicePojo(name, port, appBase);
                    res.add(servicePojo);
                }
            }

        }  catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            return res;
        }

    }

    /**
     * 遍历webapps下所有文件夹读取web.xml获取所有映射
     * @param path
     */
    private void dealWebapps(String path) {
        File file = new File(path);
        if (file.exists() && file.isDirectory()) {
            System.out.println("find webapps");
            File[] files = file.listFiles();
            for (File file1 : files) {
                if (file1.isDirectory()) {
                    File[] files1 = file1.listFiles();
                    for (File file2 : files1) {
                        if (file2.getName().contains("web.xml")) {
                            loadServlet(file2.getAbsolutePath());
                        }
                    }
                }

            }
        }
    }


    public static Document parse2Document (String xmlFilePath){

        SAXReader reader = new SAXReader();

        Document document = null;

        File f = new File(xmlFilePath);

        try {

            InputStream in = new FileInputStream(f);
            document = reader.read(in);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return document;

    }


    public static void main(String[] args) {

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.start();
    }
}
