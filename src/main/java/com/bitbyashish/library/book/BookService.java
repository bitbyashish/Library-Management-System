package com.bitbyashish.library.book;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bitbyashish.library.notification.NotificationService;
import com.bitbyashish.library.wishlist.WishlistRepository;

import jakarta.persistence.criteria.Predicate;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private NotificationService notificationService;

    public Book createBook(BookRequest request) {
        if (bookRepository.existsByIsbn(request.getIsbn())) {
            throw new RuntimeException("ISBN must be unique.");
        }
        Book book = new Book();
        BeanUtils.copyProperties(request, book);
        return bookRepository.save(book);
    }

    public Page<Book> getBooks(String author, Integer year, Pageable pageable) {
        return bookRepository.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (author != null && !author.isEmpty()) {
                predicates.add(cb.equal(root.get("author"), author));
            }
            if (year != null) {
                predicates.add(cb.equal(root.get("publishedYear"), year));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        }, pageable);
    }

    public List<Book> searchBooks(String query) {
        return bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(query, query);
    }

    public Book updateBook(Long id, BookRequest request) {
        Book existing = bookRepository.findById(id).orElseThrow();
        boolean wasBorrowed = existing.getAvailabilityStatus() == Book.AvailabilityStatus.BORROWED;

        BeanUtils.copyProperties(request, existing, "id");
        Book saved = bookRepository.save(existing);

        if (wasBorrowed && saved.getAvailabilityStatus() == Book.AvailabilityStatus.AVAILABLE) {
            notificationService.notifyWishlistUsersAsync(saved);
        }
        return saved;
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
