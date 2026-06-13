# Web of Politics Frontend

## Overview

The Web of Politics frontend is a React/D3.js visualization platform for exploring UK political finance relationships. Built as a separate Docker container, it communicates with the API backend service.

### Technology Stack

- **Framework**: React 18.2+
- **Visualization**: D3.js 7.x
- **Routing**: React Router v6
- **HTTP Client**: Axios
- **Build Tool**: Create React App / Vite (to be configured)
- **Serving**: Nginx in production

---

## Features

### Planned Components

1. **HomePage** (`/`)
   - Landing page with navigation to search and graph views
   - Quick introduction to the platform

2. **PoliticianSearch** (`/politicians`)
   - Search politicians by name or constituency
   - Display politician profiles with party, jurisdiction info
   - Navigation to relationship network view

3. **GraphView** (`/graph`)
   - D3.js force-directed graph visualization
   - Interactive node exploration (click to filter/select)
   - Relationship filtering (family, board seats, etc.)
   - Zoom and pan capabilities

---

## Architecture

### Component Structure

```
src/
├── components/
│   ├── HomePage.jsx          # Landing page
│   ├── PoliticianSearch.jsx  # Search interface
│   └── GraphView.jsx         # D3.js visualization
├── App.js                    # Main app component with routing
├── index.js                  # Entry point
└── services/
    └── api.js                # Axios HTTP client to backend API
```

---

## Quick Start

### Local Development

1. **Install Dependencies**:
   ```bash
   cd web-of-politics-frontend
   npm install
   ```

2. **Start Development Server**:
   ```bash
   npm start
   ```

3. **Access Application**: http://localhost:3000

### Docker Deployment

The frontend is containerized with Nginx for production serving. See `docker-compose.yml` to deploy alongside the backend API and Neo4j database.

---

## Styling

Uses CSS-in-JS style objects for simplicity (can be upgraded to styled-components or CSS modules).

```javascript
function GraphView() {
  return (
    <div style={{ 
      height: '100vh', 
      background: '#1a1a2e', 
      padding: '20px' 
    }}>
      {/* Graph visualization */}
    </div>
  );
}
```

---

## API Integration

The frontend connects to the backend API service via REST calls:

```javascript
import axios from 'axios';

const api = axios.create({
  baseURL: '/api', // Relative to origin for Docker deployment
});
```

### Example: Fetch Politicians

```javascript
const politicians = await api.get('/politicians');
console.log('Active politicians:', politicians.data);
```

---

## Build Production

```bash
npm run build
```

This creates an optimized production build in the `build/` directory, which is then served by Nginx in Docker.

---

## Docker Health Check

The frontend container includes a health check endpoint:

- **Endpoint**: `/health`
- **Response**: `OK` (200) when healthy
- **Default**: Fails if root page returns 404

---

## Planned Enhancements

1. **D3.js Force-Directed Graph**
   - Complete the network graph visualization
   - Implement node filtering and highlighting
   - Add zoom/pan controls

2. **API Contract Definition**
   - Define complete API endpoints
   - Add request/response schemas

3. **Data Loading & Caching**
   - Implement efficient data loading strategies
   - Cache graph snapshots for performance

4. **Filtering UI Components**
   - Party filter checkboxes
   - Relationship type filters
   - Time-range filtering (historical data)

---

## License

See project root LICENSE file.
