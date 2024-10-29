import React, { useState, useEffect } from 'react';
import axiosInstance from '../api/axiosInstance';

const AllGroupsPage = () => {
  const [groups, setGroups] = useState([]);

  useEffect(() => {
    axiosInstance
      .get('/groups/all')
      .then((response) => setGroups(response.data))
      .catch((error) => console.error('Error fetching groups:', error));
  }, []);

  return (
    <div>
      <h2>All Groups</h2>
      {/* <a href="/tasks/create">Create New Task</a> */}
      <ul>
        {groups.map((groups) => (
          <li key={groups.id}>
            <a href={`/groups/${groups.id}`}>{groups.name}</a>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default AllGroupsPage;