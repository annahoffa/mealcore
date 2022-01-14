import React, { useState } from 'react';
import apiCall from '../utils/apiCall';
import { Button, FormControl, InputLabel, makeStyles, OutlinedInput, Typography } from '@material-ui/core';


const useStyles = makeStyles((theme) => ({
  root: {
    display: 'inline-flex',
    flexDirection: 'column',
    alignItems: 'center',
    '& > *': {
      margin: theme.spacing(3),
    },
  },
  input: {
    width: '30ch',
    margin: theme.spacing(1),
  },
}));

const NewProductForm = () => {
  const { root, input } = useStyles();

  const [formValues, setFormValues] = useState({
    code: '',
    name: '',
    quantity: '',
  });

  const handleChange = (prop) => (event) => {
    setFormValues({ ...formValues, [prop]: event.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log(formValues);
    apiCall('/api/products/add', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        code: formValues.code,
        name: formValues.name,
        quantity: formValues.quantity,
      }),
    })
    .catch(() => alert('Nie udało się dodać produktu'));
  };

  //TODO #1: Change for a dropdown list with add-on fields
  //TODO #2: Add image handling
  return (
    <form className={root} onSubmit={handleSubmit} autoComplete='off'>
      <Typography variant='h5'>Wypełnij poniższe pola</Typography>

      <FormControl className={input} variant='outlined'>
        <InputLabel htmlFor='userEmail'>Link do zdjęcia</InputLabel>
        <OutlinedInput
          id='productCode'
          type='text'
          value={formValues.code}
          onChange={handleChange('code')}
          label='Kod produktu' // for correct label length when shrank
          variant='outlined'
          fullWidth
        />
      </FormControl>

      <FormControl className={input} variant='outlined'>
        <InputLabel htmlFor='userEmail'>Nazwa</InputLabel>
        <OutlinedInput
          required
          id='productName'
          type='text'
          value={formValues.name}
          onChange={handleChange('name')}
          label='Nazwa'
          variant='outlined'
          fullWidth
        />
      </FormControl>

      <FormControl className={input} variant='outlined'>
        <InputLabel htmlFor='userEmail'>Kaloryczność</InputLabel>
        <OutlinedInput
          id='userEmail'
          type='text'
          value={formValues.quantity}
          onChange={handleChange('quantity')}
          label='Ilość'
          variant='outlined'
          fullWidth
        />
      </FormControl>

      <Button type='submit' variant='contained' color='primary'>Dodaj produkt</Button>
    </form>
  );
};

export default NewProductForm;