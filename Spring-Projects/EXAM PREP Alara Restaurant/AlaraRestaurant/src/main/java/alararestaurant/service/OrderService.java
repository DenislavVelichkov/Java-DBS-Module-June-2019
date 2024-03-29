package alararestaurant.service;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface OrderService {

    Boolean ordersAreImported();

    String readOrdersXmlFile() throws JAXBException, IOException;

    String importOrders() throws JAXBException;

    String exportOrdersFinishedByTheBurgerFlippers();
}
