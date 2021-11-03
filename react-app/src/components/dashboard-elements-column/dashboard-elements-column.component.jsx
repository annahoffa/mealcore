import React from 'react';

import './dashboard-elements-column.styles.scss';


const DashboardElementsColumn = ({items, component}) => {
  const Component = component;

  return (
    <div>
      {
        items.map((item) => (
          <Component item={item} />
        ))
      }
    </div>
  );
};

export default DashboardElementsColumn;
