import React, { useState, useEffect } from 'react';
import axiosInstance from '../api/axiosInstance';

const GroupPage = () => {
    const [groups, setGroups] = useState([]);

    useEffect(() => {
        axiosInstance
          .get('/groups/my-groups')
          .then((response) => setGroups(response.data))
          .catch((error) => console.error('Error fetching groups:', error));
      }, []);

    return (
        <div>
            <h1>Groups</h1>
            <ul>
                {groups.map(group => (
                    <li key={group.id}>
                    <a href={`/groups/${group.id}`}>{group.name}</a>
                  </li>
                ))}
            </ul>
        </div>
    );
};

export default GroupPage;