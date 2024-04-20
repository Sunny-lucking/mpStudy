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


![](https://files.mdnice.com/user/3934/4da9b29d-3187-4ead-bd7b-bbf31b56dbd0.png)
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

### 测试
![](https://files.mdnice.com/user/3934/f6db4ac4-2d91-4dfb-9e00-5ec93a062798.png)
