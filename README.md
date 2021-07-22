+ 自定义ioc和aop的注解实现

+ ioc实现

  + 定义注解
    + @AutowiredIntf
    + @ComponentIntf（value=""）
    + @RepositoryIntf(value="")
    + @ServiceIntf(value="")
    + @TransactionalIntf
  + 创建Bean工厂用于扫描注解并根据注解创建相应对象封装到容器map中
    + 定义HashMap用于存储容器
    + 扫描指定包下的所有.class文件，将其转换为全限定类名后对其进行组件注解（不包括transactional）扫描
      + 对类级注解进行扫描当匹配到（@ComponentIntf，@RepositoryIntf，@ServiceIntf）时先去map容器寻找是否存在key = value的实例，当value不存在或者value = ""时，key自动赋值为首字母小写的当前类名，如果没有实例，则创建实例
      + 对类中的属性名注解进行扫描当匹配到（@AutowiredIntf）时
        + 判断是否存在key为首字母小写的当前属性名实例，如果不存在先创建该实例
          + 因为当扫描到注入注解时，当前类的对象就必须进行依赖注入，考虑到我们的容器是单例的，所以直接在扫描到Auto创建实例也不会影响后面的Component相关注解创建实例，只需要做好重复检查就可以
        + 去容器中寻找属性所在类的及其子类的实例，如果存在将上面寻找或者创建的实例注入该实例，完成依赖注入
          + 这边容器中的类不一定和类名相关，所以采用了子类匹配
      + 前面的注解因为是引用地址注入，即使当时没有实例，后面扫描到嵌套类生成对象后，引用地址指向的空间就从null变为了实例对象，当扫描到@TransactionalIntf注解时会先加入到特殊注解集合，因为该注解必须在其他嵌套实例完成初始化后才能初始化，所以这类注解最后单独处理
    + 对TransactionalIntf列表做处理，将用当前类的代理对象替代容器中原本的类，从而完成事务控制

+ aop实现

  + 通过动态代理完成事务控制

  