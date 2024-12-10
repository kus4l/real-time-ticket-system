import axios from 'axios';
import { NavLink } from 'react-router-dom';

const Navbar = () => {

    const handleStartSimulation = async () => {
        try {
            const response = await axios.post('http://localhost:8080/execution/start');
            if (response.status !== 200) throw new Error('Failed to start simulation');
            alert('Ticketing system started successfully!');
        } catch (error) {
            alert(`Error: ${error.message}`);
        }
    };

    const handleStopSimulation = async () => {
        try {
            const response = await axios.post('http://localhost:8080/execution/stop');
            if (response.status !== 200) throw new Error('Failed to stop simulation');
            alert('Simulation stopped successfully!');
        } catch (error) {
            alert(`Error: ${error.message}`);
        }
    };

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
                    <div>
                        <button
                            className="btn btn-success me-2"
                            onClick={handleStartSimulation}
                        >
                            Start
                        </button>
                        <button
                            className="btn btn-danger"
                            onClick={handleStopSimulation}
                        >
                            Stop
                        </button>
                    </div>
                </div>
            </div>
        </nav>
    );
};

export default Navbar;
