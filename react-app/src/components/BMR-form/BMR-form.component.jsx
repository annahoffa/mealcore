import React from 'react';
import Radio from '@material-ui/core/Radio';
import RadioGroup from '@material-ui/core/RadioGroup';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import FormControl from '@material-ui/core/FormControl';
import FormLabel from '@material-ui/core/FormLabel';
import {
  makeStyles,
  TextField,
  InputAdornment,
  Button,
  Typography,
  Grid,
} from '@material-ui/core';

import './BMR-form.styles.scss';


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

const BMRForm = ({ handleChange, handleSubmit, state }) => {
  const { root } = useStyles();

  return (
    <form className={root} onSubmit={handleSubmit} autoComplete='off'>
      <Typography variant='h5' align='center'>
        Oblicz swój wskaźnik zapotrzebowania kalorycznego.
      </Typography>

      <Grid container spacing={2}>
        <Grid item xs={12} sm={6} className='flex-column'>
          <FormControl component='fieldset'>
            <RadioGroup
              aria-label='gender'
              name='gender'
              value={state.gender}
              onChange={handleChange}
            >
              <FormControlLabel
                value='female'
                control={<Radio />}
                label='Kobieta'
              />

              <FormControlLabel
                value='male'
                control={<Radio />}
                label='Mężczyzna'
              />
            </RadioGroup>
          </FormControl>
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
            InputProps={{
              startAdornment: (
                <InputAdornment position='start'>kg</InputAdornment>
              ),
            }}
            variant='outlined'
          />
          <TextField
            required
            type='number'
            label='Wzrost'
            name='height'
            inputProps={{
              min: 0,
              max: 300,
            }}
            value={state.height}
            onChange={handleChange}
            InputProps={{
              startAdornment: (
                <InputAdornment position='start'>cm</InputAdornment>
              ),
            }}
            variant='outlined'
          />
          <TextField
            required
            type='number'
            label='Wiek'
            name='age'
            inputProps={{
              min: 0,
              max: 150,
            }}
            value={state.age}
            onChange={handleChange}
            InputProps={{
              startAdornment: (
                <InputAdornment position='start'>lata</InputAdornment>
              ),
            }}
            variant='outlined'
          />
        </Grid>
        <Grid item xs={12} sm={6} className='flex-column'>
          <FormControl component='fieldset'>
            <FormLabel component='legend'>Aktywność fizyczna</FormLabel>
            <RadioGroup
              aria-label='activityLevel'
              name='activityLevel'
              value={state.activityLevel}
              onChange={handleChange}
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
        </Grid>
      </Grid>

      <Button type='submit' variant='contained' color='primary'>
        Oblicz
      </Button>
    </form>
  );
};

export default BMRForm;
