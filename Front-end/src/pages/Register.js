import React, { useReducer, useState } from "react";
import { Button, Form } from "react-bootstrap";
import "../styles/Login.scss";
import displayToast from "../utils/displayToast";
import { validateInputField } from "../utils/validations";
import axios from "axios";
import { URLS } from "../routes";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faUser, faLock, faIdCard, faBriefcase, faSpinner } from "@fortawesome/free-solid-svg-icons";

const initialState = {
  username: "",
  password: "",
  confirmPassword: "",
  fullName: "",
  designation: "",
};

const reducer = (state, action) => {
  switch (action.type) {
    case "USERNAME":
      return { ...state, username: action.value };
    case "PASSWORD":
      return { ...state, password: action.value };
    case "CONFIRM_PASSWORD":
      return { ...state, confirmPassword: action.value };
    case "FULL_NAME":
      return { ...state, fullName: action.value };
    case "DESIGNATION":
      return { ...state, designation: action.value };
    case "RESET":
      return initialState;
    default:
      return state;
  }
};

function Register({ onSwitchToLogin }) {
  const [state, dispatch] = useReducer(reducer, initialState);
  const [isLoading, setIsLoading] = useState(false);

  const { username, password, confirmPassword, fullName, designation } = state;

  const handleInputChange = (type, value) => {
    dispatch({ type, value });
  };

  const validateForm = () => {
    if (!validateInputField({ field: username, fieldName: "username" })) return false;
    if (!validateInputField({ field: password, fieldName: "password" })) return false;
    if (!validateInputField({ field: fullName, fieldName: "full name" })) return false;
    if (!validateInputField({ field: designation, fieldName: "designation" })) return false;
    
    if (password !== confirmPassword) {
      displayToast({ type: "error", msg: "Passwords do not match!" });
      return false;
    }
    
    if (password.length < 6) {
      displayToast({ type: "error", msg: "Password must be at least 6 characters long!" });
      return false;
    }
    
    return true;
  };

  const submitForm = async (e) => {
    e.preventDefault();
    
    if (!validateForm()) return;
    
    setIsLoading(true);
    
    const registrationData = {
      username: username.trim(),
      password: password,
      fullName: fullName.trim(),
      designation: designation.trim()
    };

    try {
      const response = await axios.post(URLS.REGISTER_USER, registrationData);
      
      if (response.status === 200 && response.data.success) {
        displayToast({ type: "success", msg: "Registration successful! Please login." });
        resetForm();
        // Switch to login after successful registration
        if (onSwitchToLogin) {
          setTimeout(() => onSwitchToLogin(), 1500);
        }
      }
    } catch (error) {
      console.error('Registration error:', error);
      
      if (error.response && error.response.status === 409) {
        displayToast({ type: "error", msg: "Username already exists! Please choose a different username." });
      } else if (error.response && error.response.data) {
        displayToast({ type: "error", msg: error.response.data || "Registration failed!" });
      } else {
        displayToast({ type: "error", msg: "Registration failed! Please try again." });
      }
    } finally {
      setIsLoading(false);
    }
  };

  const resetForm = () => {
    dispatch({ type: "RESET" });
  };

  return (
    <div className="login-container">
      <div className="login-card">
        <div className="login-header">
          <h2 className="login-title">Create Account</h2>
          <p className="login-subtitle">Join the Inventory Management System</p>
        </div>
        
        <Form onSubmit={submitForm} className="login-form">
          <div className="form-group">
            <label className="form-label">
              <FontAwesomeIcon icon={faIdCard} className="form-icon" />
              Full Name
            </label>
            <input
              className="form-input"
              value={fullName}
              onChange={(e) => handleInputChange("FULL_NAME", e.target.value)}
              type="text"
              placeholder="Enter your full name"
              required
              disabled={isLoading}
            />
          </div>

          <div className="form-group">
            <label className="form-label">
              <FontAwesomeIcon icon={faUser} className="form-icon" />
              Username
            </label>
            <input
              className="form-input"
              value={username}
              onChange={(e) => handleInputChange("USERNAME", e.target.value)}
              type="text"
              placeholder="Choose a username"
              required
              disabled={isLoading}
            />
          </div>

          <div className="form-group">
            <label className="form-label">
              <FontAwesomeIcon icon={faBriefcase} className="form-icon" />
              Designation
            </label>
            <select
              className="form-input"
              value={designation}
              onChange={(e) => handleInputChange("DESIGNATION", e.target.value)}
              required
              disabled={isLoading}
            >
              <option value="">Select your designation</option>
              <option value="Manager">Manager</option>
              <option value="Employee">Employee</option>
              <option value="Admin">Admin</option>
              <option value="Supervisor">Supervisor</option>
            </select>
          </div>

          <div className="form-group">
            <label className="form-label">
              <FontAwesomeIcon icon={faLock} className="form-icon" />
              Password
            </label>
            <input
              className="form-input"
              value={password}
              type="password"
              placeholder="Create a password"
              onChange={(e) => handleInputChange("PASSWORD", e.target.value)}
              required
              disabled={isLoading}
            />
          </div>

          <div className="form-group">
            <label className="form-label">
              <FontAwesomeIcon icon={faLock} className="form-icon" />
              Confirm Password
            </label>
            <input
              className="form-input"
              value={confirmPassword}
              type="password"
              placeholder="Confirm your password"
              onChange={(e) => handleInputChange("CONFIRM_PASSWORD", e.target.value)}
              required
              disabled={isLoading}
            />
          </div>

          <button type="submit" className="login-button" disabled={isLoading}>
            {isLoading ? (
              <>
                <FontAwesomeIcon icon={faSpinner} spin />
                Creating Account...
              </>
            ) : (
              "Create Account"
            )}
          </button>
        </Form>
        
        <div className="login-footer">
          <p>
            Already have an account?{" "}
            <button 
              type="button" 
              className="link-button" 
              onClick={onSwitchToLogin}
              disabled={isLoading}
            >
              Sign In
            </button>
          </p>
        </div>
      </div>
    </div>
  );
}

export default Register;