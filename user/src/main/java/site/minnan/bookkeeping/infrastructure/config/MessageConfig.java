package site.minnan.bookkeeping.infrastructure.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.minnan.bookkeeping.infrastructure.utils.MessageUtil;
import site.minnan.bookkeeping.infrastructure.utils.RedisUtil;

@Configuration
public class MessageConfig {

    @Autowired
    private RedisUtil redisUtil;

    @Bean
    public MessageUtil messageUtil(){
        String accessKeyId= (String) redisUtil.getHashValue("aliyun", "AccessKeyId");
        String accessKeySecret = (String) redisUtil.getHashValue("aliyun", "AccessKeySecret");
        return new MessageUtil(accessKeyId, accessKeySecret);
    }
}
