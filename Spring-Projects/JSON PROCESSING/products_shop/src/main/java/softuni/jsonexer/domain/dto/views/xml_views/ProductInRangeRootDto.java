package softuni.jsonexer.domain.dto.views.xml_views;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductInRangeRootDto {

    @XmlElement(name = "product")
    private List<ProductsInRangeXmlViewDto> products;

    public ProductInRangeRootDto() {
    }

    public List<ProductsInRangeXmlViewDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsInRangeXmlViewDto> products) {
        this.products = products;
    }
}
