# 后端

## 框架

### controller

控制层

controller层的功能为请求和响应控制。
 controller层负责前后端交互，接受前端请求，调用service层，接收service层返回的数据，最后返回具体的页面和数据到客户端

### service

-impl

业务层

service层的作用为完成功能设计。存放业务逻辑处理，不直接对数据库进行操作，有接口和接口实现类，提供controller层调用的方法。
调用dao层接口，接收dao层返回的数据，完成项目的基本功能设计。（也就是说对于项目中的功能的需求就是在这里完成的）（对Dao层接口的实现）

### dao

-dos

-mapper

-pojo

mapper层（接口）

即数据持久层，对数据做持久化操作。也被称为mapper层。声明为接口。
 dao层的作用为访问数据库，向数据库发送sql语句，完成数据的增删改查任务。

## 统一异常处理

无论是controller层、service层还是dao层都有可能包异常

如果是意料之中的异常可以直接捕获处理

如果是意料之外的异常，需要进行统一处理，进行记录，并给用户提示相对比较友好的信息

```java
package com.heyqing.cvarst_back.handler;

import com.heyqing.cvarst_back.vo.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ClassName:AllExceptionHandler
 * Package:com.heyqing.cvarst_back.handler
 * Description:
 *
 * @Date:2023/11/11
 * @Author:Heyqing
 */

//对加了controller注解的方法进行拦截处理AOP实现
@ControllerAdvice
@ResponseBody//返回json数据
public class AllExceptionHandler {
    //进行异常处理，处理Exception.class的异常
    @ExceptionHandler(Exception.class)
    public Result doException(Exception ex){
        ex.printStackTrace();
        return Result.fail(-999,"系统异常");
    }
}


```

## 1.1首页-文章列表

### -接口说明

url： /articles

请求方式：POST

请求参数：

| 参数名称 | 参数类型 | 说明           |
| :------: | -------- | -------------- |
|   page   | int      | 当前页数       |
| pageSize | int      | 每页显示的数量 |

​	返回数据：

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data":[
        {
            "id":1,
            "title": "cvarst简介",
            "summary": "cvarst是基于计算机视觉的遥感图像检测 ",
            "createDate": "2022-02-02 12:12"
        }
    ]
}
```

注：首页主体部分

### -接口说明

url：/articles/listArchives

请求方式：POST

请求参数：无

## 1.2首页-最新文章

### -接口说明

url：/articles/new

请求方式：POST

请求参数：

| 参数名称 | 参数类型 | 说明         |
| -------- | -------- | ------------ |
| NewStart | int      | 查询起始位置 |
|          |          |              |
|          |          |              |

返回数据：

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": [
        {
            "id": 1,
            "title": "cvarst介绍以及入门案例",
        },
        {
            "id": 9,
            "title": "cvarst 是什么",
        },
        {
            "id": 10,
            "title": "计算机视觉相关",
            
        }
    ]
}
```

注：下拉刷新时优先展示，每次展示5个

## 1.3首页-最热文章

### -接口说明

url：/articles/hot

请求方式：POST

请求参数：

| 参数名称 | 参数类型 | 说明                 |
| -------- | -------- | -------------------- |
| HotStart | int      | 最热文章查询起始位置 |
|          |          |                      |
|          |          |                      |

返回数据：

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": [
        {
            "id": 1,
            "title": "cvarst介绍以及入门案例",
        },
        {
            "id": 9,
            "title": "cvarst 是什么",
        },
        {
            "id": 10,
            "title": "遥感技术相关",
            
        }
    ]
}
```

注：根据浏览次数查询出最热文章

## 1.4首页-文章详情

### -接口说明

url：/articles/view/{id}

请求方式：POST

请求参数：

| 参数名称 | 参数类型 | 说明               |
| -------- | -------- | ------------------ |
| id       | long     | 文章id（路径参数） |
|          |          |                    |
|          |          |                    |

返回数据：

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": "token"
}
```

## 1.5首页-文章标签

### -接口说明

url：/tags

请求方式：GET

请求参数：无

返回数据：

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": [
        {
            "id": 5,
            "tagName": "cvarst"
        },
        {
            "id": 6,
            "tagName": "遥感"
        },
        {
            "id": 7,
            "tagName": "计算机视觉"
        }
    ]
}
```

## 1.6首页-文章导入

文章导入设定为自动导入

（*项目结尾完成*）

-common

–AotuImportArticle

## 1.7首页文章搜索

### -接口信息

url：/articles/qsearch

请求方式：GET

请求参数：

| 参数名称 | 参数类型 | 说明       |
| -------- | -------- | ---------- |
| kw       | String   | 搜索关键字 |
|          |          |            |
|          |          |            |

返回数据：

```json
{
     "success": true,
    "code": 200,
    data: [
        {
            id:1233761683190283401,
            title:"文章标题"
        }
    ]
}
```

### -接口信息

url:/articles/search

请求方式：

请求参数：

| 参数名称 | 参数类型 | 说明       |
| -------- | -------- | ---------- |
| kw       | String   | 搜索关键字 |
|          |          |            |
|          |          |            |

返回数据：

```json
{
     "success": true,
    "code": 200,
    data: [
        {
            id:1233761683190283401,
            title:"文章标题"
        }
    ]
}
```



## 2.1个人-登录

### -接口说明

url：/login

请求方式：POST

请求参数：

| 参数名称      | 参数类型 | 说明                   |
| ------------- | -------- | ---------------------- |
| code          | String   | 用户登录凭证           |
| encryptedData | String   | 完整用户密文           |
| iv            | String   | 加密算法的初始向量     |
| rawData       | String   | 用户信息原始数据字符串 |
| signature     | String   | 使用sha1得到字符串     |

返回数据：

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": "token"
}
```

### -JWT

登录使用JWT技术

jwt可以生成一个加密的token，作为用户登录成功之后，发给客户端。

请求需要登录的资源或者接口的时候，将token携带，后端验证token是否合法。

jwt 有三部分组成：A.B.C

A：Header，{"type":"JWT","alg":"HS256"} 固定

B：playload，存放信息，比如，用户id，过期时间等等，可以被解密，不能存放敏感信息

C:  签证，A和B加上秘钥 加密而成，只要秘钥不丢失，可以认为是安全的。

jwt 验证，主要就是验证C部分 是否合法。

依赖：

```xml
  <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt</artifactId>
        <version>0.9.1</version>
    </dependency>
```

### -redis

```properties
spring.redis.host=localhost
spring.redis.port=6379
```

注：技术支持课实现人脸识别登录与指纹识别登录

## 2.2个人-获取用户信息

### -接口信息

url：/users/currentUser

请求方式：GET

请求参数：

| 参数名称      | 参数类型 | 说明              |
| ------------- | -------- | ----------------- |
| Authorization | string   | 头部信息（TOKEN） |
|               |          |                   |

返回数据：

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": {
        "id":1,
        "account":"1",
        "nickaname":"1",
        "avatar":"ss"
    }
}
```

## 2.3个人-退出登录

### -接口信息

url：/logout

请求方式：GET

请求参数：

| 参数名称      | 参数类型 | 说明            |
| ------------- | -------- | --------------- |
| Authorization | string   | 头部信息(TOKEN) |
|               |          |                 |

返回数据：

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": null
}
```

## 2.4个人-注册

### -接口说明

url：/register

请求方式：POST

请求参数：

| 参数名称 | 参数类型 | 说明 |
| -------- | -------- | ---- |
| account  | string   | 账号 |
| password | string   | 密码 |
| nickname | string   | 昵称 |

返回数据：

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": "token"
}
```

## 2.5个人-登录-拦截器

每次访问需要登录的资源的时候，都需要在代码中进行判断，一旦登录的逻辑有所改变，代码都得进行变动，非常不合适。

那么可不可以统一进行登录判断呢？

可以，使用拦截器，进行登录拦截，如果遇到需要登录才能访问的接口，如果未登录，拦截器直接返回，并跳转登录页面。

## 2.6个人-ThreadLocal保存用户信息

### -内存泄露问题解决

**实线代表强引用,虚线代表弱引用**

每一个Thread维护一个ThreadLocalMap, key为使用**弱引用**的ThreadLocal实例，value为线程变量的副本。

**强引用**，使用最普遍的引用，一个对象具有强引用，不会被垃圾回收器回收。当内存空间不足，Java虚拟机宁愿抛出OutOfMemoryError错误，使程序异常终止，也不回收这种对象。

**如果想取消强引用和某个对象之间的关联，可以显式地将引用赋值为null，这样可以使JVM在合适的时间就会回收该对象。**

**弱引用**，JVM进行垃圾回收时，无论内存是否充足，都会回收被弱引用关联的对象。在java中，用java.lang.ref.WeakReference类来表示。

## 2.7个人-我的喜欢

通过文章Id，添加到我的喜欢中

（*项目最后实现*）

### -接口信息

url：/person/like

请求方式：POST

请求参数：

| 参数名称 | 参数类型 | 说明         |
| -------- | -------- | ------------ |
| page     | int      | 当前请求页数 |
| pageSize | int      | 请求数量     |
| token    | String   | 用户token    |



## 2.8个人-删除

### -接口信息

url：/person/deleteLike

请求方式：POST

请求参数：

| 参数名称  | 参数类型 | 说明         |
| --------- | -------- | ------------ |
| articleId | Long     | 删除喜欢文章 |
|           |          |              |
|           |          |              |

### -接口信息

url：/person/deleteData

请求方式：POST

请求参数：

| 参数名称  | 参数类型 | 说明         |
| --------- | -------- | ------------ |
| photoPath | String   | 删除收藏图片 |
|           |          |              |
|           |          |              |

## 2.9个人-关于我们

### -接口信息

url：/person/feedback

请求方式：POST

请求参数：

| 参数名称     | 参数类型 | 说明     |
| ------------ | -------- | -------- |
| feedbackText | String   | 反馈意见 |
|              |          |          |
|              |          |          |

## 2.10个人-我的查询

### -接口说明

url：/person/likebyid

请求方式：POST

请求参数：

| 参数名称      | 参数类型   | 说明   |
| ------------- | ---------- | ------ |
| articleIdList | List<Long> | id列表 |
|               |            |        |
|               |            |        |

### -接口说明

url：/person/allphoto

请求方式：POST

请求参数：

| 参数名称 | 参数类型 | 说明      |
| -------- | -------- | --------- |
| token    | String   | 用户token |
|          |          |           |

## 3.1处理-检查

### -接口信息

url：/login/checktoken

请求方式：POST

请求参数：

| 参数名称 | 参数类型 | 说明      |
| -------- | -------- | --------- |
| token    | String   | 用户token |
|          |          |           |
|          |          |           |



## 4.1其他-AOP

```java
package com.heyqing.cvarst_back.common.aop;

import java.lang.annotation.*;

/**
 * ClassName:LogAnnotation
 * Package:com.heyqing.cvarst_back.common.aop
 * Description:
 *
 * @Date:2023/11/12
 * @Author:Heyqing
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {

    String module() default "";
    String operator() default "";
}

```



```java
package com.heyqing.cvarst_back.common.aop;

import com.alibaba.fastjson.JSON;
import com.heyqing.cvarst_back.utils.HttpContextUtils;
import com.heyqing.cvarst_back.utils.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * ClassName:LogAspect
 * Package:com.heyqing.cvarst_back.common.aop
 * Description:
 *
 * @Date:2023/11/12
 * @Author:Heyqing
 */
@Aspect
@Component
@Slf4j
public class LogAspect {


    @Pointcut("@annotation(com.heyqing.cvarst_back.common.aop.LogAnnotation)")
    public void logPointCut() {
    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = point.proceed();
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        //保存日志
        recordLog(point, time);
        return result;
    }

    private void recordLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
        log.info("=====================log start================================");
        log.info("module:{}", logAnnotation.module());
        log.info("operator:{}", logAnnotation.operator());

        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        log.info("request method:{}", className + "." + methodName + "()");

//        //请求的参数
        Object[] args = joinPoint.getArgs();
        String params = JSON.toJSONString(args[0]);
        log.info("params:{}", params);

        //获取request 设置IP地址
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        log.info("ip:{}", IpUtils.getIpAddr(request));


        log.info("execute time : {} ms", time);
        log.info("=====================log end================================");
    }
}
```



```java
package com.heyqing.cvarst_back.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * ClassName:HttpContexUtils
 * Package:com.heyqing.cvarst_back.utils
 * Description:
 *
 * @Date:2023/11/12
 * @Author:Heyqing
 */
public class HttpContextUtils {
    public static HttpServletRequest getHttpServletRequest(){
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
}

```

```java
package com.heyqing.cvarst_back.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * ClassName:IpUtils
 * Package:com.heyqing.cvarst_back.utils
 * Description:
 *
 * @Date:2023/11/12
 * @Author:Heyqing
 */
@Slf4j
public class IpUtils {

    /**
     * 获取IP地址
     * <p>
     * 使用Nginx等反向代理软件， 则不能通过request.getRemoteAddr()获取IP地址
     * 如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，X-Forwarded-For中第一个非unknown的有效IP字符串，则为真实IP地址
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = null, unknown = "unknown", seperator = ",";
        int maxLength = 15;
        try {
            ip = request.getHeader("x-forwarded-for");
            if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } catch (Exception e) {
            log.error("IpUtils ERROR ", e);
        }

        // 使用代理，则获取第一个IP地址
        if (StringUtils.isEmpty(ip) && ip.length() > maxLength) {
            int idx = ip.indexOf(seperator);
            if (idx > 0) {
                ip = ip.substring(0, idx);
            }
        }

        return ip;
    }

    /**
     * 获取ip地址
     *
     * @return
     */
    public static String getIpAddr() {
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        return getIpAddr(request);
    }
}
```

```java
    @LogAnnotation(module = "文章",operator = "获取文章列表")
    @PostMapping
    public Result listArticle(@RequestBody PageParams pageParams) {
        return articleService.listArticle(pageParams);
    }
```

## 4.2数据库资源网

- 中国遥感数据共享网（http://rs.ceode.ac.cn/）是国内存档周期最长的数据网站，对Landsat数据免费共享，也可订购国外商业卫星数据。
- 地理空间数据云（http://www.gscloud.cn/sources）数据资源更新比较稳定，免费数据包括Landsat系列、中巴资源卫星、MODIS数据的各种产品、DEM数字高程数据、EO-1数据、NOAA AVHRR数据产品、Sentinel数据等。
- 全球变化科学研究数据出版系统（http://www.geodoi.ac.cn/WebCn/Default.aspx）数据种类非常丰富，涉及领域较多，可以根据需要自行查找。
- 地球系统科学数据共享网、可再生资源与环境世界数据中心（http://eng.wdc.cn/）是由中国科学院地理科学与资源研究所主办，是中国资源和环境领域的长期数据归档和共享中心。

1. 美国联邦地理空间平台（https://www.geoplatform.gov/）
2. 美国联邦公共数据平台（https://www.data.gov/）
3. 美国航空航天局（https://www.nasa.gov/）
4. 美国地质调查局（https://www.usgs.gov/）
5. 美国海洋与大气管理局（https://www.noaa.gov/）
6. 国家自然资源和地理空间基础信息库（http://sgic.net.cn/web/geo/index.html）
7. 国家地理信息公共服务平台（天地图）（https://www.tianditu.gov.cn/）
8. 全国地理信息资源目录服务系统（http://www.webmap.cn/）

资讯网：

1. 国家地理信息公共服务平台（天地图）：提供全球范围内的地理信息数据和服务，包括地图、卫星影像、地形地貌、地名地址等。
2. 中国国家地理网：提供大量的地理资讯、图片、文章、活动等信息，涉及自然地理、人文地理、环境保护等多个领域。
3. 中国地理学会：提供地理学术论文、学术会议、科研项目等信息，以及地理科普、教育等方面的资讯。
4. 中国科学院地理科学与资源研究所：提供地理科研、自然资源管理、环境保护等方面的信息，以及地图数据、遥感影像等服务。
5. 地理科学网：提供地理教育、学术研究、自然资源管理等方面的信息，以及地图数据、遥感影像等服务。
6. 世界地理网：提供全球各地的地理资讯、旅游攻略、历史文化等信息，以及地图查询、酒店预订等服务。
7. 中国遥感数据共享网：提供遥感影像、地理信息数据、技术交流等方面的服务，以及相关的学习资料和教程。
8. 地理空间数据云：提供多种类型的地理空间数据下载和服务，包括Landsat系列、中巴资源卫星、MODIS数据等。
