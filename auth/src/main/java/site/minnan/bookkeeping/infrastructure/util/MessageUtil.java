package site.minnan.bookkeeping.infrastructure.util;

import cn.hutool.core.map.MapBuilder;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@Slf4j
public class MessageUtil {

    @Autowired
    private ObjectMapper objectMapper;

    final String product = "Dysmsapi";//短信API产品名称
    final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名

    //TODO 缓存中缺失密钥信息
    private final String accessKeyId;

    private final String accessKeySecret;

    public MessageUtil(String accessKeyId, String accessKeySecret) {
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
    }

    public void sendMessageVerificationCode(String phoneNumber, String verificationCode) throws ClientException,
            JsonProcessingException {
        Map<Object, Object> map = MapBuilder.create().put("code", verificationCode).build();
        String template = objectMapper.writeValueAsString(map);
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        request.putQueryParameter("SignName", "记好帐");
        request.putQueryParameter("TemplateCode", "SMS_202813494");
        request.putQueryParameter("TemplateParam", template);
        CommonResponse commonResponse = client.getCommonResponse(request);
        log.info("发送短信:{}", commonResponse.getData());
    }
}
