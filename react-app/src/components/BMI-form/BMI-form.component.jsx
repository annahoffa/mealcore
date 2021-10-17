import React from 'react';

import { makeStyles, TextField, InputAdornment, Button, Typography } from '@material-ui/core';

import './BMI-form.styles.scss';


const useStyles = makeStyles((theme) => ({
  root: {
    display: 'inline-flex',
    flexDirection: 'column',
    alignItems: 'center',
    '& > *': {
      margin: theme.spacing(1),
    },
    '& .MuiTextField-root': {
      margin: theme.spacing(2),
      width: '25ch',
    },
  },
}));

const BMIForm = ({ handleChange, handleSubmit, state }) => {
  const { root } = useStyles();

  return (
    <form className={root} onSubmit={handleSubmit} autoComplete='off'>
      <Typography variant='h5' align='center'>Oblicz swój wskaźnik BMI</Typography>
      <br />
        <TextField
          required
          type='number'
          label='Waga'
          name='weight'
          inputProps={{
            min: 0,
            max: 1000,
          }}
          value={state.weight}
          onChange={handleChange}
          InputProps={{ startAdornment: <InputAdornment position='start'>kg</InputAdornment> }}
          variant='outlined'
        />
        <TextField
          required
          type='number'
          label='Wzrost'
          name='height'
          inputProps={{
            min: 0,
            max: 1000,
          }}
          value={state.height}
          onChange={handleChange}
          InputProps={{ startAdornment: <InputAdornment position='start'>cm</InputAdornment> }}
          variant='outlined'
        />
      <Button type='submit' variant='contained' color='primary'>Oblicz</Button>
    </form>
  );


};

export default BMIForm;
