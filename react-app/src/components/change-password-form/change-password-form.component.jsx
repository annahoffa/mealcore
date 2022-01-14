import React, { useState } from 'react';
import apiCall from '../../utils/apiCall';
import { Button, FormControl, InputAdornment, InputLabel, makeStyles, OutlinedInput } from '@material-ui/core';
import IconButton from '@material-ui/core/IconButton';
import Visibility from '@material-ui/icons/Visibility';
import VisibilityOff from '@material-ui/icons/VisibilityOff';

import './change-password-form.styles.scss';


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

const ChangePasswordForm = () => {
  const { root, input } = useStyles();

  const [formValues, setFormValues] = useState({
    oldPassword: '',
    newPassword: '',
  });

  const handleChange = (prop) => (event) => {
    setFormValues({ ...formValues, [prop]: event.target.value });
  };

  const handleClickShowPassword = () => {
    setFormValues({ ...formValues, showPassword: !formValues.showPassword });
  };

  const handleMouseDownPassword = (event) => {
    event.preventDefault();
  };


  const handleSubmit = (e) => {
    e.preventDefault();
    apiCall('/api/user/changePassword', {
      method: 'PUT',
      credentials: 'include',
      headers: { 'Content-type': 'application/x-www-form-urlencoded' },
      body: `oldPassword=${formValues.oldPassword}&newPassword=${formValues.newPassword}`,
    })
    .then(() => alert('Hasło zostało zmienione pomyślnie'))
    .catch(() => alert('Wprowadzono złe hasło'));
  };

  return (
    <form className={root} onSubmit={handleSubmit} autoComplete='off'>

      <FormControl className={input} variant='outlined'>
        <InputLabel htmlFor='userPassword'>Stare hasło</InputLabel>
        <OutlinedInput
          required
          id='userPassword'
          type='password'
          value={formValues.oldPassword}
          onChange={handleChange('oldPassword')}
          label='Stare hasło' // for correct label length when shrank
          variant='outlined'
          fullWidth
        />
      </FormControl>

      <FormControl className={input} variant='outlined'>
        <InputLabel htmlFor='userPassword'>Nowe hasło</InputLabel>
        <OutlinedInput
          required
          id='userPassword'
          type={formValues.showPassword ? 'text' : 'password'}
          value={formValues.newPassword}
          inputProps={{ minLength: 8, maxLength: 20 }}
          onChange={handleChange('newPassword')}
          label='Nowe hasło' // for correct label length when shrank
          variant='outlined'
          fullWidth
          endAdornment={
            <InputAdornment position='end'>
              <IconButton
                aria-label='toggle password visibility'
                onClick={handleClickShowPassword}
                onMouseDown={handleMouseDownPassword}
                edge='end'
              >
                {formValues.showPassword ? <Visibility /> : <VisibilityOff />}
              </IconButton>
            </InputAdornment>
          }
        />
      </FormControl>

      <Button type='submit' variant='outlined' color='primary'>Zmień hasło</Button>
    </form>
  );
};

export default ChangePasswordForm;
