package com.bitbyashish.library.wishlist;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepository extends JpaRepository<UserWishlist, Long> {
    List<UserWishlist> findByBookId(Long bookId);
}
