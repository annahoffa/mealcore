import React from 'react';

import './dashboard-elements-column.styles.scss';


const DashboardElementsColumn = ({items, component, ...props}) => {
  const Component = component;

  return (
    <div>
      {
        items.map((item) => (
          <Component item={item} {...props} />
        ))
      }
    </div>
  );
};

export default DashboardElementsColumn;
