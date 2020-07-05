package com.example.demo.model;

import com.example.demo.model.ticket.Ticket;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Ticket> allTickets;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private DeliveryType deliveryType;

    public Bill(List<Ticket> tickets, DeliveryType delivery){
        this.allTickets = tickets;
        this.deliveryType = delivery;
    }

    public Integer quantityTickets() {
        return this.allTickets.size();
    }

    public List<Ticket> getTickets() {
        return this.allTickets;
    }

    public String addressOfDelivery() {
        return this.deliveryType.deliveryAddress();
    }

    public LocalDateTime deliveryTime() {
        return this.deliveryType.pickUpDate();
    }

    public Double totalPrice() {
        return allTickets.stream().mapToDouble(Ticket::getTotal).sum();
    }

    public Boolean hasTicket(Ticket aTicket) {
        return allTickets.stream().anyMatch(ticket -> ticket.equals(aTicket));
    }

    public void setId(Long id) { this.id = id;}

    public Long id() { return this.id;}

    public DeliveryType getDeliveryType() { return this.deliveryType;}

}
