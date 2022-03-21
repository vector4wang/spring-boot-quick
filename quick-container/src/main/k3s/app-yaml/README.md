# 注意：该种方式只适合单节点集群方式部署，因为pod的配置、二进制文件都从主机挂载进去的

###  sh -c 的重要性


### k8s镜像仓库设置



### 文件挂载
k3d 本身就是个容器集群，还需要将主机的文件挂进去，然后才能被k3d里面的k3s集群使用

### 使用PV时报错
```bash
# 开启nfs存储，k8s 1.20后如果要使用nfs需要开启
--kube-apiserver-arg=feature-gates=RemoveSelfLink=false 
```
参考：
- https://k3d.io/v5.2.2/faq/faq/
- https://k3d.io/v5.2.2/usage/configfile/

在 k3d 创建cluster的config中指定k3s的参数


### yaml 示例
https://www.cnblogs.com/shaozhiqi/p/12393962.html