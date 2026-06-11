/**
 * STORY-103: Relationship Graph Explorer Component
 * 
 * Interactive, zoomable force-directed graph for political finance relationships.
 */
import React, { useRef, useEffect, useState } from 'react';

export default function RelationshipGraph({ politicianId, initialHops = 2 }) {
  const svgRef = useRef(null);
  const [nodes, setNodes] = useState([]);
  const [links, setLinks] = useState([]);
  const [zoomLevel, setZoomLevel] = useState(1);
  const [selectedNode, setSelectedNode] = useState(null);

  useEffect(() => {
    fetchConnections();
  }, [politicianId]);

  const fetchConnections = async () => {
    try {
      const response = await fetch(`/api/graph/${encodeURIComponent(politicianId)}`);
      const data = await response.json();
      
      if (data.nodes) setNodes(data.nodes);
      if (data.links) setLinks(data.links);
    } catch (error) {
      console.error('Failed to fetch connections:', error);
    }
  };

  const handleZoomIn = () => setZoomLevel(Math.min(zoomLevel * 1.2, 5));
  const handleZoomOut = () => setZoomLevel(Math.max(zoomLevel / 1.2, 0.5));

  return (
    <div className="graph-container">
      {/* Legend */}
      <Legend filters={[
        { key: 'all', label: 'All', active: true },
        { key: 'politician', label: 'Politicians', active: false },
        { key: 'company', label: 'Companies', active: false },
        { key: 'party', label: 'Parties', active: false }
      ]} />

      {/* Graph */}
      <svg 
        ref={svgRef}
        className="graph-svg"
        viewBox="0 0 800 600"
        style={{ cursor: 'move' }}
      >
        {links.map((link, i) => (
          <line
            key={`link-${i}`}
            x1={link.source.x}
            y1={link.source.y}
            x2={link.target.x}
            y2={link.target.y}
            stroke="#ccc"
            strokeWidth="2"
            style={{ opacity: link.opacity || 1 }}
          />
        ))}

        {nodes.map((node, i) => (
          <circle
            key={`node-${i}`}
            cx={node.x}
            cy={node.y}
            r={(node.r * zoomLevel) / 2}
            fill={getNodeTypeColor(node.entityType)}
            stroke="#000"
            strokeWidth="1"
            onMouseDown={() => handleNodeClick(i, node)}
          />
        ))}
      </svg>

      {/* Zoom Controls */}
      <div className="zoom-controls">
        <button onClick={handleZoomIn} aria-label="Zoom in">+</button>
        <span>{zoomLevel.toFixed(1)}x</span>
        <button onClick={handleZoomOut} aria-label="Zoom out">−</button>
      </div>
    </div>
  );
}

function getNodeTypeColor(entityType) {
  const colors = {
    Politician: '#1f77b4',
    Company: '#ff7f0e',
    Party: '#2ca02c'
  };
  return colors[entityType] || '#999';
}
