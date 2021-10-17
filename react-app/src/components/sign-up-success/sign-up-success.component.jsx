import React from 'react';
import { Link } from 'react-router-dom';

import { makeStyles, Typography, Button } from '@material-ui/core';
import CheckCircleIcon from '@material-ui/icons/CheckCircle';

import './sign-up-success.styles.scss';


const useStyles = makeStyles({
  root	: {
    display: 'flex',
    flexDirection: 'row',
    '& > *': {
      margin: '0.5em',
    },
  },
  fullWidth: {
    width: '15em'
  },
  icon: {
    fontSize: '72px',
  },
  contentContainer: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    '& > *': {
      margin: '0.5rem'
    }
  }
});


const SignUpSuccessMessage = () => {
  const { root, fullWidth, icon, contentContainer } = useStyles();

  return (
    <div className={contentContainer}>
      <Typography variant='h5'>Twoje konto zostało utworzone!</Typography>
      <CheckCircleIcon color='primary' className={icon} />
      <div className={root}>
        <Button fullWidth={true} className={fullWidth} variant='outlined' color='primary' to='/' component={Link}>Wróć do strony głównej</Button>
        <Button fullWidth={true} className={fullWidth} variant='contained' color='primary' to='/login' component={Link}>Zaloguj</Button>
      </div>
    </div>
  );
};

export default SignUpSuccessMessage;
