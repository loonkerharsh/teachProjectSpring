import React from 'react';
import { Link } from 'react-router-dom';

function Dashboard() {
  const teacherName = localStorage.getItem("teacherName");

  return (
    <div>
      <nav style={{
        display: 'flex',
        justifyContent: 'space-between',
        alignItems: 'center',
        padding: '20px',
        backgroundColor: '#1e293b',
        color: 'white'
      }}>
        <h1 style={{ margin: 0 }}>Teacher Portal</h1>
        <div style={{ display: 'flex', gap: '20px' }}>
          <Link to="/semesters" style={{ color: 'white', textDecoration: 'none' }}>Semesters</Link>
          <Link to={"/profile"} style={{ color: 'white', textDecoration: 'none' }}>Profile</Link>
          <Link to="/logout" style={{ color: 'white', textDecoration: 'none' }}>Logout</Link>
        </div>
      </nav>

      <div style={{ 
        padding: '40px', 
        maxWidth: '800px', 
        margin: '0 auto',
        textAlign: 'center'
      }}>
        <h2>Welcome back, {teacherName}!</h2>
        <div style={{ 
          marginTop: '30px',
          padding: '20px',
          border: '1px solid #e2e8f0',
          borderRadius: '10px'
        }}>
          <h3>Quick Actions</h3>
          <div style={{ 
            display: 'flex', 
            justifyContent: 'center',
            gap: '20px',
            marginTop: '20px'
          }}>
            <Link
              to="/semesters"
              style={{
                padding: '15px 30px',
                backgroundColor: '#2563eb',
                color: 'white',
                borderRadius: '8px',
                textDecoration: 'none'
              }}
            >
              View Semesters
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Dashboard;