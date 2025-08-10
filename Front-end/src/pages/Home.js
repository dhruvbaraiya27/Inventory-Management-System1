import React, { useState, useEffect } from "react";
import "../styles/home.scss";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faFileInvoiceDollar,
  faFileAlt,
  faUserTie,
  faDollarSign,
  faBalanceScale,
  faWeightHanging,
  faBox,
  faPeopleCarry,
  faSpinner,
  faSync
} from "@fortawesome/free-solid-svg-icons";
import { URLS } from '../routes';
import axios from 'axios';
import displayToast from '../utils/displayToast';


function Home() {
  const [dashboardStats, setDashboardStats] = useState(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    fetchDashboardData();
  }, []);

  const fetchDashboardData = async () => {
    setIsLoading(true);
    try {
      const response = await axios.get(URLS.GET_DASHBOARD_STATS);
      setDashboardStats(response.data);
    } catch (error) {
      console.error('Error fetching dashboard data:', error);
      displayToast({ type: "error", msg: "Failed to load dashboard data" });
    } finally {
      setIsLoading(false);
    }
  };

  const getGradientClass = (index) => {
    const gradients = [
      'gradient-blue',
      'gradient-red', 
      'gradient-teal',
      'gradient-green',
      'gradient-purple',
      'gradient-orange',
      'gradient-pink',
      'gradient-indigo'
    ];
    return gradients[index % gradients.length];
  };

  const getDynamicData = () => {
    if (!dashboardStats) return [];
    
    return [
      {
        id: 1,
        title: "Purchase Orders",
        count: dashboardStats.totalOrders || 0,
        icon: faFileInvoiceDollar,
      },
      {
        id: 2,
        title: "Invoices",
        count: dashboardStats.totalInvoices || 0,
        icon: faFileAlt,
      },
      {
        id: 3,
        title: "Products",
        count: dashboardStats.totalProducts || 0,
        icon: faBox,
      },
      {
        id: 4,
        title: "Buyers",
        count: dashboardStats.totalBuyers || 0,
        icon: faPeopleCarry,
      },
      {
        id: 5,
        title: "Pending Orders",
        count: dashboardStats.pendingOrders || 0,
        icon: faWeightHanging,
      },
      {
        id: 6,
        title: "Total Sales",
        count: dashboardStats.totalSales || 0,
        icon: faDollarSign,
      },
      {
        id: 7,
        title: "Completed Orders",
        count: dashboardStats.completedOrders || 0,
        icon: faBalanceScale,
      }
    ];
  };

  const formatNumber = (num, isCurrency = false) => {
    if (isCurrency) {
      if (num >= 1000000) {
        return '$' + (num / 1000000).toFixed(1) + 'M';
      } else if (num >= 1000) {
        return '$' + (num / 1000).toFixed(1) + 'K';
      }
      return '$' + num.toString();
    }
    
    if (num >= 1000000) {
      return (num / 1000000).toFixed(1) + 'M';
    } else if (num >= 1000) {
      return (num / 1000).toFixed(1) + 'K';
    }
    return num.toString();
  };

  return (
    <div className="dashboard-container">
      <div className="dashboard-header">
        <h1 className="dashboard-title">Dashboard Overview</h1>
        <p className="dashboard-subtitle">Monitor your inventory management system</p>
        <button 
          className="refresh-btn-home"
          onClick={fetchDashboardData}
          disabled={isLoading}
          title="Refresh Dashboard Data"
        >
          <FontAwesomeIcon 
            icon={faSync} 
            spin={isLoading}
          />
          Refresh
        </button>
      </div>

      {isLoading ? (
        <div className="loading-container-home">
          <FontAwesomeIcon icon={faSpinner} spin size="3x" />
          <p>Loading dashboard data...</p>
        </div>
      ) : (
        <div className="stats-grid">
          {getDynamicData().map((item, index) => (
            <div key={item.id} className={`stat-card ${getGradientClass(index)}`}>
              <div className="stat-card-content">
                <div className="stat-icon-container">
                  <FontAwesomeIcon icon={item.icon} className="stat-icon" />
                </div>
                <div className="stat-info">
                  <div className="stat-number">
                    {formatNumber(item.count, item.title === "Total Sales")}
                  </div>
                  <div className="stat-title">{item.title}</div>
                </div>
              </div>
              <div className="stat-card-shine"></div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

export default Home;
