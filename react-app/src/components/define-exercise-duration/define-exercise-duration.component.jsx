import React from 'react';
import { Button, Dialog, DialogActions, DialogContent, DialogContentText, InputAdornment, TextField } from '@material-ui/core';


const DefineExerciseDuration = ({ durationProps, apiCall }) => (
  <Dialog open={durationProps.open} onClose={durationProps.closeQuantityDialog}>
    <DialogContent>
      <DialogContentText>
        Zmień czas trwania aktywności:
      </DialogContentText>
      <TextField
        autoFocus
        variant='standard'
        value={durationProps.exerciseDuration}
        onChange={durationProps.handleChange}
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
      <Button onClick={durationProps.closeQuantityDialog}>Anuluj</Button>
      <Button onClick={apiCall}>Zapisz</Button>
    </DialogActions>
  </Dialog>
);

export default DefineExerciseDuration;