package com.programmers.happylotto.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orders", schema = "happy_lotto")
public class Order {
    @Id
    @Column(name = "order_id", nullable = false, length = 16)
    private UUID orderId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Lotto> lottos = new ArrayList<>();

    @Builder
    private Order(UUID orderId, User user, LocalDateTime createdAt) {
        this.orderId = orderId;
        this.user = user;
        this.createdAt = createdAt;
    }
}