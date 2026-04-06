import React, { useState, useEffect } from 'react';
import api from '../api';
import { useAuth } from '../context/AuthContext';

const BookList = () => {
  const [books, setBooks] = useState([]);
  const { user } = useAuth();

  useEffect(() => {
    fetchBooks();
  }, []);

  const fetchBooks = async () => {
    const response = await api.get('/books');
    setBooks(response.data);
  };

  const borrowBook = async (bookId) => {
    try {
      await api.post(`/borrow/${bookId}`);
      alert('Book borrowed successfully!');
      fetchBooks(); // refresh list
    } catch (error) {
      alert(error.response?.data || 'Could not borrow book');
    }
  };

  const returnBook = async (borrowId) => {
    try {
      await api.post(`/borrow/${borrowId}/return`);
      alert('Book returned successfully!');
      fetchBooks(); // refresh list
    } catch (error) {
      alert(error.response?.data || 'Could not return book');
    }
  };

  return (
    <div>
      <h2>Book Catalogue</h2>
      <ul>
        {books.map((book) => (
          <li key={book.bookId}>
            <strong>{book.title}</strong> by {book.author}<br />
            Status: {book.availabilityStatus}
            {user && book.availabilityStatus === 'AVAILABLE' && (
              <button onClick={() => borrowBook(book.bookId)}>Borrow</button>
            )}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default BookList;