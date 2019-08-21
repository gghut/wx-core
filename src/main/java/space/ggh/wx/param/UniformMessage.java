package space.ggh.wx.param;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import space.ggh.wx.exception.ConfigException;

public class UniformMessage {

    private WxAppTemplateMessage appMessage;

    private WxMpTemplateMessage mpMessage;

    private String mpAppId;

    public void setAppMessage(WxAppTemplateMessage appMessage) {
        this.appMessage = appMessage;
    }

    public void setMpMessage(WxMpTemplateMessage mpMessage) {
        this.mpMessage = mpMessage;
    }

    public void setMpAppId(String mpAppId) {
        this.mpAppId = mpAppId;
    }

    public JSONObject toJson(){
        JSONObject json = new JSONObject();
        if (appMessage != null){
            json.put("touser", appMessage.getTouser());
            json.put("weapp_template_msg", appMessage.toJson());
        }else {
            if (mpMessage == null){
                throw new ConfigException("appMessage/mpMessage is missing");
            }
            if (mpAppId == null){
                throw new ConfigException("app id of mp is missing");
            }
            json.put("touser", mpMessage.getTouser());
            JSONObject msg = mpMessage.toJson();
            msg.put("appid", mpAppId);
            json.put("mp_template_msg", msg);
        }
        return json;
    }
}
