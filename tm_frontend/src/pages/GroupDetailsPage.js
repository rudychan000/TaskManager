import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axiosInstance from '../api/axiosInstance';


const GroupDetailsPage = () => {
    const { id } = useParams();
    const [group, setGroup] = useState(null);
  
    useEffect(() => {
      axiosInstance
        .get(`groups/${id}`)
        .then((response) => setGroup((response.data)))
        .catch((error) => console.error('Error fetching task:', error));
    }, [id]);
  
    console.log(group);
  
    if (!group) return <div>Groups not found.</div>;

    return (
        <div>
            <h1>{group.name}</h1>
            <h2>Users</h2>
            <ul>
                {group.users.map(user => (
                    <li key={user.id}>{user.name}</li>
                ))}
            </ul>
            <h2>Tasks</h2>
            <ul>
                {group.tasks.map(task => (
                    <li key={task.id}>{task.title}</li>
                ))}
            </ul>
        </div>
    );
};

export default GroupDetailsPage;