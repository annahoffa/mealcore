import React from 'react';
import { Button, makeStyles, Typography } from '@material-ui/core';
import { Link } from 'react-router-dom';

import './BMI-results.styles.scss';


const useStyles = makeStyles((theme) => ({
  root: {
    display: 'inline-flex',
    flexDirection: 'column',
    alignItems: 'center',
    '& > *': {
      margin: theme.spacing(2),
    },
    '& .MuiTypography-h2': {
      fontWeight: 200,
    },
  },
  resultsContainer: {
    padding: '1.8em 4em',
    border: '3px solid lightgray',
    borderRadius: '8px',
  },
}));

const BMIResults = ({ userWeight, userHeight }) => {
  const { root, resultsContainer } = useStyles();

  const calculateBMI = (weight, height) => ((weight / (height / 100) ** 2).toFixed(2));

  const assignBMIGroup = (bmi) => {
    let results;
    switch(true) {
      case (bmi < 16):
        results = 'Wygłodzenie';
        break;
      case (bmi < 17):
        results = 'Wychudzenie';
        break;
      case (bmi < 18.5):
        results = 'Niedowaga';
        break;
      case (bmi < 25):
        results = 'Waga w normie. Gratulacje!';
        break;
      case (bmi < 30):
        results = 'Nadwaga';
        break;
      case (bmi < 35):
        results = 'Otyłość I Stopnia';
        break;
      case (bmi < 40):
        results = 'Otyłość II Stopnia';
        break;
      case (40 <= bmi):
        results = 'Otyłość III Stopnia';
        break;
      default:
        results = 'Wprowadzono nieprawidłową wartość.';
    }
    return results;
  };

  const userBMI = calculateBMI(parseFloat(userWeight), parseFloat(userHeight));

  return (
    <div className={root}>
      <Typography variant='h5' align='center'>Twoje BMI:</Typography>
      <div className={resultsContainer}>
        <Typography variant='h2' align='center'>
          {userBMI}
        </Typography>
      </div>
      <Typography>
        Twój wskaźnik BMI to: <b>{assignBMIGroup(userBMI)}</b>
      </Typography>
      <Button variant='outlined' color='primary' to='/bmicalculator' component={Link}>Oblicz Ponownie</Button>
    </div>
  );
};

export default BMIResults;
