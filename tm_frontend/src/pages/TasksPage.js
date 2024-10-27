import React, { useState, useEffect } from 'react';
import axiosInstance from '../api/axiosInstance';

const TasksPage = () => {
  const [tasks, setTasks] = useState([]);

  useEffect(() => {
    axiosInstance
      .get('/tasks/my-tasks')
      .then((response) => setTasks(response.data))
      .catch((error) => console.error('Error fetching tasks:', error));
  }, []);

  return (
    <div>
      <h2>My Tasks</h2>
      <a href="/tasks/create">Create New Task</a>
      <ul>
        {tasks.map((task) => (
          <li key={task.id}>
            <a href={`/tasks/${task.id}`}>{task.title}</a>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default TasksPage;