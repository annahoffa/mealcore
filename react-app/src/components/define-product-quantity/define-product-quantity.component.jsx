import React from 'react';
import { Button, Dialog, DialogActions, DialogContent, DialogContentText, InputAdornment, TextField } from '@material-ui/core';
import Autocomplete from '@mui/material/Autocomplete';


const dishCategories = [
  { id: 0, label: 'Śniadanie', query: 'BREAKFAST' },
  { id: 1, label: 'Lunch', query: 'LUNCH' },
  { id: 2, label: 'Obiad', query: 'DINNER' },
  { id: 3, label: 'Kolacja', query: 'SUPPER' },
  { id: 4, label: 'Przekąska', query: 'SNACK' },
  { id: 9, label: 'Inne', query: 'OTHER' },
];

const DefineProductQuantity = ({ quantityProps, apiCall, showCategory }) => (
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
      {showCategory &&
        <Autocomplete
          value={dishCategories.find(dish => dish.query === quantityProps.productCategory)}
          onChange={(event, newValue) => {
            quantityProps.setProductCategory(newValue?.query);
          }}
          id='select-dish-category'
          sx={{ width: 300 }}
          options={dishCategories}
          getOptionLabel={(option) => option.label}
          noOptionsText='Nie znaleziono'
          renderInput={(params) => <TextField {...params} label='Wybierz kategorię posiłku...' />}
        />}
    </DialogContent>
    <DialogActions>
      <Button onClick={quantityProps.closeQuantityDialog}>Anuluj</Button>
      <Button onClick={apiCall}>Zapisz</Button>
    </DialogActions>
  </Dialog>
);

export default DefineProductQuantity;