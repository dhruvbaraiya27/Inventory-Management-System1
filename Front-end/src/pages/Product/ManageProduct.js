import axios from "axios";
import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { URLS } from "../../routes";
import displayToast from "../../utils/displayToast";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import "../../styles/pages.scss";
import { 
  faPlus, 
  faEdit, 
  faTrash, 
  faEye,
  faSearch,
  faFilter,
  faBox,
  faTimes
} from "@fortawesome/free-solid-svg-icons";
import Tables from "../../components/Tables";
function ManageProduct() {
  const [products, setProducts] = useState([]);
  const [currentProduct, setCurrentProduct] = useState(null);
  const [searchTerm, setSearchTerm] = useState("");
  const [isLoading, setIsLoading] = useState(true);
  const [showDeleteModal, setShowDeleteModal] = useState(false);

  const handleCloseDeleteModal = () => {
    setShowDeleteModal(false);
    setCurrentProduct(null);
  };

  const handleShowDeleteModal = () => setShowDeleteModal(true);

  useEffect(() => {
    let isActive = true;

    if (isActive) {
      fetchProducts();
    }
    return () => {
      isActive = false;
    };
  }, []);

  const fetchProducts = async () => {
    setIsLoading(true);
    const url = URLS.GET_ALL_PRODUCTS;
    axios
      .get(url)
      .then(function (response) {
        setProducts(response.data);
        setIsLoading(false);
      })
      .catch(function (error) {
        console.log(error);
        setIsLoading(false);
        displayToast({ type: "error", msg: "Oops! Something went wrong" });
      });
  };

  const deleteProductConfirmation = (product) => {
    setCurrentProduct(product);
    handleShowDeleteModal();
  };

  const deleteProduct = async () => {
    const url = URLS.DELETE_PRODUCT + currentProduct.id;
    axios
      .delete(url)
      .then(function (response) {
        handleCloseDeleteModal();
        displayToast({ type: "success", msg: "Product deleted successfully!" });
        fetchProducts();
      })
      .catch(function (error) {
        console.log(error);
        displayToast({ type: "error", msg: "Oops! Something went wrong" });
      });
  };

  // Filter products based on search term
  const filteredProducts = products.filter(product =>
    product.productName?.toLowerCase().includes(searchTerm.toLowerCase()) ||
    product.price?.toString().includes(searchTerm) ||
    product.quantity?.toString().includes(searchTerm)
  );

  // Prepare table headers
  const tableHeaders = [
    { key: "srNo", label: "Sr. No." },
    { key: "productName", label: "Product Name" },
    { key: "price", label: "Price ($)" },
    { key: "quantity", label: "Quantity" },
    { key: "actions", label: "Actions" }
  ];

  // Prepare table data
  const tableRows = filteredProducts.map((product, index) => ({
    key: product.id,
    srNo: index + 1,
    productName: product.productName,
    price: `$${product.price || 0}`,
    quantity: product.quantity,
    actions: (
      <div className="table-actions">
        <Link to={`/edit-product/?id=${product.id}`}>
          <button className="action-btn edit" title="Edit Product">
            <FontAwesomeIcon icon={faEdit} />
          </button>
        </Link>
        <button 
          className="action-btn delete" 
          onClick={() => deleteProductConfirmation(product)}
          title="Delete Product"
        >
          <FontAwesomeIcon icon={faTrash} />
        </button>
      </div>
    )
  }));

  return (
    <div className="manage-products-page">
      {/* Page Header */}
      <div className="page-header">
        <div className="header-content">
          <div className="header-text">
            <h1 className="page-title">
              <FontAwesomeIcon icon={faBox} />
              Product Management
            </h1>
            <p className="page-subtitle">
              Manage your product inventory and pricing
            </p>
          </div>
          <Link to="/add-product" className="btn-add-new">
            <FontAwesomeIcon icon={faPlus} />
            Add New Product
          </Link>
        </div>
      </div>

      {/* Search and Filters */}
      <div className="content-card">
        <div className="search-section">
          <div className="search-container">
            <FontAwesomeIcon icon={faSearch} className="search-icon" />
            <input
              type="text"
              className="search-input"
              placeholder="Search products by name, price, or quantity..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
            />
          </div>
          <div className="results-count">
            {filteredProducts.length} of {products.length} products
          </div>
        </div>
      </div>

      {/* Products Table */}
      <div className="table-section">
        {isLoading ? (
          <div className="loading-container">
            <div className="spinner"></div>
            <p>Loading products...</p>
          </div>
        ) : (
          <Tables 
            heads={tableHeaders} 
            rows={tableRows}
          />
        )}
      </div>

      {/* Delete Confirmation Modal */}
      {showDeleteModal && (
        <div className="modal-overlay">
          <div className="modal-content">
            <div className="modal-header">
              <h3>Confirm Deletion</h3>
              <button 
                className="modal-close"
                onClick={handleCloseDeleteModal}
              >
                <FontAwesomeIcon icon={faTimes} />
              </button>
            </div>
            <div className="modal-body">
              <p>
                Are you sure you want to delete{" "}
                <strong>{currentProduct?.productName}</strong>?
              </p>
              <p className="warning-text">
                This action cannot be undone.
              </p>
            </div>
            <div className="modal-footer">
              <button 
                className="btn-secondary"
                onClick={handleCloseDeleteModal}
              >
                Cancel
              </button>
              <button 
                className="btn-danger"
                onClick={deleteProduct}
              >
                <FontAwesomeIcon icon={faTrash} />
                Delete Product
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default ManageProduct;
