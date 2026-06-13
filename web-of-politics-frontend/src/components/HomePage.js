import React from 'react';

function HomePage() {
  return (
    <div style={{ 
      padding: '40px', 
      background: '#1a1a2e', 
      minHeight: '100vh',
      color: '#fff'
    }}>
      <h1>The Web of Politics</h1>
      <p>Welcome to the UK political finance network visualization platform.</p>
      
      <div style={{ marginTop: '20px' }}>
        <button 
          style={{ 
            padding: '15px 30px',
            background: '#e94560',
            color: 'white',
            border: 'none',
            borderRadius: '5px',
            cursor: 'pointer'
          }}
          onClick={() => window.location.href = '/politicians'}
        >
          Search Politicians
        </button>
        
        <button 
          style={{ 
            padding: '15px 30px',
            background: '#0f3460',
            color: 'white',
            border: 'none',
            borderRadius: '5px',
            cursor: 'pointer'
          }}
          onClick={() => window.location.href = '/graph'}
        >
          View Network Graph
        </button>
      </div>
    </div>
  );
}

export default HomePage;
