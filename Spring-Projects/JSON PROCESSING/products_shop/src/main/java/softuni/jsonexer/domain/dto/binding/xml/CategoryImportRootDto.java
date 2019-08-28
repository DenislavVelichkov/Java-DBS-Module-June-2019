package softuni.jsonexer.domain.dto.binding.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "categories")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryImportRootDto implements Serializable {

    @XmlElement(name = "category")
    private CategoryImportDto[] categoryImportDtos;

    public CategoryImportRootDto() {
    }

    public CategoryImportDto[] getCategoryImportDtos() {
        return categoryImportDtos;
    }

    public void setCategoryImportDtos(CategoryImportDto[] categoryImportDtos) {
        this.categoryImportDtos = categoryImportDtos;
    }
}
