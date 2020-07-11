package com.example.demo.sendMail;

import com.example.demo.model.Bill;
import com.example.demo.model.DeliveryType;
import com.example.demo.model.ticket.Ticket;
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

    public void sendPurchaseConfirmationMail(Bill bill, String username, DeliveryType delivery) {
        String purchaseDateTime = this.parseBillDateTime(bill.getDateTime());
        String storesNames = this.parseStoresNames(bill.getTickets());
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@baeldung.com");
        message.setTo("belen.amat29@gmail.com");
        message.setSubject("Confirmación de pedido" + " " + purchaseDateTime);
        message.setText("Tu pedido para los comercios " + storesNames + " fue confirmado");
        this.mailSender.send(message);
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
}
