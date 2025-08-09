import axios from 'axios';
import React, {useReducer, useState, useEffect, useContext} from 'react';
import { URLS } from '../../routes';
import displayToast from '../../utils/displayToast';
import { validateInputField } from '../../utils/validations';
import { useHistory, useLocation } from 'react-router';
import { AuthContext } from '../../context/Auth';
import "../../styles/pages.scss";

const initialState = {
    fullName: '',
    designation : "",
    username : "",
    salary : "",
    dob : "",
    rating : "",
    password : "",
    id : -1
};

const reducer = (state, action) => {
    switch (action.type) {
        case 'EMPLOYEE_NAME':
            return {
                ...state,
                fullName: action.fullName
            };

        case 'USER_NAME':
            return {
                ...state,
                username: action.username
            };

        case 'EMPLOYEE_DESIGNATION':
            return {
                ...state,
                designation: action.designation
            };

        case 'EMPLOYEE_SALARY':
                return {
                    ...state,
                    salary: action.salary
        };

        case 'EMPLOYEE_DOB':
                return {
                    ...state,
                    dob: action.dob
        }; 
            
        case 'EMPLOYEE_RATING':
                return {
                    ...state,
                    rating: action.rating
        };

        case 'EMPLOYEE_PASSWORD':
                return {
                    ...state,
                    password: action.password
        };

        case 'RESET':
            return initialState;

        case 'UPDATE_ALL_FIELDS':
            return action.state;    

        default:
            return state;
    }
};

function useQuery() {
    return new URLSearchParams(useLocation().search);
}

function AddEmployee() {
    
    let query = useQuery();
    const employeeId = query.get("id");
    const [state, dispatch] = useReducer(reducer, initialState);
    const {fullName, username, salary, dob, rating, designation, id, password} = state;
    const history = useHistory();
    const [isUpdate, setIsUpdate] = useState(false);
    const [isLoading, setIsLoading] = useState(true);

    const {userData} = useContext(AuthContext);

    useEffect(() => {
        let isActive = true;
        let isPageUpdate = false;

        if(employeeId){
            isPageUpdate = true;
        }
        setIsUpdate(isPageUpdate);

        if(isActive && isPageUpdate) {
            setIsLoading(true);
            fetchEmployee();
        }else{
            setIsLoading(false);
        }
        return () => {
            isActive = false;
        }
    }, []);

    const fetchEmployee = async () => {
        const url  = `${URLS.GET_EMPLOYEE_DETAILS}/${employeeId}`;

        axios.get(url)
              .then(function (response) {
                setIsLoading(false);
                dispatch({type: 'UPDATE_ALL_FIELDS', state: response.data});
              })
              .catch(function (error) {
                  
                console.log(error);
                displayToast({type : "error", msg : error.msg});
              });
    }

    const handleFullNameChange = (event) => {
        dispatch({type: 'EMPLOYEE_NAME', fullName: event.target.value});
    }

    const handleUserNameChange = (event) => {
        dispatch({type: 'USER_NAME', username: event.target.value});
    }

    const handlePasswordChange = (event) => {
        dispatch({type: 'EMPLOYEE_PASSWORD', password: event.target.value});
    }
    
    const handleSalaryChange = (event) => {
        dispatch({type: 'EMPLOYEE_SALARY', salary: event.target.value});
    }

    const handleRatingChange = (event) => {
        dispatch({type: 'EMPLOYEE_RATING', rating: event.target.value});
    }

    const handleDesignationChange = (event) => {
        dispatch({type: 'EMPLOYEE_DESIGNATION', designation: event.target.value});
    }
    
    const handleDobChange = (event) => {
        dispatch({type: 'EMPLOYEE_DOB', dob: event.target.value});
    };

    const resetForm = () => {
        dispatch({
            type : 'RESET'
        });
    }

    const submitForm = (e) =>{
        e.preventDefault();
        setIsLoading(true);

        if (validateInputField({field : fullName, fieldName : "full name"}) && 
            validateInputField({field : username, fieldName : "user name"}) &&
            validateInputField({field : salary, fieldName : "salary"}) &&
            validateInputField({field : dob, fieldName : "dob"}) &&
            validateInputField({field : designation, fieldName : "designation"}) &&
            validateInputField({field : password, fieldName : "password"})) {
            const body = {fullName, username, salary, dob, rating, designation, id, password, userId : userData.id};
            const url = isUpdate ? URLS.EDIT_EMPLOYEE : URLS.ADD_EMPLOYEE;
            
            if(isUpdate){
                body.id = id;
            };
            
            axios[isUpdate ? "put" : "post"](url, body)
              .then(function (response) {
                const {status} = response;
                if(status === 200){
                    resetForm();
                    displayToast({type : "success", msg : `${isUpdate ? fullName + ' updated' : 'Employee added'} successfully!`});
                    setIsLoading(false);
                    setTimeout(() => {
                        history.push("/manage-employees");
                    }, 1000);
                }else{
                    displayToast({type : "error", msg : "Oops! Something went wrong."});
                    setIsLoading(false);
                }
              })
              .catch(function (error) {
                setIsLoading(false);
                console.log(error);
                displayToast({type : "error", msg : error.msg});
              });

        }else{
            setIsLoading(false);
           // displayToast({type : "error", msg : "Login Failed!"});
        }
    }

    return (
        <div className="add-employee-page">
            {/* Page Header */}
            <div className="page-header">
                <h1 className="page-title">
                    {isUpdate ? "Update Employee" : "Add New Employee"}
                </h1>
                <p className="page-subtitle">
                    {isUpdate 
                        ? "Update employee information and access details" 
                        : "Enter employee details to add to your team"
                    }
                </p>
            </div>

            {/* Employee Form */}
            <div className="form-container">
                <div className="form-card">
                    <div className="form-header">
                        <h2>Employee Information</h2>
                        <p>Fill in all required details below</p>
                    </div>

                    <form onSubmit={submitForm} className="modern-form">
                        <div className="form-grid">
                            <div className="form-group">
                                <label className="form-label">
                                    Full Name <span className="required">*</span>
                                </label>
                                <input
                                    type="text"
                                    className="form-input"
                                    value={fullName}
                                    onChange={handleFullNameChange}
                                    placeholder="Enter employee's full name"
                                    required
                                />
                                <span className="input-hint">Employee's complete name</span>
                            </div>

                            <div className="form-group">
                                <label className="form-label">
                                    Username <span className="required">*</span>
                                </label>
                                <input
                                    type="text"
                                    className="form-input"
                                    value={username}
                                    onChange={handleUserNameChange}
                                    placeholder="Enter username for login"
                                    required
                                />
                                <span className="input-hint">Unique username for system access</span>
                            </div>

                            <div className="form-group">
                                <label className="form-label">
                                    Password <span className="required">*</span>
                                </label>
                                <input
                                    type="password"
                                    className="form-input"
                                    value={password}
                                    onChange={handlePasswordChange}
                                    placeholder="Enter secure password"
                                    required
                                />
                                <span className="input-hint">Secure password for login</span>
                            </div>

                            <div className="form-group">
                                <label className="form-label">
                                    Designation <span className="required">*</span>
                                </label>
                                <select 
                                    className="form-input"
                                    value={designation} 
                                    onChange={handleDesignationChange}
                                    required
                                >
                                    <option value="">Select designation</option>
                                    <option value="Manager">Manager</option>
                                    <option value="Gatekeeper">Gatekeeper</option>
                                    <option value="Employee">Employee</option>
                                </select>
                                <span className="input-hint">Employee's role in the organization</span>
                            </div>

                            <div className="form-group">
                                <label className="form-label">
                                    Date of Birth <span className="required">*</span>
                                </label>
                                <input
                                    type="date"
                                    className="form-input"
                                    value={dob}
                                    onChange={handleDobChange}
                                    required
                                />
                                <span className="input-hint">Employee's birth date</span>
                            </div>

                            <div className="form-group">
                                <label className="form-label">
                                    Salary <span className="required">*</span>
                                </label>
                                <div className="price-input-container">
                                    <span className="currency-symbol">$</span>
                                    <input
                                        type="number"
                                        className="form-input price-input"
                                        value={salary}
                                        onChange={handleSalaryChange}
                                        placeholder="0.00"
                                        min="0"
                                        step="0.01"
                                        required
                                    />
                                </div>
                                <span className="input-hint">Monthly salary amount</span>
                            </div>

                            <div className="form-group">
                                <label className="form-label">
                                    Rating
                                </label>
                                <input
                                    type="number"
                                    className="form-input"
                                    value={rating}
                                    onChange={handleRatingChange}
                                    placeholder="Enter rating (1-10)"
                                    min="1"
                                    max="10"
                                />
                                <span className="input-hint">Performance rating (1-10)</span>
                            </div>
                        </div>

                        <div className="form-actions">
                            <button
                                type="button"
                                className="btn-secondary"
                                onClick={() => window.history.back()}
                                disabled={isLoading}
                            >
                                Cancel
                            </button>
                            <button
                                type="submit"
                                className="btn-primary"
                                disabled={isLoading}
                            >
                                {isLoading ? (
                                    <span className="loading-content">
                                        <span className="spinner"></span>
                                        {isUpdate ? "Updating..." : "Adding..."}
                                    </span>
                                ) : (
                                    <span>
                                        {isUpdate ? "Update Employee" : "Add Employee"}
                                    </span>
                                )}
                            </button>
                        </div>
                    </form>
                </div>

                {/* Help Section */}
                <div className="help-card">
                    <h3>Tips for Adding Employees</h3>
                    <ul className="help-list">
                        <li>Use unique usernames for each employee</li>
                        <li>Set strong, secure passwords</li>
                        <li>Assign appropriate designations based on roles</li>
                        <li>Verify all information before saving</li>
                        <li>Keep salary information confidential</li>
                    </ul>
                </div>
            </div>
        </div>
    )
}

export default AddEmployee;
