import axios from "axios";
import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { URLS } from "../../routes";
import displayToast from "../../utils/displayToast";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { 
  faPlus, 
  faTrash, 
  faSearch,
  faShoppingCart,
  faTimes,
  faFileInvoice,
  faCheckCircle,
  faTimesCircle,
  faCalendarAlt,
  faDollarSign,
  faBox,
  faBuilding
} from "@fortawesome/free-solid-svg-icons";
import Tables from "../../components/Tables";
import "../../styles/pages.scss";

function ManagePurchaseOrder() {
  const [pos, setPos] = useState([]);
  const [currentPo, setCurrentPo] = useState(null);
  const [currentPaymentPo, setCurrentPaymentPo] = useState(null);
  const [searchTerm, setSearchTerm] = useState("");
  const [isLoading, setIsLoading] = useState(true);

  const [showDeleteModal, setShowDeleteModal] = useState(false);
  const handleCloseDeleteModal = () => {
    setShowDeleteModal(false);
    setCurrentPo(null);
  };

  const [showPaymentModal, setShowPaymentModal] = useState(false);
  const handleClosePaymentModal = () => {
    setShowPaymentModal(false);
    setCurrentPaymentPo(null);
  };

  const handleShowDeleteModal = () => setShowDeleteModal(true);
  const handleShowPaymentModal = () => setShowPaymentModal(true);

  useEffect(() => {
    let isActive = true;

    if (isActive) {
      fetchPos();
    }
    return () => {
      isActive = false;
    };
  }, []);

  const fetchPos = async () => {
    setIsLoading(true);
    const url = URLS.GET_ALL_PURCHASE_ORDERS;
    axios
      .get(url)
      .then(function (response) {
        const { status } = response;
        if (status === 200) {
          setPos(response.data);
        }
        setIsLoading(false);
      })
      .catch(function (error) {
        console.log(error);
        setIsLoading(false);
        displayToast({ type: "error", msg: "Oops! Something went wrong" });
      });
  };

  const makePayment = async () => {
    const url = URLS.GENERATE_INVOICE + currentPaymentPo.id;

    axios
      .post(url)
      .then(function (response) {
        if (response.status === 200) {
          handleClosePaymentModal();
          setCurrentPaymentPo(null);
          displayToast({
            type: "success",
            msg: "Invoice generated successfully!",
          });
          fetchPos();
        } else {
          displayToast({ type: "error", msg: "Oops! Something went wrong" });
        }
      })
      .catch(function (error) {
        console.log(error);
        displayToast({ type: "error", msg: "Oops! Something went wrong" });
      });
  };

  const deletePoConfirmation = (purchaseOrder) => {
    setCurrentPo(purchaseOrder);
    handleShowDeleteModal();
  };

  const deletePurchaseOrder = async () => {
    const url = URLS.DELETE_PURCHASE_ORDER + currentPo.id;
    axios
      .delete(url)
      .then(function (response) {
        handleCloseDeleteModal();
        displayToast({
          type: "success",
          msg: "Purchase Order deleted successfully!",
        });
        fetchPos();
      })
      .catch(function (error) {
        console.log(error);
        displayToast({ type: "error", msg: "Oops! Something went wrong" });
      });
  };

  const generateInvoiceConfirmation = (item) => {
    setCurrentPaymentPo(item);
    handleShowPaymentModal();
  };

  // Filter purchase orders based on search term
  const filteredPos = pos.filter(po => {
    const companyName = po.buyer?.companyName || '';
    const totalAmount = po.totalAmount?.toString() || '';
    const paymentDueDate = po.paymentDueDate || '';
    
    return companyName.toLowerCase().includes(searchTerm.toLowerCase()) ||
           totalAmount.includes(searchTerm) ||
           paymentDueDate.includes(searchTerm);
  });

  // Format currency
  const formatCurrency = (amount) => {
    return new Intl.NumberFormat('en-US', {
      style: 'currency',
      currency: 'USD'
    }).format(amount || 0);
  };

  // Format date
  const formatDate = (dateString) => {
    if (!dateString) return '-';
    return new Date(dateString).toLocaleDateString('en-US', {
      year: 'numeric',
      month: 'short',
      day: 'numeric'
    });
  };

  // Prepare table headers
  const tableHeaders = [
    { key: "srNo", label: "Sr. No." },
    { key: "companyName", label: "Company Name" },
    { key: "totalProducts", label: "Total Products" },
    { key: "totalPrice", label: "Total Price" },
    { key: "paymentDate", label: "Payment Date" },
    { key: "status", label: "Status" },
    { key: "actions", label: "Actions" }
  ];

  // Prepare table data
  const tableRows = filteredPos.map((item, index) => {
    const {
      id,
      products = [],
      paymentDueDate,
      paid,
      buyer = {},
      totalAmount,
    } = item;
    const { companyName = "" } = buyer;

    return {
      key: id,
      srNo: index + 1,
      companyName: (
        <div className="company-cell">
          <FontAwesomeIcon icon={faBuilding} />
          <span>{companyName || 'N/A'}</span>
        </div>
      ),
      totalProducts: (
        <div className="products-cell">
          <FontAwesomeIcon icon={faBox} />
          <span>{products.length}</span>
        </div>
      ),
      totalPrice: (
        <div className="price-cell">
          <FontAwesomeIcon icon={faDollarSign} />
          <span>{formatCurrency(totalAmount)}</span>
        </div>
      ),
      paymentDate: (
        <div className="date-cell">
          <FontAwesomeIcon icon={faCalendarAlt} />
          <span>{formatDate(paymentDueDate)}</span>
        </div>
      ),
      status: (
        <span className={`status-badge ${paid ? 'success' : 'warning'}`}>
          <FontAwesomeIcon icon={paid ? faCheckCircle : faTimesCircle} />
          {paid ? "Paid" : "Unpaid"}
        </span>
      ),
      actions: (
        <div className="table-actions">
          {paid ? (
            <span className="paid-indicator">Complete</span>
          ) : (
            <>
              <button 
                className="action-btn edit" 
                onClick={() => generateInvoiceConfirmation(item)}
                title="Generate Invoice"
              >
                <FontAwesomeIcon icon={faFileInvoice} />
              </button>
              <button 
                className="action-btn delete" 
                onClick={() => deletePoConfirmation(item)}
                title="Delete Purchase Order"
              >
                <FontAwesomeIcon icon={faTrash} />
              </button>
            </>
          )}
        </div>
      )
    };
  });

  return (
    <div className="manage-purchase-orders-page">
      {/* Page Header */}
      <div className="page-header">
        <div className="header-content">
          <div className="header-text">
            <h1 className="page-title">
              <FontAwesomeIcon icon={faShoppingCart} />
              Purchase Order Management
            </h1>
            <p className="page-subtitle">
              Manage purchase orders, payments, and invoice generation
            </p>
          </div>
          <Link to="/add-purchase-order" className="btn-add-new">
            <FontAwesomeIcon icon={faPlus} />
            Add Purchase Order
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
              placeholder="Search purchase orders by company, amount, or date..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
            />
          </div>
          <div className="results-count">
            {filteredPos.length} of {pos.length} purchase orders
          </div>
        </div>
      </div>

      {/* Purchase Orders Table */}
      <div className="table-section">
        {isLoading ? (
          <div className="loading-container">
            <div className="spinner"></div>
            <p>Loading purchase orders...</p>
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
                Are you sure you want to delete this purchase order?
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
                onClick={deletePurchaseOrder}
              >
                <FontAwesomeIcon icon={faTrash} />
                Delete Order
              </button>
            </div>
          </div>
        </div>
      )}

      {/* Payment Confirmation Modal */}
      {showPaymentModal && (
        <div className="modal-overlay">
          <div className="modal-content">
            <div className="modal-header">
              <h3>Generate Invoice</h3>
              <button 
                className="modal-close"
                onClick={handleClosePaymentModal}
              >
                <FontAwesomeIcon icon={faTimes} />
              </button>
            </div>
            <div className="modal-body">
              <p>
                You are about to generate an invoice and process the payment for{" "}
                <strong>{currentPaymentPo?.buyer?.companyName}</strong>.
              </p>
              <p>
                Total Amount: <strong>{formatCurrency(currentPaymentPo?.totalAmount)}</strong>
              </p>
              <p className="warning-text">
                Are you sure you want to continue?
              </p>
            </div>
            <div className="modal-footer">
              <button 
                className="btn-secondary"
                onClick={handleClosePaymentModal}
              >
                Cancel
              </button>
              <button 
                className="btn-primary"
                onClick={makePayment}
              >
                <FontAwesomeIcon icon={faFileInvoice} />
                Generate Invoice
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default ManagePurchaseOrder;
