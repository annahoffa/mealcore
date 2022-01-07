import React, { useContext } from 'react';
import { format } from 'date-fns';
import apiCall from '../utils/apiCall';
import { AuthContext } from '../appContext/providers';
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

export default function Calendar({ onDateChange }) {
  const { calendar } = useStyles();
  const [date, setDate] = React.useState(format(new Date(), 'yyyy-MM-dd'));

  const handleChange = (newDate) => {
    setDate(format(newDate, 'yyyy-MM-dd'));
    onDateChange(format(newDate, 'yyyy-MM-dd'));
  };

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