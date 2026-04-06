import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import { useAuth } from './context/AuthContext';
import ProtectedRoute from './components/ProtectedRoute';
import Login from './pages/Login';
import Register from './pages/Register';
import BookList from './components/BookList';
import MyBorrowings from './components/MyBorrowings';
import MyFines from './components/MyFines';

const Navbar = () => {
  const { user, logout } = useAuth();

  if (!user) return null;

  return (
    <nav>
      <Link to="/">Books</Link>
      <Link to="/borrowings">My Borrowings</Link>
      <Link to="/fines">My Fines</Link>
      <button onClick={logout}>Logout</button>
    </nav>
  );
};

function App() {
  return (
    <Router>
      <Navbar />
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route
          path="/"
          element={
            <ProtectedRoute>
              <BookList />
            </ProtectedRoute>
          }
        />
        <Route
          path="/borrowings"
          element={
            <ProtectedRoute>
              <MyBorrowings />
            </ProtectedRoute>
          }
        />
        <Route
          path="/fines"
          element={
            <ProtectedRoute>
              <MyFines />
            </ProtectedRoute>
          }
        />
      </Routes>
    </Router>
  );
}

export default App;