package com.bitbyashish.library.notification;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitbyashish.library.book.Book;
import com.bitbyashish.library.wishlist.UserWishlist;
import com.bitbyashish.library.wishlist.WishlistRepository;

@Service
public class NotificationService {

    @Autowired
    private WishlistRepository wishlistRepository;

    public void notifyWishlistUsersAsync(Book book) {
        List<UserWishlist> wishlists = wishlistRepository.findByBookId(book.getId());
        for (UserWishlist wishlist : wishlists) {
            NotificationEvent event = new NotificationEvent();
            event.setUserId(wishlist.getUserId());
            event.setMessage("Book [" + book.getTitle() + "] is now available.");

            System.out.println("Notification prepared: " + event);
        }
    }

}
