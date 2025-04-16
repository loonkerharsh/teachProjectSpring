import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';

function Subjects() {
  const { semesterId } = useParams();
  const navigate = useNavigate();
  const [subjects, setSubjects] = useState([]);
  const [error, setError] = useState("");
  const [newSubject, setNewSubject] = useState({
    name: '',
    classroom: '',
    classTime: '',
    completionNote: '',
    homeworkNote: ''
  });
  const [showAddForm, setShowAddForm] = useState(false);
  const teacherId = localStorage.getItem("teacherId");

  useEffect(() => {
    if (!teacherId) {
      setError("Teacher ID not found. Please login again.");
      return;
    }
    loadSubjects();
  }, [semesterId, teacherId]);

  const loadSubjects = () => {
    axios.get(`http://localhost:8080/api/subjects?semesterId=${semesterId}&teacherId=${teacherId}`)
      .then(response => {
        setSubjects(response.data);
      })
      .catch(err => {
        console.error("Error loading subjects:", err);
        setError("Failed to load subjects");
      });
  };

  const handleAddSubject = async () => {
    if (!teacherId) {
      setError("Teacher ID not found. Please login again.");
      return;
    }

    try {
      const subjectToAdd = {
        name: newSubject.name,
        classroom: newSubject.classroom,
        classTime: newSubject.classTime,
        completionNote: newSubject.completionNote,
        homeworkNote: newSubject.homeworkNote,
        semester: { "id": parseInt(semesterId) }, // 👈 nested object
        teacher: { "id": parseInt(teacherId)}
      };

      const response = await axios.post('http://localhost:8080/api/subjects', subjectToAdd, {
        headers: {
          'Content-Type': 'application/json'
        }
      });

      // Refresh the subjects list
      const refreshedSubjects = await axios.get(
        `http://localhost:8080/api/subjects?semesterId=${semesterId}&teacherId=${teacherId}`
      );
      setSubjects(refreshedSubjects.data);

      // Reset form
      setNewSubject({
        name: '',
        classroom: '',
        classTime: '',
        completionNote: '',
        homeworkNote: ''
      });
      setShowAddForm(false);
    } catch (err) {
      console.error("Error adding subject:", err);
      setError(err.response?.data?.message || "Failed to add subject");
    }
  };

  const handleDeleteSubject = (id) => {
    axios.delete(`http://localhost:8080/api/subjects/${id}`)
      .then(() => {
        const updatedSubjects = subjects.filter(subject => subject.id !== id);
        setSubjects(updatedSubjects);
        
        if (updatedSubjects.length === 0) {
          navigate('/semesters');
        }
      })
      .catch(err => {
        console.error("Error deleting subject:", err);
        setError("Failed to delete subject");
      });
  };

  const handleUpdateSubject = (id, updatedSubject) => {
    axios.put(`http://localhost:8080/api/subjects/${id}`, updatedSubject)
      .then(response => {
        setSubjects(subjects.map(subject => 
          subject.id === id ? { ...subject, ...response.data } : subject
        ));
      })
      .catch(err => {
        console.error("Error updating subject:", err);
        setError("Failed to update subject");
      });
  };

  return (
    <div style={{ padding: '20px' }}>
      <button 
        onClick={() => navigate('/semesters')}
        style={{
          marginBottom: '20px',
          padding: '8px 16px',
          backgroundColor: '#e2e8f0',
          border: 'none',
          borderRadius: '4px',
          cursor: 'pointer'
        }}
      >
        Back to Semesters
      </button>

      <h2>Subjects</h2>
      {error && <p style={{ color: 'red' }}>{error}</p>}

      {!showAddForm && (
        <button 
          onClick={() => setShowAddForm(true)}
          style={{
            marginBottom: '20px',
            padding: '10px 20px',
            backgroundColor: '#2563eb',
            color: 'white',
            border: 'none',
            borderRadius: '6px',
            cursor: 'pointer'
          }}
        >
          Add Subject
        </button>
      )}

      {showAddForm && (
        <div style={{
          padding: '20px',
          marginBottom: '20px',
          border: '1px solid #e2e8f0',
          borderRadius: '8px',
          backgroundColor: '#f8fafc'
        }}>
          <h3>Add New Subject</h3>
          <div style={{ marginBottom: '10px' }}>
            <label>Subject Name:</label>
            <input
              type="text"
              value={newSubject.name}
              onChange={(e) => setNewSubject({...newSubject, name: e.target.value})}
              style={{ marginLeft: '10px', padding: '5px', width: '100%' }}
            />
          </div>
          <div style={{ marginBottom: '10px' }}>
            <label>Classroom:</label>
            <input
              type="text"
              value={newSubject.classroom}
              onChange={(e) => setNewSubject({...newSubject, classroom: e.target.value})}
              style={{ marginLeft: '10px', padding: '5px', width: '100%' }}
            />
          </div>
          <div style={{ marginBottom: '10px' }}>
            <label>Class Time:</label>
            <input
              type="text"
              value={newSubject.classTime}
              onChange={(e) => setNewSubject({...newSubject, classTime: e.target.value})}
              style={{ marginLeft: '10px', padding: '5px', width: '100%' }}
            />
          </div>
          <div style={{ marginBottom: '10px' }}>
            <label>Completion Note:</label>
            <textarea
              value={newSubject.completionNote}
              onChange={(e) => setNewSubject({...newSubject, completionNote: e.target.value})}
              style={{ marginLeft: '10px', padding: '5px', width: '100%', minHeight: '60px' }}
            />
          </div>
          <div style={{ marginBottom: '10px' }}>
            <label>Homework Note:</label>
            <textarea
              value={newSubject.homeworkNote}
              onChange={(e) => setNewSubject({...newSubject, homeworkNote: e.target.value})}
              style={{ marginLeft: '10px', padding: '5px', width: '100%', minHeight: '60px' }}
            />
          </div>
          <button 
            onClick={handleAddSubject}
            style={{
              marginRight: '10px',
              padding: '8px 16px',
              backgroundColor: '#2563eb',
              color: 'white',
              border: 'none',
              borderRadius: '4px',
              cursor: 'pointer'
            }}
          >
            Save
          </button>
          <button 
            onClick={() => setShowAddForm(false)}
            style={{
              padding: '8px 16px',
              backgroundColor: '#e2e8f0',
              border: 'none',
              borderRadius: '4px',
              cursor: 'pointer'
            }}
          >
            Cancel
          </button>
        </div>
      )}

      {subjects.length > 0 ? (
        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fill, minmax(300px, 1fr))', gap: '20px' }}>
          {subjects.map((subject) => (
            <SubjectCard 
              key={subject.id}
              subject={subject}
              onDelete={handleDeleteSubject}
              onUpdate={handleUpdateSubject}
            />
          ))}
        </div>
      ) : (
        <p>No subjects found for this semester.</p>
      )}
    </div>
  );
}

function SubjectCard({ subject, onDelete, onUpdate }) {
  const [isEditing, setIsEditing] = useState(false);
  const [editedSubject, setEditedSubject] = useState({ ...subject });

  const handleSave = () => {
    const updatedSubject = {
      name: subject.name,
      classroom: editedSubject.classroom,
      classTime: editedSubject.classTime,
      completionNote: editedSubject.completionNote,
      homeworkNote: editedSubject.homeworkNote
    };
    onUpdate(subject.id, updatedSubject);
    setIsEditing(false);
  };

  return (
    <div style={{
      padding: '20px',
      border: '1px solid #e2e8f0',
      borderRadius: '8px',
      backgroundColor: '#ffffff',
      boxShadow: '0px 2px 4px rgba(0, 0, 0, 0.1)'
    }}>
      {isEditing ? (
        <div>
          <h3>{subject.name}</h3>
          <div style={{ marginBottom: '10px' }}>
            <label>Classroom:</label>
            <input
              type="text"
              value={editedSubject.classroom}
              onChange={(e) => setEditedSubject({...editedSubject, classroom: e.target.value})}
              style={{ marginLeft: '10px', padding: '5px', width: '100%' }}
            />
          </div>
          <div style={{ marginBottom: '10px' }}>
            <label>Class Time:</label>
            <input
              type="text"
              value={editedSubject.classTime}
              onChange={(e) => setEditedSubject({...editedSubject, classTime: e.target.value})}
              style={{ marginLeft: '10px', padding: '5px', width: '100%' }}
            />
          </div>
          <div style={{ marginBottom: '10px' }}>
            <label>Completion Note:</label>
            <textarea
              value={editedSubject.completionNote}
              onChange={(e) => setEditedSubject({...editedSubject, completionNote: e.target.value})}
              style={{ marginLeft: '10px', padding: '5px', width: '100%', minHeight: '60px' }}
            />
          </div>
          <div style={{ marginBottom: '10px' }}>
            <label>Homework Note:</label>
            <textarea
              value={editedSubject.homeworkNote}
              onChange={(e) => setEditedSubject({...editedSubject, homeworkNote: e.target.value})}
              style={{ marginLeft: '10px', padding: '5px', width: '100%', minHeight: '60px' }}
            />
          </div>
          <button 
            onClick={handleSave}
            style={{
              marginRight: '10px',
              padding: '8px 16px',
              backgroundColor: '#2563eb',
              color: 'white',
              border: 'none',
              borderRadius: '4px',
              cursor: 'pointer'
            }}
          >
            Save
          </button>
          <button 
            onClick={() => setIsEditing(false)}
            style={{
              padding: '8px 16px',
              backgroundColor: '#e2e8f0',
              border: 'none',
              borderRadius: '4px',
              cursor: 'pointer'
            }}
          >
            Cancel
          </button>
        </div>
      ) : (
        <div>
          <h3>{subject.name}</h3>
          <p><strong>Classroom:</strong> {subject.classroom}</p>
          <p><strong>Class Time:</strong> {subject.classTime}</p>
          <p><strong>Completion Note:</strong> {subject.completionNote || 'None'}</p>
          <p><strong>Homework Note:</strong> {subject.homeworkNote || 'None'}</p>
          <div style={{ marginTop: '15px' }}>
            <button 
              onClick={() => setIsEditing(true)}
              style={{
                marginRight: '10px',
                padding: '6px 12px',
                backgroundColor: '#e2e8f0',
                border: 'none',
                borderRadius: '4px',
                cursor: 'pointer'
              }}
            >
              Edit
            </button>
            <button 
              onClick={() => onDelete(subject.id)}
              style={{
                padding: '6px 12px',
                backgroundColor: '#ef4444',
                color: 'white',
                border: 'none',
                borderRadius: '4px',
                cursor: 'pointer'
              }}
            >
              Delete
            </button>
          </div>
        </div>
      )}
    </div>
  );
}

export default Subjects;