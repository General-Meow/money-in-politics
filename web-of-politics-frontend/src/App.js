import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import GraphView from './components/GraphView';
import PoliticianSearch from './components/PoliticianSearch';
import HomePage from './components/HomePage';

function App() {
  return (
    <Router>
      <div className="App">
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/politicians" element={<PoliticianSearch />} />
          <Route path="/graph" element={<GraphView />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
