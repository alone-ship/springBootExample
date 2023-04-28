package com.example.cache;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class redisTokenRepository implements PersistentTokenRepository {

    private final String prefixKey = "spring:security:tokens:";

    @Resource
    RedisTemplate<Object, Object> template;

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        String usernameKey = prefixKey + token.getSeries();
        template.opsForValue().set(usernameKey,token.getSeries());
        template.expire(usernameKey, 1, TimeUnit.DAYS);
        this.setToken(token);
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        PersistentRememberMeToken token = this.getTokenForSeries(series);
        setToken(new PersistentRememberMeToken(token.getUsername(), series, tokenValue, lastUsed));
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        Map<Object, Object> entries = template.opsForHash().entries(seriesId);
        String username = (String) entries.get("username");
        String series = (String) entries.get("series");
        String token = (String) entries.get("token");
        Date date = new Date(Long.parseLong((String) entries.get("lastUsed")));
        return new PersistentRememberMeToken(username, series, token, date);
    }

    @Override
    public void removeUserTokens(String username) {
        String usernameKey = prefixKey + username;
        String series = (String) template.opsForValue().get(usernameKey);
        template.delete(usernameKey);
        if (series != null) {
            template.delete(series);
        }
    }

    private void setToken(PersistentRememberMeToken token){
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token.getTokenValue());
        tokenMap.put("series", token.getSeries());
        tokenMap.put("username", token.getUsername());
        tokenMap.put("lastUsed", String.valueOf(token.getDate().getTime()));
        template.opsForHash().putAll(token.getSeries(), tokenMap);
        template.expire(token.getSeries(), 1,TimeUnit.DAYS);
    }
}
