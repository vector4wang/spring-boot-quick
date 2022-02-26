```bash
docker pull webcenter/activemq


docker run --name='activemq' \
      -itd \
	  -p 8161:8161 \
	  -p 61616:61616 \
	  -e ACTIVEMQ_ADMIN_LOGIN=admin \
	  -e ACTIVEMQ_ADMIN_PASSWORD=123456 \
	  --restart=always \
	  -v D:\\develop\\docker-data\\activemq:/data/activemq \
	  -v D:\\develop\\docker-data\\activemq\\log:/var/log/activemq \
	  webcenter/activemq
```


- 61616是 activemq 的容器使用端口
- 8161是 web 页面管理端口
- /usr/soft/activemq 是将activeMQ运行文件挂载到该目录
- /usr/soft/activemq/log是将activeMQ运行日志挂载到该目录
- -e ACTIVEMQ_ADMIN_LOGIN=admin 指定登录名
- -e ACTIVEMQ_ADMIN_PASSWORD=123456 登录密码