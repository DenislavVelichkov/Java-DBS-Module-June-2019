package alararestaurant.models.dto.binding;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "items")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderItemImportRootDto {
    
    @XmlElement(name = "item")
    private OrderItemImportDto[] orderItemImportDtos;
    
    public OrderItemImportRootDto() {
    }
    
    public OrderItemImportDto[] getOrderItemImportDtos() {
        return orderItemImportDtos;
    }
    
    public void setOrderItemImportDtos(OrderItemImportDto[] orderItemImportDtos) {
        this.orderItemImportDtos = orderItemImportDtos;
    }
}
