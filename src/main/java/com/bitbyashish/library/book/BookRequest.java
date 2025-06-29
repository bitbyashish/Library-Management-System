package com.bitbyashish.library.book;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String author;

    @NotBlank
    private String isbn;

    @NotNull
    @Min(1000)
    @Max(9999)
    private Integer publishedYear;

    @NotNull
    private Book.AvailabilityStatus availabilityStatus;
}

