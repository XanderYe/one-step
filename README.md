# 项目名称

one-step

# 项目说明

dnf活动助手，一键领取活动礼包

# 说明

项目分为服务端和客户端

## 服务端

使用SpringBoot开发，为了偷懒没有service层

管理页面：http://ip:port/ 

授权码: [application.properties](https://github.com/XanderYe/one-step/blob/master/api/src/main/resources/application.properties)文件中的[password](https://github.com/XanderYe/one-step/blob/master/api/src/main/resources/application.properties#L34)项

### 获取客户端授权码

#### 请求URL

http://ip:port/license/generate

#### 请求方式

post

#### 参数示例

```json
{
	"serial": "序列号",
	"day": 999
}
```

## 客户端
使用JavaFX开发，跨平台支持，支持自动更新

不授权是单线程执行，授权后是多线程执行

