import React, { useContext, useState } from 'react';
import { AuthContext } from '../context/Auth';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import '../styles/pages.scss';
import { 
  faUser, 
  faEnvelope, 
  faPhone, 
  faBriefcase,
  faCalendarAlt,
  faEdit,
  faSave,
  faTimes,
  faShieldAlt,
  faKey
} from '@fortawesome/free-solid-svg-icons';

function Profile() {
    const { userData } = useContext(AuthContext);
    const [isEditing, setIsEditing] = useState(false);
    const [editedData, setEditedData] = useState({
        fullName: userData?.fullName || '',
        email: userData?.email || '',
        phone: userData?.phone || '',
        designation: userData?.designation || ''
    });

    const handleEdit = () => {
        setIsEditing(true);
    };

    const handleSave = () => {
        // Here you would typically make an API call to update the user data
        console.log('Saving user data:', editedData);
        setIsEditing(false);
    };

    const handleCancel = () => {
        setEditedData({
            fullName: userData?.fullName || '',
            email: userData?.email || '',
            phone: userData?.phone || '',
            designation: userData?.designation || ''
        });
        setIsEditing(false);
    };

    const handleInputChange = (field, value) => {
        setEditedData(prev => ({
            ...prev,
            [field]: value
        }));
    };

    return (
        <div className="profile-page">
            {/* Profile Header */}
            <div className="page-header">
                <h1 className="page-title">My Profile</h1>
                <p className="page-subtitle">Manage your account information and preferences</p>
            </div>

            <div className="profile-content">
                {/* Profile Card */}
                <div className="profile-card">
                    <div className="profile-header-section">
                        <div className="profile-avatar">
                            <FontAwesomeIcon icon={faUser} />
                        </div>
                        <div className="profile-basic-info">
                            {isEditing ? (
                                <input
                                    type="text"
                                    className="edit-input name-input"
                                    value={editedData.fullName}
                                    onChange={(e) => handleInputChange('fullName', e.target.value)}
                                    placeholder="Full Name"
                                />
                            ) : (
                                <h2 className="profile-name">{userData?.fullName || 'User Name'}</h2>
                            )}
                            <p className="profile-role">
                                <FontAwesomeIcon icon={faBriefcase} />
                                {isEditing ? (
                                    <input
                                        type="text"
                                        className="edit-input role-input"
                                        value={editedData.designation}
                                        onChange={(e) => handleInputChange('designation', e.target.value)}
                                        placeholder="Designation"
                                    />
                                ) : (
                                    <span>{userData?.designation || 'Role'}</span>
                                )}
                            </p>
                        </div>
                        <div className="profile-actions">
                            {isEditing ? (
                                <div className="edit-actions">
                                    <button className="btn-save" onClick={handleSave}>
                                        <FontAwesomeIcon icon={faSave} />
                                        Save
                                    </button>
                                    <button className="btn-cancel" onClick={handleCancel}>
                                        <FontAwesomeIcon icon={faTimes} />
                                        Cancel
                                    </button>
                                </div>
                            ) : (
                                <button className="btn-edit" onClick={handleEdit}>
                                    <FontAwesomeIcon icon={faEdit} />
                                    Edit Profile
                                </button>
                            )}
                        </div>
                    </div>

                    <div className="profile-details">
                        <div className="detail-group">
                            <label className="detail-label">
                                <FontAwesomeIcon icon={faEnvelope} />
                                Email Address
                            </label>
                            {isEditing ? (
                                <input
                                    type="email"
                                    className="edit-input"
                                    value={editedData.email}
                                    onChange={(e) => handleInputChange('email', e.target.value)}
                                    placeholder="Email Address"
                                />
                            ) : (
                                <p className="detail-value">{userData?.email || 'email@example.com'}</p>
                            )}
                        </div>

                        <div className="detail-group">
                            <label className="detail-label">
                                <FontAwesomeIcon icon={faPhone} />
                                Phone Number
                            </label>
                            {isEditing ? (
                                <input
                                    type="tel"
                                    className="edit-input"
                                    value={editedData.phone}
                                    onChange={(e) => handleInputChange('phone', e.target.value)}
                                    placeholder="Phone Number"
                                />
                            ) : (
                                <p className="detail-value">{userData?.phone || '+1 (555) 123-4567'}</p>
                            )}
                        </div>

                        <div className="detail-group">
                            <label className="detail-label">
                                <FontAwesomeIcon icon={faCalendarAlt} />
                                Member Since
                            </label>
                            <p className="detail-value">January 2024</p>
                        </div>

                        <div className="detail-group">
                            <label className="detail-label">
                                <FontAwesomeIcon icon={faShieldAlt} />
                                Account Status
                            </label>
                            <p className="detail-value">
                                <span className="status-badge active">Active</span>
                            </p>
                        </div>
                    </div>
                </div>

                {/* Security Settings */}
                <div className="security-card">
                    <h3 className="section-title">
                        <FontAwesomeIcon icon={faShieldAlt} />
                        Security Settings
                    </h3>
                    <div className="security-options">
                        <div className="security-item">
                            <div className="security-info">
                                <h4>Change Password</h4>
                                <p>Update your password to keep your account secure</p>
                            </div>
                            <button className="btn-security">
                                <FontAwesomeIcon icon={faKey} />
                                Change Password
                            </button>
                        </div>
                    </div>
                </div>

                {/* Activity Summary */}
                <div className="activity-summary">
                    <h3 className="section-title">Recent Activity Summary</h3>
                    <div className="activity-stats">
                        <div className="activity-stat">
                            <span className="stat-number">12</span>
                            <span className="stat-label">Products Added</span>
                        </div>
                        <div className="activity-stat">
                            <span className="stat-number">8</span>
                            <span className="stat-label">Orders Processed</span>
                        </div>
                        <div className="activity-stat">
                            <span className="stat-number">3</span>
                            <span className="stat-label">Reports Generated</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Profile;
