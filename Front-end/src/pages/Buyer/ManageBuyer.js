import axios from "axios";
import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { URLS } from "../../routes";
import displayToast from "../../utils/displayToast";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { 
  faPlus, 
  faEdit, 
  faTrash, 
  faSearch,
  faUsers,
  faTimes
} from "@fortawesome/free-solid-svg-icons";
import Tables from "../../components/Tables";
import "../../styles/pages.scss";
function ManageBuyer() {
  const [buyers, setBuyers] = useState([]);
  const [currentBuyer, setCurrentBuyer] = useState(null);
  const [searchTerm, setSearchTerm] = useState("");
  const [isLoading, setIsLoading] = useState(true);
  const [showDeleteModal, setShowDeleteModal] = useState(false);

  const handleCloseDeleteModal = () => {
    setShowDeleteModal(false);
    setCurrentBuyer(null);
  };

  const handleShowDeleteModal = () => setShowDeleteModal(true);

  useEffect(() => {
    let isActive = true;

    if (isActive) {
      fetchBuyers();
    }
    return () => {
      isActive = false;
    };
  }, []);

  const fetchBuyers = async () => {
    setIsLoading(true);
    const url = URLS.GET_ALL_BUYERS;
    axios
      .get(url)
      .then(function (response) {
        setBuyers(response.data);
        setIsLoading(false);
      })
      .catch(function (error) {
        console.log(error);
        setIsLoading(false);
        displayToast({ type: "error", msg: "Oops! Something went wrong" });
      });
  };

  const deleteBuyerConfirmation = (buyer) => {
    setCurrentBuyer(buyer);
    handleShowDeleteModal();
  };

  const deleteBuyer = async () => {
    const url = URLS.DELETE_BUYER + currentBuyer.id;
    axios
      .delete(url)
      .then(function (response) {
        handleCloseDeleteModal();
        displayToast({ type: "success", msg: "Buyer deleted successfully!" });
        fetchBuyers();
      })
      .catch(function (error) {
        console.log(error);
        displayToast({ type: "error", msg: "Oops! Something went wrong" });
      });
  };

  // Filter buyers based on search term
  const filteredBuyers = buyers.filter(buyer =>
    buyer.ownerName?.toLowerCase().includes(searchTerm.toLowerCase()) ||
    buyer.companyName?.toLowerCase().includes(searchTerm.toLowerCase()) ||
    buyer.zipcode?.toString().includes(searchTerm)
  );

  // Prepare table headers
  const tableHeaders = [
    { key: "srNo", label: "Sr. No." },
    { key: "ownerName", label: "Owner Name" },
    { key: "companyName", label: "Company Name" },
    { key: "zipcode", label: "Zipcode" },
    { key: "actions", label: "Actions" }
  ];

  // Prepare table data
  const tableRows = filteredBuyers.map((buyer, index) => ({
    key: buyer.id,
    srNo: index + 1,
    ownerName: buyer.ownerName,
    companyName: buyer.companyName,
    zipcode: buyer.zipcode,
    actions: (
      <div className="table-actions">
        <Link to={`/edit-buyer/?id=${buyer.id}`}>
          <button className="action-btn edit" title="Edit Buyer">
            <FontAwesomeIcon icon={faEdit} />
          </button>
        </Link>
        <button 
          className="action-btn delete" 
          onClick={() => deleteBuyerConfirmation(buyer)}
          title="Delete Buyer"
        >
          <FontAwesomeIcon icon={faTrash} />
        </button>
      </div>
    )
  }));

  return (
    <div className="manage-buyers-page">
      {/* Page Header */}
      <div className="page-header">
        <div className="header-content">
          <div className="header-text">
            <h1 className="page-title">
              <FontAwesomeIcon icon={faUsers} />
              Buyer Management
            </h1>
            <p className="page-subtitle">
              Manage your customer database and business contacts
            </p>
          </div>
          <Link to="/add-buyer" className="btn-add-new">
            <FontAwesomeIcon icon={faPlus} />
            Add New Buyer
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
              placeholder="Search buyers by name, company, or zipcode..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
            />
          </div>
          <div className="results-count">
            {filteredBuyers.length} of {buyers.length} buyers
          </div>
        </div>
      </div>

      {/* Buyers Table */}
      <div className="table-section">
        {isLoading ? (
          <div className="loading-container">
            <div className="spinner"></div>
            <p>Loading buyers...</p>
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
                <strong>{currentBuyer?.ownerName}</strong> from{" "}
                <strong>{currentBuyer?.companyName}</strong>?
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
                onClick={deleteBuyer}
              >
                <FontAwesomeIcon icon={faTrash} />
                Delete Buyer
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default ManageBuyer;
