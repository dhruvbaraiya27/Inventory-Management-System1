import React, { useContext } from 'react';
import { AuthContext } from '../context/Auth';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import '../styles/pages.scss';
import { 
  faChartLine, 
  faUsers, 
  faBoxes, 
  faFileInvoice,
  faCalendarAlt,
  faClock,
  faTrendingUp
} from '@fortawesome/free-solid-svg-icons';

function Dashboard() {
    const { userData } = useContext(AuthContext);

    const getCurrentTime = () => {
        return new Date().toLocaleString('en-US', {
            weekday: 'long',
            year: 'numeric',
            month: 'long',
            day: 'numeric',
            hour: '2-digit',
            minute: '2-digit'
        });
    };

    const quickStats = [
        {
            title: "Active Products",
            value: "156",
            icon: faBoxes,
            color: "#667eea",
            trend: "+12%"
        },
        {
            title: "Total Buyers",
            value: "89",
            icon: faUsers,
            color: "#f093fb",
            trend: "+8%"
        },
        {
            title: "Monthly Sales",
            value: "$24.5K",
            icon: faChartLine,
            color: "#4facfe",
            trend: "+23%"
        },
        {
            title: "Pending Orders",
            value: "23",
            icon: faFileInvoice,
            color: "#43e97b",
            trend: "-5%"
        }
    ];

    const recentActivity = [
        { action: "New product added", time: "2 minutes ago", type: "success" },
        { action: "Order #1234 completed", time: "15 minutes ago", type: "info" },
        { action: "Buyer registration", time: "1 hour ago", type: "success" },
        { action: "Low stock alert", time: "2 hours ago", type: "warning" },
        { action: "Invoice generated", time: "3 hours ago", type: "info" }
    ];

    return (
        <div className="dashboard-page">
            {/* Welcome Header */}
            <div className="page-header">
                <div className="welcome-section">
                    <h1 className="page-title">
                        Welcome back, {userData?.fullName || 'User'}! 👋
                    </h1>
                    <p className="page-subtitle">
                        Here's what's happening with your inventory today.
                    </p>
                    <div className="current-time">
                        <FontAwesomeIcon icon={faClock} />
                        <span>{getCurrentTime()}</span>
                    </div>
                </div>
            </div>

            {/* Quick Stats Grid */}
            <div className="stats-overview">
                <h2 className="section-title">Overview</h2>
                <div className="stats-grid">
                    {quickStats.map((stat, index) => (
                        <div key={index} className="stat-card-dashboard">
                            <div className="stat-header">
                                <div 
                                    className="stat-icon-dashboard"
                                    style={{ background: `linear-gradient(135deg, ${stat.color}22, ${stat.color}44)` }}
                                >
                                    <FontAwesomeIcon 
                                        icon={stat.icon} 
                                        style={{ color: stat.color }}
                                    />
                                </div>
                                <div className="stat-trend">
                                    <FontAwesomeIcon icon={faTrendingUp} />
                                    <span>{stat.trend}</span>
                                </div>
                            </div>
                            <div className="stat-content">
                                <h3 className="stat-value">{stat.value}</h3>
                                <p className="stat-label">{stat.title}</p>
                            </div>
                        </div>
                    ))}
                </div>
            </div>

            {/* Recent Activity */}
            <div className="activity-section">
                <div className="content-row">
                    <div className="activity-feed">
                        <h2 className="section-title">Recent Activity</h2>
                        <div className="activity-list">
                            {recentActivity.map((activity, index) => (
                                <div key={index} className={`activity-item ${activity.type}`}>
                                    <div className="activity-indicator"></div>
                                    <div className="activity-content">
                                        <p className="activity-action">{activity.action}</p>
                                        <span className="activity-time">{activity.time}</span>
                                    </div>
                                </div>
                            ))}
                        </div>
                    </div>

                    {/* Quick Actions */}
                    <div className="quick-actions">
                        <h2 className="section-title">Quick Actions</h2>
                        <div className="action-grid">
                            <button className="action-btn-dashboard primary">
                                <FontAwesomeIcon icon={faBoxes} />
                                <span>Add Product</span>
                            </button>
                            <button className="action-btn-dashboard secondary">
                                <FontAwesomeIcon icon={faUsers} />
                                <span>Add Buyer</span>
                            </button>
                            <button className="action-btn-dashboard tertiary">
                                <FontAwesomeIcon icon={faFileInvoice} />
                                <span>Create Order</span>
                            </button>
                            <button className="action-btn-dashboard quaternary">
                                <FontAwesomeIcon icon={faChartLine} />
                                <span>View Reports</span>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Dashboard;
