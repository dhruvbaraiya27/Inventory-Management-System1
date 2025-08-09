import axios from "axios";
import React, { useReducer, useState, useEffect } from "react";
import { URLS } from "../../routes";
import displayToast from "../../utils/displayToast";
import { validateInputField } from "../../utils/validations";
import { useHistory, useLocation } from "react-router";
import "../../styles/pages.scss";

const initialState = {
  ownerName: "",
  companyName: "",
  zipcode: "",
  id: -1,
};

const reducer = (state, action) => {
  switch (action.type) {
    case "BUYER_NAME":
      return {
        ...state,
        ownerName: action.ownerName,
      };

    case "COMPANY_NAME":
      return {
        ...state,
        companyName: action.companyName,
      };

    case "COMPANY_ZIPCODE":
      return {
        ...state,
        zipcode: action.zipcode,
      };

    case "RESET":
      return initialState;

    case "UPDATE_ALL_FIELDS":
      return action.state;

    default:
      return state;
  }
};

function useQuery() {
  return new URLSearchParams(useLocation().search);
}

function AddBuyer() {
  let query = useQuery();
  const buyerId = query.get("id");
  const [state, dispatch] = useReducer(reducer, initialState);
  const { ownerName, companyName, zipcode, id } = state;
  const history = useHistory();
  const [isUpdate, setIsUpdate] = useState(false);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    let isActive = true;
    let isPageUpdate = false;

    if (buyerId) {
      isPageUpdate = true;
    }
    setIsUpdate(isPageUpdate);

    if (isActive && isPageUpdate) {
      setIsLoading(true);
      fetchBuyer();
    } else {
      setIsLoading(false);
    }
    return () => {
      isActive = false;
    };
  }, []);

  const fetchBuyer = async () => {
    const url = `${URLS.GET_BUYER_DETAILS}/${buyerId}`;

    axios
      .get(url)
      .then(function (response) {
        setIsLoading(false);
        // console.log({response});
        // debugger;
        dispatch({ type: "UPDATE_ALL_FIELDS", state: response.data });
      })
      .catch(function (error) {
        console.log(error);
        displayToast({ type: "error", msg: error.msg });
      });
  };

  const handleOwnerNameChange = (event) => {
    dispatch({
      type: "BUYER_NAME",
      ownerName: event.target.value,
    });
  };

  const handleCompanyNameChange = (event) => {
    dispatch({
      type: "COMPANY_NAME",
      companyName: event.target.value,
    });
  };

  const handleZipcodeChange = (event) => {
    dispatch({
      type: "COMPANY_ZIPCODE",
      zipcode: event.target.value,
    });
  };

  const resetForm = () => {
    dispatch({
      type: "RESET",
    });
  };

  const submitForm = (e) => {
    e.preventDefault();
    setIsLoading(true);
    if (
      validateInputField({ field: ownerName, fieldName: "owner name" }) &&
      validateInputField({ field: companyName, fieldName: "company name" }) &&
      validateInputField({ field: zipcode, fieldName: "zipcode" })
    ) {
      const body = { ownerName, companyName, zipcode };
      const url = isUpdate ? URLS.EDIT_BUYER : URLS.ADD_BUYER;

      if (isUpdate) {
        body.id = id;
      }

      axios[isUpdate ? "put" : "post"](url, body)
        .then(function (response) {
          const { status } = response;
          if (status === 200) {
            resetForm();
            displayToast({
              type: "success",
              msg: `${
                isUpdate ? ownerName + " updated" : "Buyer added"
              } successfully!`,
            });
            setIsLoading(false);
            setTimeout(() => {
              history.push("/manage-buyers");
            }, 1000);
          } else {
            displayToast({ type: "error", msg: "Oops! Something went wrong." });
            setIsLoading(false);
          }
        })
        .catch(function (error) {
          setIsLoading(false);
          console.log(error);
          displayToast({ type: "error", msg: error.msg });
        });
    } else {
      setIsLoading(false);
      // displayToast({type : "error", msg : "Login Failed!"});
    }
  };

  return (
    <div className="add-buyer-page">
      {/* Page Header */}
      <div className="page-header">
        <h1 className="page-title">
          {isUpdate ? "Update Buyer" : "Add New Buyer"}
        </h1>
        <p className="page-subtitle">
          {isUpdate 
            ? "Update buyer information and contact details" 
            : "Enter buyer details to add to your database"
          }
        </p>
      </div>

      {/* Buyer Form */}
      <div className="form-container">
        <div className="form-card">
          <div className="form-header">
            <h2>Buyer Information</h2>
            <p>Fill in the details below</p>
          </div>

          <form onSubmit={submitForm} className="modern-form">
            <div className="form-grid">
              <div className="form-group">
                <label className="form-label">
                  Owner Name <span className="required">*</span>
                </label>
                <input
                  type="text"
                  className="form-input"
                  value={ownerName}
                  onChange={handleOwnerNameChange}
                  placeholder="Enter owner's full name"
                  required
                />
                <span className="input-hint">The primary contact person</span>
              </div>

              <div className="form-group">
                <label className="form-label">
                  Company Name <span className="required">*</span>
                </label>
                <input
                  type="text"
                  className="form-input"
                  value={companyName}
                  onChange={handleCompanyNameChange}
                  placeholder="Enter company name"
                  required
                />
                <span className="input-hint">Official business name</span>
              </div>

              <div className="form-group">
                <label className="form-label">
                  Zipcode <span className="required">*</span>
                </label>
                <input
                  type="text"
                  className="form-input"
                  value={zipcode}
                  onChange={handleZipcodeChange}
                  placeholder="Enter postal code"
                  required
                />
                <span className="input-hint">Business location zipcode</span>
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
                    {isUpdate ? "Update Buyer" : "Add Buyer"}
                  </span>
                )}
              </button>
            </div>
          </form>
        </div>

        {/* Help Section */}
        <div className="help-card">
          <h3>Tips for Adding Buyers</h3>
          <ul className="help-list">
            <li>Use complete business names</li>
            <li>Verify contact information</li>
            <li>Ensure zipcode accuracy for shipping</li>
            <li>Double-check all details before saving</li>
          </ul>
        </div>
      </div>
    </div>
  );
}

export default AddBuyer;
