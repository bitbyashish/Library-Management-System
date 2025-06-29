package com.bitbyashish.library.book;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<BookResponse> create(@Valid @RequestBody BookRequest request) {
        Book saved = bookService.createBook(request);
        return ResponseEntity.ok(toResponse(saved));
    }

    @GetMapping
    public Page<BookResponse> list(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) Integer publishedYear,
            @PageableDefault(size = 10) Pageable pageable) {
        return bookService.getBooks(author, publishedYear, pageable)
                .map(this::toResponse);
    }

    @GetMapping("/search")
    public List<BookResponse> search(@RequestParam String query) {
        return bookService.searchBooks(query).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> update(@PathVariable Long id, @Valid @RequestBody BookRequest request) {
        Book updated = bookService.updateBook(id, request);
        return ResponseEntity.ok(toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    private BookResponse toResponse(Book book) {
        BookResponse resp = new BookResponse();
        BeanUtils.copyProperties(book, resp);
        return resp;
    }
}
