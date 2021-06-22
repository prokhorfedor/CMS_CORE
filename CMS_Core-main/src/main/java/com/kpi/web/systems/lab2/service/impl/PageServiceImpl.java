package com.kpi.web.systems.lab2.service.impl;

import com.kpi.web.systems.lab2.dto.response.PageDto;
import com.kpi.web.systems.lab2.model.Page;
import com.kpi.web.systems.lab2.model.enums.Language;
import com.kpi.web.systems.lab2.repository.PageRepository;
import com.kpi.web.systems.lab2.service.PageService;
import com.kpi.web.systems.lab2.statics.StaticFieldsUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class PageServiceImpl implements PageService {

    private final PageRepository pageRepository;

    @Override
    public String purifyPageCode(String pageCode) {
        Page page = findPageByCode(pageCode);
        return page.getAliasPage() != null
                ? page.getAliasPage().getCode()
                : page.getCode();
    }

    @Override
    public PageDto render(Language language, String pageCode) {
        Page page = findPageByCode(pageCode);

        String meta = buildMeta(page, language);
        String header = buildHeader(language);
        String subheader = buildSubheader(page, language);
        String title = page.getTitle(language);
        String imageUrl = page.getImageUrl();
        String content = buildContent(page, language);
        String footer = buildFooter(language);

        return PageDto.builder()
                .meta(meta)
                .header(header)
                .subheader(subheader)
                .title(title)
                .imageUrl(imageUrl)
                .content(content)
                .footer(footer)
                .build();
    }

    private String buildMeta(Page page, Language language) {

        return String.format(
                "        <title>%s</title>" +
                        "<meta name=\"description\" content=\"%s\">",
                page.getTitle(language), page.getDescription(language)
        );
    }

    private String buildSubheader(Page page, Language language) {
        return buildBackButton(page, language);
    }

    private String buildBackButton(Page page, Language language) {
        if (page.getParentPage() == null) {
            return "";
        }
        String parentCode = page.getParentPage().getCode(language);

        return String.format(
                "<a class=\"back-link\" href=\"%s\">← %s</a>",
                parentCode.equals("root")
                        ? "/"
                        : parentCode.equals("/en/root")
                        ? "/en"
                        : parentCode,
                page.getParentPage().getTitle(language)
        );
    }

    private String buildContent(Page page, Language language) {
        StringBuilder builder = new StringBuilder();

        String baseContent = page.getContent(language);
        builder.append("<h4>")
                .append(baseContent)
                .append("</h4>");

        String childrenContainer = buildChildrenContainer(page, language);
        builder.append(childrenContainer);

        return builder.toString();
    }

    private String buildChildrenContainer(Page page, Language language) {
        StringBuilder builder = new StringBuilder();
        builder.append("<div class=\"child-container child-container-list\">");

        List<Page> childPages = new ArrayList<>(page.getChildPages());
        childPages.sort(page.getOrderTypeOrDefault());

        for (Page childPage : childPages) {
            builder.append(String.format(
                    "<div class=\"child-reference %s\">",
                    "child-reference-list")
            );
            builder.append(String.format(
                    "<img class=\"img-small\" src=\"%s\" />",
                    childPage.getImageUrl())
            );
            builder.append(String.format(
                    "<h3>%s</h3>",
                    childPage.getTitle(language))
            );
            builder.append(String.format(
                    "<p>%s</p>",
                    childPage.getDescription(language))
            );
            builder.append(String.format(
                    "<a href=\"%s\">%s</a>",
                    childPage.getCode(language), StaticFieldsUtils.getOpenTextShortened(language)));
            builder.append("</div>");
        }
        builder.append("</div>");

        return builder.toString();
    }

    private String buildHeader(Language language) {
        return String.format(
                "<h2>%s</h2>",
                StaticFieldsUtils.getHeaderText(language)
        );
    }

    private String buildFooter(Language language) {
        return String.format(
                "          <h3>%s</h3>"
                        + "<h5>%s</h5>",
                StaticFieldsUtils.getFooterSignText(language),
                StaticFieldsUtils.getFooterCopyrightsText(language)
        );
    }

    private Page findPageByCode(String pageCode) {
        return pageRepository.findByCode(pageCode).orElse(new Page());
    }
}
