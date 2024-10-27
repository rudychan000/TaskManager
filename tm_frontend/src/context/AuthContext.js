import React, { createContext, useState, useEffect } from 'react';

export const AuthContext = createContext();

const AuthProvider = ({ children }) => {
  const [authToken, setAuthToken] = useState(localStorage.getItem('accessToken'));
  const [user, setUser] = useState(null);

  useEffect(() => {
    // Optionally, fetch user data here
  }, [authToken]);

  const login = (token) => {
    localStorage.setItem('accessToken', token);
    setAuthToken(token);
  };

  const logout = () => {
    localStorage.removeItem('accessToken');
    setAuthToken(null);
    setUser(null);
  };

  return (
    <AuthContext.Provider value={{ authToken, user, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export default AuthProvider;
