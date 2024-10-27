import React from 'react';
import { Outlet, Navigate } from 'react-router-dom';

//This component checks if the user is authenticated before allowing access to protected routes.
const PrivateRoute = () => {
  const isAuthenticated = localStorage.getItem('accessToken') !== null;

  return isAuthenticated ? <Outlet /> : <Navigate to="/login" />;
};

export default PrivateRoute;