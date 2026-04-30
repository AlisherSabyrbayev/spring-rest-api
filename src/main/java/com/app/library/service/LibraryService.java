package com.app.library.service;

import com.app.library.model.Book;
import com.app.library.model.Member;
import com.app.library.model.BorrowingRecord;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class LibraryService {

    private List<Book> books = new ArrayList<>();
    private List<Member> members = new ArrayList<>();
    private List<BorrowingRecord> borrowingRecords = new ArrayList<>();

    public List<Book> getAllBooks() {
        return books;
    }

    public Optional<Book> getBookById(Long id) {
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void updateBook(Book updatedBook) {
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            if (book.getId().equals(updatedBook.getId())) {
                books.set(i, updatedBook);
                break;
            }
        }
    }

    public void deleteBook(Long id) {
        books.removeIf(book -> book.getId().equals(id));
    }

    public List<Member> getAllMembers() {
        return members;
    }

    public Optional<Member> getMemberById(Long id) {
        return members.stream()
                .filter(member -> member.getId().equals(id))
                .findFirst();
    }

    public void addMember(Member member) {
        members.add(member);
    }

    public void updateMember(Member updatedMember) {
        for (int i = 0; i < members.size(); i++) {
            Member member = members.get(i);
            if (member.getId().equals(updatedMember.getId())) {
                members.set(i, updatedMember);
                break;
            }
        }
    }

    public void deleteMember(Long id) {
        members.removeIf(member -> member.getId().equals(id));
    }

    public List<BorrowingRecord> getAllBorrowingRecords() {
        return borrowingRecords;
    }

    public void borrowBook(BorrowingRecord record) {
        Book book = record.getBook();
        if (book.getAvailableCopies() <= 0) {
            throw new IllegalStateException("No available copies for book id " + book.getId());
        }

        record.setBorrowDate(LocalDate.now());
        record.setDueDate(LocalDate.now().plusDays(14));
        borrowingRecords.add(record);

        book.setAvailableCopies(book.getAvailableCopies() - 1);
    }

    public void returnBook(Long recordId, LocalDate returnDate) {
        for (BorrowingRecord record : borrowingRecords) {
            if (record.getId().equals(recordId)) {
                record.setReturnDate(returnDate);

                Book book = record.getBook();
                book.setAvailableCopies(book.getAvailableCopies() + 1);
                break;
            }
        }
    }
}