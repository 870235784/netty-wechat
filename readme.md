### 一个基于netty实现的聊天工具

#### 1.交互方式
```
客户端和服务端采用tcp方式交互, 交互协议内容采用json格式。后期会引入protobuf等其他编解码方式
```

#### 2.加密方式
```
md5
```

#### 3.消息格式
```
消息内容采用Json格式。
Json节点分为header（消息头）和body（消息体）。
消息头(header)包含发送时间戳(timestamp),消息类型(messageType),对话ID(callId)随机且唯一编号。
消息体（body）一般为相关消息类型所需要的消息数据结构体，内容字段可自由扩展。
```


#### 4.消息
消息类型|消息编号|备注
:--:|:--:|:--:
WECHAT_MSG_CLIENT_REGISTRY_REQ | 0 | 客户端注册请求
WECHAT_MSG_CLIENT_REGISTRY_ACK | 1 | 客户端注册响应
WECHAT_MSG_CLIENT_HEARTBEAT_REQ| 2 | 客户端心跳请求
WECHAT_MSG_CLIENT_HEARTBEAT_ACK| 3 | 客户端心跳响应
WECHAT_MSG_USER_REGISTRY_REQ | 4 | 用户注册请求
WECHAT_MSG_USER_REGISTRY_ACK | 5 | 用户注册响应
WECHAT_MSG_USER_LOGIN_REQ | 6 | 用户登录请求
WECHAT_MSG_USER_LOGIN_ACK | 7 | 用户登录响应

