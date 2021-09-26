package com.huawl.spring.context;

import com.huawl.spring.beans.definition.AbstractBeanDefinitionReader;
import com.huawl.spring.beans.definition.AnnocationBeanDefinitionReader;
import com.huawl.spring.beans.definition.BeanDefinition;
import com.huawl.spring.beans.definition.XmlBeanDefinitionReader;
import com.huawl.spring.beans.factory.AbstractBeanFactory;
import com.huawl.spring.beans.factory.AutowireCapableBeanFactory;
import com.huawl.tomcat.webserver.BioTomcatWebServer;
import com.huawl.tomcat.webserver.NioTomcatWebServer;
import com.huawl.tomcat.webserver.WebServer;
import com.huawl.spring.servlet.DispatcherServlet;

import java.util.Map;

/**
 * @author Luther
 * @version 1.0
 * @date 2021/7/16 17:27
 */
public class ClassPathApplicationContext extends AbstractApplicationContext{

    private String configLocation;

    public ClassPathApplicationContext(String configLocation) throws Exception {
        this(configLocation,new AutowireCapableBeanFactory());
    }

    public ClassPathApplicationContext(String configLocation, AutowireCapableBeanFactory beanFactory) throws Exception {
        super(beanFactory);
        this.configLocation = configLocation;
        refresh();
    }

    @Override
    protected void loadBeanDefinitions(AbstractBeanFactory beanFactory) throws Exception {
        AbstractBeanDefinitionReader reader = configLocation.contains(".xml") ? new XmlBeanDefinitionReader() :  new AnnocationBeanDefinitionReader();
        reader.loadBeanDefinition(configLocation);
        Map<String, BeanDefinition> registry = reader.getRegistry();
        for (Map.Entry<String, BeanDefinition> entry:registry.entrySet()){
            beanFactory.addBeanDefinition(entry.getKey(),entry.getValue());
        }
    }

    @Override
    protected void onRefresh() throws Exception {
        WebServer tomcat = new BioTomcatWebServer(new DispatcherServlet(this));
        tomcat.start();
    }
}
