package com.quick.es.test;

//public class ClientFactory {
//
//	public static Client nodeClient() {
//        // 启动一个本地节点，并加入子网内的ES集群
//		Node node = nodeBuilder()
//					.clusterName("elasticsearch") // 要加入的集群名为elasticsearch
//					.data(true) // 本嵌入式节点可以保存数据
//					.node(); // 构建并启动本节点
//
//		// 获得一个Client对象，该对象可以对子网内的“elasticsearch”集群进行相关操作。
//		return node.client();
//	}
//
//	public static Client transportClient() {
//		// 配置信息
//		Settings esSetting = settingsBuilder()
//								.put("cluster.name", "elasticsearch")
//								.build();
//		TransportClient transportClient = new TransportClient(esSetting);
//
//		// 添加连接地址
//		TransportAddress address = new InetSocketTransportAddress("127.0.0.1", 9300);
//		transportClient.addTransportAddress(address);
//
//		return transportClient;
//	}
//
//
//}