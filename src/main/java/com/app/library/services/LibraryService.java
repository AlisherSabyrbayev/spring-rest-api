package com.app.library.services;

import com.app.library.models.Book;
import com.app.library.models.Member;
import com.app.library.models.BorrowingRecord;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class LibraryService {

    private Map<Long, Book> books = new HashMap<Long, Book>();
    private Map<Long, Member> members = new HashMap<Long, Member>();
    private Map<Long, BorrowingRecord> borrowingRecords = new HashMap<Long, BorrowingRecord>();

    public Collection<Book> getAllBooks() {
        return books.values();
    }

    public Book getBookById(Long id) {
        return books.get(id);
    }

    public void addBook(Book book) {
        books.put(book.getId(), book);
    }

    public void updateBook(Book updatedBook) {
        books.put(updatedBook.getId(), updatedBook);
    }

    public void deleteBook(Long id) {
        books.remove(id);
    }

    public Collection<Member> getAllMembers() {
        return members.values();
    }

    public Member getMemberById(Long id) {
        return members.get(id);
    }

    public void addMember(Member member) {
        members.put(member.getId(), member);
    }

    public void updateMember(Member updatedMember) {
        members.put(updatedMember.getId(), updatedMember);
    }

    public void deleteMember(Long id) {
        members.remove(id);
    }

    public Collection<BorrowingRecord> getAllBorrowingRecords() {
        return borrowingRecords.values();
    }

    public void borrowBook(BorrowingRecord record) {
        System.out.println(record);
        record.setBorrowDate(LocalDate.now());
        record.setDueDate(LocalDate.now().plusDays(14));
        borrowingRecords.put(record.getId(), record);
        System.out.println("record.getBookId() " + record.getBookId());
        System.out.println("book " + books.get(record.getBookId()));
        Book book = books.get(record.getBookId());
        book.setAvailableCopies(book.getAvailableCopies() - 1);
    }

    public void returnBook(Long recordId, LocalDate returnDate) {
        BorrowingRecord record = borrowingRecords.get(recordId);
        Book book = books.get(record.getBookId());
        book.setAvailableCopies(book.getAvailableCopies() + 1);
    }

    public Collection<Book> getBooksByGenre(String genre) {
        Collection<Book> allBooks = (Collection<Book>) (books.values());
        return allBooks.stream()
                .filter(book -> (book.getGenre()).toLowerCase().contains(genre.toLowerCase()))
                .collect(Collectors.toList());
    }

    public Collection<Book> getBooksByAuthorAndGenre(String author, String genre) {
        Collection<Book> allBooks = (Collection<Book>) (books.values());
        return allBooks.stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                .filter(book -> genre == null || book.getGenre().toLowerCase().contains(genre.toLowerCase()))
                .collect(Collectors.toList());
    }

    public Collection<Book> getBooksDueOnDate(LocalDate dueDate) {
        Collection<BorrowingRecord> allRecords = (Collection<BorrowingRecord>) (borrowingRecords.values());

        ArrayList<Book> dueBooks = new ArrayList<Book>();

        Collection<BorrowingRecord> tempRecords = allRecords.stream()
                .filter(record -> record.getDueDate().equals(dueDate))
                .collect(Collectors.toList());

        for (BorrowingRecord record : tempRecords) {
            Book book = books.get(record.getBookId());
            if (book != null) {
                dueBooks.add(book);
            }
        }
        return dueBooks;
    }

    public LocalDate checkAvailability(Long bookId) {
        Collection<BorrowingRecord> allRecords = (Collection<BorrowingRecord>) (borrowingRecords.values());

        Book bookToCheck = books.get(bookId);

        if (bookToCheck == null) {
            return null;
        } else {
            if (bookToCheck.getAvailableCopies() >= 1) {
                return LocalDate.now();
            } else {
                List<BorrowingRecord> sortedRecords = allRecords.stream()
                        .filter(record -> record.getBookId() == bookId) // Filter by bookId
                        .sorted((b1, b2) -> b1.getDueDate().compareTo(b2.getDueDate())) // Sort by dueDate (soonest to latest)
                        .collect(Collectors.toList());
                return sortedRecords.get(0).getDueDate();
            }
        }
    }
}