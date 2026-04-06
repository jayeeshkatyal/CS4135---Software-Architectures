import React, { useState, useEffect } from 'react';
import api from '../api';

const MyBorrowings = () => {
  const [borrowings, setBorrowings] = useState([]);

  useEffect(() => {
    fetchBorrowings();
  }, []);

  const fetchBorrowings = async () => {
    const response = await api.get('/borrow/me');
    setBorrowings(response.data);
  };

  const returnBook = async (borrowId) => {
    try {
      await api.post(`/borrow/${borrowId}/return`);
      fetchBorrowings(); // refresh
    } catch (error) {
      alert(error.response?.data);
    }
  };

  return (
    <div>
      <h2>My Borrowings</h2>
      {borrowings.length === 0 ? (
        <p>No active borrowings.</p>
      ) : (
        <ul>
          {borrowings.map((b) => (
            <li key={b.borrowId}>
              Book ID: {b.bookId} - Due: {b.dueDate}
              {b.status === 'ACTIVE' && (
                <button onClick={() => returnBook(b.borrowId)}>Return</button>
              )}
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default MyBorrowings;