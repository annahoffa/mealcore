import React, { useState } from 'react';
import apiCall from '../../utils/apiCall';
import { Button, FormControl, InputLabel, makeStyles, OutlinedInput } from '@material-ui/core';

import './change-email-form.styles.scss';


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

const ChangeEmailForm = () => {
  const { root, input } = useStyles();

  const [formValues, setFormValues] = useState({
    newEmail: '',
    password: '',
  });

  const handleChange = (prop) => (event) => {
    setFormValues({ ...formValues, [prop]: event.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    apiCall('/api/user/changeLogin', {
      method: 'PUT',
      credentials: 'include',
      headers: { 'Content-type': 'application/x-www-form-urlencoded' },
      body: `newLogin=${formValues.newEmail}&password=${formValues.password}`,
    })
    .then(() => alert('Email został zmieniony pomyślnie'))
    .catch(() => alert('Nie udało się zmienić email-a'));
  };

  return (
    <form className={root} onSubmit={handleSubmit} autoComplete='off'>

      <FormControl className={input} variant='outlined'>
        <InputLabel htmlFor='userEmail'>Nowy adres e-mail</InputLabel>
        <OutlinedInput
          required
          id='userEmail'
          type='email'
          value={formValues.newEmail}
          onChange={handleChange('newEmail')}
          label='Podaj nowy adres e-mail' // for correct label length when shrank
          variant='outlined'
          fullWidth
        />
      </FormControl>

      <FormControl className={input} variant='outlined'>
        <InputLabel htmlFor='userPassword'>Aktualne hasło</InputLabel>
        <OutlinedInput
          required
          id='userPassword'
          type='password'
          value={formValues.password}
          onChange={handleChange('password')}
          label='Aktualne hasło' // for correct label length when shrank
          variant='outlined'
          fullWidth
        />
      </FormControl>

      <Button type='submit' variant='outlined' color='primary'>Zmień e-mail</Button>
    </form>
  );
};

export default ChangeEmailForm;
