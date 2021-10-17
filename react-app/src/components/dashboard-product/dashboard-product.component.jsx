import React, { useState } from 'react';
import apiCall from '../../utils/apiCall';

import { Typography } from '@material-ui/core';
import IconButton from '@material-ui/core/IconButton';
import RemoveCircleIcon from '@material-ui/icons/RemoveCircle';

import './dashboard-product.styles.scss';


const DashboardProduct = ({ product }) => {

  const deleteProductFromDashboard = () => {
    apiCall(`/api/user/removeProduct?productId=${product.id}`, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
      },
    })
    .then(() => {window.location.reload()})
    .catch(error => console.log(error));
  };

  return (
    <div className={`dashboard-product ${product.allergenWarning ? 'dashboard-product-with-warning' : ''}`}>
      <div className='product-title'>
        <Typography noWrap>
          {product.name} ({product.quantity})
        </Typography>
        <IconButton onClick={deleteProductFromDashboard} title='Usuń produkt'
          aria-label='Delete product from dashboard'>
          <RemoveCircleIcon />
        </IconButton>
      </div>
      <ul className='nutrients'>
        <li>Kcal: {product.nutrients?.kcal || '\u2014'}</li>
        <li>{product.nutrients?.proteins || '\u2014'}</li>
        <li>{product.nutrients?.carbohydrates || '\u2014'}</li>
        <li>{product.nutrients?.fat || '\u2014'}</li>
        <li>{product.nutrients?.fiber || '\u2014'}</li>
      </ul>
    </div>
  );
};

export default DashboardProduct;
