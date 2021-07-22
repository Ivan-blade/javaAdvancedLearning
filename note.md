### 自定义IOC和AOP框架

#### 框架整体回顾

#### IOC思想

+ 概念：控制反转，不用自己创建对象，由IOC容器创建管理对象
+ IOC和DI区别
  + IOC和DI描述的是同一件事情：对象实例化以及依赖关系的维护
  + IOC是站在对象的角度强调对象实例化和管理的权利交给容器
  + DI是站在容器的角度：容器会把对象依赖的其他对象注入给该对象，比如实例化的A需要B对象，容器则会将B对象注入给A

#### AOP思想

#### 手写IOC和AOP问题分析

#### 手写IOC和AOP之new关键字耦合问题

#### 手写IOC和AOP之事务控制问题分析

#### 手写IOC和AOP之当前线程绑定唯一Connection

#### 手写IOC和AOP之Service方法添加事务控制

#### 手写IOC和AOP之JDK动态代理扩展

#### 手写IOC和AOP之CGLIB动态代理扩展

#### 手写IOC和AOP之使用动态代理改造service事务控制

### SpringIOC高级应用与源码剖析

#### IOC基础说明

#### BeanFactory和ApplicationContext区别

#### SpringIoc纯xml方式

#### Bean的创建方式以及Bean标签属性回顾

#### Spring Di依赖注入配置回顾

#### xml与注解相结合模式回顾

#### 纯注解模式回顾

#### lazy-init延时加载

#### FactoryBean

#### 后置处理器

#### Spring源码剖析之注意事项和源码构建

#### Ioc容器初始化主题流程值Bean周期关键时机点代码调用分析

#### IOC容器初始化主题流程值refresh方法

#### BeanFactory获取子流程

#### BeanDefinition加载注册子流程

#### Bean对象创建流程

#### lazy-init延时加载机制

#### 循环依赖问题

### Spring Aop高级应用

#### AOP术语回顾

#### AOP的纯xml模式回顾

#### 事务特性——并发问题——隔离级别——传播行为

#### Spring声明式事务纯xml模式回顾

#### Spring声明式事务半注解及全注解模式回顾

#### Spring AOP代理对象创建流程

#### 声明式事务源码剖析

