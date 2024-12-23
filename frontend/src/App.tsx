import React from 'react';
import './App.css';
import Tickets from './Tickets';

const App: React.FC = () => {
    return (
        <div className="App">
            <h1>Ticketing System</h1>
            <Tickets />
        </div>
    );
}

export default App;
