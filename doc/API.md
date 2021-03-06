### API部分
> 内部实现见 _InfiniteBot\api\InfiniteBotAPI.java_

#### 调用: ####
> 此Bot为 _InfiniteBot\bot\Bot_ 类  
>```Bot.getAPI()```

#### 方法: ####
+ ##### 判断 #####
  + __hasFriend(QQ)__
    - _判断机器人是否为该QQ好友_  
  + __hasGroup(群号)__
    - _判断机器人是否在群中_
+ ##### 发送消息 #####
  + __sendFriendMessage(QQ, String/Message)__
    - _向好友发送一条消息_  
  + __sendGroupMessage(群号, String/Message)__
    - _向所在的群发送一条消息_  
  + __sendTempMessage(群号, QQ, String/Message)__
    - _向所在的群内一位成员发送一条临时会话消息_ 
+ ##### 绑定 ##### 
  + __setBind(UUID, QQ)__
    - _强制绑定玩家QQ与游戏账号_  
  + __unBind(UUID)__
    - _强制解除玩家QQ与游戏账号的绑定_ 
  + __getUserId(UUID)__
    - _根据uuid获取绑定此游戏账号的QQ_  
  + __getPlayerId(QQ)__
    - _根据QQ获取绑定此游戏账号的玩家的UUID_ 
+ ##### 其他 ##### 
  + __changeUserTagCard(QQ, 群号, 群名片)__
    - _更改一位群成员的群名片(~~机器人需要有管理员权限~~)_ 
  + __getBot()__
    - _获取Mirai机器人实例_  