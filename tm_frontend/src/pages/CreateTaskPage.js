import React, { useState } from 'react';
import axiosInstance from '../api/axiosInstance';

const CreateTaskPage = () => {
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [status, setStatus] = useState('PENDING');
  const [dueDate, setDueDate] = useState('');

  const handleCreateTask = (e) => {
    e.preventDefault();

    axiosInstance
      .post('/tasks/private', {
        title,
        description,
        status,
        dueDate,
      })
      .then((response) => {
        alert('Task created successfully!');
        window.location.href = '/tasks';
      })
      .catch((error) => {
        console.error('Error creating task:', error);
        alert('Failed to create task.');
      });
  };

  return (
    <div>
      <h2>Create New Task</h2>
      <form onSubmit={handleCreateTask}>
        <input
          type="text"
          placeholder="Title"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          required
        />
        <textarea
          placeholder="Description"
          value={description}
          onChange={(e) => setDescription(e.target.value)}
        ></textarea>
        <select value={status} onChange={(e) => setStatus(e.target.value)}>
          <option value="PENDING">Pending</option>
          <option value="IN_PROGRESS">In Progress</option>
          <option value="COMPLETED">Completed</option>
        </select>
        <input
          type="date"
          value={dueDate}
          onChange={(e) => setDueDate(e.target.value)}
        />
        <button type="submit">Create Task</button>
      </form>
    </div>
  );
};

export default CreateTaskPage;
