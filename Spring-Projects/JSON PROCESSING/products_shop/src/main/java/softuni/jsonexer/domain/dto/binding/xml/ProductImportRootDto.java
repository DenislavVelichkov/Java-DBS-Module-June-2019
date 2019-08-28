package softuni.jsonexer.domain.dto.binding.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductImportRootDto implements Serializable {

    @XmlElement(name = "product")
    private ProductImportDto[] productImportDtos;

    public ProductImportRootDto() {
    }

    public ProductImportDto[] getProductImportDtos() {
        return productImportDtos;
    }

    public void setProductImportDtos(ProductImportDto[] productImportDtos) {
        this.productImportDtos = productImportDtos;
    }
}
