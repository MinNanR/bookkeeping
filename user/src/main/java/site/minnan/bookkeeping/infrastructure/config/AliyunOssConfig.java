package site.minnan.bookkeeping.infrastructure.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import site.minnan.bookkeeping.infrastructure.utils.RedisUtil;

@Slf4j
@Configuration
public class AliyunOssConfig {

    @Autowired
    RedisUtil redisUtil;

//    @Bean
//    public OSS oss() {
//        String endpoint = (String) redisUtil.getValue("endpoint");
//        String accessKeyId = (String) redisUtil.getValue("accessKeyId");
//        String accessKeySecret = (String) redisUtil.getValue("accessKeySecret");
//        if (endpoint == null || accessKeyId == null || accessKeySecret == null) {
//            log.warn("加载阿里云配置信息失败，请检查redis缓存");
//        }
//        return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//        return null;
//    }
}
