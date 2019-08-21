package space.ggh.wx.param;

import com.alibaba.fastjson.JSONObject;
import space.ggh.wx.exception.ConfigException;

import java.util.ArrayList;
import java.util.List;

public class WxAppTemplateMessage {

    private String touser, templateId, page, formId, emphasisKeyword;

    private List<String> keywords = new ArrayList<>();

    String getTouser(){
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public void setEmphasisKeyword(String emphasisKeyword) {
        this.emphasisKeyword = emphasisKeyword;
    }

    public void addKeyword(String keyword){
        this.keywords.add(keyword);
    }

    public JSONObject toJson(){
        JSONObject json = new JSONObject();
        if (touser == null||templateId == null || formId == null){
            throw new ConfigException("touser,templateId,formId is necessary");
        }
        json.put("touser", touser);
        json.put("template_id", templateId);
        json.put("page", page);
        json.put("form_id", formId);
        json.put("emphasis_keyword", emphasisKeyword);
        JSONObject data = new JSONObject();
        for (int i= 0; i<keywords.size(); i++){
            data.put("keyword"+(i+1), formatValue(keywords.get(i)));
        }
        json.put("data", data);
        return json;
    }

    private JSONObject formatValue(String value){
        JSONObject json = new JSONObject();
        json.put("value", value);
        return json;
    }
}
