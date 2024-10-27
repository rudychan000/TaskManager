import React from 'react';

const Dashboard = () => {
  return (
    <div>
      <h2>Welcome to the Task Management App</h2>
      {/* Links to tasks and groups */}
      <a href="/tasks">My Tasks</a>
      <br />
      <a href="/groups">My Groups</a>
    </div>
  );
};

export default Dashboard;