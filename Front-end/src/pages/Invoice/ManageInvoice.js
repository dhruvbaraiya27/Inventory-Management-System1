import axios from "axios";
import React, { useEffect, useState } from "react";
import { URLS } from "../../routes";
import displayToast from "../../utils/displayToast";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { 
  faFileInvoiceDollar,
  faSearch,
  faCalendarAlt,
  faBuilding,
  faDollarSign,
  faBox
} from "@fortawesome/free-solid-svg-icons";
import Tables from "../../components/Tables";
import "../../styles/pages.scss";

function ManageInvoice() {
  const [invoices, setInvoices] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    let isActive = true;

    if (isActive) {
      fetchInvoices();
    }
    return () => {
      isActive = false;
    };
  }, []);

  const fetchInvoices = async () => {
    setIsLoading(true);
    const url = URLS.GET_ALL_INVOICE;
    axios
      .get(url)
      .then(function (response) {
        setInvoices(response.data);
        setIsLoading(false);
      })
      .catch(function (error) {
        console.log(error);
        setIsLoading(false);
        displayToast({ type: "error", msg: "Oops! Something went wrong" });
      });
  };

  // Filter invoices based on search term
  const filteredInvoices = invoices.filter(invoice => {
    const companyName = invoice.purchaseOrder?.buyer?.companyName || '';
    const totalAmount = invoice.purchaseOrder?.totalAmount?.toString() || '';
    const paymentDate = invoice.paymentDate || '';
    
    return companyName.toLowerCase().includes(searchTerm.toLowerCase()) ||
           totalAmount.includes(searchTerm) ||
           paymentDate.includes(searchTerm);
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
    { key: "totalQuantity", label: "Total Quantity" },
    { key: "totalPrice", label: "Total Price" },
    { key: "paymentDate", label: "Payment Date" }
  ];

  // Prepare table data
  const tableRows = filteredInvoices.map((item, index) => {
    const { id, purchaseOrder, paymentDate } = item;
    const { products = [], buyer = {}, totalAmount } = purchaseOrder || {};
    const { companyName = "" } = buyer;
    const qty = products.reduce((acc, current) => acc + current.quantity, 0);

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
      totalQuantity: qty,
      totalPrice: (
        <div className="price-cell">
          <FontAwesomeIcon icon={faDollarSign} />
          <span>{formatCurrency(totalAmount)}</span>
        </div>
      ),
      paymentDate: (
        <div className="date-cell">
          <FontAwesomeIcon icon={faCalendarAlt} />
          <span>{formatDate(paymentDate)}</span>
        </div>
      )
    };
  });

  return (
    <div className="manage-invoices-page">
      {/* Page Header */}
      <div className="page-header">
        <div className="header-content">
          <div className="header-text">
            <h1 className="page-title">
              <FontAwesomeIcon icon={faFileInvoiceDollar} />
              Invoice Management
            </h1>
            <p className="page-subtitle">
              View and manage all generated invoices and payments
            </p>
          </div>
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
              placeholder="Search invoices by company, amount, or date..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
            />
          </div>
          <div className="results-count">
            {filteredInvoices.length} of {invoices.length} invoices
          </div>
        </div>
      </div>

      {/* Invoices Table */}
      <div className="table-section">
        {isLoading ? (
          <div className="loading-container">
            <div className="spinner"></div>
            <p>Loading invoices...</p>
          </div>
        ) : (
          <Tables 
            heads={tableHeaders} 
            rows={tableRows}
          />
        )}
      </div>
    </div>
  );
}

export default ManageInvoice;
