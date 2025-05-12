import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from './pages/Login';
import Dashboard from './pages/Dashboard';
import Semesters from './pages/Semesters';
import Subjects from './pages/Subjects';
import AddSemester from './pages/AddSemester';
import Profile from './pages/Profile';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/semesters" element={<Semesters />} />
        <Route path="/semesters/:semesterId/subjects" element={<Subjects />} />
        <Route path="/profile" element={<Profile />} />
        <Route path="/semesters/new" element={<AddSemester />} />
      </Routes>
    </Router>
  );
}

export default App;