// Semesters.js
import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

function Semesters() {
  const [semesters, setSemesters] = useState([]); // Initialize as array
  const [error, setError] = useState("");
  const navigate = useNavigate();
  const teacherId = localStorage.getItem("teacherId");

  useEffect(() => {
    if (!teacherId) {
      setError("Please login first");
      return;
    }

    axios.get(`http://localhost:8080/api/semesters?teacherId=${teacherId}`)
      .then(response => {
        // Ensure we're working with an array
        const data = Array.isArray(response.data) ? response.data : [];
        setSemesters(data);
      })
      .catch(err => {
        console.error("Error loading semesters:", err);
        setError("Failed to load semesters");
      });
  }, [teacherId]);

  const handleSemesterClick = (semesterId) => {
    navigate(`/semesters/${semesterId}/subjects`);
  };

  return (
    <div style={{ padding: '20px' }}>
      <h2>Semesters</h2>
      {error && <p style={{ color: 'red' }}>{error}</p>}
      
      {semesters.length > 0 ? (
        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fill, minmax(200px, 1fr))', gap: '20px' }}>
          {semesters.map((semester) => (
            <div 
              key={semester.id}
              onClick={() => handleSemesterClick(semester.id)}
              style={{
                padding: '20px',
                border: '1px solid #e2e8f0',
                borderRadius: '8px',
                cursor: 'pointer',
                backgroundColor: '#f8fafc'
              }}
            >
              <h3>{semester.name}</h3>
            </div>
          ))}
        </div>
      ) : (
        <p>No semesters found</p>
      )}
    </div>
  );
}

export default Semesters;