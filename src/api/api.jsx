import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

// Semester related API calls
export const semesterApi = {
  createSemester: (semesterData) => {
    return axios.post(`${API_BASE_URL}/semesters`, semesterData);
  },
  
  getAllSemesters: (teacherId) => {
    return axios.get(`${API_BASE_URL}/semesters?teacherId=${teacherId}`);
  },
  
  getSemesterById: (id) => {
    return axios.get(`${API_BASE_URL}/semesters/${id}`);
  },
  
  updateSemester: (id, semesterData) => {
    return axios.put(`${API_BASE_URL}/semesters/${id}`, semesterData);
  },
  
  deleteSemester: (id) => {
    return axios.delete(`${API_BASE_URL}/semesters/${id}`);
  }
};

// You can add more API sections here for other entities
// Example:
// export const courseApi = { ... }
// export const studentApi = { ... }
