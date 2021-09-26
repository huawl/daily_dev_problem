package com.huawl.spring.beans.definition;

import com.huawl.spring.annocation.Autowire;
import com.huawl.spring.annocation.Component;
import com.huawl.spring.annocation.Controller;
import com.huawl.spring.util.AnnotationUtil;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Luther
 * @version 1.0
 * @date 2021/7/16 18:20
 */
public class AnnocationBeanDefinitionReader extends AbstractBeanDefinitionReader{

    private List<File> classFiles = new ArrayList<>();

    @Override
    public void loadBeanDefinition(String basePackge)  throws Exception {
        URL url = AnnocationBeanDefinitionReader.class.getClass().getResource("/");
        File root = new File(url.getFile());
        registerClass(root);
        Map<String,BeanDefinition> registry = getRegistry();
        for (int i = 0; i < classFiles.size(); i++) {
            File f = classFiles.get(i);
            BeanDefinition bd = new BeanDefinition();
            String className = f.getName().substring(0,f.getName().indexOf("."));
            String name = className.substring(0,1).toLowerCase() + className.substring(1);
            bd.setName(name);
            String replacePath = f.getPath().replaceAll("\\\\",".");
            String clazzPath = replacePath.substring(replacePath.indexOf(basePackge),replacePath.indexOf(".class"));
            Class clazz = Class.forName(clazzPath);
            if(AnnotationUtil.isClass(clazz,false,Controller.class,Component.class)){
                bd.setClazz(clazz);
                Field[] fs = clazz.getDeclaredFields();
                for (int j = 0; j < fs.length; j++) {
                    if(AnnotationUtil.isField(fs[j],false,Autowire.class)){
                        BeanReference beanReference = new BeanReference(fs[j].getName());
                        bd.getPropertyValues().addPropertyValue(new PropertyValue(fs[j].getName(), beanReference));
                    }
                }
                registry.put(name,bd);
            }
        }
    }

    private void registerClass(File root){
        File[] files = root.listFiles();
        for (int i = 0; i < files.length; i++) {
            File f = files[i];
            if(f.isDirectory()){
                registerClass(f);
            }else if(f.getName().endsWith(".class")){
                classFiles.add(f);
            }
        }
    }

}
