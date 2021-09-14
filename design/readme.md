### sql


```sql
create database javaFurtherStage2Module2;

use javaFurtherStage2Module2;
create table user(
	id INT PRIMARY KEY AUTO_INCREMENT, 
	username varchar(20),
  password varchar(20)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

### nginx

+ 请求转发给本地的两个端口

```conf
   upstream tomcatserver{
      server 192.168.1.8:8080;
      server 192.168.1.8:8081;
    }

    server {
        listen       80;
        server_name  localhost;

        #charset koi8-r;

        #access_log  logs/host.access.log  main;

        location / {
           proxy_pass http://tomcatserver/;

           # root   html;
           # index  index.html index.htm;
        }
   }
```

### 服务参数

+ 使用了jar包分别在两个端口运行，没有tomcat相关配置
