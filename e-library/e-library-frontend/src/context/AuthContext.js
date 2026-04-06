import React, { createContext, useState, useContext, useEffect } from 'react';
import api from '../api';

const AuthContext = createContext();

export const useAuth = () => useContext(AuthContext);

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const token = localStorage.getItem('token');
    const userId = localStorage.getItem('userId');
    const role = localStorage.getItem('role');
    if (token && userId) {
      setUser({ userId, role });
    }
    setLoading(false);
  }, []);

  const login = async (email, password) => {
    try {
      const response = await api.post('/auth/login', { email, password });
      const { token, userId, role } = response.data;
      localStorage.setItem('token', token);
      localStorage.setItem('userId', userId);
      localStorage.setItem('role', role);
      setUser({ userId, role });
      return { success: true };
    } catch (error) {
      return { success: false, message: error.response?.data || 'Login failed' };
    }
  };

  const register = async (email, password, name, contactInfo) => {
    try {
      await api.post('/auth/register', { email, password, name, contactInfo });
      return { success: true };
    } catch (error) {
      return { success: false, message: error.response?.data || 'Registration failed' };
    }
  };

  const logout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('userId');
    localStorage.removeItem('role');
    setUser(null);
    window.location.href = '/login';
  };

  const value = {
    user,
    login,
    register,
    logout,
    loading,
  };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};