### 事件部分
>注意: 下列所有事件均为InfiniteBot\event下的包装后事件
&emsp;(~~我觉得你应该清楚这一点~~)
---
#### 基本事件: ####
+ __InfiniteEvent__
  - 基本事件
  - _一切Mirai事件及本插件事件都会触发此事件_
+ __MessageReceiveEvent__ 
  - 消息接受事件
  - _一切消息接受事件都会触发此事件_
+ __MessageRecallEvent__ 
  - 消息撤回事件
  - _一切消息撤回事件都会触发此事件_
#### 高级事件: ####
+ ##### 组事件 #####
  + __GroupMessageEvent__
      - 组消息事件  
  + __GroupTempMessageEvent__
      - 组临时会话消息事件
+ ##### 好友事件 #####
  + __FriendMessageEvent__
      - 好友消息事件  

> 注意: 所有InfiniteBot\event下的消息事件均包含sendMessage方法  
> &emsp;&ensp;&emsp;该方法可发送 String List\<String\> Message 三种消息 更多方法见API部分

---
#### 调用Mirai中的事件 ####
&emsp;1.使用 getEvent() 方法 获取经包装前的Mirai事件
> 所有InfiniteBot\event下的事件都包含了该方法 下为范例  
>```
>    @EventHandler
>    public void onMessage(GroupMessageEvent e) {
>        net.mamoe.mirai.event.events.GroupMessageEvent event = e.getEvent();
>    }
>```  
  
&emsp;2.监听 InfiniteEvent 获得任意Mirai事件
>```
>    @EventHandler
>    public void onMessage(InfiniteEvent e) {
>        if (e.getEvent() instanceof StrangerMessageEvent)
>        {
>            StrangerMessageEvent event = (StrangerMessageEvent) e.getEvent();
>            //code
>        }
>    }
>```
