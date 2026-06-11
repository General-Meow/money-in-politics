/**
 * Legend Component with Entity Type Filters for Relationship Graph.
 */
import React from 'react';

export default function Legend({ filters, onFilterChange }) {
  const nodeColors = {
    Politician: '#1f77b4',
    Company: '#ff7f0e',
    Party: '#2ca02c'
  };

  return (
    <div className="legend" role="group" aria-label="Graph filters">
      <span className="legend-title">Show:</span>
      
      <label className="filter-item">
        <input 
          type="checkbox" 
          checked={filters.all}
          onChange={() => onFilterChange('all', !filters.all)}
          disabled={!filters.all && !filters.company && !filters.p arty && !filters.politician} // At least one must be active
        />
        <span className={`legend-dot ${nodeColors['Politician']}`}></span>
        All Nodes
      </label>

      <label className="filter-item">
        <input 
          type="checkbox" 
          checked={filters.politician}
          onChange={() => onFilterChange('politician', !filters.politician)}
        />
        <span className={`legend-dot ${nodeColors['Politician']}`}></span>
        Politicians
      </label>

      <label className="filter-item">
        <input 
          type="checkbox" 
          checked={filters.company}
          onChange={() => onFilterChange('company', !filters.company)}
        />
        <span className={`legend-dot ${nodeColors['Company']}`}></span>
        Companies
      </label>

      <label className="filter-item">
        <input 
          type="checkbox" 
          checked={filters.party}
          onChange={() => onFilterChange('party', !filters.party)}
        />
        <span className={`legend-dot ${nodeColors['Party']}`}></span>
        Parties
      </label>

      <div className="legend-separator">Relationship Types:</div>

      <label className="filter-item">
        <input type="checkbox" checked disabled />
        <span className="legend-dot #9467bd"></span>
        Family
      </label>

      <label className="filter-item">
        <input type="checkbox" checked disabled />
        <span className="legend-dot #85c1e9"></span>
        Board Seat
      </label>

      <label className="filter-item">
        <input type="checkbox" checked disabled />
        <span className="legend-dot #ed7f32"></span>
        Donation
      </label>
    </div>
  );
}
