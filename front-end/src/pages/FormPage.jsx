import { useState } from 'react';
import axios from 'axios';

const FormPage = () => {
    const [formData, setFormData] = useState({
        totalTickets: '',
        customerRate: '',
        vendorRate: '',
        maxTickets: '',
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
        if (!formData.totalTickets || isNaN(formData.totalTickets) || formData.totalTickets <= 0) {
            newErrors.totalTickets = 'Enter a valid number of total tickets.';
        }
        if (!formData.customerRate || isNaN(formData.customerRate) || formData.customerRate <= 0) {
            newErrors.customerRate = 'Enter a valid customer rate.';
        }
        if (!formData.vendorRate || isNaN(formData.vendorRate) || formData.vendorRate <= 0) {
            newErrors.vendorRate = 'Enter a valid vendor rate.';
        }
        if (!formData.maxTickets || isNaN(formData.maxTickets) || formData.maxTickets <= 0) {
            newErrors.maxTickets = 'Enter a valid max number of tickets.';
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
            const response = await axios.post('http://localhost:8080/config/submit', formData);
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
                        className={`form-control ${errors.customerRate ? 'is-invalid' : ''}`}
                        name="customerRate"
                        value={formData.customerRate}
                        onChange={handleChange}
                    />
                    {errors.customerRate && <div className="invalid-feedback">{errors.customerRate}</div>}
                </div>

                <div className="mb-3">
                    <label className="form-label">Vendor Rate</label>
                    <input
                        type="text"
                        className={`form-control ${errors.vendorRate ? 'is-invalid' : ''}`}
                        name="vendorRate"
                        value={formData.vendorRate}
                        onChange={handleChange}
                    />
                    {errors.vendorRate && <div className="invalid-feedback">{errors.vendorRate}</div>}
                </div>

                <div className="mb-3">
                    <label className="form-label">Max Tickets</label>
                    <input
                        type="text"
                        className={`form-control ${errors.maxTickets ? 'is-invalid' : ''}`}
                        name="maxTickets"
                        value={formData.maxTickets}
                        onChange={handleChange}
                    />
                    {errors.maxTickets && <div className="invalid-feedback">{errors.maxTickets}</div>}
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