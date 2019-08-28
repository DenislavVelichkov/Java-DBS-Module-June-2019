package alararestaurant.models.dto.binding;

import alararestaurant.models.entities.OrderType;
import org.springframework.format.datetime.DateFormatter;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderImportDto {
    private final DateFormatter dateFormat = new DateFormatter("dd-MM-yyyy HH:mm");

    @XmlElement(name = "customer")
    private String customerName;
    
    @XmlElement(name = "employee")
    private String employeeName;
    
    @XmlElement(name = "date-time")
    private String orderDate;
    
    @XmlElement(name = "type")
    private OrderType orderType = OrderType.ForHere;
    
    @XmlElement
    private OrderItemImportRootDto[] items;
    
    public OrderImportDto() {
    }

    @NotNull
    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @NotNull
    public String getEmployeeName() {
        return employeeName;
    }
    
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    @NotNull
    public String getOrderDate() {
        return orderDate;
    }
    
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate ;
    }

    @NotNull
    public OrderType getOrderType() {
        return orderType;
    }
    
    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }
    
    public OrderItemImportRootDto[] getItems() {
        return items;
    }
    
    public void setItems(OrderItemImportRootDto[] items) {
        this.items = items;
    }
}
