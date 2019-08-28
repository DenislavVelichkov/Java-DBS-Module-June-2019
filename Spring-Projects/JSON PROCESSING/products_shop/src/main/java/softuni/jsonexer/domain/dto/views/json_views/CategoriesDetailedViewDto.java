package softuni.jsonexer.domain.dto.views.json_views;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class CategoriesDetailedViewDto implements Serializable {

    @Expose
    private String name;

    @Expose
    private Integer productsCount;

    @Expose
    private BigDecimal averagePrice;

    @Expose
    private BigDecimal totalRevenue;

    public CategoriesDetailedViewDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProductsCount() {
        return productsCount;
    }

    public void setProductsCount(Integer productsCount) {
        this.productsCount = productsCount;
    }

    public BigDecimal getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(BigDecimal averagePrice) {
        DecimalFormat df = new DecimalFormat("#.######");
        this.averagePrice =
                BigDecimal.valueOf(Double.valueOf(df.format(averagePrice)));
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        DecimalFormat df = new DecimalFormat("#.######");
        this.totalRevenue =
                BigDecimal.valueOf(Double.valueOf(df.format(totalRevenue)));
    }
}
