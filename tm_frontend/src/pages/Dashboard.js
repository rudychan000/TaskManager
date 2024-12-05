import React, { useState } from 'react';
import { Button, Typography, Box } from '@mui/material';


const Dashboard = () => {


  return (
    <Box>
      <Typography variant="h4" gutterBottom>
        Welcome to the Task Management App
      </Typography>
      <Typography variant="h6" gutterBottom>
        Hello, {localStorage.getItem('userName')}!
      </Typography>
      <Button variant="contained" color="primary" href="/tasks">
        My Tasks
      </Button>
      <br />
      <br />
      <Button variant="contained" color="primary" href="/groups">
        My Groups
      </Button>
      <br />
      <br />
      <Button variant="contained" color="primary" href="/allGroups">
        All Groups
      </Button>
      <br />
      <br />
      <Button
        variant="contained"
        color="secondary"
        onClick={() => {
          localStorage.clear();
          window.location.href = '/login';
        }}
      >
        Logout
      </Button>
    </Box>
  );
};

export default Dashboard;