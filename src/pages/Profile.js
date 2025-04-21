import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

function Profile() {
    const [profile, setProfile] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    const navigate = useNavigate();
    const teacherId = localStorage.getItem("teacherId");

    useEffect(() => {
        if (!teacherId) {
            navigate('/login');
            return;
        }

        const fetchProfile = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/api/users/${teacherId}/profile`);
                setProfile(response.data);
            } catch (err) {
                setError(err.response?.data || "Failed to load profile");
                console.error("Profile load error:", err);
            } finally {
                setLoading(false);
            }
        };

        fetchProfile();
    }, [teacherId, navigate]);

    if (loading) return <div>Loading profile...</div>;
    if (error) return <div className="error">{error}</div>;
    if (!profile) return <div>No profile data found</div>;

    return (
        <div className="profile-container">
            <h2>Teacher Profile</h2>
            
            <div className="profile-details">
                <div className="profile-row">
                    <span className="profile-label">Name:</span>
                    <span className="profile-value">{profile.name}</span>
                </div>
                
                <div className="profile-row">
                    <span className="profile-label">Username:</span>
                    <span className="profile-value">{profile.username}</span>
                </div>
                
                <div className="profile-row">
                    <span className="profile-label">Role:</span>
                    <span className="profile-value">{profile.role}</span>
                </div>
                
                {profile.semesters && profile.semesters.length > 0 && (
                    <div className="profile-section">
                        <h3>Assigned Semesters</h3>
                        <ul className="semester-list">
                            {profile.semesters.map(semester => (
                                <li key={semester.id} className="semester-item">
                                    {semester.name}
                                </li>
                            ))}
                        </ul>
                    </div>
                )}
            </div>
        </div>
    );
}

export default Profile;