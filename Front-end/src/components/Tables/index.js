import React from 'react';
import './Tables.scss';

function Tables({ heads, rows, onRowClick }) {
    return (
        <div className="modern-table-container">
            <div className="table-wrapper">
                <table className="modern-table">
                    <thead>
                        <tr>
                            {heads?.map((item, index) => (
                                <th key={item.key || index} className="table-header">
                                    {item.label}
                                </th>
                            ))}
                        </tr>
                    </thead>
                    <tbody>
                        {rows?.length > 0 ? (
                            rows.map((item, rowIndex) => (
                                <tr 
                                    key={item.key || rowIndex} 
                                    className={`table-row ${onRowClick ? 'clickable' : ''}`}
                                    onClick={() => onRowClick && onRowClick(item)}
                                >
                                    {heads?.map((head, cellIndex) => (
                                        <td key={`${rowIndex}-${cellIndex}`} className="table-cell">
                                            {item[head.key] || '-'}
                                        </td>
                                    ))}
                                </tr>
                            ))
                        ) : (
                            <tr>
                                <td colSpan={heads?.length || 1} className="empty-state">
                                    <div className="empty-content">
                                        <div className="empty-icon">📋</div>
                                        <h3>No data available</h3>
                                        <p>There are no records to display at the moment.</p>
                                    </div>
                                </td>
                            </tr>
                        )}
                    </tbody>
                </table>
            </div>
        </div>
    );
}

export default Tables
