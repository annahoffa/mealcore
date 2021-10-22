import React from 'react';
import { format, compareAsc } from 'date-fns';

import Stack from '@mui/material/Stack';
import TextField from '@mui/material/TextField';
import AdapterDateFns from '@mui/lab/AdapterDateFns';
import LocalizationProvider from '@mui/lab/LocalizationProvider';
import DesktopDatePicker from '@mui/lab/DesktopDatePicker';
import { makeStyles } from '@material-ui/core';
import apiCall from '../../utils/apiCall';


const useStyles = makeStyles((theme) => ({
  calendar: {
    padding: '2em 0',
  },
}));

export default function Calendar({ setUserProducts }) {
  const { calendar } = useStyles();
  const [date, setDate] = React.useState(format(new Date(), 'yyyy-MM-dd'));

  const handleChange = (newDate) => {
    setDate(format(newDate, 'yyyy-MM-dd'));
  };

  React.useEffect(() => {
    apiCall(`/api/user/getUserProducts?date=${date}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
    })
    .then(data => setUserProducts(data))
    .catch(error => console.log(error));
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