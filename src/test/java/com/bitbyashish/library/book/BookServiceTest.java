package com.bitbyashish.library.book;

import com.bitbyashish.library.notification.NotificationService;
import com.bitbyashish.library.wishlist.WishlistRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private WishlistRepository wishlistRepository;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private BookService bookService;

    public BookServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBook() {
        BookRequest req = new BookRequest();
        req.setTitle("Test Book");
        req.setAuthor("Test Author");
        req.setIsbn("123456");
        req.setPublishedYear(2024);
        req.setAvailabilityStatus(Book.AvailabilityStatus.AVAILABLE);

        when(bookRepository.existsByIsbn("123456")).thenReturn(false);
        when(bookRepository.save(any(Book.class))).thenAnswer(i -> i.getArgument(0));

        Book created = bookService.createBook(req);

        assertThat(created.getTitle()).isEqualTo("Test Book");
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    void testGetBooks_noFilters() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Book> page = new PageImpl<>(Collections.emptyList());
        when(bookRepository.findAll(pageable)).thenReturn(page);

        Page<Book> result = bookService.getBooks(null, null, pageable);

        assertThat(result.getContent()).isEmpty();
        verify(bookRepository).findAll(pageable);
    }

    @Test
    void testUpdateBook_triggersNotification() {
        Book book = new Book();
        book.setId(1L);
        book.setAvailabilityStatus(Book.AvailabilityStatus.BORROWED);

        BookRequest req = new BookRequest();
        req.setTitle("Updated");
        req.setAuthor("Updated");
        req.setIsbn("123");
        req.setPublishedYear(2024);
        req.setAvailabilityStatus(Book.AvailabilityStatus.AVAILABLE);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book updated = bookService.updateBook(1L, req);

        assertThat(updated.getAvailabilityStatus()).isEqualTo(Book.AvailabilityStatus.AVAILABLE);
        verify(notificationService).notifyWishlistUsersAsync(book);
    }
}
