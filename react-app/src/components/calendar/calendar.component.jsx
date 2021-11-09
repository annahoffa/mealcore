import React, { useContext } from 'react';
import { format } from 'date-fns';
import apiCall from '../../utils/apiCall';
import { AuthContext } from '../../appContext/providers';

import Stack from '@mui/material/Stack';
import TextField from '@mui/material/TextField';
import AdapterDateFns from '@mui/lab/AdapterDateFns';
import LocalizationProvider from '@mui/lab/LocalizationProvider';
import DesktopDatePicker from '@mui/lab/DesktopDatePicker';
import { makeStyles } from '@material-ui/core';


const useStyles = makeStyles(() => ({
  calendar: {
    padding: '2em 0',
  },
}));

export default function Calendar({ setUserProducts, setUserExercises }) {
  const { calendar } = useStyles();
  const authContext = useContext(AuthContext);
  const [date, setDate] = React.useState(format(new Date(), 'yyyy-MM-dd'));

  const handleChange = (newDate) => {
    setDate(format(newDate, 'yyyy-MM-dd'));
  };

  React.useEffect(() => {
    if(authContext.isLoggedIn) {
      apiCall(`/api/user/getUserProducts?date=${date}`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },
      })
      .then(data => setUserProducts(data))
      .catch(error => console.log(error));

      apiCall(`/api/user/getUserExercises?date=${date}`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },
      })
      .then(data => setUserExercises(data))
      .catch(error => console.log(error));
    }
  }, [date]);

  return (
    <div className={calendar}>
      <LocalizationProvider dateAdapter={AdapterDateFns}>
        <Stack spacing={3}>
          <DesktopDatePicker
            label='Date desktop'
            inputFormat='yyyy-MM-dd'
            value={date}
            onChange={handleChange}
            renderInput={(params) => <TextField {...params} />}
          />
        </Stack>
      </LocalizationProvider>
    </div>
  );
}