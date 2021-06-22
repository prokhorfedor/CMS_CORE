package com.kpi.web.systems.lab2.service;

import com.kpi.web.systems.lab2.dto.response.PageDto;
import com.kpi.web.systems.lab2.model.enums.Language;

public interface PageService {

    String purifyPageCode(String pageCode);

    PageDto render(Language language, String pageCode);
}
