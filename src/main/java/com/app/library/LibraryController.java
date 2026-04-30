package com.app.library;

import com.app.library.model.Book;
import com.app.library.model.BorrowingRecord;
import com.app.library.model.Member;
import com.app.library.service.LibraryService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class LibraryController {

    private static final Logger logger = LoggerFactory.getLogger(LibraryController.class);

    @Autowired
    private LibraryService libraryService;

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = libraryService.getAllBooks();
        logger.info("The list of books returned" + books);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = libraryService.getBookById(id);
        logger.info("The book returned" + book);
        return book.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/books")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        libraryService.addBook(book);
        logger.info("The book was added");
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
        if (!libraryService.getBookById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        updatedBook.setId(id);
        libraryService.updateBook(updatedBook);
        logger.info("The book has been updated " + updatedBook);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        if (!libraryService.getBookById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        libraryService.deleteBook(id);
        logger.info("The book has been deleted ");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/members")
    public ResponseEntity<List<Member>> getAllMembers() {
        List<Member> members = libraryService.getAllMembers();
        logger.info("The members in the system " + members);
        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        Optional<Member> member = libraryService.getMemberById(id);
        logger.info("The member you retrieved " + member);
        return member.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/members")
    public ResponseEntity<Member> addMember(@RequestBody Member member) {
        libraryService.addMember(member);
        logger.info("THe member has been added ");
        return new ResponseEntity<>(member, HttpStatus.CREATED);
    }

    // Update a member
    @PutMapping("/members/{id}")
    public ResponseEntity<Member> updateMember(@PathVariable Long id, @RequestBody Member updatedMember) {
        if (!libraryService.getMemberById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        updatedMember.setId(id);
        libraryService.updateMember(updatedMember);
        logger.info("The member has been updated " + updatedMember);
        return new ResponseEntity<>(updatedMember, HttpStatus.OK);
    }

    @DeleteMapping("/members/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        if (!libraryService.getMemberById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        libraryService.deleteMember(id);
        logger.info("The member has been deleted " + id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/borrowing-records")
    public ResponseEntity<List<BorrowingRecord>> getAllBorrowingRecords() {
        List<BorrowingRecord> records = libraryService.getAllBorrowingRecords();
        logger.info("The borrowing records in the system " + records);
        return new ResponseEntity<>(records, HttpStatus.OK);
    }

    @PostMapping("/borrow")
    public ResponseEntity<BorrowingRecord> borrowBook(@RequestBody BorrowingRecord record) {
        record.setBorrowDate(LocalDate.now());
        record.setDueDate(LocalDate.now().plusDays(14));
        libraryService.borrowBook(record);
        logger.info("The book has been borrowed " + record);
        return new ResponseEntity<>(record, HttpStatus.CREATED);
    }

    //return a book
    @PutMapping("/return/{recordId}")
    public ResponseEntity<Void> returnBook(@PathVariable Long recordId) {
        libraryService.returnBook(recordId, LocalDate.now());
        logger.info("The book has been returned " + recordId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/")
    public String listBooks() {
        return "<h1>Here are the list of books</h1>";
    }
}
