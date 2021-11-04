import React from 'react';
import { Button, Dialog, DialogActions, DialogContent, DialogContentText, InputAdornment, TextField } from '@material-ui/core';


const DefineProductQuantity = ({ quantityProps, apiCall }) => (
  <Dialog open={quantityProps.open} onClose={quantityProps.closeQuantityDialog}>
    <DialogContent>
      <DialogContentText>
        Zmień ilość produktu
      </DialogContentText>
      <TextField
        autoFocus
        variant='standard'
        value={quantityProps.productQuantity}
        onChange={quantityProps.handleChange}
        type='number'
        inputProps={{
          min: 0,
        }}
        InputProps={{
          startAdornment: <InputAdornment position='start'>g</InputAdornment>,
        }}
        margin='dense'
        fullWidth
      />
    </DialogContent>
    <DialogActions>
      <Button onClick={quantityProps.closeQuantityDialog}>Anuluj</Button>
      <Button onClick={apiCall}>Zapisz</Button>
    </DialogActions>
  </Dialog>
);

export default DefineProductQuantity;