import React, { useContext, useState } from 'react';
import { useHistory } from 'react-router-dom';
import apiCall from '../../utils/apiCall';
import { AuthContext } from '../../appContext/providers';
import { Button, FormControl, InputLabel, makeStyles, OutlinedInput } from '@material-ui/core';

import './log-in-form.styles.scss';


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

const LogInForm = () => {
  let history = useHistory();
  const authContext = useContext(AuthContext);

  const [formValues, setFormValues] = useState({
    email: '',
    password: '',
  });

  const handleChange = (prop) => (event) => {
    setFormValues({ ...formValues, [prop]: event.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    apiCall(`/api/user/login?username=${formValues.email}&password=${formValues.password}`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
    })
    .then((data) => {
      authContext.logIn();
      if(data.authorities.filter(element => element.authority === 'ADMIN').length > 0) {
        authContext.logInAdmin();
      }
      history.replace({ pathname: '/' });
    })
    .catch(() => alert('Wprowadzono zły login lub hasło'));
  };

  const { root, input } = useStyles();

  return (
    <form className={root} onSubmit={handleSubmit} autoComplete='off'>

      <FormControl className={input} variant='outlined'>
        <InputLabel htmlFor='userEmail'>E-mail</InputLabel>
        <OutlinedInput
          required
          id='userEmail'
          type='email'
          value={formValues.email}
          onChange={handleChange('email')}
          label='E-mail' // for correct label length when shrank
          variant='outlined'
          fullWidth
        />
      </FormControl>

      <FormControl className={input} variant='outlined'>
        <InputLabel htmlFor='userPassword'>Hasło</InputLabel>
        <OutlinedInput
          required
          id='userPassword'
          type='password'
          value={formValues.password}
          onChange={handleChange('password')}
          label='Hasło' // for correct label length when shrank
          variant='outlined'
          fullWidth
        />
      </FormControl>

      <Button type='submit' variant='contained' color='primary'>Zaloguj</Button>
    </form>
  );


};

export default LogInForm;

