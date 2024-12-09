import { NavLink } from 'react-router-dom';

const Navbar = () => {
    return (
        <nav className="navbar navbar-expand-lg navbar-light bg-primary-subtle">
            <div className="container-fluid">
                <span className="navbar-brand">Ticketing System</span>
                <div className="collapse navbar-collapse">
                    <ul className="navbar-nav me-auto mb-2 mb-lg-0">
                        <li className="nav-item">
                            <NavLink to="/" className="nav-link" activeClassName="active">
                                Form
                            </NavLink>
                        </li>
                        <li className="nav-item">
                            <NavLink to="/simulation" className="nav-link" activeClassName="active">
                                Simulation
                            </NavLink>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
    );
};

export default Navbar;
