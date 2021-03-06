### API部分
> 内部实现见 _InfiniteBot\api\InfiniteBotAPI.java_

#### 调用: ####
> 此Bot为 _InfiniteBot\bot\Bot_ 类  
>```Bot.getAPI()```

#### 方法: ####
+ ##### 判断 #####
  + __hasFriend(QQ)__
    - 判断机器人是否为该QQ好友  
  + __hasGroup(群号)__
    - 判断机器人是否在群中
+ ##### 发送消息 #####
  + __sendFriendMessage(QQ, String/Message)__
    - 向好友发送一条消息  
  + __sendGroupMessage(群号, String/Message)__
    - 向所在的群发送一条消息  
  + __sendTempMessage(群号, QQ, String/Message)__
    - 向所在的群内一位成员发送一条临时会话消息 
+ ##### 绑定 ##### 
  + __setBind(UUID, QQ)__
    - 强制绑定玩家QQ与游戏账号  
  + __unBind(UUID)__
    - 强制解除玩家QQ与游戏账号的绑定 
  + __getUserId(UUID)__
    - 根据uuid获取绑定此游戏账号的QQ  
  + __getPlayerId(QQ)__
    - 根据QQ获取绑定此游戏账号的玩家的UUID 
+ ##### 其他 ##### 
  + __changeUserTagCard(QQ, 群号, 群名片)__
    - 更改一位群成员的群名片(~~机器人需要有管理员权限~~) 
  + __getBot()__
    - 获取Mirai机器人实例  
