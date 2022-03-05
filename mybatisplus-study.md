> 官方文档：https://baomidou.com/pages/226c21/#%E9%85%8D%E7%BD%AE

# 1、导入依赖

```properties
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.5.1</version>
</dependency>
```

# 2、创建User实体类

# 3、创建UserMapper.java接口

> com.tuzhi.xxx.mapper.UserMapper.java

1. 该接口类继承BaseMapper<实体类>。

2. 加@Mapper注解。

3. ```java
   @Mapper
   public interface UserMapper extends BaseMapper<User> {
   }
   ```

# 4、使用

```java
@SpringBootTest
class MybatisplusStudyApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    void findAll() {
        System.out.println(userMapper.selectList(null));
    }
    @Test
    void findById() {
        System.out.println(userMapper.selectById(1));
    }

}
```

# 5、设置主键增长策略

1. 使用mybatisplus添加到时候，如果主键id为null的时候则会自动生成一个组件。

2. 使用，在实体类上的id成员变量加注解@TableId(type = IdType.xxx)

3. ```java
   @Getter
   public enum IdType {
       /**
        * 数据库ID自增
        * <p>该类型请确保数据库设置了 ID自增 否则无效</p>
        */
       AUTO(0),
       /**
        * 该类型为未设置主键类型(注解里等于跟随全局,全局里约等于 INPUT)
        */
       NONE(1),
       /**
        * 用户输入ID
        * <p>该类型可以通过自己注册自动填充插件进行填充</p>
        */
       INPUT(2),
   
       /* 以下3种类型、只有当插入对象ID 为空，才自动填充。 */
       /**
        * 分配ID (主键类型为number或string）,
        * 默认实现类 {@link com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator}(雪花算法)
        *
        * @since 3.3.0
        */
       ASSIGN_ID(3),
       /**
        * 分配UUID (主键类型为 string)
        * 默认实现类 {@link com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator}(UUID.replace("-",""))
        */
       ASSIGN_UUID(4);
   
       private final int key;
   
       IdType(int key) {
           this.key = key;
       }
   }
   ```

   # 5、自动填充

   1. 可用于自动填写创建时间修改时间等属性。

   2. 在实体类中需要自动填充的成员变量加注解：@TableField(fill = FieldFill.INSERT)

   3. ```java
      //    在插入时进行自动填充
          @TableField(fill = FieldFill.INSERT)
          private Date createTime;
      //    在插入更新时自动填充
          @TableField(fill = FieldFill.INSERT_UPDATE)
          private Date updateTime;
      ```

   4. 编写处理类实现MetaObjectHandler接口，并且加上@Configuration注解

   5. ```java
      @Component
      public class MyMetaObjectHandler implements MetaObjectHandler {
          @Override
          public void insertFill(MetaObject metaObject) {
              this.setFieldValByName("createTime", new Date(), metaObject);
              updateFill(metaObject);
          }
      
          @Override
          public void updateFill(MetaObject metaObject) {
              this.setFieldValByName("updateTime", new Date(), metaObject);
          }
      }
      ```

# 6、乐观锁实现

1. 表中添加version字段类型为int

2. 实体类中添加version字段，并且添加@Version注解；

   ~~~java
   //    乐观锁版本
       @Version
       private Integer version;
   ~~~

   

3. 编写配置类

   > com.tuzhi.xxx.config.MybatisPlusConfig.java

   ```java
   @Configuration
   public class MybatisPlusConfig {
       @Bean
       public MybatisPlusInterceptor mybatisPlusInterceptor() {
           MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
           interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
           return interceptor;
       }
   }
   ```

4. 使用

   > 更新的时候先查在更新

   ```java
   @Test
   void updateOptimistic() {
       User user = userMapper.selectById(2042732552);
       user.setPassword("123");
       System.out.println(userMapper.updateById(user));
   }
   ```

# 6、批量操作（批量增删改查）

1、xxxbatchIds(Arrays.asList(xxx))方法

# 7、分页

1. 编写配置类

   > com.tuzhi.xxx.config.MybatisPlusConfig.java

   ~~~java
   @Bean
       public MybatisPlusInterceptor mybatisPlusInterceptor() {
           MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
           interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
           return interceptor;
       }
   ~~~

2. 使用

   ```java
   @Test
   void page() {
       Page<User> userPage = new Page<>(1, 3);
       userPage = userMapper.selectPage(userPage, null);
       System.out.println("当前页：" + userPage.getCurrent());
       System.out.println("每页数据List的集合：" + userPage.getRecords());
       System.out.println("每页显示的记录数：" + userPage.getSize());
       System.out.println("总记录数：" + userPage.getTotal());
       System.out.println("总页数：" + userPage.getPages());
       System.out.println("是否有下一页：" + userPage.hasNext());
       System.out.println("是否有上一页：" + userPage.hasPrevious());
   }
   ```