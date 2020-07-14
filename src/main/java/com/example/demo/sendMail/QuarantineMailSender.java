package com.example.demo.sendMail;

import com.example.demo.model.AcquiredProduct;
import com.example.demo.model.Bill;
import com.example.demo.model.DeliveryType;
import com.example.demo.model.ticket.Ticket;
import com.example.demo.model.user.StoreAdminUser;
import com.example.demo.model.user.User;
import com.example.demo.services.users.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class QuarantineMailSender {

    private Map<DayOfWeek, String> daysOfWeekNames = new HashMap<>();
    private Map<Month, String> monthsNames = new HashMap<>();

    private final JavaMailSender mailSender;

    @Autowired
    IUserService userService;

    public QuarantineMailSender(JavaMailSender javaMailSender) {
        this.mailSender = javaMailSender;
        this.daysOfWeekNames.put(DayOfWeek.MONDAY, "Lunes");
        this.daysOfWeekNames.put(DayOfWeek.TUESDAY, "Martes");
        this.daysOfWeekNames.put(DayOfWeek.WEDNESDAY, "Miercoles");
        this.daysOfWeekNames.put(DayOfWeek.THURSDAY, "Jueves");
        this.daysOfWeekNames.put(DayOfWeek.FRIDAY, "Viernes");
        this.daysOfWeekNames.put(DayOfWeek.SATURDAY, "Sabado");
        this.daysOfWeekNames.put(DayOfWeek.SUNDAY, "Domingo");
    }

    public void sendMiMensajePiola(SimpleMailMessage message) {
        this.mailSender.send(message);
    }

    public void sendPurchaseConfirmationMail(Bill bill, User user, DeliveryType delivery) {
        String purchaseDateTime = this.parseBillDateTime(bill.getDateTime());
        String storesNames = this.parseStoresNames(bill.getTickets());
        String deliveryText = this.parseDelivery(delivery, user);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@baeldung.com");
        message.setTo(user.username());
        message.setSubject("Confirmación de pedido" + " " + purchaseDateTime);
        message.setText("Tu pedido para los comercios " + storesNames + " fue confirmado." + deliveryText + ". ¡Muchas gracias por tu compra!");
        this.mailSender.send(message);
    }

    private String parseDelivery(DeliveryType delivery, User user) {
        if(delivery.isStorePickUp()) {
            return " Cada comercio se va a estar comunicando con vos para coordinar un turno";
        } else {
            String date = this.parseBillDateAndTime(delivery.pickUpDate());
            return "Tu pedido va a estar llegando a " + user.address() + date;
        }
    }

    private void sendMailToTicketStore(Ticket ticket, User clientUser, DeliveryType deliveryType) {
        String clientText = "¡Ey! tenes un nuevo pedido del usuario " + clientUser.username();
        String deliveryText = deliveryAddressAndDateTimeForStoreMail(clientUser, deliveryType, ticket);
        StoreAdminUser storeAdminUser = userService.findStoreAdmin(ticket.store().id());
        String mailProductList = parseProductList(ticket.getListOfAdquiredProducts());
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@baeldung.com");
        message.setTo(storeAdminUser.username());
        message.setSubject("¡Ey! Tenes un nuevo pedido");
        message.setText(clientText + "\n\n"
                        + mailProductList + "\n\n"
                        + deliveryText);
        this.mailSender.send(message);

    }

    private String parseProductList(List<AcquiredProduct> listOfAdquiredProducts) {
        String productListText = "";
        for (AcquiredProduct acquiredProduct : listOfAdquiredProducts) {
            productListText = productListText + acquiredProduct.getMerchandise().name() + ", " + acquiredProduct.getMerchandise().brand() + " -> " + acquiredProduct.quantity() + " unidades" + "\n\n";
        }
        return productListText;
    }


    private String parseStoresNames(List<Ticket> tickets) {
        String storeNamesText = "";
        List<String> storeNames = tickets.stream().map(ticket -> ticket.store().name()).collect(Collectors.toList());
        for (String aStoreName : storeNames) {
            storeNamesText = storeNamesText.concat(aStoreName).concat(", ");
        }
        return storeNamesText;
    }

    private String parseBillDateTime(LocalDateTime dateTime) {
        String dayOfWeek = this.daysOfWeekNames.get(dateTime.getDayOfWeek());
        String monthDay = String.valueOf(dateTime.getDayOfMonth());
        String monthNumber = String.valueOf(dateTime.getMonthValue());
        String year = String.valueOf(dateTime.getYear());
        String billDateTimeText = dayOfWeek + " " + monthDay + "-" + monthNumber + "-" + year;
        return billDateTimeText;
    };

    private String parseBillDateAndTime(LocalDateTime localDateTime) {
        String date = this.parseBillDateTime(localDateTime);
        String hour = String.valueOf(localDateTime.getHour());
        String minute = String.valueOf(localDateTime.getMinute());
        return "el " + date + " aproximadamente a las " + hour + ":" + minute;
    }

    public void sendStoresNewPurchaseMail(Bill bill, User clientUser, DeliveryType deliveryType) {
        bill.getTickets().stream().forEach(ticket -> sendMailToTicketStore(ticket, clientUser, deliveryType));
    }

    private String deliveryAddressAndDateTimeForStoreMail(User clientUser, DeliveryType deliveryType, Ticket ticket) {
        String deliveryMessage = "";
        if(deliveryType.isStorePickUp()) {
            deliveryMessage = "Le cliente pasará a buscar el pedido por el local " + this.parseBillDateAndTime(ticket.store().nextTurn(LocalDateTime.now()));
        } else {
            deliveryMessage = "Le cliente espera el pedido para " + this.parseBillDateAndTime(deliveryType.pickUpDate()) + " en la direccion " + clientUser.address();
        }
        return deliveryMessage;
    }
}
