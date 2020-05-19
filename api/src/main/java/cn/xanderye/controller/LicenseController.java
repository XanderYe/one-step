package cn.xanderye.controller;

import cn.xanderye.base.ResultBean;
import cn.xanderye.exception.BusinessException;
import cn.xanderye.util.RSAEncrypt;
import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.interfaces.RSAPrivateKey;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

/**
 * Created on 2020/5/18.
 *
 * @author XanderYe
 */
@RestController
@RequestMapping("license")
public class LicenseController {

    @PostMapping("generate")
    public ResultBean generate(@RequestBody JSONObject params) throws Exception {
        String serial = params.getString("serial");
        Integer day = params.getInteger("day");
        if (StringUtils.isEmpty(serial)) {
            throw new BusinessException("序列号不能为空");
        }
        if (day == null || day < 0) {
            throw new BusinessException("天数不能为空");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, day);
        InputStream inputStream = this.getClass().getResourceAsStream("/privateKey.keystore");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String privateKey = bufferedReader.readLine();
        RSAPrivateKey rsaPrivateKey = RSAEncrypt.loadPrivateKeyByStr(privateKey);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("serial", serial);
        jsonObject.put("expireDate", calendar.getTime().getTime());
        String cipher = jsonObject.toJSONString();
        byte[] cipherData = RSAEncrypt.encrypt(rsaPrivateKey, cipher.getBytes());
        return new ResultBean<>(Base64.getEncoder().encodeToString(cipherData));
    }
}
