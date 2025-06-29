package com.bitbyashish.library.book;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Data
@Entity
@Table(name = "books", uniqueConstraints = { @UniqueConstraint(columnNames = "isbn") })
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String author;

    @Column(nullable = false, unique = true)
    private String isbn;

    private Integer publishedYear;

    @Enumerated(EnumType.STRING)
    private AvailabilityStatus availabilityStatus;

    public enum AvailabilityStatus {
        AVAILABLE,
        BORROWED
    }
}
