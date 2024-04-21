[toc]
## 一、创建并初始化数据库
### 1、创建数据库：
**mybatis_plus**
### 2、创建 User 表
其表结构如下：
![image](https://github.com/Sunny-lucking/blog/assets/44115602/a8d81e6e-395e-414d-8aca-7f432801c4b2)

创建数据库表脚本如下
```js
DROP TABLE IF EXISTS user;

CREATE TABLE user

(

 id BIGINT(20) NOT NULL COMMENT '主键ID',
 name VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
 age INT(11) NULL DEFAULT NULL COMMENT '年龄',
 email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
 PRIMARY KEY (id)
);
```
创建数据脚本如下
```
DELETE FROM user;

INSERT INTO user (id, name, age, email) VALUES
(1, 'Jone', 18, 'test1@baomidou.com'),
(2, 'Jack', 20, 'test2@baomidou.com'),、
(3, 'Tom', 28, 'test3@baomidou.com'),
(4, 'Sandy', 21, 'test4@baomidou.com'),
(5, 'Billie', 24, 'test5@baomidou.com');
````

## 二、初始化工程

使用 Spring Initializr 快速初始化一个 Spring Boot 工程
```
Group：com.atguigu
Artifact：mybatis-plus

版本：2.2.1.RELEASE
```

![](https://files.mdnice.com/user/3934/1d8c9587-0d4a-40c0-a773-070f228b7f2e.png)

![](https://files.mdnice.com/user/3934/04b646b0-9578-4034-89ef-44323d932300.png)

## 三、添加依赖

1、引入依赖

`spring-boot-starter、spring-boot-starter-test`

添加：`mybatis-plus-boot-starter、MySQL、lombok、`

在项目中使用**Lombok**可以减少很多重复代码的书写。比如说getter/setter/toString等方法的编写

pom.xml文件
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.2.1.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.atguigu</groupId>
    <artifactId>mpStudy</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>mpStudy</name>
    <description>Demo project for Spring Boot</description>

    <properties>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
            <version>2.2.1.RELEASE</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <version>2.2.1.RELEASE</version>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>org.junit.vintage</groupId>
                    <artifactId>junit-vintage-engine</artifactId>
                </exclusion>
            </exclusions>
        </dependency>

        <!--mybatis-plus-->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.0.5</version>
        </dependency>

        <!--mysql-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.18</version>
        </dependency>

        <!--lombok用来简化实体类-->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.10</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>

```
**注意**：引入 MyBatis-Plus 之后请不要再次引入 MyBatis 以及 MyBatis-Spring，以避免因版本差异导致的问
题。

## 四、配置

在 application.properties 配置文件中添加 MySQL 数据库的相关配置：mysql8以上（spring boot 2.1）
```xml
#mysql数据库连接
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/itguigu?serverTimezone=GMT%2B8
spring.datasource.username=root
spring.datasource.password=12345678
```

## 五、编写代码

### 1、主类

在 Spring Boot 启动类中添加 @MapperScan 注解，扫描 Mapper 文件夹

注意：扫描的包名根据实际情况修改
```java
@SpringBootApplication
@MapperScan("com.example.mpstudy.mapper")
public class MpStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(MpStudyApplication.class, args);
    }

}

```

### 2、实体
创建包 entity 编写实体类 User.java（此处使用了 Lombok 简化代码）
```java
@Data
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
```

### 3、mapper

创建包 mapper 编写Mapper 接口： UserMapper.java
```java
@Repository
public interface UserMapper extends BaseMapper<User> {
}
```

## 六、开始使用

添加测试类，进行功能测试：

```

@SpringBootTest

public class MybatisPlusApplicationTests {
    @Autowired
    private UserMapper userMapper;
    
    @Test
    public void testSelectList() {
        System.out.println(("----- selectAll method test ------"));
        //UserMapper 中的 selectList() 方法的参数为 MP 内置的条件封装器 Wrapper

        //所以不填写就是无任何条件
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
   }
}
```
打印结果

![](https://files.mdnice.com/user/3934/7baf7b16-2443-4be3-826f-4e54040bc3a7.png)

## 七、配置日志
查看sql输出日志

```
#mybatis日志
mybatis-plus.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl
```

# mybatis常用操作
## 一、insert

### 1、插入操作



```java
@Test
public void testInsert(){
    User user = new User();
    user.setName("Helen");
    user.setAge(18);
    user.setEmail("55317332@qq.com");
    int result = userMapper.insert(user);
    System.out.println(result); //影响的行数
    System.out.println(user); //id自动回填

}
```
![](https://files.mdnice.com/user/3934/4da9b29d-3187-4ead-bd7b-bbf31b56dbd0.png)
### 2、主键策略

#### （1）ID_WORKER

MyBatis-Plus默认的主键策略是：ID_WORKER  (全局唯一ID)


#### （2）自增策略
要想主键自增需要配置如下主键策略

- 需要在创建数据表的时候设置主键自增

- 实体字段中配置 @TableId(type = IdType.AUTO)

```
@TableId(type = IdType.AUTO)
private Long id;
```

要想影响所有实体的配置，可以设置全局主键配置
```
#全局设置主键生成策略
mybatis-plus.global-config.db-config.id-type=auto
```

其它主键策略：分析 IdType 源码可知
```
@Getter
public enum IdType {
    /**
     * 数据库ID自增
     */
    AUTO(0),
    
    /**
     * 该类型为未设置主键类型
     */
    NONE(1),
    
    /**
     * 用户输入ID
     * 该类型可以通过自己注册自动填充插件进行填充
     */
    INPUT(2),
    
    /* 以下3种类型、只有当插入对象ID 为空，才自动填充。 */
    /**
     * 全局唯一ID (idWorker)
     */
    ID_WORKER(3),
    
    /**
     * 全局唯一ID (UUID)
     */
    UUID(4),
    
    /**
     * 字符串全局唯一ID (idWorker 的字符串表示)
     */
    ID_WORKER_STR(5);
    private int key;
    IdType(int key) {
        this.key = key;
   }
}
```

## 二、update

### 1、根据Id更新操作

注意：update时生成的sql自动是动态`sql：UPDATE user SET age=? WHERE id=? `

```java
@Test
public void testUpdateById() {
    User user = new User();
    user.setId(1L);
    user.setAge(28);
    int result = userMapper.updateById(user);
    System.out.println(result);
}
```

![](https://files.mdnice.com/user/3934/87a605f2-6dfd-400a-9529-e0ebc6ca7e45.png)

### 2、自动填充

项目中经常会遇到一些数据，每次都使用相同的方式填充，例如记录的创建时间，更新时间等。

我们可以使用MyBatis Plus的自动填充功能，完成这些字段的赋值工作：

#### （1）数据库表中添加自动填充字段
在User表中添加datetime类型的新的字段 create_time、update_time

#### （2）实体上添加注解
```
@Data

public class User {
   ......
        
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    //@TableField(fill = FieldFill.UPDATE)

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
```

#### （3）实现元对象处理器接口
注意：不要忘记添加 @Component 注解
```java
package com.example.mpstudy.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    private static final Logger LOGGER =
            LoggerFactory.getLogger(MyMetaObjectHandler.class);
    @Override
    public void insertFill(MetaObject metaObject) {
        LOGGER.info("start insert fill ....");
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }
    @Override
    public void updateFill(MetaObject metaObject) {
        LOGGER.info("start update fill ....");
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }
}
```

#### 测试
![](https://files.mdnice.com/user/3934/f6db4ac4-2d91-4dfb-9e00-5ec93a062798.png)

### 3、乐观锁

主要适用场景：当要更新一条记录的时候，希望这条记录没有被别人更新，也就是说实现线程安全的数据更新

乐观锁实现方式：

- 取出记录时，获取当前version
- 更新时，带上这个version
- 执行更新时， set version = newVersion where version = oldVersion
- 如果version不对，就更新失败

#### （1）数据库中添加version字段



![](https://files.mdnice.com/user/3934/613913b4-b487-43ff-81d4-7f20e32852e9.png)
#### （2）实体类添加version字段

并添加 @Version 注解
```
@Version
    @TableField(fill = FieldFill.INSERT)
    private Integer version;
```
#### （3）元对象处理器接口添加version的insert默认值
```java
@Override
public void insertFill(MetaObject metaObject) {
   ......
    this.setFieldValByName("version", 1, metaObject);
}
```

**特别说明:**

- 支持的数据类型只有 int,Integer,long,Long,Date,Timestamp,LocalDateTime
- 整数类型下 newVersion = oldVersion + 1
- newVersion 会回写到 entity 中
- 仅支持 updateById(id) 与 update(entity, wrapper) 方法
- 在 update(entity, wrapper) 方法下, wrapper 不能复用!!!

#### （4）在 MybatisPlusConfig 中注册 Bean

创建配置类

```java
package com.example.mpstudy.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import org.springframework.context.annotation.Bean;

@Configuration
@MapperScan("com.example.mpstudy.mapper")
public class MybatisPlusConfig {
    /**
     * 乐观锁插件
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }
}
```

#### （5）测试乐观锁可以修改成功

测试后分析打印的sql语句，将version的数值进行了加1操作

```java

/**
 * 测试 乐观锁插件
 */
@Test
public void testOptimisticLocker() {
    //查询
    User user = userMapper.selectById(1L);
    //修改数据
    user.setName("Helen Yao");
    user.setEmail("helen@qq.com");
    //执行更新
    userMapper.updateById(user);
}
```
![](https://files.mdnice.com/user/3934/143bc0a5-71f5-4ae0-8043-47d5bd9bc005.png)
#### （5）测试乐观锁修改失败

```java
@Test
    public void testOptimisticLockerFail() {
        //查询
        User user = userMapper.selectById(1L);
        //修改数据
        user.setName("Helen Yao1");
        user.setEmail("helen@qq.com1");
        //模拟取出数据后，数据库中version实际数据比取出的值大，即已被其它线程修改并更新了version

        user.setVersion(user.getVersion() - 1);
        //执行更新
        userMapper.updateById(user);
    }
```

![](https://files.mdnice.com/user/3934/b3d1b57d-ea17-46ea-9d26-0e4172037fac.png)

## 三、select

### 1、根据id查询记录
```java
@Test
public void testSelectById(){
    User user = userMapper.selectById(1L);
    System.out.println(user);
}
```
### 2、通过多个id批量查询
完成了动态sql的foreach的功能
```java
@Test
public void testSelectBatchIds(){
    List<User> users = userMapper.selectBatchIds(Arrays.asList(1, 2, 3));
    users.forEach(System.out::println);
}
```

### 3、简单的条件查询

通过map封装查询条件
```java
@Test
public void testSelectByMap(){
    HashMap<String, Object> map = new HashMap<>();
    map.put("name", "Helen");
    map.put("age", 18);
    List<User> users = userMapper.selectByMap(map);
    users.forEach(System.out::println);
}
```
注意：map中的key对应的是数据库中的列名。例如数据库user_id，实体类是userId，这时map的key需要填写user_id\

### 4、分页

MyBatis Plus自带分页插件，只要简单的配置即可实现分页功能

#### （1）创建配置类

```java

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
  ```
（2）测试selectPage分页
```java
    @Test
    public void testSelectPage() {
        Page<User> page = new Page<>(1,5);
        userMapper.selectPage(page, null);
        page.getRecords().forEach(System.out::println);
        System.out.println(page.getCurrent());
        System.out.println(page.getPages());
        System.out.println(page.getSize());
        System.out.println(page.getTotal());
        System.out.println(page.hasNext());
        System.out.println(page.hasPrevious());
    }
```



测试：最终通过page对象获取相关数据
![](https://files.mdnice.com/user/3934/7d698914-b82b-462e-ba05-7801f80fb276.png)

#### （3）测试selectMapsPage分页：结果集是Map

```java
    @Test
    public void testSelectMapsPage() {
        Page<User> page = new Page<>(1, 5);
        IPage<Map<String, Object>> mapIPage = userMapper.selectMapsPage(page, null);
        //注意：此行必须使用 mapIPage 获取记录列表，否则会有数据类型转换错误
        mapIPage.getRecords().forEach(System.out::println);
        System.out.println(page.getCurrent());
        System.out.println(page.getPages());
        System.out.println(page.getSize());
        System.out.println(page.getTotal());
        System.out.println(page.hasNext());
        System.out.println(page.hasPrevious());
    }
```

![](https://files.mdnice.com/user/3934/c25aefc2-a673-4205-bfb4-1f4c21c0b9c2.png)
## 四、delete

### 1、根据id删除记录

```java
    @Test
    public void testDeleteById(){
        int result = userMapper.deleteById(8L);
        System.out.println(result);
    }
```
### 2、批量删除

```java
    @Test
    public void testDeleteBatchIds() {
        int result = userMapper.deleteBatchIds(Arrays.asList(8, 9, 10));
        System.out.println(result);
    }
```
### 3、简单的条件查询删除

```java
    @Test
    public void testDeleteByMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "Helen");
        map.put("age", 18);
        int result = userMapper.deleteByMap(map);
        System.out.println(result);
    }
```

### 4、逻辑删除

- 物理删除：真实删除，将对应数据从数据库中删除，之后查询不到此条被删除数据

- 逻辑删除：假删除，将对应数据中代表是否被删除字段状态修改为“被删除状态”，之后在数据库中仍旧能看到此条数据记录

#### （1）数据库中添加 deleted字段

![](https://files.mdnice.com/user/3934/314c3df2-89b6-4372-a6e2-111ee0cfb1a4.png)

#### （2）实体类添加deleted 字段

并加上 @TableLogic 注解 和 @TableField(fill = FieldFill.INSERT) 注解

#### （3）元对象处理器接口添加deleted的insert默认值
```java
@Override

public void insertFill(MetaObject metaObject) {
   ......
    this.setFieldValByName("deleted", 0, metaObject);
}
```
#### （4）application.properties 加入配置
此为默认值，如果你的默认值和mp默认的一样,该配置可无

```
mybatis-plus.global-config.db-config.logic-delete-value=1

mybatis-plus.global-config.db-config.logic-not-delete-value=0
```

#### （5）在 MybatisPlusConfig 中注册 Bean
```java
@Bean
public ISqlInjector sqlInjector() {
    return new LogicSqlInjector();
}
```

#### （6）测试逻辑删除
- 测试后发现，数据并没有被删除，deleted字段的值由0变成了1

- 测试后分析打印的sql语句，是一条update

- 注意：被删除数据的deleted 字段的值必须是 0，才能被选取出来执行逻辑删除的操作

![](https://files.mdnice.com/user/3934/a1a23f23-5799-4000-9caa-a78e4d92587e.png)


![](https://files.mdnice.com/user/3934/7234a97f-7a4f-437f-8f18-d9df7b49f638.png)

## 六、复杂条件查询
### 一、wapper介绍

如果想进行复杂条件查询，那么需要使用条件构造器 Wapper

![](https://files.mdnice.com/user/3934/59b96d28-c238-4cb3-8f63-a811b6882100.png)

- Wrapper ： 条件构造抽象类，最顶端父类
- AbstractWrapper ： 用于查询条件封装，生成 sql 的 where 条件    - QueryWrapper ： Entity 对象封装操作类，不是用lambda语法        - UpdateWrapper ： Update 条件封装，用于Entity对象更新操作
- AbstractLambdaWrapper ： Lambda 语法使用 Wrapper统一处理解析 lambda 获取 column。
- LambdaQueryWrapper ：看名称也能明白就是用于Lambda语法使用的查询Wrapper
- LambdaUpdateWrapper ： Lambda 更新封装Wrapper

### 二、AbstractWrapper

以下条件构造器的方法入参中的 column 均表示数据库字段

#### 1、ge、gt、le、lt、isNull、isNotNull

```java
    @Test
    public void testDelete() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .isNull("name")
                .ge("age", 12)
                .isNotNull("email");
        int result = userMapper.delete(queryWrapper);
        System.out.println("delete return count = " + result);
    }
```

![](https://files.mdnice.com/user/3934/1155f4e2-3dc7-4f11-a74e-32cb329a56a0.png)

#### 2、eq、ne

```java
    @Test
    public void testSelectOne() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", "Tom");
        User user = userMapper.selectOne(queryWrapper);
        System.out.println(user);
    }
```


#### 3、between、notBetween

```java
    @Test
    public void testSelectCount() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("age", 20, 30);
        Integer count = userMapper.selectCount(queryWrapper);
        System.out.println(count);
    }
```    
#### 4、allEq

```java
    @Test
    public void testSelectList1() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        Map<String, Object> map = new HashMap<>();
        map.put("id", 2);
        map.put("name", "Jack");
        map.put("age", 20);
        queryWrapper.allEq(map);
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }
```

#### 5、like、notLike、likeLeft、likeRight

```java
    @Test
    public void testSelectMaps() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .notLike("name", "e")
                .likeRight("email", "t");
        List<Map<String, Object>> maps = userMapper.selectMaps(queryWrapper);//返回值是Map列表
        maps.forEach(System.out::println);
    }
```

#### 6、in、notIn、inSql、notinSql、exists、notExists

```java
    @Test
    public void testSelectObjs() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //queryWrapper.in("id", 1, 2, 3);

        queryWrapper.inSql("id", "select id from user where id < 3");
        List<Object> objects = userMapper.selectObjs(queryWrapper);//返回值是Object列表

        objects.forEach(System.out::println);
    }
 ```


![](https://files.mdnice.com/user/3934/7bed4e00-846e-4b65-ad4e-a9ccacead6d2.png)

#### 7、or、and

注意：这里使用的是 UpdateWrapper

不调用or则默认为使用 and 连

```java
    @Test
    public void testUpdate1() {
        //修改值
        User user = new User();
        user.setAge(99);
        user.setName("Andy");
        //修改条件

        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper
                .like("name", "h")
                .or()
                .between("age", 20, 30);
        int result = userMapper.update(user, userUpdateWrapper);
        System.out.println(result);
    }
```

![](https://files.mdnice.com/user/3934/1a89d29a-370d-49b4-8f1c-30f923ba20cb.png)


#### 8、嵌套or、嵌套and

这里使用了lambda表达式，or中的表达式最后翻译成sql时会被加上圆括号

```java
    @Test
    public void testUpdate2() {
        //修改值
        User user = new User();
        user.setAge(99);
        user.setName("Andy");
        //修改条件

        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper
                .like("name", "h")
                .or(i -> i.eq("name", "李白").ne("age", 20));
        int result = userMapper.update(user, userUpdateWrapper);
        System.out.println(result);
    }
```    
#### 9、orderBy、orderByDesc、orderByAsc
```java
@Test
public void testSelectListOrderBy() {
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    queryWrapper.orderByDesc("id");
    List<User> users = userMapper.selectList(queryWrapper);
    users.forEach(System.out::println);
}
```

#### 10、last

直接拼接到 sql 的最后

注意：只能调用一次,多次调用以最后一次为准 有sql注入的风险,请谨慎使用

```java
@Test
public void testSelectListLast() {
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    queryWrapper.last("limit 1");
    List<User> users = userMapper.selectList(queryWrapper);
    users.forEach(System.out::println);
}
```
#### 11、select指定要查询的列

```java
@Test
public void testSelectListColumn() {
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    queryWrapper.select("id", "name", "age");
    List<User> users = userMapper.selectList(queryWrapper);
    users.forEach(System.out::println);
}
```

#### 12、set、setSql


最终的sql会合并 user.setAge()，以及 userUpdateWrapper.set()  和 setSql() 中 的字段

```java
@Test

public void testUpdateSet() {
    //修改值

    User user = new User();
    user.setAge(99);
    //修改条件

    UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
    userUpdateWrapper
       .like("name", "h")
       .set("name", "老李头")//除了可以查询还可以使用set设置修改的字段

       .setSql(" email = '123@qq.com'");//可以有子查询

    int result = userMapper.update(user, userUpdateWrapper);
}
```