import React from 'react';
import { Button, Dialog, DialogActions, DialogContent, DialogContentText, InputAdornment, TextField } from '@material-ui/core';
import Autocomplete from '@mui/material/Autocomplete';


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
      <Autocomplete
        value={quantityProps.productCategory}
        onChange={(event, newValue) => {
          quantityProps.setProductCategory(newValue?.query);
        }}
        id='select-dish-category'
        sx={{ width: 300 }}
        options={quantityProps.dishCategories}
        getOptionLabel={(option) => option.label}
        noOptionsText='Nie znaleziono'
        renderInput={(params) => <TextField {...params} label='Wybierz kategorię posiłku...' />}
      />
    </DialogContent>
    <DialogActions>
      <Button onClick={quantityProps.closeQuantityDialog}>Anuluj</Button>
      <Button onClick={apiCall}>Zapisz</Button>
    </DialogActions>
  </Dialog>
);

export default DefineProductQuantity;