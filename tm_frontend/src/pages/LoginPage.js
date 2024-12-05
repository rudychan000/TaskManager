import React, { useState } from 'react';
import axios from 'axios';

const LoginPage = () => {
  const [usernameOrEmail, setUsernameOrEmail] = useState('');
  const [password, setPassword] = useState('');

  const handleLogin = async (e) => {
    e.preventDefault();

    const data = {
        usernameOrEmail: usernameOrEmail,
        password: password,
      };

    try {
    const response = await axios.post('http://localhost:8080/api/auth/login', data);

      // Save the access token
      localStorage.setItem('accessToken', response.data.accessToken);
      localStorage.setItem('userName',usernameOrEmail);
      // Redirect to dashboard
      window.location.href = '/';
    } catch (error) {
      console.error('Login failed:', error);
      alert('Login failed. Please check your credentials.');
    }
  };

  return (
    <div>
      <h2>Login</h2>
      <form onSubmit={handleLogin}>
        <input
          type="text"
          placeholder="Email or Username"
          value={usernameOrEmail}
          onChange={(e) => setUsernameOrEmail(e.target.value)}
          required
        />
        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />
        <button type="submit">Login</button>
      </form>
      <a href="/register">Don't have an account? Register</a>
    </div>
  );
};

export default LoginPage;