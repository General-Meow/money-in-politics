import React, { useState } from 'react';

function PoliticianSearch() {
  const [searchQuery, setSearchQuery] = useState('');
  
  return (
    <div style={{ 
      padding: '20px', 
      background: '#1a1a2e', 
      minHeight: '100vh',
      color: '#fff'
    }}>
      <h1>Politician Search</h1>
      
      <input
        type="text"
        placeholder="Search by name or constituency..."
        value={searchQuery}
        onChange={(e) => setSearchQuery(e.target.value)}
        style={{ 
          padding: '10px',
          width: '300px',
          border: 'none',
          borderRadius: '5px',
          marginRight: '20px'
        }}
      />
      
      <button 
        onClick={() => console.log('Search:', searchQuery)}
        style={{ 
          padding: '10px 20px',
          background: '#e94560',
          color: 'white',
          border: 'none',
          borderRadius: '5px'
        }}
      >
        Search
      </button>
    </div>
  );
}

export default PoliticianSearch;
