package com.kpi.web.systems.lab2.model;

import com.kpi.web.systems.lab2.model.enums.Language;
import com.kpi.web.systems.lab2.model.enums.OrderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.kpi.web.systems.lab2.model.enums.Language.UA;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "PAGE")
public class Page implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pages_generator")
    @SequenceGenerator(name = "pages_generator", sequenceName = "pages_sequence")
    private Long id;
    /*
     * unique code of page
     */
    @NaturalId
    @Column(name = "CODE", unique = true, nullable = false)
    private String code;
    /*
     * title, h1
     */
    @Column(name = "TITLE_UA", nullable = false)
    private String titleUa;

    @Column(name = "TITLE_EN", nullable = false)
    private String titleEn;
    /*
     * short description
     */
    @Column(name = "DESCRIPTION_UA", nullable = false)
    private String descriptionUa;

    @Column(name = "DESCRIPTION_EN", nullable = false)
    private String descriptionEn;

    @Column(name = "CONTENT_UA", nullable = false)
    private String contentUa;

    @Column(name = "CONTENT_EN", nullable = false)
    private String contentEn;

    @Column(name = "IMAGE_URL", nullable = false)
    private String imageUrl;

    @Column(name = "CREATION_DATE", nullable = false)
    private Date creationDate;

    @Column(name = "UPDATE_DATE", nullable = false)
    private Date updateDate;

    @ManyToOne
    @JoinColumn(name = "PARENT_CODE", referencedColumnName = "CODE")
    private Page parentPage;

    @OneToMany(mappedBy = "parentPage")
    private List<Page> childPages = new ArrayList<>();
    /*
     * to determine current page position in parent container
     */
    @Column(name = "ORDER_NUM")
    private Integer orderNum;
    /*
     * to determine order type of children in current container
     */
    @Column(name = "ORDER_TYPE")
    @Enumerated(EnumType.STRING)
    private OrderType orderType;

    @ManyToOne
    @JoinColumn(name = "ALIAS_PAGE", referencedColumnName = "CODE")
    private Page aliasPage;

    public String getCode(Language language) {
        return UA.equals(language)
                ? "" + code
                : "/en/" + code;
    }

    public String getTitle(Language language) {
        return UA.equals(language)
                ? titleUa
                : titleEn;
    }

    public String getDescription(Language language) {
        return UA.equals(language)
                ? descriptionUa
                : descriptionEn;
    }

    public String getContent(Language language) {
        return UA.equals(language)
                ? contentUa
                : contentEn;
    }

    public Comparator<Page> getOrderTypeOrDefault(){
        return this.orderType!=null?this.orderType.getComparator():OrderType.DEFAULT.getComparator();
    }
}
