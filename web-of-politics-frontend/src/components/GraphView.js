import React, { useState, useEffect } from 'react';

function GraphView() {
  const [nodes, setNodes] = useState([]);
  const [links, setLinks] = useState([]);

  return (
    <div style={{ height: '100vh', background: '#1a1a2e', padding: '20px' }}>
      <h1>Political Finance Network Graph</h1>
      <div id="graph-container" style={{ 
        width: '100%', 
        height: '90vh', 
        borderRadius: '8px',
        background: '#16213e'
      }} />
    </div>
  );
}

export default GraphView;
