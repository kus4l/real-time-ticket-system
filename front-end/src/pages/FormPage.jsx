import { useState } from 'react';
import axios from 'axios';

const FormPage = () => {
    const [formData, setFormData] = useState({
        totalTickets: '',
        customerRetrievalRate: '',
        ticketReleaseRate: '',
        maxTicketCapacity: '',
    });

    const [errors, setErrors] = useState({});
    const [isSubmitting, setIsSubmitting] = useState(false);
    const [successMessage, setSuccessMessage] = useState('');

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const validateForm = () => {
        const newErrors = {};

        // Validate totalTickets
        if (!formData.totalTickets || isNaN(formData.totalTickets) || formData.totalTickets <= 0) {
            newErrors.totalTickets = 'Enter a valid number of total tickets.';
        }

        // Validate maxTicketCapacity
        if (!formData.maxTicketCapacity || isNaN(formData.maxTicketCapacity) || formData.maxTicketCapacity <= 0) {
            newErrors.maxTicketCapacity = 'Enter a valid max number of tickets.';
        }

        // Validate relationship between totalTickets and maxTicketCapacity
        if (formData.totalTickets && formData.maxTicketCapacity && formData.totalTickets > formData.maxTicketCapacity) {
            newErrors.totalTickets = 'Total tickets cannot exceed the maximum ticket capacity.';
        }

        // Validate ticketReleaseRate
        if (!formData.ticketReleaseRate || isNaN(formData.ticketReleaseRate) || formData.ticketReleaseRate <= 0) {
            newErrors.ticketReleaseRate = 'Enter a valid vendor rate in milliseconds.';
        }

        // Validate customerRetrievalRate
        if (!formData.customerRetrievalRate || isNaN(formData.customerRetrievalRate) || formData.customerRetrievalRate <= 0) {
            newErrors.customerRetrievalRate = 'Enter a valid customer rate in milliseconds.';
        }

        // Validate ticketReleaseRate frequency
        if (formData.ticketReleaseRate && formData.ticketReleaseRate > 60000) {
            newErrors.ticketReleaseRate = 'Ticket release rate should be less than or equal to 60,000 milliseconds (1 minute).';
        }

        // Validate customerRetrievalRate frequency
        if (formData.customerRetrievalRate && formData.customerRetrievalRate > 60000) {
            newErrors.customerRetrievalRate = 'Customer retrieval rate should be less than or equal to 60,000 milliseconds (1 minute).';
        }

        return newErrors;
    };


    const handleSubmit = async (e) => {
        e.preventDefault();
        setSuccessMessage('');
        const validationErrors = validateForm();
        if (Object.keys(validationErrors).length > 0) {
            setErrors(validationErrors);
            return;
        }

        setErrors({});
        setIsSubmitting(true);

        try {
            const response = await axios.post('http://localhost:8080/config/add', formData);
            setSuccessMessage('Form submitted successfully!');
            console.log('Response:', response.data);
        } catch (error) {
            console.error('Error submitting form:', error);
            setErrors({ api: 'Failed to submit the form. Please try again later.' });
        } finally {
            setIsSubmitting(false);
        }
    };

    return (
        <div className="container mt-5">
            <h2>Ticketing Simulation Form</h2>
            <form onSubmit={handleSubmit} className="mt-4">
                <div className="mb-3">
                    <label className="form-label">Total Tickets</label>
                    <input
                        type="text"
                        className={`form-control ${errors.totalTickets ? 'is-invalid' : ''}`}
                        name="totalTickets"
                        value={formData.totalTickets}
                        onChange={handleChange}
                    />
                    {errors.totalTickets && <div className="invalid-feedback">{errors.totalTickets}</div>}
                </div>

                <div className="mb-3">
                    <label className="form-label">Customer Rate</label>
                    <input
                        type="text"
                        className={`form-control ${errors.customerRetrievalRate ? 'is-invalid' : ''}`}
                        name="customerRetrievalRate"
                        value={formData.customerRetrievalRate}
                        onChange={handleChange}
                    />
                    {errors.customerRetrievalRate && <div className="invalid-feedback">{errors.customerRetrievalRate}</div>}
                </div>

                <div className="mb-3">
                    <label className="form-label">Vendor Rate</label>
                    <input
                        type="text"
                        className={`form-control ${errors.ticketReleaseRate ? 'is-invalid' : ''}`}
                        name="ticketReleaseRate"
                        value={formData.ticketReleaseRate}
                        onChange={handleChange}
                    />
                    {errors.ticketReleaseRate && <div className="invalid-feedback">{errors.ticketReleaseRate}</div>}
                </div>

                <div className="mb-3">
                    <label className="form-label">Max Tickets</label>
                    <input
                        type="text"
                        className={`form-control ${errors.maxTicketCapacity ? 'is-invalid' : ''}`}
                        name="maxTicketCapacity"
                        value={formData.maxTicketCapacity}
                        onChange={handleChange}
                    />
                    {errors.maxTicketCapacity && <div className="invalid-feedback">{errors.maxTicketCapacity}</div>}
                </div>

                {errors.api && <div className="alert alert-danger">{errors.api}</div>}
                {successMessage && <div className="alert alert-success">{successMessage}</div>}

                <button type="submit" className="btn btn-primary" disabled={isSubmitting}>
                    {isSubmitting ? 'Submitting...' : 'Submit'}
                </button>
            </form>
        </div>
    );
};

export default FormPage;