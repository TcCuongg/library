package com.example.library.repository;

import com.example.library.entity.Book;
import com.example.library.more.BookManage;
import com.example.library.more.BookMore;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("select DISTINCT book.status from Book book")
    public List<String> findAllStatus();

    @Query("select new com.example.library.more.BookMore(book.title, category.name, author.name, bookStorage.image, " +
            "book.content, book.id, author.id, category.id, book.cost, book.follow, bookStorage.quantity," +
            " book.sale + category.sale, bookStorage.id, bookStorage.importTime, book.status) from Book book inner join" +
            " book.categoryToBook category on book.categoryToBook.id = category.id" +
            " inner join book.authorBooksFromBook authorBook on book.id = authorBook.bookToAuthorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " inner join book.bookStoragesFromBook bookStorage on bookStorage.bookToBookStorage.id = book.id" +
            " inner join bookStorage.storageToBookStorage storage on bookStorage.storageToBookStorage.id = storage.id" +
            " where book.status = 'Đang bán' and storage.status = 'Mở cửa'")
    public List<BookMore> getAllBook(Pageable pageable);

    @Query("select count(book.id) from Book book inner join" +
            " book.categoryToBook category on book.categoryToBook.id = category.id" +
            " inner join book.authorBooksFromBook authorBook on book.id = authorBook.bookToAuthorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " inner join book.bookStoragesFromBook bookStorage on bookStorage.bookToBookStorage.id = book.id" +
            " inner join bookStorage.storageToBookStorage storage on bookStorage.storageToBookStorage.id = storage.id" +
            " where book.status = 'Đang bán' and storage.status = 'Mở cửa'")
    public int getCountAllBook();

    @Query("select max(book.follow) from Book book")
    public int maxBookFollow();

    @Query("select new com.example.library.more.BookMore(book.title, category.name, author.name, bookStorage.image, " +
            "book.content, book.id, author.id, category.id, book.cost, book.follow, bookStorage.quantity," +
            " book.sale + category.sale, bookStorage.id, bookStorage.importTime, book.status) from Book book inner join" +
            " book.categoryToBook category on book.categoryToBook.id = category.id" +
            " inner join book.authorBooksFromBook authorBook on book.id = authorBook.bookToAuthorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " inner join book.bookStoragesFromBook bookStorage on bookStorage.bookToBookStorage.id = book.id" +
            " inner join bookStorage.storageToBookStorage storage on bookStorage.storageToBookStorage.id = storage.id" +
            " where storage.status = 'Mở cửa' and book.status = 'Đang bán' and book.follow = (select max(book.follow) from Book book)")
    public List<BookMore> getTopBook(Pageable pageable);

    @Query("select new com.example.library.more.BookMore(book.title, category.name, author.name, bookStorage.image, " +
            "book.content, book.id, author.id, category.id, book.cost, book.follow, bookStorage.quantity, " +
            "book.sale + category.sale, bookStorage.id, bookStorage.importTime, book.status) from Book book inner join book.categoryToBook" +
            " category on book.categoryToBook.id = category.id" +
            " inner join book.authorBooksFromBook authorBook on book.id = authorBook.bookToAuthorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " inner join book.bookStoragesFromBook bookStorage on bookStorage.bookToBookStorage.id = book.id" +
            " inner join bookStorage.storageToBookStorage storage on bookStorage.storageToBookStorage.id = storage.id" +
            " where book.status = 'Đang bán' and storage.status = 'Mở cửa'")
    public List<BookMore> getBookFollowDesc(Pageable pageable);

    @Query("select new com.example.library.more.BookMore(book.title, category.name, author.name, bookStorage.image, " +
            "book.content, book.id, author.id, category.id, book.cost, book.follow, bookStorage.quantity, " +
            "book.sale + category.sale, bookStorage.id, bookStorage.importTime, book.status) from Book book inner join book.categoryToBook" +
            " category on book.categoryToBook.id = category.id" +
            " inner join book.authorBooksFromBook authorBook on book.id = authorBook.bookToAuthorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " inner join book.bookStoragesFromBook bookStorage on bookStorage.bookToBookStorage.id = book.id" +
            " inner join bookStorage.storageToBookStorage storage on bookStorage.storageToBookStorage.id = storage.id" +
            " where book.status = 'Đang bán' and storage.status = 'Mở cửa' and bookStorage.id = :id")
    public BookMore getBookByBookStorageId(@Param("id") Long id);

    @Query("select new com.example.library.more.BookMore(book.title, category.name, author.name, bookStorage.image, " +
            "book.content, book.id, author.id, category.id, book.cost, book.follow, bookStorage.quantity, " +
            "book.sale + category.sale, bookStorage.id, bookStorage.importTime, book.status) from Book book inner join book.categoryToBook" +
            " category on book.categoryToBook.id = category.id" +
            " inner join book.authorBooksFromBook authorBook on book.id = authorBook.bookToAuthorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " inner join book.bookStoragesFromBook bookStorage on bookStorage.bookToBookStorage.id = book.id" +
            " inner join bookStorage.storageToBookStorage storage on bookStorage.storageToBookStorage.id = storage.id" +
            " where book.status = 'Đang bán' and storage.status = 'Mở cửa' and category.name = :name and book.status = :status")
    public List<BookMore> getBookByCategory(@Param("status") String status, @Param("name") String name, Pageable pageable);

    @Query("select count(book) from Book book inner join book.categoryToBook" +
            " category on book.categoryToBook.id = category.id" +
            " inner join book.authorBooksFromBook authorBook on book.id = authorBook.bookToAuthorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " inner join book.bookStoragesFromBook bookStorage on bookStorage.bookToBookStorage.id = book.id" +
            " inner join bookStorage.storageToBookStorage storage on bookStorage.storageToBookStorage.id = storage.id" +
            " where book.status = 'Đang bán' and storage.status = 'Mở cửa' and category.name = :name and book.status = 'Đang bán'")
    public int getCountBookByCategory(@Param("name") String name);

    @Query("select new com.example.library.more.BookMore(book.title, category.name, author.name, bookStorage.image, " +
            "book.content, book.id, author.id, category.id, book.cost, book.follow, bookStorage.quantity, " +
            "book.sale + category.sale, bookStorage.id, bookStorage.importTime, book.status) from Book book inner join book.categoryToBook" +
            " category on book.categoryToBook.id = category.id" +
            " inner join book.authorBooksFromBook authorBook on book.id = authorBook.bookToAuthorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " inner join book.bookStoragesFromBook bookStorage on bookStorage.bookToBookStorage.id = book.id" +
            " inner join bookStorage.storageToBookStorage storage on bookStorage.storageToBookStorage.id = storage.id" +
            " where book.status = 'Đang bán' and storage.status = 'Mở cửa' and author.name = :name")
    public List<BookMore> getBookByAuthor(@Param("name") String name, Pageable pageable);

    @Query("select new com.example.library.more.BookMore(book.title, category.name, author.name, bookStorage.image, " +
            "book.content, book.id, author.id, category.id, book.cost, book.follow, bookStorage.quantity, " +
            "book.sale + category.sale, bookStorage.id, bookStorage.importTime, book.status) from Book book inner join book.categoryToBook" +
            " category on book.categoryToBook.id = category.id" +
            " inner join book.authorBooksFromBook authorBook on book.id = authorBook.bookToAuthorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " inner join book.bookStoragesFromBook bookStorage on bookStorage.bookToBookStorage.id = book.id" +
            " inner join bookStorage.storageToBookStorage storage on bookStorage.storageToBookStorage.id = storage.id" +
            " where book.status = 'Đang bán' and storage.status = 'Mở cửa' and book.content = :content")
    public List<BookMore> getBookByContent(@Param("content") String content, Pageable pageable);

    @Query("select new com.example.library.more.BookMore(book.title, category.name, author.name, bookStorage.image, " +
            "book.content, book.id, author.id, category.id, book.cost, book.follow, bookStorage.quantity, " +
            "book.sale + category.sale, bookStorage.id, bookStorage.importTime, book.status) from Book book inner join book.categoryToBook" +
            " category on book.categoryToBook.id = category.id" +
            " inner join book.authorBooksFromBook authorBook on book.id = authorBook.bookToAuthorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " inner join book.bookStoragesFromBook bookStorage on bookStorage.bookToBookStorage.id = book.id" +
            " inner join bookStorage.storageToBookStorage storage on bookStorage.storageToBookStorage.id = storage.id" +
            " where book.status = 'Đang bán' and storage.status = 'Mở cửa' and book.status = :status")
    public List<BookMore> getBookByStatus(@Param("status") String status, Pageable pageable);

    @Query("select new com.example.library.more.BookMore(book.title, category.name, author.name, bookStorage.image, " +
            "book.content, book.id, author.id, category.id, book.cost, book.follow, bookStorage.quantity, " +
            "book.sale + category.sale, bookStorage.id, bookStorage.importTime, book.status) from Book book inner join book.categoryToBook" +
            " category on book.categoryToBook.id = category.id" +
            " inner join book.authorBooksFromBook authorBook on book.id = authorBook.bookToAuthorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " inner join book.bookStoragesFromBook bookStorage on bookStorage.bookToBookStorage.id = book.id" +
            " inner join bookStorage.storageToBookStorage storage on bookStorage.storageToBookStorage.id = storage.id" +
            " where book.status = 'Đang bán' and storage.status = 'Mở cửa' and book.title = :title and book.status = 'Đang bán'")
    public List<BookMore> getBookByTitle(@Param("title") String title, Pageable pageable);

    @Query("select count(book) from Book book inner join book.categoryToBook" +
            " category on book.categoryToBook.id = category.id" +
            " inner join book.authorBooksFromBook authorBook on book.id = authorBook.bookToAuthorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " inner join book.bookStoragesFromBook bookStorage on bookStorage.bookToBookStorage.id = book.id" +
            " inner join bookStorage.storageToBookStorage storage on bookStorage.storageToBookStorage.id = storage.id" +
            " where book.status = 'Đang bán' and storage.status = 'Mở cửa' and book.title = :title and book.status = 'Đang bán'")
    public int getCountBookByTitle(@Param("title") String title);

    @Query("select new com.example.library.more.BookMore(book.title, category.name, author.name, bookStorage.image, " +
            "book.content, book.id, author.id, category.id, book.cost, book.follow, bookStorage.quantity, " +
            "book.sale + category.sale, bookStorage.id, bookStorage.importTime, book.status) from Book book inner join book.categoryToBook" +
            " category on book.categoryToBook.id = category.id" +
            " inner join book.authorBooksFromBook authorBook on book.id = authorBook.bookToAuthorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " inner join book.bookStoragesFromBook bookStorage on bookStorage.bookToBookStorage.id = book.id" +
            " inner join bookStorage.storageToBookStorage storage on bookStorage.storageToBookStorage.id = storage.id" +
            " where book.status = 'Đang bán' and storage.status = 'Mở cửa' and book.cost = :cost")
    public List<BookMore> getBookByCost(@Param("cost") Long cost, Pageable pageable);

    @Query("select new com.example.library.more.BookMore(book.title, category.name, author.name, bookStorage.image, " +
            "book.content, book.id, author.id, category.id, book.cost, book.follow, bookStorage.quantity, " +
            "book.sale + category.sale, bookStorage.id, bookStorage.importTime, book.status) from Book book inner join book.categoryToBook" +
            " category on book.categoryToBook.id = category.id" +
            " inner join book.authorBooksFromBook authorBook on book.id = authorBook.bookToAuthorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " inner join book.bookStoragesFromBook bookStorage on bookStorage.bookToBookStorage.id = book.id" +
            " inner join bookStorage.storageToBookStorage storage on bookStorage.storageToBookStorage.id = storage.id" +
            " where book.status = 'Đang bán' and storage.status = 'Mở cửa' and book.sale = :sale")
    public List<BookMore> getBookBySale(@Param("sale") int sale, Pageable pageable);

    @Query("select new com.example.library.more.BookMore(book.title, category.name, author.name, bookStorage.image, " +
            "book.content, book.id, author.id, category.id, book.cost, book.follow, bookStorage.quantity, " +
            "book.sale + category.sale, bookStorage.id, bookStorage.importTime, book.status, account.email) from Book book inner join book.categoryToBook" +
            " category on book.categoryToBook.id = category.id" +
            " inner join book.authorBooksFromBook authorBook on book.id = authorBook.bookToAuthorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " inner join book.bookStoragesFromBook bookStorage on bookStorage.bookToBookStorage.id = book.id" +
            " inner join bookStorage.storageToBookStorage storage on storage.id = bookStorage.storageToBookStorage.id" +
            " inner join bookStorage.accountToBookStorage account on bookStorage.accountToBookStorage.cardNumber = account.cardNumber" +
            " where storage.id = :id")
    public List<BookMore> getBookByStorageId(@Param("id") Long id, Pageable pageable);

    @Query("select new com.example.library.more.BookMore(book.title, category.name, author.name, bookStorage.image, " +
            "book.content, book.id, author.id, category.id, book.cost, book.follow, bookStorage.quantity, " +
            "book.sale + category.sale, bookStorage.id, bookStorage.importTime, book.status, account.email) from Book book inner join book.categoryToBook" +
            " category on book.categoryToBook.id = category.id" +
            " inner join book.authorBooksFromBook authorBook on book.id = authorBook.bookToAuthorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " inner join book.bookStoragesFromBook bookStorage on bookStorage.bookToBookStorage.id = book.id" +
            " inner join bookStorage.storageToBookStorage storage on storage.id = bookStorage.storageToBookStorage.id" +
            " inner join bookStorage.accountToBookStorage account on bookStorage.accountToBookStorage.cardNumber = account.cardNumber" +
            " where storage.id = :id and bookStorage.quantity = 0")
    public List<BookMore> getBookRemainsZero(@Param("id") Long id, Pageable pageable);

    @Query("select count(book.id) from Book book inner join book.categoryToBook" +
            " category on book.categoryToBook.id = category.id" +
            " inner join book.authorBooksFromBook authorBook on book.id = authorBook.bookToAuthorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " inner join book.bookStoragesFromBook bookStorage on bookStorage.bookToBookStorage.id = book.id" +
            " inner join bookStorage.storageToBookStorage storage on storage.id = bookStorage.storageToBookStorage.id" +
            " inner join bookStorage.accountToBookStorage account on bookStorage.accountToBookStorage.cardNumber = account.cardNumber" +
            " where storage.id = :id and bookStorage.quantity = 0")
    public int getCountBookRemainsZero(@Param("id") Long id);

    @Query("select count(book.id) from Book book inner join book.categoryToBook" +
            " category on book.categoryToBook.id = category.id" +
            " inner join book.authorBooksFromBook authorBook on book.id = authorBook.bookToAuthorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " inner join book.bookStoragesFromBook bookStorage on bookStorage.bookToBookStorage.id = book.id" +
            " inner join bookStorage.storageToBookStorage storage on storage.id = bookStorage.storageToBookStorage.id" +
            " inner join bookStorage.accountToBookStorage account on bookStorage.accountToBookStorage.cardNumber = account.cardNumber" +
            " where storage.id = :id")
    public int getCountBookByStorage(@Param("id") Long id);

    @Query("select new com.example.library.more.BookMore(book.title, category.name, author.name, bookStorage.image, " +
            "book.content, book.id, author.id, category.id, book.cost, book.follow, bookStorage.quantity, " +
            "book.sale + category.sale, bookStorage.id, bookStorage.importTime, book.status, account.email) from Book book inner join book.categoryToBook" +
            " category on book.categoryToBook.id = category.id" +
            " inner join book.authorBooksFromBook authorBook on book.id = authorBook.bookToAuthorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " inner join book.bookStoragesFromBook bookStorage on bookStorage.bookToBookStorage.id = book.id" +
            " inner join bookStorage.storageToBookStorage storage on storage.id = bookStorage.storageToBookStorage.id" +
            " inner join bookStorage.accountToBookStorage account on bookStorage.accountToBookStorage.cardNumber = account.cardNumber" +
            " where (:categoryName is null or category.name = :categoryName) and (:authorName is null or author.name = :authorName) and " +
            "bookStorage.importTime >= :timeStart and bookStorage.importTime <= :timeEnd and " +
            "bookStorage.quantity >= :quantityStart and bookStorage.quantity <= :quantityEnd and storage.id = :id")
    public List<BookMore> selectBookInStorage(@Param("id") Long id, @Param("categoryName") String categoryName
            , @Param("authorName") String authorName, @Param("timeStart") LocalDateTime timeStart, @Param("timeEnd") LocalDateTime timeEnd
            , @Param("quantityStart") int quantityStart, @Param("quantityEnd") int quantityEnd, Pageable pageable);

    @Query("select count(book.id) from Book book inner join book.categoryToBook" +
            " category on book.categoryToBook.id = category.id" +
            " inner join book.authorBooksFromBook authorBook on book.id = authorBook.bookToAuthorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " inner join book.bookStoragesFromBook bookStorage on bookStorage.bookToBookStorage.id = book.id" +
            " inner join bookStorage.storageToBookStorage storage on storage.id = bookStorage.storageToBookStorage.id" +
            " inner join bookStorage.accountToBookStorage account on bookStorage.accountToBookStorage.cardNumber = account.cardNumber" +
            " where (:categoryName is null or category.name = :categoryName) and (:authorName is null or author.name = :authorName) and " +
            "bookStorage.importTime >= :timeStart and bookStorage.importTime <= :timeEnd and " +
            "bookStorage.quantity >= :quantityStart and bookStorage.quantity <= :quantityEnd and storage.id = :id")
    public int countSelectBookInStorage(@Param("id") Long id, @Param("categoryName") String categoryName
            , @Param("authorName") String authorName, @Param("timeStart") LocalDateTime timeStart, @Param("timeEnd") LocalDateTime timeEnd
            , @Param("quantityStart") int quantityStart, @Param("quantityEnd") int quantityEnd);

    @Query("select new com.example.library.more.BookMore(book.title, category.name, author.name, bookStorage.image, " +
            "book.content, book.id, author.id, category.id, book.cost, book.follow, bookStorage.quantity, " +
            "book.sale + category.sale, bookStorage.id, bookStorage.importTime, book.status) from Book book inner join book.categoryToBook" +
            " category on book.categoryToBook.id = category.id" +
            " inner join book.authorBooksFromBook authorBook on book.id = authorBook.bookToAuthorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " inner join book.bookStoragesFromBook bookStorage on bookStorage.bookToBookStorage.id = book.id" +
            " inner join bookStorage.cartsFromBookStorage cart on bookStorage.id = cart.bookStorageToCart.id" +
            " inner join cart.accountToCart account on cart.accountToCart.cardNumber = account.cardNumber" +
            " inner join bookStorage.storageToBookStorage storage on bookStorage.storageToBookStorage.id = storage.id" +
            " where book.status = 'Đang bán' and storage.status = 'Mở cửa' and account.cardNumber = :cardNumber")
    public List<BookMore> getAllBookByAccountCart(@Param("cardNumber") Long cardNumber, Pageable pageable);

    @Query("select count(book) from Book book inner join book.categoryToBook" +
            " category on book.categoryToBook.id = category.id" +
            " inner join book.authorBooksFromBook authorBook on book.id = authorBook.bookToAuthorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " inner join book.bookStoragesFromBook bookStorage on bookStorage.bookToBookStorage.id = book.id" +
            " inner join bookStorage.cartsFromBookStorage cart on bookStorage.id = cart.bookStorageToCart.id" +
            " inner join cart.accountToCart account on cart.accountToCart.cardNumber = account.cardNumber" +
            " inner join bookStorage.storageToBookStorage storage on bookStorage.storageToBookStorage.id = storage.id" +
            " where book.status = 'Đang bán' and storage.status = 'Mở cửa' and account.cardNumber = :cardNumber")
    public int getCountAllBookByAccountCart(@Param("cardNumber") Long cardNumber);

    @Query("select new com.example.library.more.BookMore(book.title, category.name, author.name, bookStorage.image, " +
            "book.content, book.id, author.id, category.id, book.cost, book.follow, bookStorage.quantity, " +
            "book.sale + category.sale, bookStorage.id, bookStorage.importTime, book.status) from Book book inner join book.categoryToBook" +
            " category on book.categoryToBook.id = category.id" +
            " inner join book.authorBooksFromBook authorBook on book.id = authorBook.bookToAuthorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " inner join book.bookStoragesFromBook bookStorage on bookStorage.bookToBookStorage.id = book.id" +
            " inner join bookStorage.buysFromBookStorage buy on bookStorage.id = buy.bookStorageToBuy.id" +
            " inner join buy.accountToBuy account on buy.accountToBuy.cardNumber = account.cardNumber" +
            " where account.cardNumber = :cardNumber")
    public List<BookMore> getAllBookByAccountBuy(@Param("cardNumber") Long cardNumber, Pageable pageable);


    @Query("select count(book) from Book book inner join book.categoryToBook" +
            " category on book.categoryToBook.id = category.id" +
            " inner join book.authorBooksFromBook authorBook on book.id = authorBook.bookToAuthorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " inner join book.bookStoragesFromBook bookStorage on bookStorage.bookToBookStorage.id = book.id" +
            " inner join bookStorage.buysFromBookStorage buy on bookStorage.id = buy.bookStorageToBuy.id" +
            " inner join buy.accountToBuy account on buy.accountToBuy.cardNumber = account.cardNumber" +
            " where account.cardNumber = :cardNumber")
    public int getCountAllBookByAccountBuy(@Param("cardNumber") Long cardNumber);


    @Query("select new com.example.library.more.BookManage(book.id, author.id, book.title, book.cost, book.content, book.status, book.sale" +
            ", category.name, author.name) from Book book inner join book.categoryToBook category on" +
            " book.categoryToBook.id = category.id inner join book.authorBooksFromBook authorBook on book.id = authorBook.bookToAuthorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id")
    public List<BookManage> getAllBookManage(Pageable pageable);

    @Query("select count(book.id) from Book book inner join book.categoryToBook category on" +
            " book.categoryToBook.id = category.id inner join book.authorBooksFromBook authorBook on book.id = authorBook.bookToAuthorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id")
    public int getCountAllBookManage();

//    String category, String author, String costStart, String costEnd, String status
    @Query("select new com.example.library.more.BookManage(book.id, author.id, book.title, book.cost, book.content, book.status, book.sale" +
            ", category.name, author.name) from Book book inner join book.categoryToBook category on" +
            " book.categoryToBook.id = category.id inner join book.authorBooksFromBook authorBook on book.id = authorBook.bookToAuthorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " where (:categoryName is null or category.name = :categoryName) and" +
            " (:authorName is null or author.name = :authorName) and (:costStart is null or book.cost >= :costStart)" +
            " and (:costEnd is null or book.cost <= :costEnd) and (:status is null or book.status = :status)")
    public List<BookManage> selectBook(@Param("categoryName") String categoryName, @Param("authorName") String authorName,
                                       @Param("costStart") Long costStart, @Param("costEnd") Long costEnd,
                                       @Param("status") String status, Pageable pageable);

    @Query("select count(book.id) from Book book inner join book.categoryToBook category on" +
            " book.categoryToBook.id = category.id inner join book.authorBooksFromBook authorBook on book.id = authorBook.bookToAuthorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " where (:categoryName is null or category.name = :categoryName) and" +
            " (:authorName is null or author.name = :authorName) and (:status is null or book.status = :status)")
    public int getCountSelectBookManage(@Param("categoryName") String categoryName, @Param("authorName") String authorName, @Param("status") String status);

    @Query("select new com.example.library.more.BookManage(book.id, author.id, book.title, book.cost, book.content, book.status, book.sale" +
            ", category.name, author.name) from Book book inner join book.categoryToBook category on" +
            " book.categoryToBook.id = category.id inner join book.authorBooksFromBook authorBook on book.id = authorBook.bookToAuthorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " where book.title = :title")
    public List<BookManage> getBookManageByTitle(@Param("title") String title, Pageable pageable);

    @Query("select count(book.id) from Book book inner join book.categoryToBook category on" +
            " book.categoryToBook.id = category.id inner join book.authorBooksFromBook authorBook on book.id = authorBook.bookToAuthorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " where book.title = :title")
    public int getCountBookManageByTitle(@Param("title") String title);

    @Query("select new com.example.library.more.BookManage(book.id, author.id, book.title, book.cost, book.content, book.status, book.sale" +
            ", category.name, author.name) from Book book inner join book.categoryToBook category on" +
            " book.categoryToBook.id = category.id inner join book.authorBooksFromBook authorBook on book.id = authorBook.bookToAuthorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " where book.cost = :cost")
    public List<BookManage> getBookManageByCost(@Param("cost") Long cost, Pageable pageable);

    @Query("select count(book.id) from Book book inner join book.categoryToBook category on" +
            " book.categoryToBook.id = category.id inner join book.authorBooksFromBook authorBook on book.id = authorBook.bookToAuthorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " where book.cost = :cost")
    public int getCountBookManageByCost(@Param("cost") Long cost);



    @Query("select new com.example.library.more.BookManage(book.id, author.id, book.title, book.cost, book.content, book.status, book.sale" +
            ", category.name, author.name) from Book book inner join book.categoryToBook category on" +
            " book.categoryToBook.id = category.id inner join book.authorBooksFromBook authorBook on book.id = authorBook.bookToAuthorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " where book.status = :status")
    public List<BookManage> getBookManageByStatus(@Param("status") String status, Pageable pageable);

    @Query("select count(book.id) from Book book inner join book.categoryToBook category on" +
            " book.categoryToBook.id = category.id inner join book.authorBooksFromBook authorBook on book.id = authorBook.bookToAuthorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " where book.status = :status")
    public int getCountBookManageByStatus(@Param("status") String status);

    @Query("select new com.example.library.more.BookManage(book.id, author.id, book.title, book.cost, book.content, book.status, book.sale" +
            ", category.name, author.name) from Book book inner join book.categoryToBook category on" +
            " book.categoryToBook.id = category.id inner join book.authorBooksFromBook authorBook on book.id = authorBook.bookToAuthorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " where book.sale + category.sale = :sale")
    public List<BookManage> getBookManageBySale(@Param("sale") int sale, Pageable pageable);

    @Query("select count(book.id) from Book book inner join book.categoryToBook category on" +
            " book.categoryToBook.id = category.id inner join book.authorBooksFromBook authorBook on book.id = authorBook.bookToAuthorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " where book.sale + category.sale = :sale")
    public int getCountBookManageBySale(@Param("sale") int sale);

    @Query("select new com.example.library.more.BookManage(book.id, author.id, book.title, book.cost, book.content, book.status, book.sale" +
            ", category.name, author.name) from Book book inner join book.categoryToBook category on" +
            " book.categoryToBook.id = category.id inner join book.authorBooksFromBook authorBook on book.id = authorBook.bookToAuthorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " where category.name = :name")
    public List<BookManage> getBookManageByCategory(@Param("name") String name, Pageable pageable);

    @Query("select count(book.id) from Book book inner join book.categoryToBook category on" +
            " book.categoryToBook.id = category.id inner join book.authorBooksFromBook authorBook on book.id = authorBook.bookToAuthorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " where category.name = :name")
    public int getCountBookManageByCategory(@Param("name") String name);

    @Query("select new com.example.library.more.BookManage(book.id, author.id, book.title, book.cost, book.content, book.status, book.sale" +
            ", category.name, author.name) from Book book inner join book.categoryToBook category on" +
            " book.categoryToBook.id = category.id inner join book.authorBooksFromBook authorBook on book.id = authorBook.bookToAuthorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " where author.name = :name")
    public List<BookManage> getBookManageByAuthor(@Param("name") String name, Pageable pageable);

    @Query("select count(book.id) from Book book inner join book.categoryToBook category on" +
            " book.categoryToBook.id = category.id inner join book.authorBooksFromBook authorBook on book.id = authorBook.bookToAuthorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " where author.name = :name")
    public int getCountBookManageByAuthor(@Param("name") String name);

    @Query("select new com.example.library.more.BookManage(book.id, author.id, book.title, book.cost, book.content, book.status, book.sale" +
            ", category.name, author.name) from Book book inner join book.categoryToBook category on" +
            " book.categoryToBook.id = category.id inner join book.authorBooksFromBook authorBook on book.id = authorBook.bookToAuthorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " where book.id = :id")
    public BookManage getBookManageByBookId(@Param("id") Long id);

    public Book findFirstById(Long id);

    @Query("select new com.example.library.more.BookMore(book.title, category.name, author.name, bookStorage.image, " +
            "book.content, book.id, author.id, category.id, book.cost, book.follow, bookStorage.quantity, " +
            "book.sale + category.sale, bookStorage.id, bookStorage.importTime, book.status) from Book book inner join book.categoryToBook" +
            " category on book.categoryToBook.id = category.id" +
            " inner join book.authorBooksFromBook authorBook on book.id = authorBook.bookToAuthorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " inner join book.bookStoragesFromBook bookStorage on bookStorage.bookToBookStorage.id = book.id" +
            " where book.title = :title")
    public List<BookMore> getBookByTitleOnManage(@Param("title") String title, Pageable pageable);

    @Query("select new com.example.library.more.BookMore(book.title, category.name, author.name, bookStorage.image, " +
            "book.content, book.id, author.id, category.id, book.cost, book.follow, bookStorage.quantity, " +
            "book.sale + category.sale, bookStorage.id, bookStorage.importTime, book.status) from Book book inner join book.categoryToBook" +
            " category on book.categoryToBook.id = category.id" +
            " inner join book.authorBooksFromBook authorBook on book.id = authorBook.bookToAuthorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " inner join book.bookStoragesFromBook bookStorage on bookStorage.bookToBookStorage.id = book.id" +
            " where category.name = :name")
    public List<BookMore> getBookByCategoryOnManage(@Param("name") String name, Pageable pageable);





    @Query("select new com.example.library.more.BookMore(book.title, category.name, author.name, bookStorage.image, " +
            "book.content, book.id, author.id, category.id, book.cost, book.follow, bookStorage.quantity, " +
            "book.sale + category.sale, bookStorage.id, bookStorage.importTime, book.status) from Book book inner join" +
            " book.categoryToBook category on book.categoryToBook.id = category.id" +
            " inner join book.authorBooksFromBook authorBook on book.id = authorBook.bookToAuthorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " inner join book.bookStoragesFromBook bookStorage on bookStorage.bookToBookStorage.id = book.id" +
            " inner join bookStorage.storageToBookStorage storage on bookStorage.storageToBookStorage.id = storage.id" +
            " where storage.id = :id and book.title = :title")
    public List<BookMore> getAllBookByStorageAndTitle(@Param("id") Long id, @Param("title") String title, Pageable pageable);

    @Query("select count(book.id) from Book book inner join" +
            " book.categoryToBook category on book.categoryToBook.id = category.id" +
            " inner join book.authorBooksFromBook authorBook on book.id = authorBook.bookToAuthorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " inner join book.bookStoragesFromBook bookStorage on bookStorage.bookToBookStorage.id = book.id" +
            " inner join bookStorage.storageToBookStorage storage on bookStorage.storageToBookStorage.id = storage.id" +
            " where storage.id = :id and book.title = :title")
    public int getCountAllBookByStorageAndTitle(@Param("id") Long id, @Param("title") String title);

    @Query("select new com.example.library.more.BookMore(book.title, category.name, author.name, bookStorage.image, " +
            "book.content, book.id, author.id, category.id, book.cost, book.follow, bookStorage.quantity, " +
            "book.sale + category.sale, bookStorage.id, bookStorage.importTime, book.status) from Book book inner join" +
            " book.categoryToBook category on book.categoryToBook.id = category.id" +
            " inner join book.authorBooksFromBook authorBook on book.id = authorBook.bookToAuthorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " inner join book.bookStoragesFromBook bookStorage on bookStorage.bookToBookStorage.id = book.id" +
            " inner join bookStorage.storageToBookStorage storage on bookStorage.storageToBookStorage.id = storage.id" +
            " where storage.id = :id and category.name = :name")
    public List<BookMore> getAllBookByStorageAndCategory(@Param("id") Long id, @Param("name") String name, Pageable pageable);

    @Query("select count(book.id) from Book book inner join" +
            " book.categoryToBook category on book.categoryToBook.id = category.id" +
            " inner join book.authorBooksFromBook authorBook on book.id = authorBook.bookToAuthorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " inner join book.bookStoragesFromBook bookStorage on bookStorage.bookToBookStorage.id = book.id" +
            " inner join bookStorage.storageToBookStorage storage on bookStorage.storageToBookStorage.id = storage.id" +
            " where storage.id = :id and category.name = :name")
    public int getCountAllBookByStorageAndCategory(@Param("id") Long id, @Param("name") String name);

    @Query("select new com.example.library.more.BookMore(book.title, category.name, author.name, bookStorage.image, " +
            "book.content, book.id, author.id, category.id, book.cost, book.follow, bookStorage.quantity, " +
            "book.sale + category.sale, bookStorage.id, bookStorage.importTime, book.status) from Book book inner join" +
            " book.categoryToBook category on book.categoryToBook.id = category.id" +
            " inner join book.authorBooksFromBook authorBook on book.id = authorBook.bookToAuthorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " inner join book.bookStoragesFromBook bookStorage on bookStorage.bookToBookStorage.id = book.id" +
            " inner join bookStorage.storageToBookStorage storage on bookStorage.storageToBookStorage.id = storage.id" +
            " where storage.id = :id and author.name = :author")
    public List<BookMore> getAllBookByStorageAndAuthor(@Param("id") Long id, @Param("author") String author, Pageable pageable);

    @Query("select count(book.id) from Book book inner join" +
            " book.categoryToBook category on book.categoryToBook.id = category.id" +
            " inner join book.authorBooksFromBook authorBook on book.id = authorBook.bookToAuthorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " inner join book.bookStoragesFromBook bookStorage on bookStorage.bookToBookStorage.id = book.id" +
            " inner join bookStorage.storageToBookStorage storage on bookStorage.storageToBookStorage.id = storage.id" +
            " where storage.id = :id and author.name = :author")
    public int getCountAllBookByStorageAndAuthor(@Param("id") Long id, @Param("author") String author);




    public Book findFirstByTitle(String title);
    public Book findFirstByOrderByIdDesc();
}
