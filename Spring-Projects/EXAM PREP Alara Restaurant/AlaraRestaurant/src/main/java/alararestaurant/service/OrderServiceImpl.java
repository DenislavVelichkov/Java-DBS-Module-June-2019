package alararestaurant.service;

import alararestaurant.models.dto.binding.OrderImportDto;
import alararestaurant.models.dto.binding.OrderImportRootDto;
import alararestaurant.models.dto.binding.OrderItemImportDto;
import alararestaurant.models.dto.binding.OrderItemImportRootDto;
import alararestaurant.models.entities.*;
import alararestaurant.repository.EmployeeRepository;
import alararestaurant.repository.ItemRepository;
import alararestaurant.repository.OrderItemRepository;
import alararestaurant.repository.OrderRepository;
import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidationUtil;
import alararestaurant.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private static final String ORDERS_PATH =
            System.getProperty("user.dir") + "/src/main/resources/files/orders.xml";
    private static final String POSITION = "Burger Flipper";
    private final DateTimeFormatter dateTimeFormatter;
    private final EmployeeRepository employeeRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ItemRepository itemRepository;
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;


    @Autowired
    public OrderServiceImpl(EmployeeRepository employeeRepository,
                            OrderRepository orderRepository,
                            OrderItemRepository orderItemRepository,
                            ItemRepository itemRepository,
                            FileUtil fileUtil,
                            ValidationUtil validationUtil,
                            ModelMapper modelMapper,
                            XmlParser xmlParser) {
        this.employeeRepository = employeeRepository;
        this.orderItemRepository = orderItemRepository;
        this.dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }

    @Override
    public Boolean ordersAreImported() {
        return this.orderRepository.count() != 0;
    }

    @Override
    public String readOrdersXmlFile() throws IOException {
        return this.fileUtil.readFile(ORDERS_PATH);
    }

    @Override
    public String importOrders() throws JAXBException {
        StringBuilder sb = new StringBuilder();
        OrderImportRootDto orderImportRootDto =
                this.xmlParser.parseXml(OrderImportRootDto.class, ORDERS_PATH);
        List<Order> orders = new ArrayList<>();

OrderImportValidation:
        for (OrderImportDto orderImportDto :
                orderImportRootDto.getOrderImportDtos()) {
            List<OrderItem> orderedItems = new ArrayList<>();
            for (OrderItemImportRootDto orderItemImportRootDto :
                    orderImportDto.getItems()) {
                for (OrderItemImportDto orderItemImportDto :
                        orderItemImportRootDto.getOrderItemImportDtos()) {

                    if (!this.validationUtil.isValid(orderImportDto)) {
                        System.out.println("Invalid data format.");

                        continue OrderImportValidation;
                    }

                    OrderItem orderItem = this.modelMapper.map(orderItemImportDto, OrderItem.class);
                    Item item =
                            this.itemRepository
                                    .findAllByName(orderItemImportDto.getItemName())
                                    .orElse(null);

                    if (item == null) {
                        continue OrderImportValidation;
                    }

                    orderItem.setItem(item);
                    orderedItems.add(orderItem);
                }
            }

            Order order = this.orderRepository
                    .findByEmployeeName(orderImportDto.getEmployeeName())
                    .orElse(null);

            if (order == null && orderedItems.size() != 0) {
                orderedItems.forEach(this.orderItemRepository::saveAndFlush);
                order = this.modelMapper.map(orderImportDto, Order.class);
                order.setDateTime(LocalDateTime.parse(
                        orderImportDto.getOrderDate(), dateTimeFormatter));
                Employee employee = this.employeeRepository.findAllByName(orderImportDto
                        .getEmployeeName())
                        .orElse(null);
                if (employee == null) {
                    System.out.println("Missing Employee!");
                    continue;
                }

                order.setEmployee(employee);
                Order finalOrder = order;
                orderedItems.forEach(item -> item.setOrder(finalOrder));
                order.setOrderItems(orderedItems);
                this.orderRepository.saveAndFlush(order);
                sb.append(String.format("Order for %s on %s added",
                        order.getCustomer(), order.getDateTime()
                                .toString().replace("T", " ")))
                        .append(System.lineSeparator());
            }
        }

        return sb.toString().trim();
    }

    @Override
    public String exportOrdersFinishedByTheBurgerFlippers() {
        StringBuilder sb = new StringBuilder();

        this.orderRepository.findAll()
                .stream()
                .filter(order -> order.getEmployee().getPosition().getName().equals(POSITION))
                .sorted(Comparator.comparing((Order o) -> o.getEmployee().getName()).thenComparingInt(BaseEntity::getId))
                .forEach(order -> {
                    sb.append(String.format("Name: %s", order.getEmployee().getName()))
                            .append(System.lineSeparator())
                            .append("Orders:")
                            .append(System.lineSeparator())
                            .append(String.format("\tCustomer: %s", order.getCustomer()))
                            .append("Items:")
                            .append(System.lineSeparator());
                    order.getOrderItems().forEach(orderItem -> {
                        sb.append(String.format("\tName: %s", orderItem.getItem().getName()))
                                .append(System.lineSeparator())
                                .append(String.format("\tPrice: %s", orderItem.getItem().getPrice()))
                                .append(System.lineSeparator())
                        .append(String.format("\tQuantity: %d", orderItem.getQuantity()))
                        .append(System.lineSeparator())
                        .append(System.lineSeparator());
                    });
                });

        return sb.toString().trim();
    }
}
