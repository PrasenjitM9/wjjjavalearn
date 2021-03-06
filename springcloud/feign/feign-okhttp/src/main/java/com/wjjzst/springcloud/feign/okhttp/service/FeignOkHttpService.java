package com.wjjzst.springcloud.feign.okhttp.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: Wjj
 * @Date: 2020/4/29 1:04 上午
 * @desc:
 */
@FeignClient(name = "github-client", url = "https://api.github.com")
public interface FeignOkHttpService {
    /**
     * content: {"message":"Validation Failed","errors":[{"resource":"Search","field":"q","code":"missing"}],
     * "documentation_url":"https://developer.github.com/v3/search"}
     *
     * @param queryStr
     * @return
     */
    @RequestMapping(value = "/search/repositories", method = RequestMethod.GET)
    // 开启压缩返回二进制流
    String searchRepo(@RequestParam("q") String queryStr);
}
