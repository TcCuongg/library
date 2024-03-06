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

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("select new com.example.library.more.BookMore(book.title, category.name, author.name, bookStorage.image, " +
            "book.content, book.id, author.id, category.id, book.cost, book.follow, bookStorage.quantity, " +
            "book.sale + category.sale, bookStorage.id, bookStorage.importTime, book.status) from Book book inner join" +
            " book.categoryToBook category on book.categoryToBook.id = category.id" +
            " inner join book.authorBooksFromBook authorBook on book.id = authorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " inner join book.bookStoragesFromBook bookStorage on bookStorage.bookToBookStorage.id = book.id")
    public List<BookMore> getAllBook(Pageable pageable);

    @Query("select new com.example.library.more.BookMore(book.title, category.name, author.name, bookStorage.image, " +
            "book.content, book.id, author.id, category.id, book.cost, book.follow, bookStorage.quantity, " +
            "book.sale + category.sale, bookStorage.id, bookStorage.importTime, book.status) from Book book inner join book.categoryToBook" +
            " category on book.categoryToBook.id = category.id" +
            " inner join book.authorBooksFromBook authorBook on book.id = authorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " inner join book.bookStoragesFromBook bookStorage on bookStorage.bookToBookStorage.id = book.id" +
            " where book.status = 'Đang bán' and book.follow = (select max(book.follow) from Book book)")
    public BookMore getTopBook();

    @Query("select new com.example.library.more.BookMore(book.title, category.name, author.name, bookStorage.image, " +
            "book.content, book.id, author.id, category.id, book.cost, book.follow, bookStorage.quantity, " +
            "book.sale + category.sale, bookStorage.id, bookStorage.importTime, book.status) from Book book inner join book.categoryToBook" +
            " category on book.categoryToBook.id = category.id" +
            " inner join book.authorBooksFromBook authorBook on book.id = authorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " inner join book.bookStoragesFromBook bookStorage on bookStorage.bookToBookStorage.id = book.id" +
            " where book.status = 'Đang bán'")
    public List<BookMore> getBookFollowDesc(Pageable pageable);

    @Query("select new com.example.library.more.BookMore(book.title, category.name, author.name, bookStorage.image, " +
            "book.content, book.id, author.id, category.id, book.cost, book.follow, bookStorage.quantity, " +
            "book.sale + category.sale, bookStorage.id, bookStorage.importTime, book.status) from Book book inner join book.categoryToBook" +
            " category on book.categoryToBook.id = category.id" +
            " inner join book.authorBooksFromBook authorBook on book.id = authorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " inner join book.bookStoragesFromBook bookStorage on bookStorage.bookToBookStorage.id = book.id" +
            " where bookStorage.id = :id")
    public BookMore getBookByBookStorageId(@Param("id") Long id);

    @Query("select new com.example.library.more.BookMore(book.title, category.name, author.name, bookStorage.image, " +
            "book.content, book.id, author.id, category.id, book.cost, book.follow, bookStorage.quantity, " +
            "book.sale + category.sale, bookStorage.id, bookStorage.importTime, book.status) from Book book inner join book.categoryToBook" +
            " category on book.categoryToBook.id = category.id" +
            " inner join book.authorBooksFromBook authorBook on book.id = authorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " inner join book.bookStoragesFromBook bookStorage on bookStorage.bookToBookStorage.id = book.id" +
            " where category.name = :name and book.status = :status")
    public List<BookMore> getBookByCategory(@Param("status") String status, @Param("name") String name, Pageable pageable);

    @Query("select new com.example.library.more.BookMore(book.title, category.name, author.name, bookStorage.image, " +
            "book.content, book.id, author.id, category.id, book.cost, book.follow, bookStorage.quantity, " +
            "book.sale + category.sale, bookStorage.id, bookStorage.importTime, book.status) from Book book inner join book.categoryToBook" +
            " category on book.categoryToBook.id = category.id" +
            " inner join book.authorBooksFromBook authorBook on book.id = authorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " inner join book.bookStoragesFromBook bookStorage on bookStorage.bookToBookStorage.id = book.id" +
            " where category.name = :name")
    public List<BookMore> getBookByCategoryOnManage(@Param("name") String name, Pageable pageable);

    @Query("select new com.example.library.more.BookMore(book.title, category.name, author.name, bookStorage.image, " +
            "book.content, book.id, author.id, category.id, book.cost, book.follow, bookStorage.quantity, " +
            "book.sale + category.sale, bookStorage.id, bookStorage.importTime, book.status) from Book book inner join book.categoryToBook" +
            " category on book.categoryToBook.id = category.id" +
            " inner join book.authorBooksFromBook authorBook on book.id = authorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " inner join book.bookStoragesFromBook bookStorage on bookStorage.bookToBookStorage.id = book.id" +
            " where author.name = :name")
    public List<BookMore> getBookByAuthor(@Param("name") String name, Pageable pageable);

    @Query("select new com.example.library.more.BookMore(book.title, category.name, author.name, bookStorage.image, " +
            "book.content, book.id, author.id, category.id, book.cost, book.follow, bookStorage.quantity, " +
            "book.sale + category.sale, bookStorage.id, bookStorage.importTime, book.status) from Book book inner join book.categoryToBook" +
            " category on book.categoryToBook.id = category.id" +
            " inner join book.authorBooksFromBook authorBook on book.id = authorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " inner join book.bookStoragesFromBook bookStorage on bookStorage.bookToBookStorage.id = book.id" +
            " where book.content = :content")
    public List<BookMore> getBookByContent(@Param("content") String content, Pageable pageable);

    @Query("select new com.example.library.more.BookMore(book.title, category.name, author.name, bookStorage.image, " +
            "book.content, book.id, author.id, category.id, book.cost, book.follow, bookStorage.quantity, " +
            "book.sale + category.sale, bookStorage.id, bookStorage.importTime, book.status) from Book book inner join book.categoryToBook" +
            " category on book.categoryToBook.id = category.id" +
            " inner join book.authorBooksFromBook authorBook on book.id = authorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " inner join book.bookStoragesFromBook bookStorage on bookStorage.bookToBookStorage.id = book.id" +
            " where book.status = :status")
    public List<BookMore> getBookByStatus(@Param("status") String status, Pageable pageable);

    @Query("select new com.example.library.more.BookMore(book.title, category.name, author.name, bookStorage.image, " +
            "book.content, book.id, author.id, category.id, book.cost, book.follow, bookStorage.quantity, " +
            "book.sale + category.sale, bookStorage.id, bookStorage.importTime, book.status) from Book book inner join book.categoryToBook" +
            " category on book.categoryToBook.id = category.id" +
            " inner join book.authorBooksFromBook authorBook on book.id = authorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " inner join book.bookStoragesFromBook bookStorage on bookStorage.bookToBookStorage.id = book.id" +
            " where book.title = :title and book.status = 'Đang bán'")
    public List<BookMore> getBookByTitle(@Param("title") String title, Pageable pageable);

    @Query("select new com.example.library.more.BookMore(book.title, category.name, author.name, bookStorage.image, " +
            "book.content, book.id, author.id, category.id, book.cost, book.follow, bookStorage.quantity, " +
            "book.sale + category.sale, bookStorage.id, bookStorage.importTime, book.status) from Book book inner join book.categoryToBook" +
            " category on book.categoryToBook.id = category.id" +
            " inner join book.authorBooksFromBook authorBook on book.id = authorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " inner join book.bookStoragesFromBook bookStorage on bookStorage.bookToBookStorage.id = book.id" +
            " where book.title = :title")
    public List<BookMore> getBookByTitleOnManage(@Param("title") String title, Pageable pageable);

    @Query("select new com.example.library.more.BookMore(book.title, category.name, author.name, bookStorage.image, " +
            "book.content, book.id, author.id, category.id, book.cost, book.follow, bookStorage.quantity, " +
            "book.sale + category.sale, bookStorage.id, bookStorage.importTime, book.status) from Book book inner join book.categoryToBook" +
            " category on book.categoryToBook.id = category.id" +
            " inner join book.authorBooksFromBook authorBook on book.id = authorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " inner join book.bookStoragesFromBook bookStorage on bookStorage.bookToBookStorage.id = book.id" +
            " where book.cost = :cost")
    public List<BookMore> getBookByCost(@Param("cost") Long cost, Pageable pageable);

    @Query("select new com.example.library.more.BookMore(book.title, category.name, author.name, bookStorage.image, " +
            "book.content, book.id, author.id, category.id, book.cost, book.follow, bookStorage.quantity, " +
            "book.sale + category.sale, bookStorage.id, bookStorage.importTime, book.status) from Book book inner join book.categoryToBook" +
            " category on book.categoryToBook.id = category.id" +
            " inner join book.authorBooksFromBook authorBook on book.id = authorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " inner join book.bookStoragesFromBook bookStorage on bookStorage.bookToBookStorage.id = book.id" +
            " where book.sale = :sale")
    public List<BookMore> getBookBySale(@Param("sale") int sale, Pageable pageable);

    @Query("select new com.example.library.more.BookMore(book.title, category.name, author.name, bookStorage.image, " +
            "book.content, book.id, author.id, category.id, book.cost, book.follow, bookStorage.quantity, " +
            "book.sale + category.sale, bookStorage.id, bookStorage.importTime, book.status) from Book book inner join book.categoryToBook" +
            " category on book.categoryToBook.id = category.id" +
            " inner join book.authorBooksFromBook authorBook on book.id = authorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " inner join book.bookStoragesFromBook bookStorage on bookStorage.bookToBookStorage.id = book.id" +
            " inner join bookStorage.storageToBookStorage storage on storage.id = bookStorage.storageToBookStorage.id" +
            " where storage.id = :id and book.status = :status")
    public List<BookMore> getBookByStorageId(@Param("status") String status, @Param("id") Long id, Pageable pageable);

    @Query("select new com.example.library.more.BookMore(book.title, category.name, author.name, bookStorage.image, " +
            "book.content, book.id, author.id, category.id, book.cost, book.follow, bookStorage.quantity, " +
            "book.sale + category.sale, bookStorage.id, bookStorage.importTime, book.status) from Book book inner join book.categoryToBook" +
            " category on book.categoryToBook.id = category.id" +
            " inner join book.authorBooksFromBook authorBook on book.id = authorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " inner join book.bookStoragesFromBook bookStorage on bookStorage.bookToBookStorage.id = book.id" +
            " inner join bookStorage.cartsFromBookStorage cart on bookStorage.id = cart.bookStorageToCart.id" +
            " inner join cart.accountToCart account on cart.accountToCart.cardNumber = account.cardNumber" +
            " where account.cardNumber = :cardNumber")
    public List<BookMore> getAllBookByAccountCart(@Param("cardNumber") Long cardNumber, Pageable pageable);

    @Query("select new com.example.library.more.BookMore(book.title, category.name, author.name, bookStorage.image, " +
            "book.content, book.id, author.id, category.id, book.cost, book.follow, bookStorage.quantity, " +
            "book.sale + category.sale, bookStorage.id, bookStorage.importTime, book.status) from Book book inner join book.categoryToBook" +
            " category on book.categoryToBook.id = category.id" +
            " inner join book.authorBooksFromBook authorBook on book.id = authorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " inner join book.bookStoragesFromBook bookStorage on bookStorage.bookToBookStorage.id = book.id" +
            " inner join bookStorage.buysFromBookStorage buy on bookStorage.id = buy.bookStorageToBuy.id" +
            " inner join buy.accountToBuy account on buy.accountToBuy.cardNumber = account.cardNumber" +
            " where account.cardNumber = :cardNumber")
    public List<BookMore> getAllBookByAccountBuy(@Param("cardNumber") Long cardNumber, Pageable pageable);

    @Query("select new com.example.library.more.BookManage(book.id, author.id, book.title, book.cost, book.content, book.status, book.sale" +
            ", category.name, author.name) from Book book inner join book.categoryToBook category on" +
            " book.categoryToBook.id = category.id inner join book.authorBooksFromBook authorBook on book.id = authorBook.bookToAuthorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id")
    public List<BookManage> getAllBookManage(Pageable pageable);

    @Query("select new com.example.library.more.BookManage(book.id, author.id, book.title, book.cost, book.content, book.status, book.sale" +
            ", category.name, author.name) from Book book inner join book.categoryToBook category on" +
            " book.categoryToBook.id = category.id inner join book.authorBooksFromBook authorBook on book.id = authorBook.bookToAuthorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " where book.title = :title")
    public List<BookManage> getBookManageByTitle(@Param("title") String title, Pageable pageable);

    @Query("select new com.example.library.more.BookManage(book.id, author.id, book.title, book.cost, book.content, book.status, book.sale" +
            ", category.name, author.name) from Book book inner join book.categoryToBook category on" +
            " book.categoryToBook.id = category.id inner join book.authorBooksFromBook authorBook on book.id = authorBook.bookToAuthorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " where book.cost = :cost")
    public List<BookManage> getBookManageByCost(@Param("cost") Long cost, Pageable pageable);

    @Query("select new com.example.library.more.BookManage(book.id, author.id, book.title, book.cost, book.content, book.status, book.sale" +
            ", category.name, author.name) from Book book inner join book.categoryToBook category on" +
            " book.categoryToBook.id = category.id inner join book.authorBooksFromBook authorBook on book.id = authorBook.bookToAuthorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " where book.status = :status")
    public List<BookManage> getBookManageByStatus(@Param("status") String status, Pageable pageable);

    @Query("select new com.example.library.more.BookManage(book.id, author.id, book.title, book.cost, book.content, book.status, book.sale" +
            ", category.name, author.name) from Book book inner join book.categoryToBook category on" +
            " book.categoryToBook.id = category.id inner join book.authorBooksFromBook authorBook on book.id = authorBook.bookToAuthorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " where book.sale + category.sale = :sale")
    public List<BookManage> getBookManageBySale(@Param("sale") int sale, Pageable pageable);

    @Query("select new com.example.library.more.BookManage(book.id, author.id, book.title, book.cost, book.content, book.status, book.sale" +
            ", category.name, author.name) from Book book inner join book.categoryToBook category on" +
            " book.categoryToBook.id = category.id inner join book.authorBooksFromBook authorBook on book.id = authorBook.bookToAuthorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " where category.name = :name")
    public List<BookManage> getBookManageByCategory(@Param("name") String name, Pageable pageable);

    @Query("select new com.example.library.more.BookManage(book.id, author.id, book.title, book.cost, book.content, book.status, book.sale" +
            ", category.name, author.name) from Book book inner join book.categoryToBook category on" +
            " book.categoryToBook.id = category.id inner join book.authorBooksFromBook authorBook on book.id = authorBook.bookToAuthorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " where author.name = :name")
    public List<BookManage> getBookManageByAuthor(@Param("name") String name, Pageable pageable);

    public Book findFirstById(Long id);

    @Query("select new com.example.library.more.BookMore(book.title, category.name, author.name, bookStorage.image, " +
            "book.content, book.id, author.id, category.id, book.cost, book.follow, bookStorage.quantity, " +
            "book.sale + category.sale, bookStorage.id, bookStorage.importTime, book.status) from Book book inner join" +
            " book.categoryToBook category on book.categoryToBook.id = category.id" +
            " inner join book.authorBooksFromBook authorBook on book.id = authorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " inner join book.bookStoragesFromBook bookStorage on bookStorage.bookToBookStorage.id = book.id" +
            " inner join bookStorage.storageToBookStorage storage on bookStorage.storageToBookStorage.id = storage.id" +
            " where storage.id = :id and book.title = :title")
    public List<BookMore> getAllBookByStorageAndTitle(@Param("id") Long id, @Param("title") String title, Pageable pageable);

    @Query("select new com.example.library.more.BookMore(book.title, category.name, author.name, bookStorage.image, " +
            "book.content, book.id, author.id, category.id, book.cost, book.follow, bookStorage.quantity, " +
            "book.sale + category.sale, bookStorage.id, bookStorage.importTime, book.status) from Book book inner join" +
            " book.categoryToBook category on book.categoryToBook.id = category.id" +
            " inner join book.authorBooksFromBook authorBook on book.id = authorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " inner join book.bookStoragesFromBook bookStorage on bookStorage.bookToBookStorage.id = book.id" +
            " inner join bookStorage.storageToBookStorage storage on bookStorage.storageToBookStorage.id = storage.id" +
            " where storage.id = :id and category.name = :name")
    public List<BookMore> getAllBookByStorageAndCategory(@Param("id") Long id, @Param("name") String name, Pageable pageable);

    @Query("select new com.example.library.more.BookMore(book.title, category.name, author.name, bookStorage.image, " +
            "book.content, book.id, author.id, category.id, book.cost, book.follow, bookStorage.quantity, " +
            "book.sale + category.sale, bookStorage.id, bookStorage.importTime, book.status) from Book book inner join" +
            " book.categoryToBook category on book.categoryToBook.id = category.id" +
            " inner join book.authorBooksFromBook authorBook on book.id = authorBook.id" +
            " inner join authorBook.authorToAuthorBook author on authorBook.authorToAuthorBook.id = author.id" +
            " inner join book.bookStoragesFromBook bookStorage on bookStorage.bookToBookStorage.id = book.id" +
            " inner join bookStorage.storageToBookStorage storage on bookStorage.storageToBookStorage.id = storage.id" +
            " where storage.id = :id and author.name = :author")
    public List<BookMore> getAllBookByStorageAndAuthor(@Param("id") Long id, @Param("author") String author, Pageable pageable);
}
