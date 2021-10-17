import React, { useState } from 'react';
import apiCall from '../../utils/apiCall';

import { Button, Container, FormControl, FormControlLabel, FormLabel, InputAdornment, makeStyles, Radio, RadioGroup, TextField, Typography } from '@material-ui/core';

import './change-personal-data.styles.scss';


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

const ChangePersonalDataForm = ({ currentData }) => {
  const { root, input } = useStyles();

  const [formValues, setFormValues] = useState({
    gender: currentData.gender,
    age: currentData.age,
    weight: currentData.weight,
    height: currentData.height,
    activityLevel: currentData.activityLevel,
    allergens: currentData.allergens.join('\n'),
  });

  const handleChange = (prop) => (event) => {
    setFormValues({ ...formValues, [prop]: event.target.value });
  };

  const stringToArray = (string) => {
    if(string) {
      let array = string.split('\n');
      return array;
    }
    return [];
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log(formValues);
    apiCall('/api/user/changePersonalData', {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        gender: formValues.gender,
        age: formValues.age,
        weight: formValues.weight,
        height: formValues.height,
        activityLevel: formValues.activityLevel,
        allergens: stringToArray(formValues.allergens),
      }),
    })
    .then(() => alert('Wartości zostały zmienione pomyślnie'))
    .catch(() => alert('Nie udało się zaktualizować wartości.'));
  };


  return (
    <form className={root} onSubmit={handleSubmit} autoComplete='off'>

      <FormControl component='fieldset'>
        <FormLabel component='legend'>Płeć</FormLabel>
        <RadioGroup
          aria-label='gender'
          name='gender'
          value={formValues.gender}
          onChange={handleChange('gender')}
        >
          <FormControlLabel
            value='FEMALE'
            control={<Radio />}
            label='Kobieta'
          />
          <FormControlLabel
            value='MALE'
            control={<Radio />}
            label='Mężczyzna'
          />
        </RadioGroup>
      </FormControl>

      <TextField
        className={input}
        type='number'
        label='Waga'
        name='weight'
        inputProps={{
          min: 0,
          max: 1000,
        }}
        value={formValues.weight}
        onChange={handleChange('weight')}
        InputProps={{ startAdornment: <InputAdornment position='start'>kg</InputAdornment> }}
        variant='outlined'
      />

      <TextField
        className={input}
        type='number'
        label='Wzrost'
        name='height'
        inputProps={{
          min: 0,
          max: 300,
        }}
        value={formValues.height}
        onChange={handleChange('height')}
        InputProps={{ startAdornment: <InputAdornment position='start'>cm</InputAdornment> }}
        variant='outlined'
      />

      <TextField
        className={input}
        type='number'
        label='Wiek'
        name='age'
        inputProps={{
          min: 0,
          max: 150,
        }}
        value={formValues.age}
        onChange={handleChange('age')}
        InputProps={{ startAdornment: <InputAdornment position='start'>lata</InputAdornment> }}
        variant='outlined'
      />

      <FormControl component='fieldset'>
        <FormLabel component='legend'>Aktywność fizyczna</FormLabel>
        <RadioGroup
          value={formValues.activityLevel}
          onChange={handleChange('activityLevel')}
          aria-label='activityLevel'
          name='activityLevel'
        >
          <FormControlLabel
            value='LACK_OF_ACTIVITY'
            control={<Radio />}
            label={
              <>
                <div>Siedzący tryb życia</div>
                <small>Nie uprawiasz żadnej aktywności fizycznej.</small>
              </>
            }
          />
          <FormControlLabel
            value='LITTLE_ACTIVITY'
            control={<Radio />}
            label={
              <>
                <div>Niska aktywność</div>
                <small>Aktywność fizyczna 1-3 razy w tygodniu.</small>
              </>
            }
          />
          <FormControlLabel
            value='MODERATE_ACTIVITY'
            control={<Radio />}
            label={
              <>
                <div>Średnia aktywność</div>
                <small>Aktywność fizyczna 3-5 razy w tygodniu.</small>
              </>
            }
          />
          <FormControlLabel
            value='LOT_OF_ACTIVITY'
            control={<Radio />}
            label={
              <>
                <div>Wysoka aktywność</div>
                <small>Aktywność fizyczna 6-7 razy w tygodniu.</small>
              </>
            }
          />
          <FormControlLabel
            value='VERY_ACTIVE'
            control={<Radio />}
            label={
              <>
                <div>Intensywna aktywność</div>
                <small>Pracujesz fizycznie.</small>
              </>
            }
          />
        </RadioGroup>
      </FormControl>

      <Container className={input}>
        <Typography variant='body2' align='center'>Kolejne alergeny wpisz w nowych liniach.</Typography>
        <br />
        <TextField
          multiline
          rows={4}
          label='Twoje alergeny'
          value={formValues.allergens}
          onChange={handleChange('allergens')}
          placeholder='np. mleko'
          variant='outlined'
        />
      </Container>

      <Button type='submit' variant='outlined' color='primary'>Zaktualizuj dane</Button>
    </form>
  );


};

export default ChangePersonalDataForm;

