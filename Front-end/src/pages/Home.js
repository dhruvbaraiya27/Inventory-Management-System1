import React from "react";
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
} from "@fortawesome/free-solid-svg-icons";
const DATA = [
  {
    id: 1,
    title: "Purchase Orders",
    count: 10,
    icon: faFileInvoiceDollar,
  },
  {
    id: 2,
    title: "Invoices",
    count: 8,
    icon: faFileAlt,
  },
  {
    id: 3,
    title: "Products",
    count: 5,
    icon: faBox,
  },
  {
    id: 4,
    title: "Buyers",
    count: 3,
    icon: faPeopleCarry,
  },
  {
    id: 5,
    title: "Employees",
    count: 3,
    icon: faUserTie,
  },
  {
    id: 6,
    title: "Total Amount",
    count: 190000,
    icon: faDollarSign,
  },
  {
    id: 7,
    title: "Total Quantity",
    count: 504,
    icon: faBalanceScale,
  },
  {
    id: 8,
    title: "Total Orders",
    count: 40,
    icon: faWeightHanging,
  },
];

function Home() {
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

  const formatNumber = (num) => {
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
      </div>

      <div className="stats-grid">
        {DATA.map((item, index) => (
          <div key={item.id} className={`stat-card ${getGradientClass(index)}`}>
            <div className="stat-card-content">
              <div className="stat-icon-container">
                <FontAwesomeIcon icon={item.icon} className="stat-icon" />
              </div>
              <div className="stat-info">
                <div className="stat-number">{formatNumber(item.count)}</div>
                <div className="stat-title">{item.title}</div>
              </div>
            </div>
            <div className="stat-card-shine"></div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default Home;
