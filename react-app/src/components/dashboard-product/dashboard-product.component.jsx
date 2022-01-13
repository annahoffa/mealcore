import React, { useContext, useState } from 'react';
import { Link } from 'react-router-dom';
import apiCall from '../../utils/apiCall';

import { Grid, Typography } from '@material-ui/core';
import IconButton from '@material-ui/core/IconButton';
import RemoveCircleIcon from '@material-ui/icons/RemoveCircle';
import EditIcon from '@material-ui/icons/Edit';
import DefineProductQuantity from '../define-product-quantity/define-product-quantity.component';

import './dashboard-product.styles.scss';
import { DashboardProviderContext } from '../../pages/dashboard/dashboard-provider';


const DashboardProduct = ({ item: product, date }) => {
  const {
    userProductsQuery,
  } = useContext(DashboardProviderContext);

  const modifyProductQuantity = () => {
    apiCall(`/api/user/editProduct?productId=${product.id}&quantity=${productQuantity}&category=${product.category}${date ? `&date=${date}` : ''}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
    })
    .then(() => {userProductsQuery.refetch()})
    .catch(error => console.log(error));

    closeQuantityDialog();
  };

  const deleteProductFromDashboard = () => {
    apiCall(`/api/user/removeProduct?productId=${product.id}&category=${product.category}${date ? `&date=${date}` : ''}`, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
      },
    })
    .then(() => {userProductsQuery.refetch()})
    .catch(error => console.log(error));
  };

  //--- Quantity modification dialog ---

  const [productQuantity, setProductQuantity] = useState('');
  const [productCategory, setProductCategory] = useState(null);

  const handleChange = (event) => {
    setProductQuantity(event.target.value);
  };

  const [open, setOpen] = useState(false);

  const openQuantityDialog = () => {
    setOpen(true);
  };

  const closeQuantityDialog = () => {
    setOpen(false);
  };

  const quantityProps = {
    productQuantity,
    setProductQuantity,
    productCategory,
    setProductCategory,
    open,
    openQuantityDialog,
    closeQuantityDialog,
    handleChange,
  };
  //------------------------------------


  return (
    // TODO: Adjust for products which have both allergen warnings and reaction warnings

    <div className={`dashboard-product ${product.allergenWarning ? 'dashboard-product-with-warning' : ''} ${!product.allergenWarning && product.badReaction ? 'dashboard-product-with-reaction-warning' : ''}`}>
      <div className='product-title'>
        <Grid item xs={12} id={product.id}>
            <Link className='product-link' to={{ pathname: `/productinfo/${product.id}` }}>
              <div className='product-item'>
                <div className='image-container'>
                  <img src={product.images.find(obj => obj.url)?.url ?? ''} alt='Product' />
                </div>
                <Typography variant='body1'>
                  <b>{product.name}</b>
                </Typography>
              </div>
            </Link>
          </Grid>
        <div className='icons'>
          <IconButton onClick={openQuantityDialog} title='Zmień ilość'
            aria-label="Modify product's quantity">
            <EditIcon />
          </IconButton>

          {/*hidden modification dialog*/}
          <DefineProductQuantity quantityProps={quantityProps} apiCall={modifyProductQuantity} />

          <IconButton onClick={deleteProductFromDashboard} title='Usuń produkt'
            aria-label='Delete product from dashboard'>
            <RemoveCircleIcon />
          </IconButton>
        </div>
      </div>
      <ul className='nutrients'>
        <li>Kcal:<br /><b>{product.nutrients?.energyKcal || '\u2014'}</b></li>
        <li>Białko:<br /><b>{product.nutrients?.proteins || '\u2014'}</b></li>
        <li>Węglowodany:<br /><b>{product.nutrients?.carbohydrates || '\u2014'}</b></li>
        <li>Tłuszcz:<br /><b>{product.nutrients?.fat || '\u2014'}</b></li>
        <li>Błonnik:<br /><b>{product.nutrients?.fiber || '\u2014'}</b></li>
      </ul>
      <div className='quantity'>
        <span>Ilość: <b>{product.addedQuantity + ' g' || '\u003F'}</b></span>
      </div>
    </div>
  );
};

export default DashboardProduct;
