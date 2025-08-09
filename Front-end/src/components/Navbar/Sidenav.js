import React, { useContext, useState } from "react";
import { useHistory, useLocation } from "react-router-dom";
import { AuthContext } from "../../context/Auth";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faHome,
  faUserTie,
  faBoxOpen,
  faPeopleCarry,
  faFileInvoice,
  faBullseye,
  faBars,
  faSignOutAlt,
  faTimes,
} from "@fortawesome/free-solid-svg-icons";
import displayToast from "../../utils/displayToast";
import "../../styles/navbar.scss";

function Sidenav() {
  const history = useHistory();
  const location = useLocation();
  const { isLoggedIn, setUserData, userData } = useContext(AuthContext);
  const [sidebarOpen, setSidebarOpen] = useState(false);

  const logoutUser = () => {
    displayToast("Logged out successfully!", "success");
    setTimeout(() => {
      setUserData(null);
      history.push("/");
    }, 1000);
  };

  const menuItems = [
    {
      path: "/",
      name: "Dashboard",
      icon: faHome,
    },
    {
      path: "/manage-products",
      name: "Products",
      icon: faBoxOpen,
    },
    ...(userData?.designation?.toUpperCase() === "MANAGER" ? [{
      path: "/manage-employees",
      name: "Employees",
      icon: faUserTie,
    }] : []),
    {
      path: "/manage-buyers",
      name: "Buyers",
      icon: faPeopleCarry,
    },
    {
      path: "/manage-purchase-order",
      name: "Purchase Orders",
      icon: faFileInvoice,
    },
    {
      path: "/manage-invoice",
      name: "Invoices",
      icon: faBullseye,
    },
  ];

  const navigateToPath = (path) => {
    if (location.pathname !== path) {
      history.push(path);
    }
    setSidebarOpen(false);
  };

  if (!isLoggedIn) {
    return null;
  }

  return (
    <React.Fragment>
      {/* Modern Top Navigation Bar */}
      <nav className="modern-navbar">
        <div className="navbar-content">
          <div className="navbar-left">
            <button 
              className="sidebar-toggle"
              onClick={() => setSidebarOpen(!sidebarOpen)}
            >
              <FontAwesomeIcon icon={sidebarOpen ? faTimes : faBars} />
            </button>
            <div className="navbar-brand">
              <h2>Inventory Pro</h2>
            </div>
          </div>
          
          <div className="navbar-right">
            <div className="user-info">
              <div className="user-avatar">
                <FontAwesomeIcon icon={faUserTie} />
              </div>
              <div className="user-details">
                <span className="user-name">{userData.fullName}</span>
                <span className="user-role">{userData.designation}</span>
              </div>
            </div>
            
            <button className="logout-btn" onClick={logoutUser}>
              <FontAwesomeIcon icon={faSignOutAlt} />
              <span>Logout</span>
            </button>
          </div>
        </div>
      </nav>

      {/* Modern Sidebar */}
      <div className={`modern-sidebar ${sidebarOpen ? 'sidebar-open' : ''}`}>
        <div className="sidebar-header">
          <div className="sidebar-logo">
            <h3>Menu</h3>
          </div>
        </div>
        
        <nav className="sidebar-nav">
          {menuItems.map((item, index) => (
            <button
              key={index}
              className={`nav-item ${location.pathname === item.path ? 'active' : ''}`}
              onClick={() => navigateToPath(item.path)}
            >
              <div className="nav-icon">
                <FontAwesomeIcon icon={item.icon} />
              </div>
              <span className="nav-text">{item.name}</span>
            </button>
          ))}
        </nav>
      </div>

      {/* Sidebar Overlay */}
      {sidebarOpen && (
        <div 
          className="sidebar-overlay"
          onClick={() => setSidebarOpen(false)}
        />
      )}
    </React.Fragment>
  );
}

export default Sidenav;
