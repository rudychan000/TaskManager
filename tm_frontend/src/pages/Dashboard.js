import React from 'react';
import { Button, Typography, Box } from '@mui/material';

const Dashboard = () => {
  return (
    <Box>
      <Typography variant="h4" gutterBottom>
        Welcome to the Task Management App
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
    </Box>
  );
};

export default Dashboard;