import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import axiosInstance from '../api/axiosInstance';

const TaskDetailsPage = () => {
  const { id } = useParams();
  const [task, setTask] = useState(null);

  useEffect(() => {
    axiosInstance
      .get(`tasks/${id}`)
      .then((response) => setTask((response.data)))
      .catch((error) => console.error('Error fetching task:', error));
  }, [id]);

  console.log(task);

  if (!task) return <div>Loading...</div>;

  return (
    <div>
      <h2>Title: {task.title}</h2>
      <p>Description: {task.description}</p>
      <p>Status: {task.status}</p>
      <p>Due Date: {task.dueDate}</p>
      {/* Add options to edit or delete task */}
    </div>
  );
};

export default TaskDetailsPage;
