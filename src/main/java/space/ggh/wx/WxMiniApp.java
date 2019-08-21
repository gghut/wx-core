package space.ggh.wx;

import com.alibaba.fastjson.JSONObject;
import space.ggh.wx.core.WxCore;
import space.ggh.wx.exception.ConfigException;
import space.ggh.wx.param.UniformMessage;
import space.ggh.wx.param.WxAppTemplateMessage;

public class WxMiniApp extends WxCore {

    public static void main(String[] args){
        WxMiniApp app = new WxMiniApp("wx3c04cb2af3c1452d", "4602ff575bbd56a6d4d20a3ddb1121c1");
        UniformMessage message = new UniformMessage();
        WxAppTemplateMessage appMessage = new WxAppTemplateMessage();
        appMessage.setTouser("o5ecf5Yi66_Z_en1RPtms2S_Thh4");
        appMessage.setFormId("a9bbe153a307493d857d08938a2beec1");
        appMessage.setTemplateId("NjxDBM238Gf5psaKlp6TyIN_wCgustTLwuzo-81Eh9o");
        appMessage.addKeyword("测试1");
        appMessage.addKeyword("测试2");
        appMessage.addKeyword("测试3");
        message.setAppMessage(appMessage);
        app.sendUniformMessage(message);
    }

    public WxMiniApp(String appId, String secret) {
        super(appId, secret);
    }

    public boolean sendTemplateMessage(WxAppTemplateMessage message){
        String response = this.httpService.postForObject("https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token="+getToken(), message.toJson().toJSONString());
        JSONObject json = JSONObject.parseObject(response);
        if (json.containsKey("errcode") && json.containsValue(0)){
            return true;
        }else{
            throw new ConfigException(response);
        }
    }

    /**
     * 下发小程序和公众号统一的服务消息
     * <br>优先发送小程序模板消息，即当参数对象中的appMessage（json参数weapp_template_msg）不为空时，发送小程序模板消息
     * <br>否则发送公众号模板消息,当使用公众号模板消息时，必须设置需要发送使用的公众号app id
     * @param message {@link UniformMessage}
     * @return true-发送成功，false-发送失败
     */
    public boolean sendUniformMessage(UniformMessage message){
        String response = this.httpService.postForObject("https://api.weixin.qq.com/cgi-bin/message/wxopen/template/uniform_send?access_token="+getToken(), message.toJson().toJSONString());
        JSONObject json = JSONObject.parseObject(response);
        if (json.containsKey("errcode") && json.containsValue(0)){
            return true;
        }else{
            throw new ConfigException(response);
        }
    }


}
