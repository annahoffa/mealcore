import React, { useEffect, useState } from 'react';

import DashboardProduct from '../dashboard-product/dashboard-product.component';

import './dashboard-products-list.styles.scss';


const DashboardProductsList = ({ productsList }) => {

  return (
    <div>
      {
        productsList.map((product) => (
          <DashboardProduct product={product} />
        ))
      }
    </div>
  );
};

export default DashboardProductsList;
