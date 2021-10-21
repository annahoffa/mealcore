import React, { useState } from 'react';
import apiCall from '../../utils/apiCall';

import { Button, Popover, Typography, TextField, Dialog, DialogTitle, DialogContent, DialogContentText, DialogActions, InputAdornment } from '@material-ui/core';
import IconButton from '@material-ui/core/IconButton';
import RemoveCircleIcon from '@material-ui/icons/RemoveCircle';
import EditIcon from '@material-ui/icons/Edit';

import './dashboard-product.styles.scss';


const DashboardProduct = ({ product }) => {

  const modifyProductQuantity = () => {
    apiCall(`/api/user/editProduct?productId=${product.id}&quantity=${productQuantity}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
    })
    .then(() => {window.location.reload()})
    .catch(error => console.log(error));

    closeQuantityDialog();
  };

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

  const [productQuantity, setProductQuantity] = useState('');

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


  return (
    <div className={`dashboard-product ${product.allergenWarning ? 'dashboard-product-with-warning' : ''}`}>
      <div className='product-title'>
        <Typography noWrap>
          {product.name} ({product.quantity})
        </Typography>
        <div className='icons'>
          <IconButton onClick={openQuantityDialog} title='Zmień ilość'
            aria-label="Modify product's quantity">
            <EditIcon />
          </IconButton>

          {/*Hidden modification dialog*/}
          <Dialog open={open} onClose={closeQuantityDialog}>
            <DialogContent>
              <DialogContentText>
                Zmień ilość produktu:
              </DialogContentText>
              <TextField
                autoFocus
                variant='standard'
                value={productQuantity}
                onChange={handleChange}
                type='number'
                inputProps={{
                  min: 0,
                }}
                InputProps={{
                  startAdornment: <InputAdornment position='start'>mg</InputAdornment>,
                }}
                margin='dense'
                fullWidth
              />
            </DialogContent>
            <DialogActions>
              <Button onClick={closeQuantityDialog}>Anuluj</Button>
              <Button onClick={modifyProductQuantity}>Zapisz</Button>
            </DialogActions>
          </Dialog>

          <IconButton onClick={deleteProductFromDashboard} title='Usuń produkt'
            aria-label='Delete product from dashboard'>
            <RemoveCircleIcon />
          </IconButton>
        </div>
      </div>
      <ul className='nutrients'>
        <li>Kcal: {product.nutrients?.kcal || '\u2014'}</li>
        <li>{product.nutrients?.proteins || '\u2014'}</li>
        <li>{product.nutrients?.carbohydrates || '\u2014'}</li>
        <li>{product.nutrients?.fat || '\u2014'}</li>
        <li>{product.nutrients?.fiber || '\u2014'}</li>
      </ul>
      <div className='quantity'>
        <span>Ilość: {product.quantity || '\u003F'}</span>
      </div>
    </div>
  );
};

export default DashboardProduct;
