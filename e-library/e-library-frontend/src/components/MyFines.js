import React, { useState, useEffect } from 'react';
import api from '../api';

const MyFines = () => {
  const [fines, setFines] = useState([]);

  useEffect(() => {
    fetchFines();
  }, []);

  const fetchFines = async () => {
    const response = await api.get('/fines/me');
    setFines(response.data);
  };

  const payFine = async (fineId) => {
    try {
      await api.post(`/fines/${fineId}/pay`);
      fetchFines(); // refresh
    } catch (error) {
      alert(error.response?.data);
    }
  };

  return (
    <div>
      <h2>My Fines</h2>
      {fines.length === 0 ? (
        <p>No fines.</p>
      ) : (
        <ul>
          {fines.map((fine) => (
            <li key={fine.fineId}>
              Amount: {fine.amount} - Status: {fine.status}
              {fine.status === 'UNPAID' && (
                <button onClick={() => payFine(fine.fineId)}>Pay</button>
              )}
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default MyFines;