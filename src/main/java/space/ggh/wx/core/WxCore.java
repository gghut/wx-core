package space.ggh.wx.core;

import space.ggh.wx.exception.ConfigException;

public class WxCore {

    private String appId;

    private String secret;

    private String token;

    private long tokenTimestamp = 0;

    public WxHttpService httpService;

    public WxCore(String appId, String secret){
        this.appId = appId;
        this.secret = secret;
        httpService = new WxHttpService();
    }

    public String getAppId() {
        return appId;
    }

    public String getToken() {
        if (System.currentTimeMillis() - tokenTimestamp < 360000){
            return token;
        }else{
            if (appId == null){
                throw new ConfigException("app id is missing");
            }
            if(secret == null){
                throw new ConfigException("secret is missing");
            }
            setToken(httpService.getAccessToken(appId, secret));
            reInitTimestamp();
        }
        return token;
    }

    private void setToken(String token){
        this.token = token;
    }

    private void reInitTimestamp(){
        tokenTimestamp = System.currentTimeMillis();
    }
}
