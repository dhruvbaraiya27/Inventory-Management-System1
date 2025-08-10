import React, { useContext, useState, useEffect } from 'react';
import { AuthContext } from '../context/Auth';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { URLS } from '../routes';
import axios from 'axios';
import displayToast from '../utils/displayToast';
import '../styles/pages.scss';
import { 
  faChartLine, 
  faUsers, 
  faBoxes, 
  faFileInvoice,
  faCalendarAlt,
  faClock,
  faTrendingUp,
  faSpinner,
  faSync
} from '@fortawesome/free-solid-svg-icons';

function Dashboard() {
    const { userData } = useContext(AuthContext);
    const [dashboardStats, setDashboardStats] = useState(null);
    const [recentActivity, setRecentActivity] = useState([]);
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        fetchDashboardData();
    }, []);

    const fetchDashboardData = async () => {
        setIsLoading(true);
        try {
            // Fetch dashboard statistics
            const statsResponse = await axios.get(URLS.GET_DASHBOARD_STATS);
            setDashboardStats(statsResponse.data);

            // Fetch recent activity
            const activityResponse = await axios.get(URLS.GET_RECENT_ACTIVITY);
            setRecentActivity(activityResponse.data);

        } catch (error) {
            console.error('Error fetching dashboard data:', error);
            displayToast({ type: "error", msg: "Failed to load dashboard data" });
        } finally {
            setIsLoading(false);
        }
    };

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

    const formatCurrency = (amount) => {
        return new Intl.NumberFormat('en-US', {
            style: 'currency',
            currency: 'USD',
            minimumFractionDigits: 0,
            maximumFractionDigits: 0
        }).format(amount || 0);
    };

    const getQuickStats = () => {
        if (!dashboardStats) return [];
        
        return [
            {
                title: "Active Products",
                value: dashboardStats.totalProducts?.toString() || "0",
                icon: faBoxes,
                color: "#667eea",
                trend: dashboardStats.totalProducts > 0 ? "Active" : "None"
            },
            {
                title: "Total Buyers",
                value: dashboardStats.totalBuyers?.toString() || "0",
                icon: faUsers,
                color: "#f093fb",
                trend: dashboardStats.totalBuyers > 0 ? "Registered" : "None"
            },
            {
                title: "Total Sales",
                value: formatCurrency(dashboardStats.totalSales),
                icon: faChartLine,
                color: "#4facfe",
                trend: `${dashboardStats.completedOrders || 0} Orders`
            },
            {
                title: "Pending Orders",
                value: dashboardStats.pendingOrders?.toString() || "0",
                icon: faFileInvoice,
                color: "#43e97b",
                trend: dashboardStats.pendingOrders === 0 ? "All Clear" : "Pending"
            }
        ];
    };



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
                <div className="section-header">
                    <h2 className="section-title">Overview</h2>
                    <button 
                        className="refresh-btn"
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
                    <div className="loading-container">
                        <FontAwesomeIcon icon={faSpinner} spin size="2x" />
                        <p>Loading dashboard data...</p>
                    </div>
                ) : (
                    <div className="stats-grid">
                        {getQuickStats().map((stat, index) => (
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
                )}
            </div>

            {/* Recent Activity */}
            <div className="activity-section">
                <div className="content-row">
                    <div className="activity-feed">
                        <h2 className="section-title">Recent Activity</h2>
                        <div className="activity-list">
                            {isLoading ? (
                                <div className="loading-container">
                                    <FontAwesomeIcon icon={faSpinner} spin />
                                    <span>Loading activity...</span>
                                </div>
                            ) : recentActivity.length > 0 ? (
                                recentActivity.map((activity, index) => (
                                    <div key={index} className={`activity-item ${activity.type}`}>
                                        <div className="activity-indicator"></div>
                                        <div className="activity-content">
                                            <p className="activity-action">{activity.action}</p>
                                            <span className="activity-time">{activity.time}</span>
                                        </div>
                                    </div>
                                ))
                            ) : (
                                <div className="no-activity">
                                    <p>No recent activity to display</p>
                                </div>
                            )}
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
