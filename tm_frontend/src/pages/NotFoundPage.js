import React from 'react';
import { Link } from 'react-router-dom';
import './NotFoundPage.css'; // Optional: if you want to add some custom styles

const NotFoundPage = () => {
    return (
        <div className="not-found-page">
            <h1>404 - Page Not Found</h1>
            <p>Sorry, the page you are looking for does not exist.</p>
            <Link to="/">Go to Homepage</Link>
        </div>
    );
};

export default NotFoundPage;