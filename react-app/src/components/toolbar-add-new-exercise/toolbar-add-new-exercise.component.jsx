import React, { useEffect, useState } from 'react';
import apiCall from '../../utils/apiCall';
import IconButton from '@material-ui/core/IconButton';
import AddIcon from '@material-ui/icons/Add';
import { Button, Dialog, DialogActions, DialogContent, DialogContentText, InputAdornment, makeStyles, TextField, Tooltip } from '@material-ui/core';
import Autocomplete from '@mui/material/Autocomplete';


const useStyles = makeStyles(() => ({
    icon: {
      maxWidth: 'max-content',
      maxHeight: 'fit-content',
      backgroundColor: '#F6F6F6',
      borderRadius: '5px',
    },
    popper: {
      zIndex: 200,
    },
  }
));

const ToolbarAddNewExercise = () => {
  const { icon, popper } = useStyles();
  const [availableExercises, setAvailableExercises] = useState([]);
  const [exerciseDuration, setExerciseDuration] = useState('');
  const [value, setValue] = useState('');

  useEffect(() => {
    apiCall('/api/sport/getAll', {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
    })
    .then(data => setAvailableExercises(data))
    .catch(error => console.log(error));
  }, []);

  const addNewExercise = () => {
    apiCall(`/api/user/addExercise?sportId=${value}&duration=${exerciseDuration}`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
    })
    .then(() => {window.location.reload()})
    .catch(error => console.log(error));
  };

  const handleChange = (event) => {
    setExerciseDuration(event.target.value);
  };

  const [open, setOpen] = useState(false);

  const openNewExerciseDialog = () => {
    setOpen(true);
  };

  const closeNewExerciseDialog = () => {
    setOpen(false);
  };

  return (
    <>
      <Tooltip title='Dodaj aktywność' className={icon}>
        <IconButton onClick={openNewExerciseDialog}>
          <AddIcon />
        </IconButton>
      </Tooltip>

      {/*hidden modification dialog*/}
      <Dialog open={open} onClose={closeNewExerciseDialog}>
        <DialogContent>
          <DialogContentText>
            Dodaj nową aktywność
          </DialogContentText>
          {/* Item name is necessary as a label, item id is for the api call */}
          <Autocomplete
            value={value.id}
            onChange={(event, newValue) => {
              setValue(newValue.id);
            }}
            classes={{ popper: popper }}
            id='select-new-exercise'
            sx={{ width: 300 }}
            options={availableExercises}
            getOptionLabel={(option) => option.name}
            noOptionsText='Nie znaleziono'
            renderInput={(params) => <TextField {...params} label='Wybierz aktywność...'/>}
          />
          <TextField
            autoFocus
            variant='standard'
            value={exerciseDuration}
            onChange={handleChange}
            type='number'
            inputProps={{
              min: 0,
            }}
            InputProps={{
              startAdornment: <InputAdornment position='start'>h</InputAdornment>,
            }}
            margin='dense'
            fullWidth
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={closeNewExerciseDialog}>Anuluj</Button>
          <Button onClick={addNewExercise}>Zapisz</Button>
        </DialogActions>
      </Dialog>
    </>
  );
};

export default ToolbarAddNewExercise;