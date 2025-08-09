import React, { useReducer, useState, useEffect } from "react";
import axios from "axios";
import { URLS } from "../../routes";
import displayToast from "../../utils/displayToast";
import { validateInputField } from "../../utils/validations";
import { useHistory, useLocation } from "react-router";
import "../../styles/pages.scss";

const initialState = {
  productName: "",
  quantity: 0,
  price: 0,
  id: -1,
};

const reducer = (state, action) => {
  switch (action.type) {
    case "PRODUCT_NAME":
      return {
        ...state,
        productName: action.productName,
      };

    case "PRODUCT_QUANTITY":
      return {
        ...state,
        quantity: action.quantity,
      };

    case "PRODUCT_PRICE":
      return {
        ...state,
        price: action.price,
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

function AddProduct() {
  let query = useQuery();
  const productId = query.get("id");
  const [state, dispatch] = useReducer(reducer, initialState);
  const { productName, quantity, price, id } = state;
  const history = useHistory();
  const [isUpdate, setIsUpdate] = useState(false);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    let isActive = true;
    let isPageUpdate = false;

    if (productId) {
      isPageUpdate = true;
    }

    setIsUpdate(isPageUpdate);

    if (isActive && isPageUpdate) {
      setIsLoading(true);
      fetchProduct();
    } else {
      setIsLoading(false);
    }
    return () => {
      isActive = false;
    };
  }, []);

  const fetchProduct = async () => {
    const url = `${URLS.GET_PRODUCT_DETAILS}/${productId}`;

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

  const handleProductNameChange = (event) => {
    dispatch({
      type: "PRODUCT_NAME",
      productName: event.target.value,
    });
  };

  const handleQuantityChange = (event) => {
    dispatch({
      type: "PRODUCT_QUANTITY",
      quantity: event.target.value,
    });
  };

  const handlePriceChange = (event) => {
    dispatch({
      type: "PRODUCT_PRICE",
      price: event.target.value,
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
      validateInputField({ field: productName, fieldName: "product name" }) &&
      validateInputField({ field: quantity, fieldName: "quantity" }) &&
      validateInputField({ field: price, fieldName: "price" })
    ) {
      const body = { productName, quantity, price, purchaseOrder: [] };
      const url = isUpdate ? URLS.EDIT_PRODUCT : URLS.ADD_PRODUCT;

      if (isUpdate) {
        body.id = id;
      }
      debugger;
      axios[isUpdate ? "put" : "post"](url, body)
        .then(function (response) {
          const { status } = response;
          if (status === 200) {
            resetForm();
            displayToast({
              type: "success",
              msg: `${
                isUpdate ? productName + " updated" : "Product added"
              } successfully!`,
            });
            setIsLoading(false);
            setTimeout(() => {
              history.push("/manage-products");
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
    <div className="add-product-page">
      {/* Page Header */}
      <div className="page-header">
        <h1 className="page-title">
          {isUpdate ? "Update Product" : "Add New Product"}
        </h1>
        <p className="page-subtitle">
          {isUpdate 
            ? "Update product information and inventory details" 
            : "Enter product details to add to your inventory"
          }
        </p>
      </div>

      {/* Product Form */}
      <div className="form-container">
        <div className="form-card">
          <div className="form-header">
            <h2>Product Information</h2>
            <p>Fill in the details below</p>
          </div>

          <form onSubmit={submitForm} className="modern-form">
            <div className="form-grid">
              <div className="form-group">
                <label className="form-label">
                  Product Name <span className="required">*</span>
                </label>
                <input
                  type="text"
                  className="form-input"
                  value={productName}
                  onChange={handleProductNameChange}
                  placeholder="Enter product name"
                  required
                />
                <span className="input-hint">Enter a descriptive product name</span>
              </div>

              <div className="form-group">
                <label className="form-label">
                  Quantity <span className="required">*</span>
                </label>
                <input
                  type="number"
                  className="form-input"
                  value={quantity}
                  onChange={handleQuantityChange}
                  placeholder="Enter quantity"
                  min="0"
                  required
                />
                <span className="input-hint">Available quantity in stock</span>
              </div>

              <div className="form-group">
                <label className="form-label">
                  Price <span className="required">*</span>
                </label>
                <div className="price-input-container">
                  <span className="currency-symbol">$</span>
                  <input
                    type="number"
                    className="form-input price-input"
                    value={price}
                    onChange={handlePriceChange}
                    placeholder="0.00"
                    min="0"
                    step="0.01"
                    required
                  />
                </div>
                <span className="input-hint">Price per unit</span>
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
                    {isUpdate ? "Update Product" : "Add Product"}
                  </span>
                )}
              </button>
            </div>
          </form>
        </div>

        {/* Help Section */}
        <div className="help-card">
          <h3>Tips for Adding Products</h3>
          <ul className="help-list">
            <li>Use clear, descriptive product names</li>
            <li>Ensure accurate quantity counts</li>
            <li>Set competitive pricing</li>
            <li>Double-check all information before saving</li>
          </ul>
        </div>
      </div>
    </div>
  );
}

export default AddProduct;
