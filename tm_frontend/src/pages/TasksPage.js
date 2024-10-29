import React, { useState, useEffect } from 'react';
import axiosInstance from '../api/axiosInstance';
import { Container, Typography, Button, List, ListItem, ListItemText, Link } from '@mui/material';

const TasksPage = () => {
  const [tasks, setTasks] = useState([]);
  const [privateTasks, setPrivateTasks] = useState([]);
  const [publicTasks, setPublicTasks] = useState([]);

  useEffect(() => {
    axiosInstance
      .get('/tasks/my-tasks')
      .then((response) => setTasks(response.data))
      .catch((error) => console.error('Error fetching tasks:', error));

    axiosInstance
      .get('/tasks/my-public-tasks')
      .then((response) => setPublicTasks(response.data))
      .catch((error) => console.error('Error fetching public tasks:', error));

    axiosInstance
      .get('/tasks/my-private-tasks')
      .then((response) => setPrivateTasks(response.data))
      .catch((error) => console.error('Error fetching private tasks:', error));
  }, []);

  return (
    <Container>
      <Typography variant="h4" gutterBottom>My Tasks</Typography>
      <Button variant="contained" color="primary" href="/tasks/create">Create New Task</Button>
      <div style={{ display: 'flex', justifyContent: 'space-between' }}>
        <div style={{ width: '48%' }}>
          <Typography variant="h5" gutterBottom>
            Private Tasks
          </Typography>
          <List>
            {privateTasks.map((task) => (
              <ListItem key={task.id} alignItems="flex-start">
                <ListItemText
                  primary={`${task.title}`}
                  secondary={
                    <>
                      <Typography component="span" variant="body2" color="textPrimary">
                        Description: {task.description}
                      </Typography>
                      <br />
                      Status: {task.status}
                      <br />
                      Due Date: {task.dueDate}
                      <br />
                      <Link href={`/tasks/${task.id}`}>Details</Link>
                    </>
                  }
                />
              </ListItem>
            ))}
          </List>
        </div>
        <div style={{ width: '48%' }}>
          <Typography variant="h5" gutterBottom>
            Public Tasks
          </Typography>
          <List>
            {publicTasks.map((task) => (
              <ListItem key={task.id} alignItems="flex-start">
                <ListItemText
                  primary={`${task.title}`}
                  secondary={
                    <>
                      <Typography component="span" variant="body2" color="textPrimary">
                        Description: {task.description}
                      </Typography>
                      <br />
                      Status: {task.status}
                      <br />
                      Due Date: {task.dueDate}
                      <br />
                      <Link href={`/tasks/${task.id}`}>Details</Link>
                    </>
                  }
                />
              </ListItem>
            ))}
          </List>
        </div>
      </div>
    </Container>
  );
};

export default TasksPage;