import React, {useState} from 'react';
import apiCall from '../../utils/apiCall';

import {
    Button,
    Checkbox,
    Container,
    FormControl,
    FormControlLabel,
    FormLabel,
    InputAdornment,
    InputLabel,
    makeStyles,
    OutlinedInput,
    Radio,
    RadioGroup,
    TextField,
    Typography
} from '@material-ui/core';
import IconButton from '@material-ui/core/IconButton';
import Visibility from '@material-ui/icons/Visibility';
import VisibilityOff from '@material-ui/icons/VisibilityOff';

import './sign-up-form.styles.scss';


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

const SignUpForm = ({ isCompleted }) => {
  const { root, input } = useStyles();

  const [formValues, setFormValues] = useState({
    email: '',
    password: '',
    showPassword: false,
    gender: '',
    age: '',
    weight: '',
    height: '',
    activityLevel: 'LACK_OF_ACTIVITY',
    allergens: '',
  });

  const [agreementChecked, setAgreement] = useState(false);

  const handleChange = (prop) => (event) => {
    setFormValues({ ...formValues, [prop]: event.target.value });
  };

  const handleClickShowPassword = () => {
    setFormValues({ ...formValues, showPassword: !formValues.showPassword });
  };

  const handleMouseDownPassword = (event) => {
    event.preventDefault();
  };

  const handleCheckboxChange = (event) => {
    setAgreement(event.target.checked);
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
    apiCall('/api/user/register', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        login: formValues.email,
        password: formValues.password,
        gender: formValues.gender,
        age: formValues.age,
        weight: formValues.weight,
        height: formValues.height,
        activityLevel: formValues.activityLevel,
        allergens: stringToArray(formValues.allergens),
      }),
    }).then(() => isCompleted(true))
    .catch(() => alert('Nie udało się utworzyć konta'));
  };


  return (
    <form className={root} onSubmit={handleSubmit} autoComplete='off'>
      <Typography variant='h5'>Załóż konto i dołącz do nas</Typography>

      <FormControl className={input} variant='outlined'>
        <InputLabel htmlFor='userEmail'>Email</InputLabel>
        <OutlinedInput
          required
          id='userEmail'
          type='email'
          value={formValues.email}
          onChange={handleChange('email')}
          label='Email' // for correct label length when shrank
          variant='outlined'
          fullWidth
        />
      </FormControl>

      <FormControl className={input} variant='outlined'>
        <InputLabel htmlFor='userPassword'>Hasło</InputLabel>
        <OutlinedInput
          required
          id='userPassword'
          type={formValues.showPassword ? 'text' : 'password'}
          value={formValues.password}
          inputProps={{ minLength: 8, maxLength: 20 }}
          onChange={handleChange('password')}
          label='Hasło' // for correct label length when shrank
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

      <FormControl required component='fieldset'>
        <FormLabel component='legend'>Płeć</FormLabel>
        <RadioGroup
          aria-label='gender'
          name='gender'
          value={formValues.gender}
          onChange={handleChange('gender')}
        >
          <FormControlLabel
            value='FEMALE'
            control={<Radio required/>}
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
        required
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
        required
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
        required
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

      <FormControl required component='fieldset'>
        <FormLabel component='legend'>Aktywność fizyczna</FormLabel>
        <RadioGroup
          value={formValues.activityLevel}
          onChange={handleChange('activityLevel')}
          aria-label='activityLevel'
          name='activityLevel'
        >
          <FormControlLabel
            value='LACK_OF_ACTIVITY'
            control={<Radio required/>}
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

      <FormControlLabel
        control={
          <Checkbox
            required
            checked={agreementChecked}
            onChange={handleCheckboxChange}
            inputProps={{ 'aria-label': 'primary checkbox' }}
            color='primary'
          />
        }
        label='Akceptuję Regulamin i Politykę Prywatności. *'
      />

      <Button type='submit' variant='contained' color='primary'>Zarejestruj</Button>
    </form>
  );


};

export default SignUpForm;

