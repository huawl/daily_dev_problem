package com.huawl.spring.beans.factory;

import com.huawl.spring.beans.definition.BeanDefinition;
import com.huawl.spring.beans.definition.BeanReference;
import com.huawl.spring.beans.definition.PropertyValue;
import com.huawl.spring.beans.definition.PropertyValues;
import com.huawl.spring.beans.processor.BeanPostProcessor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Luther
 * @version 1.0
 * @date 2021/7/16 16:22
 */
public abstract class AbstractBeanFactory implements BeanFactory{

    /***bean定义信息map**/
    private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    /**bean定义信息集合，主要是beanDefinitionMap里面的key的集合**/
    private List<String> beanDefinitionNames = new ArrayList<>();

    /****一级缓存，value成品对象,属性全部填充*/
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);

    /**二级缓存，value半成品对象,属性未填充，只实例化过*/
    private final Map<String, Object> earlySingletonObjects = new HashMap<>(16);

    /**bean周期扩展类**/
    private List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    /***
     * 根据类元信息实例化bean
     * @param beanDefinition
     * @return
     */
    private Object getInstantiateInstance(BeanDefinition beanDefinition){
        try {
            Class clazz = beanDefinition.getClazz();
            if(FactoryBean.class.isAssignableFrom(clazz)){
                return getFactoryBeanInstance(beanDefinition);
            }
            return clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected Object getFactoryBeanInstance(BeanDefinition beanDefinition){
        Class clazz = beanDefinition.getClazz();
        try {
            List<Class> parameterTypesList = beanDefinition.getParameterTypes();
            Class[] parameterTypes = new Class[parameterTypesList.size()];
            Class[] parameterValues = new Class[parameterTypesList.size()];
            for (int i = 0; i < parameterTypesList.size(); i++) {
                parameterTypes[i] = parameterTypesList.get(i).getClass();
                parameterValues[i] = parameterTypesList.get(i);
            }
            //这里构造函数使用通用Class对象
            Constructor ctor = clazz.getConstructor(parameterTypes);
            //这里生成实例使用类本身的对象
            FactoryBean factoryBean = (FactoryBean) ctor.newInstance(parameterValues);
            return factoryBean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 该方法需要做以下操作
     * 1.优先从一级缓存里面获取，有直接返回
     * 2.实例化bean开辟内存空间，将对象放到二级缓存
     * 3.往二级缓存对象填充属性
     * 4.移动二级缓存的对象到一级缓存
     * @param name
     * @return
     */
    @Override
    public Object getBean(String name) {
        //这里获取的是已经设置好属性的对象
        Object bean = singletonObjects.get(name);
        if(bean != null){
            return bean;
        }
        //这里放的是只实例化的对象
        bean = earlySingletonObjects.get(name);
        if(bean != null){
            return bean;
        }
        bean = doGetBean(name);
        return bean;
    }

    @Override
    public List<Object> getBean(Class type) {
        List<Object> list = new ArrayList<>();
        for (Map.Entry<String, BeanDefinition> entry:beanDefinitionMap.entrySet()) {
            BeanDefinition bd = entry.getValue();
            if(type.isAssignableFrom(bd.getClazz())){
                list.add(getBean(bd.getName()));
            }
        }
        return list;
    }

    @Override
    public Map<String, BeanDefinition> getBeanDefinitions(String name) {
        if(name != null && name != "" && beanDefinitionMap.containsKey(name)){
            Map<String, BeanDefinition> map = new HashMap<>(1);
            map.put(name,beanDefinitionMap.get(name));
            return map;
        }
        return beanDefinitionMap;
    }

    /**
     * 注册bean定义信息
     */
    public void addBeanDefinition(String name,BeanDefinition beanDefinition){
        beanDefinitionMap.put(name,beanDefinition);
        beanDefinitionNames.add(name);
    }

    /**
     * 预实例化所有的bean
     */
    public void preInstantiateSingletons(){
        for (int i = 0,length = beanDefinitionNames.size(); i < length; i++) {
            String name = beanDefinitionNames.get(i);
            getBean(name);
        }
    }

    /**
     * 加入BeanPostProcessor
     * @param beanPostProcessor
     */
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor){
        this.beanPostProcessors.add(beanPostProcessor);
    }

    /**
     * 创建bean
     * @param name
     * @return
     */
    private Object doGetBean(String name) {
        Object earlyInstance = earlySingletonObjects.get(name);
        //1.获取bean的实例化对象
        BeanDefinition beanDefinition = beanDefinitionMap.get(name);
        if(earlyInstance == null){
            //实例化bean
            earlyInstance = getInstantiateInstance(beanDefinition);
            //只要刚实例化的对象，内存地址已经确定就在二级缓存存放
            earlySingletonObjects.put(name,earlyInstance);
        }
        //2.初始化bean实例
        return initialization(beanDefinition,earlyInstance);
    }

    /**
     * 初始化bean
     * @param beanDefinition
     * @param earlyInstance
     * @return
     */
    protected Object initialization(BeanDefinition beanDefinition, Object earlyInstance){
        Object bean = populateBean(beanDefinition,earlyInstance);
        bean = initializeBean(bean,beanDefinition);
        return bean;
    }

    /**
     * 填充属性，这里执行完毕是一个完整对象
     * @param beanDefinition
     * @param earlyInstance
     * @return
     */
    protected Object populateBean(BeanDefinition beanDefinition, Object earlyInstance){
        String name = beanDefinition.getName();
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        if(propertyValues == null){
            earlySingletonObjects.remove(name);
            singletonObjects.put(name,earlyInstance);
            return earlyInstance;
        }
        List<PropertyValue> propertyValueList = propertyValues.getPropertyValueList();
        if(propertyValueList.size() == 0){
            earlySingletonObjects.remove(name);
            singletonObjects.put(name,earlyInstance);
            return earlyInstance;
        }
        Class clazz = beanDefinition.getClazz();
        for (PropertyValue propertyValue:propertyValueList) {
            Object value = propertyValue.getValue();
            try {
                if(value instanceof BeanReference){
                    BeanReference bf = (BeanReference) value;
                    value = getBean(bf.getName());
                }
                Method declaredMethod = clazz.getDeclaredMethod(
                        "set" + propertyValue.getName().substring(0, 1).toUpperCase()
                                + propertyValue.getName().substring(1), value.getClass());
                declaredMethod.setAccessible(true);
                declaredMethod.invoke(earlyInstance, value);
            } catch (Exception e) {
                Field declaredField = null;
                try {
                    declaredField = earlyInstance.getClass().getDeclaredField(propertyValue.getName());
                    declaredField.setAccessible(true);
                    declaredField.set(earlyInstance, value);
                } catch (Exception noSuchFieldException) {
                    noSuchFieldException.printStackTrace();
                }
            }
        }
        //到此earlyInstance就是完整对象
        earlySingletonObjects.remove(name);
        singletonObjects.put(name,earlyInstance);
        return earlyInstance;
    }

    /**
     * 扩展bean
     * @param bean
     * @param beanDefinition
     * @return
     */
    protected Object initializeBean(Object bean, BeanDefinition beanDefinition){

        invokeAwareMethods(bean);

        for (BeanPostProcessor beanPostProcessor:beanPostProcessors) {
            bean = beanPostProcessor.postProcessBeforeInitialization(bean,beanDefinition.getName());
        }

        invokeInitMethods(bean);

        for (BeanPostProcessor beanPostProcessor:beanPostProcessors) {
            bean = beanPostProcessor.postProcessAfterInitialization(bean,beanDefinition.getName());
        }
        return bean;
    }

    /**
     * 一堆容器对象的设置处理
     * @param bean
     */
    protected void invokeAwareMethods(Object bean){
        if(bean instanceof Aware){
            List<Object> awares = this.getBean(BeanFactoryAware.class);
            if(awares == null || awares.size() == 0){
                return;
            }
            for (int i = 0,length = awares.size(); i < length; i++) {
                BeanFactoryAware aware = (BeanFactoryAware)awares.get(i);
                aware.setBeanFactory(this);
            }
        }
    }

    /**
     * 一堆容器对象的处理
     * @param bean
     */
    protected void invokeInitMethods(Object bean){
        if(bean instanceof InitializingBean){
            List<Object> initializingBeans = this.getBean(InitializingBean.class);
            if(initializingBeans == null || initializingBeans.size() == 0){
                return;
            }
            for (int i = 0,length = initializingBeans.size(); i < length; i++) {
                InitializingBean initializingBean = (InitializingBean)initializingBeans.get(i);
                initializingBean.afterPropertiesSet();
            }
        }
    }
}
