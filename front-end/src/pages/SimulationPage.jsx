import { useState, useEffect } from "react";
import axios from "axios";

const SimulationPage = () => {
    const [config, setConfig] = useState(null);
    const [error, setError] = useState("");

    useEffect(() => {
        // Fetch configuration data from the API
        axios
            .get("http://localhost:8080/config/get")
            .then((response) => {
                setConfig(response.data); // Update state with the fetched data
            })
            .catch((err) => {
                setError("Error fetching configuration data.");
                console.error(err);
            });
    }, []);

    return (
        <div className="container mt-5 text-center">
            <h2>Simulation Page</h2>
            {error && <p className="text-danger mt-4">{error}</p>}
            {config ? (
                <div className="mt-4">
                    <h4>Configuration Details</h4>
                    <table className="table table-bordered table-striped mt-3">
                        <thead className="thead-dark">
                        <tr>
                            <th>Total Tickets</th>
                            <th>Max Ticket Capacity</th>
                            <th>Ticket Release Rate</th>
                            <th>Customer Retrieval Rate</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>{config.totalTickets}</td>
                            <td>{config.maxTicketCapacity}</td>
                            <td>{config.ticketReleaseRate}</td>
                            <td>{config.customerRetrievalRate}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            ) : (
                !error && <p className="mt-4">Loading configuration data...</p>
            )}
        </div>
    );
};

export default SimulationPage;
