package space.ggh.wx.param;

import com.alibaba.fastjson.JSONObject;
import space.ggh.wx.exception.ConfigException;

import java.util.ArrayList;
import java.util.List;

public class WxMpTemplateMessage {

    private String touser,templateId,url,miniAppId,miniAppPath;

    private Keyword dataFirst,dataRemark;

    private List<Keyword> keywords = new ArrayList<>();

    String getTouser(){
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setMiniAppId(String miniAppId) {
        this.miniAppId = miniAppId;
    }

    public void setMiniAppPath(String miniAppPath) {
        this.miniAppPath = miniAppPath;
    }

    public void setDataFirst(String value, String color) {
        this.dataFirst = new Keyword(value, color);
    }

    public void setDataFirst(String value) {
        this.dataFirst = new Keyword(value);
    }

    public void setDataRemark(String value, String color) {
        this.dataRemark = new Keyword(value, color);
    }

    public void setDataRemark(String value) {
        this.dataRemark = new Keyword(value);
    }

    public void addKeyword(String value, String color){
        keywords.add(new Keyword(value, color));
    }

    public void addKeyword(String value){
        keywords.add(new Keyword(value));
    }

    public JSONObject toJson(){
        if (touser == null||templateId == null){
            throw new ConfigException("touser,templateId,formId is necessary");
        }
        JSONObject json = new JSONObject();
        json.put("touser", touser);
        json.put("template_id", templateId);
        json.put("url", url);
        JSONObject miniApp = new JSONObject();
        miniApp.put("appid", miniAppId);
        miniApp.put("pagepath", miniAppPath);
        json.put("miniprogram", miniApp);
        JSONObject data = new JSONObject();
        data.put("first", dataFirst!=null?dataFirst.toJson():null);
        data.put("remark", dataRemark!=null?dataRemark.toJson():null);
        for (int i=0;i<keywords.size();i++){
            data.put("keyword"+(i+1), keywords.get(i).toJson());
        }
        json.put("data", data);
        return json;
    }

    private class Keyword{
        private String value,color;

        private Keyword(String value){
            this(value, "#173177");
        }

        private Keyword(String value, String color){
            this.value = value;
            this.color = color;
        }

        private JSONObject toJson(){
            JSONObject json = new JSONObject();
            json.put("value", value);
            json.put("color", color);
            return json;
        }
    }
}
