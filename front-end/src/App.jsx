import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Navbar from './components/Navbar';
import FormPage from './pages/FormPage';
import SimulationPage from './pages/SimulationPage';

const App = () => {
    return (
        <Router>
            <Navbar />
            <Routes>
                <Route path="/" element={<FormPage />} />
                <Route path="/simulation" element={<SimulationPage />} />
            </Routes>
        </Router>
    );
};

export default App;
