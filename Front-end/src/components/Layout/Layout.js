import React, { useContext } from 'react'
import { ToastContainer } from 'react-toastify';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'react-toastify/dist/ReactToastify.css';
import '../../styles/index.scss';
import Sidenav from '../Navbar/Sidenav';
import { AuthContext } from '../../context/Auth';

function Layout({ children }) {
    const { isLoggedIn } = useContext(AuthContext);
    return (
        <div className="app-layout">
            <Sidenav />
            <main className={isLoggedIn ? "main-content" : "main-content-fullscreen"}>
                {children}
            </main>
            <ToastContainer
                position="top-right"
                autoClose={3000}
                hideProgressBar={false}
                newestOnTop={false}
                closeOnClick
                rtl={false}
                pauseOnFocusLoss
                draggable
                pauseOnHover
                theme="light"
            />
        </div>
    )
}

export default Layout;

