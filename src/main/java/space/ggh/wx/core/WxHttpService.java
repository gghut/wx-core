package space.ggh.wx.core;

import com.alibaba.fastjson.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class WxHttpService {

    private RestTemplate template;

    WxHttpService(){
        template = new RestTemplate();
        template = new RestTemplateBuilder()
                .setConnectTimeout(1000)
                .setReadTimeout(1000)
                .build();
        template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        stringHttpMessageConverter.setWriteAcceptCharset(true);

        List<MediaType> mediaTypeList = new ArrayList<>();
        mediaTypeList.add(MediaType.ALL);

        for (int i = 0; i < template.getMessageConverters().size(); i++) {
            if (template.getMessageConverters().get(i) instanceof StringHttpMessageConverter) {
                template.getMessageConverters().remove(i);
                template.getMessageConverters().add(i, stringHttpMessageConverter);
            }
            if (template.getMessageConverters().get(i) instanceof MappingJackson2HttpMessageConverter) {
                try {
                    ((MappingJackson2HttpMessageConverter) template.getMessageConverters().get(i)).setSupportedMediaTypes(mediaTypeList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    String getAccessToken(String appId, String secret){
        String response = template.getForObject("https://api.weixin.qq.com/cgi-bin/token" +
                "?grant_type=client_credential" +
                "&appId=" + appId +
                "&secret=" + secret, String.class);
        JSONObject responseJson = JSONObject.parseObject(response);
        return responseJson.getString("access_token");
    }

    public void sendMessage(Object object, String token){
        String response = template.postForObject("https://api.weixin.qq.com/cgi-bin/message/wxopen/template/uniform_send?access_token="+token, object.toString(), String.class);
        System.out.println(response);
    }

    public String getFortObject(String url){
        return template.getForObject(url, String.class);
    }

    public String postForObject(String url, String body){
        return template.postForObject(url, body, String.class);
    }
}
