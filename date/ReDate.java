package me.illtamer.infinitebot.date;

/**
 * 玩家登录数据
 * @author IllTamer
 */
public class ReDate
{
    private final String time;
    private final String code;

    public ReDate(String time, String code)
    {
        this.time = time;
        this.code = code;
    }

    public String getTime() {
        return time;
    }

    public String getCode() {
        return code;
    }
}
