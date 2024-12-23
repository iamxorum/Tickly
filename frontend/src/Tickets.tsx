import React, { useEffect, useState } from 'react';

interface Ticket {
    id: number;
    title: string;
    description: string;
    status: string;
    priority: string;
    assignee: string;
    createdAt: string;
    updatedAt: string;
}

const Tickets: React.FC = () => {
    const [tickets, setTickets] = useState<Ticket[]>([]);
    const [loading, setLoading] = useState<boolean>(true);

    useEffect(() => {
        fetch('http://localhost:8080/api/tickets')
            .then((response) => response.json())
            .then((data) => {
                setTickets(data);
                setLoading(false);
            })
            .catch((error) => {
                console.error('Error fetching tickets:', error);
                setLoading(false);
            });
    }, []);

    if (loading) {
        return <div>Loading...</div>;
    }

    return (
        <div>
            <h2>Tickets</h2>
            <table>
                <thead>
                <tr>
                    <th>Title</th>
                    <th>Description</th>
                    <th>Status</th>
                    <th>Priority</th>
                    <th>Assignee</th>
                </tr>
                </thead>
                <tbody>
                {tickets.map((ticket) => (
                    <tr key={ticket.id}>
                        <td>{ticket.title}</td>
                        <td>{ticket.description}</td>
                        <td>{ticket.status}</td>
                        <td>{ticket.priority}</td>
                        <td>{ticket.assignee}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default Tickets;
