package com.iwomi.nofiaPay.frameworks.externals.nomenclature;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(name = "nomenclature-service", url = "${externals.base-url.nomen}")
public interface NomenclatureClient {
    @RequestMapping(value = "/getNomenDataByTabcd/{tabcd}", method = RequestMethod.GET)
    public Map<String, Object> getSanmByTabcd(@PathVariable String tabcd);
}
