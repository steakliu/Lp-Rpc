### Lp-Rpc

 **版本** ：1.0.0

 **技术栈** ：Netty Zookeeper Kryo Spring    

 **实现思想** ：本rpc在服务端启动时候，获取标注有@LpService的类，表示此类是一个服务，需要注册到zk注册中心，此注解有group和version两个属性，group表示组，为了区分存在同名服务，version表示接口的版本，在zk注册中心根节点名称为lpRpc,是一个持久化节点，而子节点则是服务，是临时节点，当服务端关闭以后，相应的服务则会自动删除，服务名格式为，服service:group:version,如： **UserService:business:1.0.0** ，服务节点存储类路径，远程服务地址，格式为:host:port:className,如 **192.168.0.1:8080:com.lp.rpc.service.impl.UserServiceImpl** ,服务端进行服务注册以后，客户端启动去zk注册中心拿到所有服务，然后存在本地缓存中，然后监听服务端服务的变化，实时更新本地缓存，调用服务时首先在本地缓存中查找是否存在服务，存在的话直接发起远程调用，不存在则去注册中心查找服务，客户端通过反射获取到类信息以后发起远程调用，然后经过序列化等一系列操作，最终返回调用结果。

 **说明** ：此版本是第一个版本，比较简陋，后续会陆续进行更新 :kissing_heart: 

调用示例：
 **server** 

```
        <dependency>
            <groupId>com.lp</groupId>
            <artifactId>Lp-common</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
        <dependency>
            <groupId>com.lp</groupId>
            <artifactId>Lp-registry</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
```
 

```
        version和business不是必选，如果指定了，name客户端在发起请求的时候也需要指定，不然找不到服务

        @LpService(version = "1.0.0" , group = "business")
        public class HelloServiceImpl implements HelloService {
            @Override
            public String sayHello() {
                return "business group";
            }
        }
```


```
        public class LpServerBootstrap {
        public static void main(String[] args) {
              new ClassPathXmlApplicationContext("spring.xml");
            }
        }
```

   client
    client端必须引入以下几个包，Lp-client客户端代理，Lp-registry服务发现，Lp-common一些依赖。
   

```
        <dependency>
            <groupId>com.lp</groupId>
            <artifactId>Lp-common</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
        <dependency>
            <groupId>com.lp</groupId>
            <artifactId>Lp-registry</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
        <dependency>
            <groupId>com.lp</groupId>
            <artifactId>Lp-client</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
```



```
        public class LpClientBootstrap {
            public static void main(String[] args) throws NoSuchFieldException {
                ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
                HelloService helloService = LpClientProxy.createProxy(HelloService.class, "basic", "1.0.0");
                System.out.println(helloService.sayHello());
            }
        }

```


 **版本更新日志：** 
    
