import React, { useReducer, useContext, useState } from "react";
import { Button, Form, Row, Col } from "react-bootstrap";
import "../styles/Login.scss";
import displayToast from "../utils/displayToast";
import { validateInputField } from "../utils/validations";
import axios from "axios";
import { URLS } from "../routes";
import { useHistory } from "react-router";
import { AuthContext } from "../context/Auth";
import Register from "./Register";

const initialState = {
  userName: "",
  password: "",
};

const reducer = (state, action) => {
  switch (action.type) {
    case "USER_NAME":
      return {
        ...state,
        userName: action.userName,
      };

    case "USER_PASSWORD":
      return {
        ...state,
        password: action.password,
      };

    case "RESET":
      return initialState;
    default:
      return state;
  }
};

function Login() {
  const { userData, setUserData, isLoggedIn } = useContext(AuthContext);

  const [state, dispatch] = useReducer(reducer, initialState);
  const [showRegister, setShowRegister] = useState(false);
  const history = useHistory();

  const handleUserNameChange = (event) => {
    dispatch({
      type: "USER_NAME",
      userName: event.target.value,
    });
  };

  const handlePasswordChange = (event) => {
    dispatch({
      type: "USER_PASSWORD",
      password: event.target.value,
    });
  };

  const { userName, password } = state;

  const submitForm = (e) => {
    e.preventDefault();

    if (
      validateInputField({ field: userName, fieldName: "user name" }) &&
      validateInputField({ field: password, fieldName: "password" })
    ) {
      const body = { username: userName, password };

      axios
        .post(URLS.VERIFY_USER, body)
        .then(function (response) {
          // console.log(response);
          const { status } = response;

          if (status == 200) {
            if (response.data && response.data.id) {
              setUserData(response.data);
              resetForm();
              displayToast({ type: "success", msg: "Login Successful!" });
            } else {
              displayToast({
                type: "error",
                msg: "Please enter valid credentials!",
              });
            }
          }
          // setTimeout(() => {
          //     history.push("/manage-buyers");
          //   }, 1000);
        })
        .catch(function (error) {
          console.log(error);
          displayToast({ type: "error", msg: "Oops! Something went wrong" });
        });
    } else {
      // displayToast({type : "error", msg : "Login Failed!"});
    }
  };

  const resetForm = () => {
    dispatch({
      type: "RESET",
    });
  };

  // If showing register component
  if (showRegister) {
    return <Register onSwitchToLogin={() => setShowRegister(false)} />;
  }

  // Default login component
  return (
    <div className="login-container">
      <div className="login-card">
        <div className="login-header">
          <h2 className="login-title">Welcome Back</h2>
          <p className="login-subtitle">Sign in to your Inventory Management System</p>
        </div>
        
        <Form onSubmit={submitForm} className="login-form">
          <div className="form-group">
            <label className="form-label">Username</label>
            <input
              className="form-input"
              value={userName}
              onChange={handleUserNameChange}
              type="text"
              placeholder="Enter your username"
              required
            />
          </div>

          <div className="form-group">
            <label className="form-label">Password</label>
            <input
              className="form-input"
              value={password}
              type="password"
              placeholder="Enter your password"
              onChange={handlePasswordChange}
              required
            />
          </div>

          <button type="submit" className="login-button">
            Sign In
          </button>
        </Form>
        
        <div className="login-footer">
          <p>
            Don't have an account?{" "}
            <button 
              type="button" 
              className="link-button" 
              onClick={() => setShowRegister(true)}
            >
              Create Account
            </button>
          </p>
        </div>
      </div>
    </div>
  );
}

export default Login;
